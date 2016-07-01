package com.sumscope.optimus.definition.datatype;

import com.sumscope.optimus.definition.datacom.Data;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/7/1.
 */
public class String extends Data {
    private java.lang.String data;

    public String(java.lang.String data){
        this.data = data;
        setEncodeType(Type.String.value());
    }

    @Override
    public String encode(ByteBuffer buffer) throws UnsupportedEncodingException {
        byte[] value = data.getBytes(DEFAULT_ENCODING);
        buffer.putShort((short)value.length);
        buffer.put(value);
        setSize(2 + value.length);
        return this;
    }
}
