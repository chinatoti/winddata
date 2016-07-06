package com.sumscope.optimus.definition;

import com.sumscope.optimus.definition.datacom.*;
import com.sumscope.optimus.definition.datatype.Int32;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by wenshuai.li on 2016/7/1.
 */
public class LoginCommand extends Command{

    public LoginCommand(){
        super();
    }

    public LoginCommand(short version,byte isCompressed){
        super(version,isCompressed);
    }

    @Override
    public Fields getFields() {
        Field commandId = new Field("CommandID", Data.Type.Int32.value());
        Field loginName = new Field("LoginName", Data.Type.String.value());
        Field password = new Field("Password", Data.Type.String.value());

        Fields fields = new Fields();
        fields.addField(commandId);
        fields.addField(loginName);
        fields.addField(password);

        return fields;
    }

    @Override
    public Rows getRows() {
        Rows rows = new Rows();

        Row row = new Row()
                .addFieldData(new Int32(1001))
                .addFieldData(new com.sumscope.optimus.definition.datatype.String("sum1"))
                .addFieldData(new com.sumscope.optimus.definition.datatype.String("sum1"));

        rows.addRow(row);
        return rows;
    }

    @Override
    public ByteBuffer pack() throws Exception {
        Message message = new Message(getHeader(),getFields(),getRows());
        return message.pack();
    }

}
