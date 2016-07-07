package com.sumscope.optimus.definition;

import com.sumscope.optimus.definition.datacom.*;
import com.sumscope.optimus.definition.datatype.Int16;
import com.sumscope.optimus.definition.datatype.Int32;

import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/7/7.
 */
public class SubscribeCommand extends Command {

    private short subscribeType;
    private byte subscribeOption;

    public SubscribeCommand(){

    }

    public SubscribeCommand(short subscribeType,byte subscribeOption){
        this.subscribeType = subscribeType;
        this.subscribeOption = subscribeOption;
    }
    @Override
    public Fields getFields() {
        Field commandId = new Field("CommandID", Data.Type.Int32.value());
        Field subscribeType = new Field("SubscribeType", Data.Type.Int16.value());
        Field option = new Field("Option", Data.Type.Byte.value());

        Fields fields = new Fields();
        fields.addField(commandId);
        fields.addField(subscribeType);
        fields.addField(option);

        return fields;
    }

    @Override
    public Rows getRows() {
        Rows rows = new Rows();

        Row row = new Row()
                .addFieldData(new Int32(1005))
                .addFieldData(new Int16(subscribeType))
                .addFieldData(new com.sumscope.optimus.definition.datatype.Byte(subscribeOption));

        rows.addRow(row);
        return rows;
    }
}
