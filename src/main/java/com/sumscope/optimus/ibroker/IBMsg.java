package com.sumscope.optimus.ibroker;

import com.sumscope.optimus.ibroker.IBGateway.IBOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;


public abstract class IBMsg {

	//constant data
	public final static short IB_VERSION = 2;
	public final static byte IS_COMPRESSED = 0x00;
	public final static int MAX_MSG_SIZE = 1024*1000*20; //20M
	public final static ByteOrder BYTE_ORDER= ByteOrder.BIG_ENDIAN;
	public final static String STR_CODE="UTF-8";
	public final static byte SUB_METHOD = 0x01; 	//0--query, 1--query and subscribe 2--subscribe
	public final static short  SUB_TYPE_TRADE = 12;
	public final static short  SUB_TYPE_QUOTE = 13;
	public final static long   HB_INTERVAL=1000;	//1 second
	public final static int  IB_HEADER_LENGH=7;
	public final static int  IB_SMALL_RESP_LENGH= 64;
	static Logger m_log = LogManager.getLogger(IBMsg.class.getName());
	
	protected IBOptions m_ib_options = new IBOptions();
	
	public enum IB_STATUS {
		UNCONNECTED(0),
		CONNECTED(1),
		LOGIN(2),
		SUBSCRIBED(3);
		
		private int m_value;
		private IB_STATUS (int value) {
			m_value = value;
		}
		
		public int value() {
			return m_value;
		}		
	}
	
	public enum IB_FUNC {
		LOGIN(1001),
		LOGOUT(1002),
		HEARTBEAT(1003),
		CLOSE(1004),
		SUBSCRIBE_TRADE(1005),
		SUBSCRIBE_QUOTE(1005),
		UNSUBSCRIBE(1006),
		UNSUBSCRIBE_ALL(1007),
		PUBLISH(1008),
		UNKNOWN(-1);
		
		private int m_value;
		IB_FUNC(int value) {
			m_value = value;
		}
		
		public int value() {
			return m_value;
		}
	}
	
	interface IBDataType {
		int size();
		void encode(ByteBuffer buf);
		void decode(ByteBuffer buf);
		String toString();
		Object toObject();
	}
	
	/*
	 * Data type defination
	 */
	public static class BYTE implements IBDataType {
		byte m_value;
		
		BYTE(){};
		
		BYTE( byte value) {
			m_value = value;
		}
		

		public static byte encodeType() {
			return 1;
		}
		
		@Override
		public int size() {
			return 1;
		}	
		
		public byte get() {
			return m_value;
		}
		
		@Override
		public void encode(ByteBuffer buf){	
			buf.put(m_value);		
		}
		
		static BYTE valueOf(ByteBuffer buf) {
			BYTE ins = new BYTE();
			ins.decode(buf);
			return ins;
		}
		@Override
		public void decode(ByteBuffer buf){			
			m_value = buf.get();	
		}	
		
		@Override
		public String toString(){
			return String.valueOf(m_value);
		}
		
		@Override
		public Object toObject() {
			return m_value;
		}
	}
	
	public static class BOOL extends BYTE {

		private BOOL() {
			super();
		}
		
		BOOL(byte value) {
			super(value);
		}
		
		public static byte encodeType() {
			return 0;
		}		
		
		static BOOL valueOf(ByteBuffer buf) {
			BOOL ins = new BOOL();
			ins.decode(buf);
			return ins;
		}		
		
		@Override
		public Object toObject() {
			if( m_value == (byte)0x01) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	
	public static class INT16 implements IBDataType {
		private short m_value;		
		
		private INT16(){};
		
		INT16(int value) {
			m_value = (short)value; 
		}
		
		INT16(short value) {
			m_value = (Short)value; 
		}
		
		public static byte encodeType() {
			return 2;
		}
		
		@Override
		public int size() {
			return 2;
		}	
		
		public short get() {
			return m_value;
		}
		
		@Override
		public void encode(ByteBuffer buf){
			buf.putShort(m_value);	
		}
		
		static INT16 valueOf(ByteBuffer buf) {
			INT16 ins = new INT16();
			ins.decode(buf);
			return ins;
		}
		
		@Override
		public void decode(ByteBuffer buf){			
			m_value = buf.getShort();	
		}	
		
		@Override
		public String toString(){
			return String.valueOf(m_value);
		}
		
		public void add (int i) {
			m_value+=(short) i;
		}
		
		@Override
		public Object toObject() {
			return new Short(m_value);
		}
	}
	
	public static class INT32 implements IBDataType {
		private int m_value;
		private INT32(){};
		INT32(int value) {
			m_value = value;
		}
		
		public static byte encodeType() {
			return 3;
		}
		
		@Override
		public int size() {
			return 4;
		}	
		
		public int get() {
			return m_value;
		}
		@Override
		public void encode(ByteBuffer buf){
			buf.putInt(m_value);
		}
		
		static INT32 valueOf(ByteBuffer buf) {
			INT32 ins = new INT32();
			ins.decode(buf);
			return ins;
		}
		
		@Override
		public void decode(ByteBuffer buf){			
			m_value = buf.getInt(); 	
		}	
		
		public void add (int i) {
			m_value+=(short) i;
		}
		
		@Override
		public String toString(){
			return String.valueOf(m_value);
		}
		
		@Override
		public Object toObject() {
			return m_value;
		}
	}
	
	public static class INT64 implements IBDataType {
		private long m_value;
		private INT64(){};
		INT64(long value) {
			m_value = value;
		}
		
		public static byte encodeType() {
			return 4;
		}
		
		@Override
		public int size() {
			return 8;
		}	
		
		public long get() {
			return m_value;
		}
		@Override
		public void encode(ByteBuffer buf){
			buf.putLong(m_value);
		}
		
		static INT64 valueOf(ByteBuffer buf) {
			INT64 ins = new INT64();
			ins.decode(buf);
			return ins;
		}
		
		@Override
		public void decode(ByteBuffer buf){			
			m_value = buf.getLong(); 	
		}	
		
		public void add (int i) {
			m_value+=(short) i;
		}
		
		@Override
		public String toString(){
			return String.valueOf(m_value);
		}
		
		@Override
		public Object toObject() {
			return new Long(m_value);
		}
	}
	
	public static class FLOAT implements IBDataType {
		private float m_value;
		private FLOAT(){};
		FLOAT(float value) {
			m_value = value;
		}
		
		public static byte encodeType() {
			return 5;
		}
		
		@Override
		public int size() {
			return 4;
		}	
		
		public float get() {
			return m_value;
		}
		@Override
		public void encode(ByteBuffer buf){
			buf.putFloat(m_value);
		}
		
		static FLOAT valueOf(ByteBuffer buf) {
			FLOAT ins = new FLOAT();
			ins.decode(buf);
			return ins;
		}
		
		@Override
		public void decode(ByteBuffer buf){			
			m_value = buf.getFloat(); 	
		}	
		
		@Override
		public String toString(){
			return String.valueOf(m_value);
		}
		
		@Override
		public Object toObject() {
			return new Float(m_value);
		}
	}
	
	public static class DOUBLE implements IBDataType {
		private double m_value;
		private DOUBLE(){};
		DOUBLE(float value) {
			m_value = value;
		}
		
		public static byte encodeType() {
			return 6;
		}
		
		@Override
		public int size() {
			return 8;
		}	
		
		public double get() {
			return m_value;
		}
		@Override
		public void encode(ByteBuffer buf){
			buf.putDouble(m_value);
		}
		
		static DOUBLE valueOf(ByteBuffer buf) {
			DOUBLE ins = new DOUBLE();
			ins.decode(buf);
			return ins;
		}
		
		@Override
		public void decode(ByteBuffer buf){			
			m_value = buf.getDouble(); 	
		}	
		
		@Override
		public String toString(){
			return String.valueOf(m_value);
		}
		
		@Override
		public Object toObject() {
			return new Double(m_value);
		}
	}
	
	public static class WINDSTREAM implements IBDataType {
		private IBMsgResponse m_value = null;
		private WINDSTREAM(){};
		
		
		public static byte encodeType() {
			return 11;
		}
		
		@Override
		public int size() {
			return m_value.length();
		}	
		
		public IBMsgResponse get() {
			return m_value;
		}
		
		@Override
		public void encode(ByteBuffer buf){
			m_log.error("Not supported operation");
		}
		
		static WINDSTREAM valueOf(ByteBuffer buf) {
			WINDSTREAM ins = new WINDSTREAM();
			ins.decode(buf);
			return ins;
		}
		
		@Override
		public void decode(ByteBuffer buf){		
			int pos = buf.position();
			int len = buf.capacity() - pos;
			m_value = new IBMsgResponse(buf.capacity());
			
			m_value.decode(buf.array(), pos, len);			
		
			if( m_value.length()+pos > buf.capacity()) {	//because of compressed data
				buf.position(buf.capacity() -1);
			} else {
				buf.position(pos+m_value.length());
			}
			
		}		
		
		@Override
		public String toString(){
			return m_value.toString();
		}
		
		@Override
		public Object toObject() {
			return null;
		}
	}
	
	public static class STRING implements IBDataType {
		private String m_value;
		private short m_len;
		
		private STRING(){};
		
		STRING(String value) {
			m_value = value;
			m_len = (short)m_value.length();
		}
		
		public static byte encodeType() {
			return 0x07;
		}
		
		@Override
		public int size() {
			return 2 + m_value.length();
		}	
		
		public String get() {
			return m_value;
		}
		
		@Override
		public void encode(ByteBuffer buf) {
			buf.putShort(m_len);
			buf.put(m_value.getBytes());
		}
		
		static STRING valueOf(ByteBuffer buf) {
			STRING ins = new STRING();
			ins.decode(buf);
			return ins;
		}
		
		@Override
		public void decode(ByteBuffer buf){		
			m_len = buf.getShort();
			int pos = buf.position();
			try {
				m_value = new String(buf.array(), pos, m_len, STR_CODE);				
			} catch (UnsupportedEncodingException e) {
				
			}
			buf.position(pos+m_len);
		}	
		
		@Override
		public String toString(){
			return m_value;
		}
		
		@Override
		public Object toObject() {
			return new String(m_value);
		}
	}
	
	
	
	/*
	 * Message Header
	 */
	public static class IBMessageHeader {
		INT16 m_version = new INT16(IB_VERSION);
		BOOL m_is_compressed = new BOOL(IS_COMPRESSED);
		INT32 m_size =new INT32(0);		//message total size
		
		public IBMessageHeader() {
			m_version = new INT16(2);
			m_is_compressed =new BOOL((byte)0);
		}
		
		public int size() {
			return m_version.size() + m_is_compressed.size() + m_size.size();
		}
		
		void encode(ByteBuffer buf) {
			m_version.encode(buf);
			m_is_compressed.encode(buf);
			m_size.encode(buf);
		}
		
		public void decode(ByteBuffer buf){			
			m_version = INT16.valueOf(buf);
			m_is_compressed= BOOL.valueOf(buf);
			m_size = INT32.valueOf(buf);
		}	
		
		@Override
		public String toString() {
			StringBuffer buf =new StringBuffer();
			buf.append(Const.CRLF + "VERSION=" + m_version.get() + Const.CRLF);
			buf.append("IS_COMPRESSED=" + m_is_compressed.get() + Const.CRLF);
			buf.append("SIZE=" + m_size.get() + Const.CRLF);
			return buf.toString();
		}
		
		public boolean is_compressed () {
			boolean ret = false;
			if( m_is_compressed.get() == 0x01) {
				ret = true;
			}
			return ret;
		}
		
		public static IBMessageHeader instance(byte[] bytes) {
			ByteBuffer buf =ByteBuffer.wrap(bytes);
			buf.position(0);
			IBMessageHeader instance = new IBMessageHeader();
			instance.decode(buf);
			return instance;
		}
		
		public int body_size() {
			return m_size.get();
		}
	}	
	
	/*
	 * IB Field defination
	 */
	public static class IBField {
		STRING m_field_name;
		BYTE m_field_type;

		IBField () {};
		
		IBField(String name, byte type) {
			m_field_name = new STRING(name);
			m_field_type = new BYTE(type);
		}
		
		int size() {
			int size =0;
			size += m_field_name.size();
			size += m_field_type.size();		
			return size;
		}
		
		void encode(ByteBuffer buf) {
			m_field_name.encode(buf);
			m_field_type.encode(buf);
		}
		
		void decode(ByteBuffer buf){			
			m_field_name = STRING.valueOf(buf);
			m_field_type= BYTE.valueOf(buf);
		}	
	}
	
	/*
	 * Message fields declaration
	 */
	public static class IBMessageFields {
		INT16 m_field_count;
		private List<IBField> m_field_list = new LinkedList<IBField>();
		
		void addFieldName (String name, byte type) {
			m_field_list.add(new IBField(name,type));
		}
		
		int size() {
			int size =0;
			size += m_field_count.size();
			for( int i=0; i< m_field_list.size(); i++) {
				IBField field = m_field_list.get(i);
				size+=field.size();
			}
			return size;
		}
		
		
		void encode(ByteBuffer buf) {
			m_field_count.encode(buf);
			for( IBField field: m_field_list) {
				field.encode(buf);
			}
		}
		
		void decode(ByteBuffer buf){			
			m_field_count = INT16.valueOf(buf);
		
			m_field_list.clear();
			for(int i=0; i<m_field_count.get(); i++) {
				IBField field = new IBField();
				field.decode(buf);
				m_field_list.add(field);
			}			
		}
		
		public String toString() {
			StringBuffer buf =new StringBuffer();
			buf.append( Const.CRLF + "" + m_field_count.toString()+" fields. "+Const.CRLF);
			for( int i=0; i< m_field_list.size(); i++) {
				IBField field = m_field_list.get(i);
				buf.append("[" + i+"]"+field.m_field_name +"(" + field.m_field_type+")");
				buf.append(",");
			}
			return buf.toString();
		}
	}
	
	/**
	 * Message Row 
	 */
	public class IBRow {
		List<IBDataType> m_body = new LinkedList<>();
		
		void addField(IBDataType... values) {
			for(int i=0; i<values.length; i++) {
				m_body.add( values[i]) ;
			}
		}
		
		int size() {
			int size =0;			
			for( int i=0; i< m_body.size(); i++) {
				IBDataType field = m_body.get(i);
				size += field.size();
			}
			return size;
		}
		
		void encode(ByteBuffer buf) {
			for(IBDataType item: m_body) {
				item.encode(buf);
			}			
		}
		
		void decode(ByteBuffer buf){			
			for( int i=0; i< getFieldsCount();i++) {
				IBDataType value = getFieldType(i);
				value.decode(buf);
				m_body.add(value);
			}			
		}	
		
		
		@Override
		public String toString() {
			StringBuffer buf =new StringBuffer();
			for(IBDataType item: m_body) {
				buf.append(item.toString());
				buf.append(",");
			}
			return buf.toString();
		}
	}
	
	public class IBMessageRows{
		INT32 m_row_count = new INT32(0);
		List<IBRow> m_body = new LinkedList<IBRow>();
		
		int size() {
			int size =0;		
			size += m_row_count.size();
			for( int i=0; i< m_body.size(); i++) {
				IBRow row = m_body.get(i);
				size += row.size();
			}
			return size;
		}
		
		void encode(ByteBuffer buf) {
			m_row_count.encode(buf);
			for(IBRow row: m_body) {
				row.encode(buf);
			}			
		}
		
		void decode(ByteBuffer buf){	
			m_row_count = INT32.valueOf(buf);
			m_body.clear();
			for( int i=0; i< m_row_count.get();i++) {
				IBRow row = new IBRow();
				row.decode(buf);
				m_body.add(row);				
			}	
		}
		
		public String toString() {
			StringBuffer buf =new StringBuffer();
			buf.append(Const.CRLF+m_row_count.toString()+" rows" + Const.CRLF);
			for( int i=0; i<  m_body.size(); i++) {
				IBRow row = m_body.get(i);
				buf.append("[").append(String.format("%04d", i+1)).append("]");
				for(IBDataType field: row.m_body) {
					buf.append(field.toString()).append(",");
				}
				buf.append(Const.CRLF);
				
				if (i>10) {
					buf.append(".....");
					break;
				}
			}			
		
			return buf.toString();
		}
	}
	
	/*
	 * data member
	 */
	IBMessageHeader m_header = new IBMessageHeader();
	IBMessageFields m_fields =new IBMessageFields() ;
	IBMessageRows m_rows = new IBMessageRows();
	
	
	//internal bytes buffer
	private ByteBuffer m_data = null; 
	
		
	IBMsg(int datasize) {
		m_data = ByteBuffer.allocate(datasize); 
		m_log.info("allocating " + datasize + " bytes");
		 
		m_data.order(BYTE_ORDER);		
	}
	
	
	private IBDataType getFieldType (int index) {
		
		IBDataType ret = null;
		IBField field = m_fields.m_field_list.get(index);
		
		while (field != null) {
			if( field.m_field_type.get() == BYTE.encodeType()) {
				ret = new BYTE();
				break;
			}
			
			if( field.m_field_type.get() == BOOL.encodeType()) {
				ret = new BOOL();
				break;
			}
			
			if( field.m_field_type.get() == INT16.encodeType()) {
				ret = new INT16();
				break;
			}
			
			if( field.m_field_type.get() == INT32.encodeType()) {
				ret = new INT32();
				break;
			}
			
			if( field.m_field_type.get() == INT64.encodeType()) {
				ret = new INT64();
				break;
			}
			
			if( field.m_field_type.get() == FLOAT.encodeType()) {
				ret = new FLOAT();
				break;
			}
			
			if( field.m_field_type.get() == DOUBLE.encodeType()) {
				ret = new DOUBLE();
				break;
			}
			
			if( field.m_field_type.get() == STRING.encodeType()) {
				ret = new STRING();
				break;
			}
			
			if( field.m_field_type.get() == WINDSTREAM.encodeType()) {
				ret = new WINDSTREAM();
				break;
			}
			
			break;
		}		
		
		if( ret == null) {
			m_log.error("Unknown field type. " + index);
		}
		return ret;
	}
	
	void set_ib_options(IBOptions options) {
		m_ib_options = options;
	}
	
	private short getFieldsCount () {
		return (short)m_fields.m_field_list.size();
	}
	/*
	 * Functionality: Add one message row
	 */
	public void addMessageRow(IBRow row) {
		m_rows.m_row_count.add(1);
		m_rows.m_body.add(row);
	}
	
	/*
	 * Encode message as WindStream format,
	 * returns: lengths of bytes filled in 
	 */
	public void encode () {
		final byte DELI = 0x01;
		//Calculate total size
		int body_size =  m_fields.size() + m_rows.size();
		m_header.m_size = new INT32(body_size);
		
		//encode
		m_data.clear();
		
		//stream header
		if( m_ib_options.m_wrapper.compareToIgnoreCase("1") ==0) {
			m_data.order(ByteOrder.LITTLE_ENDIAN);	
			m_data.put("8=stream".getBytes());
			m_data.put(DELI);
			m_data.put("9=".getBytes());
			m_data.putInt(body_size+ m_header.size()+29);		
			m_data.put(DELI);
			m_data.put("99=".getBytes());
			m_data.putInt(body_size+ m_header.size());	//until here, total 23 bytes
		}
		
		m_data.order(BYTE_ORDER);		
		m_header.encode(m_data);
		m_fields.encode(m_data);
		m_rows.encode(m_data);
		
		//stream end
		if( m_ib_options.m_wrapper.compareToIgnoreCase("1") ==0) {
			m_data.put(DELI);
			m_data.put("10=0".getBytes());
			m_data.put(DELI);
		}
	}
	
	/*
	 * decode message from WindStream format into header-fields-rows
	 */
	void decode (byte[] streams, int pos, int len) {
		boolean isOk = true;
		m_data.clear();
		m_data.put(streams,pos,len);
		m_data.position(0);
		
		m_header.decode(m_data);

		if( m_header.is_compressed() ) {
			isOk = unCompress(m_data.array(), m_data.position(), m_header.m_size.get());
		}

		if(isOk) {
			m_fields.decode(m_data);	
			m_rows.decode(m_data);	
		}
	}
	
	//Manipulate m_data buffer
	public boolean unCompress(byte[] bytes, int pos, int len) {
		
		boolean ret = true;
		try {		  
			ByteArrayInputStream in = new ByteArrayInputStream(bytes, pos, len);	
			GZIPInputStream gunzip = new GZIPInputStream(in);  
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			byte[] tmp_buf = new byte[1024];
			int read_bytes = 0;	
			while (true) {
				read_bytes = gunzip.read(tmp_buf, 0, tmp_buf.length);
				if( read_bytes <= 0) {
					break;
				}
				out.write(tmp_buf,0, read_bytes);
			}
			
			read_bytes = out.size();	//size after compressed
			if( read_bytes > IBMsg.MAX_MSG_SIZE) {
				 m_log.error("total_size " + read_bytes +" exceeds expected max size "+ IBMsg.MAX_MSG_SIZE);
				return false;
			}
			
			ByteBuffer buf =ByteBuffer.allocate(read_bytes);
			buf.order(BYTE_ORDER);
			m_log.info("allocating " + len + " bytes");
			
			buf.put(out.toByteArray(), 0,read_bytes);
			buf.position(0);
			in.close();
			gunzip.close();
			m_data = null;
			m_data = buf;
			m_log.info("uncompress data from " + len + " bytes to " + read_bytes );
			
		} catch (IOException e) {
			m_log.error(e.toString());
			ret = false;
		} 
		return ret;
	}
	
	public byte[] bytes() {
		return m_data.array();
	}
	
	public int length() {
		return m_data.position();
	}
	
    public int get_field_count() {
    	return m_fields.m_field_count.get();
	}
	
    public int get_row_count() {
    	return m_rows.m_body.size();
	}
    
    public String getFieldName(int index) {
		String ret = null;
		while (true) {
			
			if(m_fields.m_field_count.get() <1) {
				break;
			}
			
			if(m_fields.m_field_list.size() < index-1) {
				break;
			}
			ret = m_fields.m_field_list.get(index).m_field_name.get();
			break;
		}
		return ret;
	}

    public IBDataType getFieldValue(int index) {
		return getFieldValue(0,index);
	}

    public IBDataType getFieldValue(int row, int index) {
		
		IBDataType ret = null;
		while (true) {
			if (m_rows.m_row_count.get() <1) {
				break;
			}
			if( m_rows.m_body.size() < 1) {
				break;
			}
			
			if( m_rows.m_body.get(0).m_body.size() < index+1) {
				break;
			}
			
			ret = m_rows.m_body.get(0).m_body.get(index);
			break;
		}
		return ret;
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		buf.append(m_header.toString());
		
		buf.append(m_fields.toString());
		
		
		buf.append(m_rows.toString());
		return buf.toString();
	}
	
	public static String bytesToString( byte[] bytes, int pos, int len) { 
		StringBuffer buf =new StringBuffer();
		for (int i = pos; i < len; i++) { 
			String hex = Integer.toHexString(bytes[i] & 0xFF); 
			if (hex.length() == 1) { 
					hex = '0' + hex; 
			} 
			buf.append(hex.toUpperCase());	buf.append(" ");
			if( (i+1)%20==0 ) {
				buf.append(Const.CRLF);
			}
			if (i>100) {
				buf.append(".....");
				break;
			}
		} 
		return buf.toString();
	}
}
