package com.sumscope.optimus.definition.datacom;

import com.sumscope.optimus.definition.datatype.Int16;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenshuai.li on 2016/6/30.
 */
public class Fields {
    private short fieldCount;
    private List<Field> fieldList = new ArrayList<>();

    public Fields(){

    }

    public Fields(short fieldCount){
        this.fieldCount = fieldCount;
    }
    public Fields(short fieldCount,List<Field> fieldList){
        this.fieldCount = fieldCount;
        this.fieldList = fieldList;
    }
    public void addField(Field field){
        fieldList.add(field);
    }

    public short getFieldCount() {
        fieldCount = (short) fieldList.size();
        return fieldCount;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public int pack(ByteBuffer buffer) throws UnsupportedEncodingException {
        int size = 0;
        size += new Int16(getFieldCount()).encode(buffer).getSize();
        for (Field f : fieldList){
            size += f.pack(buffer);
        }
        return size;
    }

    public Fields unpack(ByteBuffer buffer) throws Exception {
        this.fieldCount = buffer.getShort();
        for (int i=0;i<fieldCount;i++){

            com.sumscope.optimus.definition.datatype.String f
                    = new com.sumscope.optimus.definition.datatype.String().decode(buffer);
            Field field = new Field(f.getData(),f.getEncodeType());
            fieldList.add(field);
        }
        return this;
    }
}
