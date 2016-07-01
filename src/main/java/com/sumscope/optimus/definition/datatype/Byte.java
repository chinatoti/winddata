package com.sumscope.optimus.definition.datatype;

import com.sumscope.optimus.definition.datacom.Data;

import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/7/1.
 */
public class Byte extends Data {
    private byte data;

    public Byte(byte data){
        this.data = data;
        setEncodeType(Type.Byte.value());
        setSize(1);
    }
    @Override
    public Byte encode(ByteBuffer buffer) {
        byte value = data;
        buffer.put(value);
        return this;
    }
}
