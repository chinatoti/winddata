package com.sumscope.optimus.definition.datacom;

import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/7/1.
 */
public class Row {

    private Field field;
    public Row(Field field){
        this.field = field;
    }

    public int pack(ByteBuffer buffer) throws Exception {
        int size = 0;
        Data data = field.getData();
        size += data.encode(buffer).getSize();
        return size;
    }
}
