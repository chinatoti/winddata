package com.sumscope.optimus.ibroker;


public class IBMsgUnsubscribeAll extends IBMsg {
	
	private final static int m_datasize = 256;
	
	public IBMsgUnsubscribeAll() {
		super(m_datasize);
		m_fields.m_field_count = new INT16(1);
		m_fields.addFieldName(
				"CommandID", 
				IBMsg.INT32.encodeType()
				);
	}

	public void encode() {
		IBRow row =new IBRow();
		row.addField(new INT32(IB_FUNC.UNSUBSCRIBE_ALL.value()));
		addMessageRow(row);
		super.encode();
	}
}
