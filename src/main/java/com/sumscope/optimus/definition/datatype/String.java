package com.sumscope.optimus.definition.datatype;

import com.sumscope.optimus.definition.datacom.Data;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/7/1.
 */
public class String extends Data {
    private java.lang.String data;

    public String(){
        encodeType = Type.String.value();
    }

    public String(java.lang.String data){
        this.data = data;
        encodeType = Type.String.value();
    }

    @Override
    public String encode(ByteBuffer buffer) throws UnsupportedEncodingException {
        byte[] value = data.getBytes(DEFAULT_ENCODING);
        buffer.putShort((short)value.length);
        buffer.put(value);
        setSize(2 + value.length);
        return this;
    }

    public java.lang.String getData() {
        return data;
    }

    public void setData(java.lang.String data) {
        this.data = data;
    }

    public String decode(ByteBuffer buffer) throws UnsupportedEncodingException {
        short length = buffer.getShort();
        byte[] bb = new byte[length];
        buffer.get(bb,0,length);
        this.data = new java.lang.String(bb);
        return this;
    }
}
