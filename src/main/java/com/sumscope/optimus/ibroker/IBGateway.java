package com.sumscope.optimus.ibroker;

import com.sumscope.optimus.ibroker.IBMsg.IBMessageHeader;
import com.sumscope.optimus.ibroker.IBMsg.IB_STATUS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * IBroker protocol Gateway.
 * functionality:
 * This is a gateway connecting to IBroker data provider by socket.
 * Usage:
 *   class MyListener extends IBMsgListener;
 *   		--Just one function interface: override onMsg(IBMsg) 
 * 	 IBGateway gateway = new Gateway(configfile);
 *   if( gateway.isOk())
 *   	gateway.start( new Listener());
 *   then incoming message will call on function listener.onMsg(IBMsg);
 *   
 * @author yufei.sun
 * @date April 29, 2016
 */

public final class IBGateway {
	
	private enum Params {
		IP("IP"),
		PORT("PORT"),
		PWD("PASSWORD"),
		USER("USER"),
		WRAPPER("WRAPPER");
		
		
		private String m_value;
		Params (String value) {
			m_value = value;
		}
		public String value() {
			return m_value;
		}		
	}
	
	public static class IBOptions {
		public String m_ip;
		public String m_port;
		public String m_user;
		public String m_pwd;
		public String m_wrapper="1";		
		
		public String toString() {
			StringBuffer buf =new StringBuffer();
			buf.append(m_ip).append("|");
			buf.append(m_port).append("|");
			buf.append(m_user).append("|");
			buf.append(m_pwd).append("|");			
			return buf.toString();
		}
		
		public boolean isValid() {
			boolean ret = true;
			if( m_ip == null || m_port == null || m_user == null || m_pwd==null || m_wrapper==null) {
				ret = false;
			}
			return ret;
		}
	}
	
	/**
	 * Heart beat timer
	 * @author yufei.sun
	 *
	 */
	class Beat extends TimerTask {
		@Override
		public void run() {
			try {
				if( m_ib_status.value() >= IB_STATUS.LOGIN.value() ) {
					ibSendHb();
				}
			} catch (Exception e) {
				m_log.info(e.toString());
			}			
		}
		
	}
	
	class Worker extends Thread {		
		boolean is_login = false;
		
		@Override
		public void run () {
			m_log.info("Worker thread started");
			/*
			 * send login first
			 */
			ib_send(IBMsg.IB_FUNC.LOGIN);
		
			while(true) {				
				try {
					m_buf_header.clear();
					int len = socket_receive(m_buf_header.array());
					if(ib_valid(m_buf_total.array(), len)){
						ibResponse(m_buf_total.array(), len);
					}					
				} catch (IOException e) {
					m_log.error(e.toString());
				}
			}
			
		}
	}

	/**
	 * Data Member
	 */
	private static Logger m_log = LogManager.getLogger(IBGateway.class.getName());
	private Config m_config;
	private IBOptions m_ib_options = new IBOptions();
	private final static String m_err_msg = "Wrong configuration file.";
	private IBMsg.IB_STATUS m_ib_status = IB_STATUS.UNCONNECTED;
	private IBMsgListener m_listener = null;
	
	private Socket m_socket = null;
	private InputStream m_bytes_in;
	private OutputStream m_bytes_out;
	private Worker m_worker =new Worker();
	private ByteBuffer m_buf_header =ByteBuffer.allocate(IBMsg.IB_HEADER_LENGH);
	private ByteBuffer m_buf_total =ByteBuffer.allocate(IBMsg.IB_HEADER_LENGH);	
	private Timer m_hb_timer =new Timer();
	private final IBMsgHeartBeat m_hb_msg = new IBMsgHeartBeat();
	
	private boolean m_isOk =false;
	
	public IBGateway(Config config) {
		m_log.info("IBGateway started with configuration. " + config.get_config_file());	
		m_config = config;
		boolean ret = init();
		if( !ret ) {
			m_log.error(m_err_msg);
		} else {
			m_isOk = true;
		}
	}

	/**
	 * Checking configuration
	 * @return
	 */
	private boolean init() {
		boolean ret = false;
		while(true) {
			if (!m_config.isOk()) {
				break;
			}
					
			m_ib_options.m_ip = m_config.get_property(Params.IP.value() );
			m_ib_options.m_port = m_config.get_property(Params.PORT.value() );
			m_ib_options.m_user = m_config.get_property(Params.USER.value() );
			m_ib_options.m_pwd = m_config.get_property(Params.PWD.value() );
			m_ib_options.m_wrapper = m_config.get_property(Params.WRAPPER.value() );
			
			
			if(!m_ib_options.isValid()) {
				m_log.error("IBroker configuration file error");
				break;
			}
			
			
			m_hb_msg.set_ib_options(m_ib_options);
			m_hb_msg.encode();
			
			ret = true;
			break;
		}

		return ret;
	}

	public void start(IBMsgListener listener) throws Exception {
		m_listener = listener;
		socket_open();		
	}
	
	public void stop() {
		//stop worker thread
		m_worker.interrupt();
	
		try {
			if( m_ib_status.value() > IB_STATUS.LOGIN.value()) {
				//stop heartbeat timer 
				m_hb_timer.cancel();			
				//logout and close socket
				ib_send(IBMsg.IB_FUNC.LOGOUT);
				socket_close();
			}
		} catch (IOException e) {
			m_log.error(e.toString());
		}
		m_ib_status = IB_STATUS.UNCONNECTED;
	}

	public boolean isOk() {
		return m_isOk;
	}
	
	
	 private String socket_info() {
		 
		String info = "Socket status [closed:" + m_socket.isClosed() +
				 "| connected:" + m_socket.isConnected() +
				 "| inputStreamDown:" + m_socket.isInputShutdown() +
				 "| outputStreamDown:" + m_socket.isOutputShutdown() +"]";
		
		return info;
	 }
	 
	 private void socket_open() throws IOException {		
		 m_log.info("Openning socket on:" + m_ib_options.m_ip + "  " + m_ib_options.m_port);
			
		//start worker thread
		 try {
				m_socket = new Socket(m_ib_options.m_ip, Integer.valueOf(m_ib_options.m_port));		
				m_bytes_in = m_socket.getInputStream(); 
				m_bytes_out = m_socket.getOutputStream();				
				m_worker.start();			
				m_ib_status = IB_STATUS.CONNECTED;
		} catch (IOException e) {
				m_log.info(e.toString());
		}
		m_log.info(socket_info());
	 }
	
	 private void socket_close() throws IOException {		
				
		 if( !m_socket.isInputShutdown() ) {
			 m_bytes_in.close();
		 }
		 
		 if( !m_socket.isOutputShutdown() ) {
			 m_bytes_out.close();
		 }
		 
		 m_socket.close();
		 m_ib_status = IB_STATUS.UNCONNECTED;
		 m_log.info("socket closed"); 
	}
	 
	 private int socket_receive(byte[] header_bytes) throws IOException {
		 //read header first 
		 int read_bytes=0;
		 Arrays.fill(header_bytes, (byte)0x00);
		 
		 read_bytes = m_bytes_in.read(header_bytes, read_bytes, IBMsg.IB_HEADER_LENGH);			
		 	 		 
		 IBMessageHeader header = IBMessageHeader.instance(header_bytes);
		 int body_size = header.body_size();
		 int total_size = body_size +IBMsg.IB_HEADER_LENGH;
		
		 if( total_size > IBMsg.MAX_MSG_SIZE) {
			 m_log.error("total_size " + total_size +" exceeds expected max size "+ IBMsg.MAX_MSG_SIZE);
			 return -1;
		 }
		
		 if ( m_buf_total.capacity() < total_size ) {
			 m_buf_total = null;
			 m_buf_total = ByteBuffer.allocate(total_size);
			 m_log.info("expanding buffer size to " + total_size + " bytes");
		 }
		 
		 m_buf_total.clear();
		 m_buf_total.put(header_bytes,0, IBMsg.IB_HEADER_LENGH);
		 
		 while(read_bytes < total_size) {			
			 read_bytes+=m_bytes_in.read(m_buf_total.array(), read_bytes, total_size-read_bytes);				
		 }
			 
		/*
		 * skip heart beat response
		 */
		if(total_size == 45) {
			if( m_buf_total.array()[40] == (byte)0x03 && m_buf_total.array()[41] == (byte)0xEB)  {
				return -1;
			}
		}
		
		if( m_log.isInfoEnabled()) {
			m_log.info("received " +read_bytes+ " bytes:" + Const.CRLF+IBMsg.bytesToString(m_buf_total.array(), 0,total_size));
		}
		return total_size;
	 }
	 
	 private void socket_send(IBMsg.IB_FUNC func, byte[] bytes, int off, int len) throws IOException {		 
		 if( len <= 0 || bytes.length < (off +len) ) {
			 return;
		 }
		 
		 m_bytes_out.write(bytes, off, len);
		 m_bytes_out.flush();
		 
		 if(func != IBMsg.IB_FUNC.HEARTBEAT) {
			 if( m_log.isInfoEnabled()) {
				 m_log.info("send " + func +" " +len+ " bytes:" +  Const.CRLF+IBMsg.bytesToString(bytes, 0, len));
			 }			 
		 }
	 }	
	 
	 void ibSendHb () {
		try {
			socket_send(IBMsg.IB_FUNC.HEARTBEAT, m_hb_msg.bytes(), 0, m_hb_msg.length());
		} catch (IOException e) {
			m_log.error("writing socket failed. " + e.toString());
		} 
	 }

	
	void ib_send (IBMsg.IB_FUNC func) {		
		IBMsg msg= null;
		
		switch (func) {
		case LOGIN:
			IBMsgLogin login_msg =new IBMsgLogin();		
			login_msg.setLoginName(this.m_ib_options.m_user);
			login_msg.setLoginPwd(this.m_ib_options.m_pwd);
			msg = login_msg;
			break;
		case LOGOUT:
			IBMsgLogout logout_msg =new IBMsgLogout();				
			msg = logout_msg;
			break;	
		case SUBSCRIBE_TRADE:
			IBMsgSubscribeTrade susbscribe_trade_msg =new IBMsgSubscribeTrade();			
			msg = susbscribe_trade_msg;
			break;
		case SUBSCRIBE_QUOTE:
			IBMsgSubscribeQuote susbscribe_quote_msg =new IBMsgSubscribeQuote();			
			msg = susbscribe_quote_msg;
			break;
		case UNSUBSCRIBE_ALL:
			IBMsgUnsubscribeAll unsusbscribe_all_msg =new IBMsgUnsubscribeAll();			
			msg = unsusbscribe_all_msg;
			break;
		default:			
			break;
		}
		
		if ( msg != null) {		 
			try {
				msg.set_ib_options(m_ib_options);
				msg.encode();		
				if( m_log.isInfoEnabled()) {
					 m_log.info(msg.toString());		
				}
				socket_send(func, msg.bytes(), 0, msg.length());
			} catch (IOException e) {
				m_log.error("writing socket failed. " + e.toString());
			} catch (Exception e) {
				m_log.error(func.value() + " msg sending failed. " + e.toString());
			}
		}
	}
	
	boolean ib_valid(byte[] bytes, int len) {
		boolean ret = true;
		while(true) {
			if(len <0) {
				ret = false;
				break;
			}			
			break;
		}		
		return ret;
	}
	
	void ibResponse(byte[] bytes, int len) {
		
		IBMsgResponse rsp = new IBMsgResponse(len);
		try {
			rsp.decode(bytes, 0, len);
		} catch (Exception e) {
			e.printStackTrace();
			m_log.error("decode error." + e.toString());
			return;
		}

		m_log.info("Recevied command [" + rsp.getCommandId() + "] from iBroker");

		while (true) {
			// login successfully
			if (rsp.getCommandId() == IBMsg.IB_FUNC.LOGIN.value()) {
				if(rsp.getIsOk()) {
					m_ib_status = IB_STATUS.LOGIN;
					// start heart beat timer
					m_hb_timer.schedule(new Beat(), 0, IBMsg.HB_INTERVAL);
					m_log.info("Schedule heartbeat timer on inteval of " + IBMsg.HB_INTERVAL);

					// subscribe trade data
					ib_send(IBMsg.IB_FUNC.SUBSCRIBE_TRADE);
					ib_send(IBMsg.IB_FUNC.SUBSCRIBE_QUOTE);
				}
				m_log.info(rsp.toString());
				break;
			}

			// subscribe trade successfully
			if (rsp.getCommandId() == IBMsg.IB_FUNC.SUBSCRIBE_TRADE.value()) {
				m_ib_status = IB_STATUS.SUBSCRIBED;
				m_log.info(rsp.toString());	
				m_listener.onMsg(rsp);
			
				break;
			}

			// subscribe trade successfully
			if (rsp.getCommandId() == IBMsg.IB_FUNC.SUBSCRIBE_QUOTE.value()) {
				m_ib_status = IB_STATUS.SUBSCRIBED;
				m_log.info(rsp.toString());
				m_listener.onMsg(rsp);
				break;
			}
			
			// subscribe trade successfully
			if (rsp.getCommandId() == IBMsg.IB_FUNC.PUBLISH.value()) {
				m_log.info(rsp.toString());
				m_listener.onMsg(rsp);
				break;
			}
			break;
		}
	}

	public static void main(String[] args) {
		Config config = new Config("icap.properties",true);

		IBGateway ibGateway = new IBGateway(config);
		ibGateway.ib_send(IBMsg.IB_FUNC.LOGIN);
	}
}
