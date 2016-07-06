package com.sumscope.optimus.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Created by wenshuai.li on 2016/7/6.
 */
public class MessageClientCodecFactory implements ProtocolCodecFactory {

    private MessageClientEncoder messageClientEncoder;
    private MessageClientDecoder messageClientDecoder;

    public MessageClientCodecFactory(MessageClientEncoder messageClientEncoder,MessageClientDecoder messageClientDecoder){
        this.messageClientEncoder = messageClientEncoder;
        this.messageClientDecoder = messageClientDecoder;
    }
    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return messageClientEncoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return messageClientDecoder;
    }
}
