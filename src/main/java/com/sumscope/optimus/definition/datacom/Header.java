package com.sumscope.optimus.definition.datacom;

import com.sumscope.optimus.definition.datatype.Byte;
import com.sumscope.optimus.definition.datatype.Int16;
import com.sumscope.optimus.definition.datatype.Int32;

import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/6/30.
 */
public class Header {
    private short version;
    private byte isCompressed;
    private int size;

    public Header(short version,byte isCompressed){
        this.version = version;
        this.isCompressed = isCompressed;
    }
    public Header(short version,byte isCompressed,int size){
        this.version = version;
        this.isCompressed = isCompressed;
        this.size = size;
    }
    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public byte getIsCompressed() {
        return isCompressed;
    }

    public void setIsCompressed(byte isCompressed) {
        this.isCompressed = isCompressed;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void pack(ByteBuffer buffer){
        new Int16(version).encode(buffer);
        new Byte(isCompressed).encode(buffer);
        new Int32(size).encode(buffer);
    }
}
