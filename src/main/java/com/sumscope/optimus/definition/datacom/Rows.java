package com.sumscope.optimus.definition.datacom;

import com.sumscope.optimus.definition.datatype.Int32;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenshuai.li on 2016/6/30.
 */
public class Rows {
    private int rowCount;
    private List<Row> rowList = new ArrayList<>();

    public Rows(){

    }
    public Rows(short rowCount,List<Row> rowList){
        this.rowCount = rowCount;
        this.rowList = rowList;
    }

    public void addRow(Row row){
        rowList.add(row);
    }

    public int getRowCount(){
        return rowList.size();
    }

    public int pack(ByteBuffer buffer) throws Exception {
        int size = 0;
        size += new Int32(rowCount).encode(buffer).getSize();
        for(Row row : rowList){
            size += row.pack(buffer);
        }
        return size;
    }
}
