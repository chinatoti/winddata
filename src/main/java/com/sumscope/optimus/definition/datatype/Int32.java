package com.sumscope.optimus.definition.datatype;

import com.sumscope.optimus.definition.datacom.Data;

import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/7/1.
 */
public class Int32 extends Data {
    private int data;

    public Int32(int data){
        this.data = data;
        setSize(4);
        setEncodeType(Type.Int32.value());
    }

    @Override
    public Int32 encode(ByteBuffer buffer) {
        buffer.putInt(data);
        return this;
    }

    @Override
    public Data decode(ByteBuffer buffer) throws Exception {
        this.data = buffer.getInt();
        return this;
    }
}
