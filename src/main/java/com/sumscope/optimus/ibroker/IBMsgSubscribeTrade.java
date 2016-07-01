package com.sumscope.optimus.ibroker;

public class IBMsgSubscribeTrade extends IBMsg {
	private final static int m_datasize = 256;
	short m_subscribe_type;

	public IBMsgSubscribeTrade() {
		super(m_datasize);
		m_fields.m_field_count = new INT16(3);
		m_fields.addFieldName("CommandID", IBMsg.INT32.encodeType());
		m_fields.addFieldName("SubscribeType", IBMsg.INT16.encodeType());
		m_fields.addFieldName("Option", IBMsg.BYTE.encodeType());

		
		m_subscribe_type = SUB_TYPE_TRADE;
	}

	public void set_subscribe_type(short type) {
		m_subscribe_type = type;
	}

	@Override
	public void encode() {
		IBRow row = new IBRow();
		row.addField(new INT32(IB_FUNC.SUBSCRIBE_TRADE.value()), new INT16(m_subscribe_type), new BYTE(IBMsg.SUB_METHOD));
		addMessageRow(row);
		super.encode();
	}
}
