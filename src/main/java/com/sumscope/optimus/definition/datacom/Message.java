package com.sumscope.optimus.definition.datacom;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by wenshuai.li on 2016/6/30.
 */
public class Message {
    public ByteBuffer buffer = ByteBuffer.allocate(256);
    public ByteBuffer buffer1 = ByteBuffer.allocate(256);

    private Header header;
    private Fields fields;
    private Rows rows;

    public Message(Header header,Fields fields,Rows rows){
        this.header = header;
        this.fields = fields;
        this.rows = rows;

        buffer = ByteBuffer.allocate(256).order(ByteOrder.BIG_ENDIAN);
        buffer1 = ByteBuffer.allocate(256).order(ByteOrder.BIG_ENDIAN);
    }

    public ByteBuffer pack() throws Exception {
        int size = 0;
        size += fields.pack(buffer);
        size += rows.pack(buffer);

        header.setSize(size);
        header.pack(buffer1);
        buffer.flip();
        int count = buffer.remaining();
        while (count > 0){
            buffer1.put(buffer.get());
            count--;
        }
        return buffer1;
    }

    public void unpack(ByteBuffer buffer) throws Exception {

    }
}
