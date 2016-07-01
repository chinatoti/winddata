package com.sumscope.optimus.ibroker;

public class IBMsgSubscribeQuote extends IBMsgSubscribeTrade {
	public IBMsgSubscribeQuote() {
		super();
		m_subscribe_type = SUB_TYPE_QUOTE;
	}
}
