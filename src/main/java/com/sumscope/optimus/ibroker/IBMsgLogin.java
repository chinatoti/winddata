package com.sumscope.optimus.ibroker;


public class IBMsgLogin extends IBMsg{
	private final static int m_datasize = 256;
	private String m_name;
	private String m_pwd;
	
	public IBMsgLogin() {
			super(m_datasize);
			m_fields.m_field_count = new INT16(3);
			m_fields.addFieldName(
					"CommandID", 
					IBMsg.INT32.encodeType()
			);
			m_fields.addFieldName(
					"LoginName", 
					IBMsg.STRING.encodeType()
			);
			m_fields.addFieldName(
					"Password", 
					IBMsg.STRING.encodeType()
			);				
			
	}

	public void setLoginName(String name) {
		m_name = name;
	}
	
	public void setLoginPwd(String pwd) {
		m_pwd = pwd;
	}
	
	@Override
	public void encode() {
		IBRow row = new IBRow();
		row.addField(new INT32(IB_FUNC.LOGIN.value()), new STRING(m_name), new STRING(m_pwd));
		addMessageRow(row);
		super.encode();
	}
}
