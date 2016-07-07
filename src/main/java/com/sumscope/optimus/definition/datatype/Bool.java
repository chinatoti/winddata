package com.sumscope.optimus.definition.datatype;

import com.sumscope.optimus.definition.datacom.Data;

import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/6/30.
 */
public class Bool extends Data {

    private boolean data;

    public Bool(boolean data){
        this.data = data;
        setEncodeType(Type.Bool.value());
        setSize(1);
    }

    @Override
    public Bool encode(ByteBuffer buffer) {
        byte value = data?(byte)1:(byte)0;
        buffer.put(value);
        return this;
    }

    @Override
    public Data decode(ByteBuffer buffer) throws Exception {
        data = buffer.get() == (byte)1? true:false;
        return this;
    }
}
