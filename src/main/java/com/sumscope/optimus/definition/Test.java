package com.sumscope.optimus.definition;

import com.sumscope.optimus.definition.datatype.Int32;

import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/6/30.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        LoginCommand cc = new LoginCommand();
        ByteBuffer bb = cc.pack();
        System.out.println(bb);
    }
}
