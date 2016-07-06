package com.sumscope.optimus.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;

/**
 * Created by wenshuai.li on 2016/7/6.
 */
public class MessageClientDecoder extends ProtocolDecoderAdapter {
    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        System.out.println("MessageClientDecoder");
        return;
    }
}
