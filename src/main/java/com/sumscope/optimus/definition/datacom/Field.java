package com.sumscope.optimus.definition.datacom;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/6/30.
 */
public class Field {
    private String fieldName;
    private byte dataType;
    private Data data;

    public Field(String fieldName,Data data){
        this.fieldName = fieldName;
        this.data = data;
    }

    @Deprecated
    public Field(String fieldName,byte dataType,Data data){
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.data = data;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int pack(ByteBuffer buffer) throws UnsupportedEncodingException {
        int size = 0;
        size += new com.sumscope.optimus.definition.datatype.String(fieldName).encode(buffer).getSize();
        size += new com.sumscope.optimus.definition.datatype.Byte(data.getEncodeType()).encode(buffer).getSize();
        return size;
    }
}
