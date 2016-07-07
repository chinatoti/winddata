package com.sumscope.optimus.definition.datacom;

import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/7/1.
 */
public abstract class Data {
    protected static final String DEFAULT_ENCODING = "UTF-8";
    public final static int MAX_MSG_SIZE = 1024*1000*20;
    protected byte encodeType;
    protected int size;

    public enum Type{
        Bool((byte)0),
        Byte((byte)1),
        Int16((byte)2),
        Int32((byte)3),
        String((byte)7);
        Type(byte t){
            this.t = t;
        }
        private byte t;
        public byte value(){
            return t;
        }
    }
    public abstract Data encode(ByteBuffer buffer) throws Exception;
    public abstract Data decode(ByteBuffer buffer) throws Exception;

    public byte getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(byte encodeType) {
        this.encodeType = encodeType;
    }

    public int getSize() {
        return size;
    }

    protected void setSize(int size) {
        this.size = size;
    }
}
