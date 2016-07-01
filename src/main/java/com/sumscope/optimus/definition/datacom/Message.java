package com.sumscope.optimus.definition.datacom;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by wenshuai.li on 2016/6/30.
 */
public class Message {
    public static ByteBuffer buffer = ByteBuffer.allocate(Data.MAX_MSG_SIZE);
    public static ByteBuffer buffer1 = ByteBuffer.allocate(Data.MAX_MSG_SIZE);

    private Header header;
    private Fields fields;
    private Rows rows;

    public Message(Header header,Fields fields,Rows rows){
        this.header = header;
        this.fields = fields;
        this.rows = rows;
    }
    public void init(){
        buffer.clear();
        buffer1.clear();
    }
    public synchronized ByteBuffer pack() throws Exception {
        init();
        buffer.order(ByteOrder.BIG_ENDIAN);
        int size = 0;
        size += fields.pack(buffer);
        size += rows.pack(buffer);

        header.setSize(size + 7);
        header.pack(buffer1);
        buffer.flip();
        int count = buffer.remaining();
        while (count > 0){
            buffer1.put(buffer.get());
            count--;
        }
        return buffer1;
    }
}
