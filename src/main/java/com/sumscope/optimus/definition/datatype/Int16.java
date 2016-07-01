package com.sumscope.optimus.definition.datatype;

import com.sumscope.optimus.definition.datacom.Data;

import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/7/1.
 */
public class Int16 extends Data {
    private short data;


    public Int16(int data){
        this.data = (short)data;
        setSize(2);
        setEncodeType(Type.Int16.value());
    }

    public Int16(short data){
        this.data = data;
        setSize(2);
        setEncodeType(Type.Int16.value());
    }

    @Override
    public Int16 encode(ByteBuffer buffer) {
        buffer.putShort(data);
        return this;
    }
}
