package com.sumscope.optimus.definition.datacom;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/6/30.
 */
public class Field {
    private String fieldName;
    private byte dataType;


    public Field(String fieldName,byte dataType){
        this.fieldName = fieldName;
        this.dataType = dataType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public byte getDataType() {
        return dataType;
    }

    public void setDataType(byte dataType) {
        this.dataType = dataType;
    }

    public int pack(ByteBuffer buffer) throws UnsupportedEncodingException {
        int size = 0;
        size += new com.sumscope.optimus.definition.datatype.String(fieldName).encode(buffer).getSize();
        size += new com.sumscope.optimus.definition.datatype.Byte(dataType).encode(buffer).getSize();
        return size;
    }
}
