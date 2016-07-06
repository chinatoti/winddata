package com.sumscope.optimus.definition;

import com.sumscope.optimus.definition.datacom.Fields;
import com.sumscope.optimus.definition.datacom.Header;
import com.sumscope.optimus.definition.datacom.Rows;

import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/7/1.
 */
public abstract class Command {

    private short version = (short)2;
    private byte isCompressed = (byte)0;

    private Header header;

    public Command(){
        header = new Header(version,isCompressed);
    }
    public Command(short version,byte isCompressed){
        header = new Header(version,isCompressed);
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public abstract Fields getFields();
    public abstract Rows getRows();
    public abstract ByteBuffer pack() throws Exception;
}
