package com.sumscope.optimus.ibroker;

import java.util.LinkedList;
import java.util.List;

public class IBMsgResponse extends IBMsg{
	
	enum FIELD_NAME {
		COMMANDID(0),
		ISOK (1),
		MSG(2),
		SUBSCRIBEID(3),
		DATA(4),			//data in query
		DATA_PUBLISH(1);	//data in publish
		
		private int m_value;
		FIELD_NAME (int value) {
			m_value = value;
		}
		int value() {
			return m_value;
		}
	}
	
	enum TRADE_FIELD {
		DoneID(0),
		TradeId (1),
		YieldRate(2),
		IssueDatetime(3),
		Status(4),
		Disable(5),
		Description(6),
		WorkBench(7),
		SysType(8),
		ItemType(9);

		private int m_value;
		TRADE_FIELD (int value) {
			m_value = value;
		}
		
		int value() {
			return m_value;
		}
	}
	
	enum QUOTE_FIELD {
		BestID(0),
		TradeId (1),
		IssueDatetime(2),
		Disable(3),
		WorkBench(4),
		YieldRatebid(5),
		YieldRateofr(6),
		Volumebid(7),
		Volumeofr(8),
		Description(9),
		SysType(10),
		ItemType(11);

		private int m_value;
		QUOTE_FIELD (int value) {
			m_value = value;
		}
		
		int value() {
			return m_value;
		}
	}
	
	public static class Trade{
		public Integer m_done_id;
		public String m_trade_id;
		public String m_yield_rate;
		public Long m_issure_datetime;
		public Byte m_status;
		public Byte m_disable;
		public String m_description;
		public Byte m_workbench;
		public Byte m_sys_type;
		public Short m_item_type;	
		
		Trade(IBRow row) {
			m_done_id = (Integer)(row.m_body.get(TRADE_FIELD.DoneID.value()).toObject());
			m_trade_id = (String)(row.m_body.get(TRADE_FIELD.TradeId.value()).toObject());
			m_yield_rate = (String)(row.m_body.get(TRADE_FIELD.YieldRate.value()).toObject());
			m_issure_datetime = (Long)(row.m_body.get(TRADE_FIELD.IssueDatetime.value()).toObject());
			m_status = (Byte)(row.m_body.get(TRADE_FIELD.Status.value()).toObject());
			m_disable = (Byte)(row.m_body.get(TRADE_FIELD.Disable.value()).toObject());
			m_description = (String)(row.m_body.get(TRADE_FIELD.Description.value()).toObject());
			m_workbench = (Byte)(row.m_body.get(TRADE_FIELD.WorkBench.value()).toObject());
			m_sys_type = (Byte)(row.m_body.get(TRADE_FIELD.SysType.value()).toObject());
			m_item_type = (Short)(row.m_body.get(TRADE_FIELD.ItemType.value()).toObject());	
		}
		
		public String toString() {
			StringBuilder buf = new StringBuilder();

			buf.append(m_done_id).append(",");
			buf.append(m_trade_id).append(",");
			buf.append(m_yield_rate).append(",");
			buf.append(m_issure_datetime).append(",");
			buf.append(m_status).append(",");
			buf.append(m_disable).append(",");
			buf.append(m_description).append(",");
			buf.append(m_workbench).append(",");
			buf.append(m_sys_type).append(",");
			buf.append(m_item_type).append("");
			return buf.toString();
		}
	}
	
	public static class Quote{
		public Integer m_best_id;
		public String m_trade_id;
		public Long m_issure_datetime;
		public Byte m_disable;
		public Byte m_workbench;
		public String m_yield_rate_bid;
		public String m_yield_rate_ofr;
		public String m_volume_bid;
		public String m_volume_ofr;
		public String m_description;
		public Byte m_sys_type;
		public Short m_item_type;	
		
		Quote(IBRow row) {
			m_best_id = (Integer)(row.m_body.get(QUOTE_FIELD.BestID.value()).toObject());
			m_trade_id = (String)(row.m_body.get(QUOTE_FIELD.TradeId.value()).toObject());
			m_issure_datetime = (Long)(row.m_body.get(QUOTE_FIELD.IssueDatetime.value()).toObject());
			m_disable = (Byte)(row.m_body.get(QUOTE_FIELD.Disable.value()).toObject());
			m_workbench = (Byte)(row.m_body.get(QUOTE_FIELD.WorkBench.value()).toObject());
			m_yield_rate_bid = (String)(row.m_body.get(QUOTE_FIELD.YieldRatebid.value()).toObject());
			m_yield_rate_ofr = (String)(row.m_body.get(QUOTE_FIELD.YieldRateofr.value()).toObject());
			m_volume_bid = (String)(row.m_body.get(QUOTE_FIELD.Volumebid.value()).toObject());
			m_volume_ofr = (String)(row.m_body.get(QUOTE_FIELD.Volumeofr.value()).toObject());
			m_description = (String)(row.m_body.get(QUOTE_FIELD.Description.value()).toObject());
			m_sys_type = (Byte)(row.m_body.get(QUOTE_FIELD.SysType.value()).toObject());
			m_item_type = (Short)(row.m_body.get(QUOTE_FIELD.ItemType.value()).toObject());	
		}
		
		public String toString() {
			StringBuffer buf = new StringBuffer();
			
			buf.append(m_best_id).append(",");
			buf.append(m_trade_id).append(",");
			buf.append(m_issure_datetime).append(",");
			buf.append(m_disable).append(",");
			buf.append(m_workbench).append(",");
			buf.append(m_yield_rate_bid).append(",");
			buf.append(m_yield_rate_ofr).append(",");
			buf.append(m_volume_bid).append(",");
			buf.append(m_volume_ofr).append(",");
			buf.append(m_description).append("");
			buf.append(m_sys_type).append(",");
			buf.append(m_item_type).append("");
			return buf.toString();
		}
	}
	
	public IBMsgResponse(int size) {
			super(size);		
	}
		

	@Override
	public void decode(byte[] bytes, int pos, int len) {		
		super.decode(bytes, pos, len);
	}
	
	private Object getField(int index ) {
		return getFieldValue(index) ;
	}
	
	public int getCommandId() {
		int ret = -1 ; 
		while (true ) {
			Object obj = getField(FIELD_NAME.COMMANDID.value());
			if( obj != null) {
				INT32 value = (INT32)obj;
				ret = value.get();
			} 
			break;
		}
		return ret;
	}
	
	public boolean getIsOk() {
		boolean ret = false ; 
		while (true ) {
			Object obj = getField(FIELD_NAME.ISOK.value());
			if( obj != null) {
				BOOL value = (BOOL)obj;
				if ( value.get() == (byte)0x01) {
					ret = true;
				}
			} 
			break;
		}
		return ret;
	}
	
	public String getMsg() {
		String ret = "" ; 
		while (true ) {
			Object obj = getField(FIELD_NAME.MSG.value());
			if( obj != null) {
				STRING value = (STRING)obj;
				ret = value.get();
			} 
			break;
		}
		return ret;
	}
	
	protected IBMsgResponse getData() {
		IBMsgResponse ret  =null; 
		while (true ) {
			Object obj = getField(FIELD_NAME.DATA.value());
			if( obj == null) {
				obj = getField(FIELD_NAME.DATA_PUBLISH.value());
			}
			if( obj != null) {
				WINDSTREAM value = (WINDSTREAM)obj;
				ret = value.get();
			} 
			break;
		}
		return ret;
	}
	
	public boolean isTrade() {
		boolean ret = false;
		while(true) {
			int cmd_id = getCommandId();
			if (cmd_id != IBMsg.IB_FUNC.PUBLISH.value() &&
				cmd_id != IBMsg.IB_FUNC.SUBSCRIBE_TRADE.value()) {
				break;
			}
			
			IBMsgResponse inner_rsp = getData();
			if( inner_rsp == null) {
				break;
			}
			
			String done_id = (String)inner_rsp.getFieldName(TRADE_FIELD.DoneID.value());
			if( done_id ==null) {
				break;
			}
			
			if(done_id.compareToIgnoreCase("DoneID") ==0) {
				ret=true;
			}
			break;
		}
		return ret;
	}
	
	public boolean isQuote() {
		boolean ret = false;
		while(true) {
			int cmd_id = getCommandId();
			if (cmd_id != IBMsg.IB_FUNC.PUBLISH.value() &&
				cmd_id != IBMsg.IB_FUNC.SUBSCRIBE_QUOTE.value()) {
				break;
			}
		
			IBMsgResponse inner_rsp = getData();
			if( inner_rsp == null) {
				break;
			}
		
			String done_id = (String)inner_rsp.getFieldName(TRADE_FIELD.DoneID.value());
			if( done_id ==null) {
				break;
			}
		
			if(done_id.compareToIgnoreCase("BestID") ==0) {
				ret=true;
			}
			break;
		}
		return ret;
	}
	
	/**
	 * Wrapper decoded message for java object
	 */
	
	public List<Quote> getQuotes() {
		List<Quote> quotes = new LinkedList<> ();
		IBMsgResponse inner_rsp = getData();
		int rowcount = inner_rsp.get_row_count();
		for( int i=0; i< rowcount ; i++) {
			Quote quote = new Quote(inner_rsp.m_rows.m_body.get(i));
			quotes.add(quote);
		}
		return quotes;
	}
	
	public List<Trade> getTrades() {
		List<Trade> trades = new LinkedList<> ();
		IBMsgResponse inner_rsp = getData();
		int rowcount = inner_rsp.get_row_count();
		for( int i=0; i< rowcount ; i++) {
			Trade trade = new Trade(inner_rsp.m_rows.m_body.get(i));
			trades.add(trade);
		}
		return trades;
	}
}
