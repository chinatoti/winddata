package com.sumscope.optimus.codec;

import com.sumscope.optimus.definition.Command;
import com.sumscope.optimus.definition.datacom.Message;
import com.sumscope.optimus.ibroker.IBMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/7/5.
 */

public class MessageClientEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        IoBuffer iobuffer = IoBuffer.allocate(100).setAutoExpand(true);

        Command msg = (Command)message;
        ByteBuffer byteBuffer = msg.pack();
        byteBuffer.flip();

        iobuffer.put(byteBuffer);
        iobuffer.flip();
        out.write(iobuffer);
    }
}
