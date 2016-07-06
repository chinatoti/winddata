package com.sumscope.optimus.definition.datacom;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenshuai.li on 2016/7/1.
 */
public class Row {
    private List<Data> fields = new ArrayList<>();

    public Row(){
    }


    public Row addFieldData(Data data){
        fields.add(data);
        return this;
    }

    public int pack(ByteBuffer buffer) throws Exception {
        int size = 0;
        for (Data data : fields){
            size += data.encode(buffer).getSize();
        }
        return size;
    }
}
