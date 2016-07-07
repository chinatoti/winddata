package com.sumscope.optimus.definition;

import com.sumscope.optimus.definition.datacom.Fields;
import com.sumscope.optimus.definition.datacom.Header;
import com.sumscope.optimus.definition.datacom.Message;
import com.sumscope.optimus.definition.datacom.Rows;

import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/7/1.
 */
public abstract class Command {

    private short version = (short)2;
    private byte isCompressed = (byte)0;

    private Header header;

    public enum SubscribeType{
        quote((short) 12),
        trade((short) 13);
        private short t;
        SubscribeType(short t){
            this.t = t;
        }
        public short value(){
            return t;
        }
    }

    public enum SubscribeOption{
        query((byte)0),
        querySubscribe((byte)1),
        subscribe((byte)2);
        private byte t;
        SubscribeOption(byte t){
            this.t = t;
        }
        public byte value(){
            return t;
        }
    }
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
    /*public abstract ByteBuffer pack() throws Exception;*/

    public ByteBuffer pack() throws Exception {
        Message message = new Message(getHeader(),getFields(),getRows());
        return message.pack();
    }
}
