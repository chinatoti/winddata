package com.sumscope.optimus.definition;

import com.sumscope.optimus.definition.datatype.Int32;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

/**
 * Created by wenshuai.li on 2016/6/30.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        /*LoginCommand cc = new LoginCommand();
        ByteBuffer bb = cc.pack();
        System.out.println(bb);*/

        List<String> aaa = new ArrayList<>();
        aaa.add("1");
        aaa.add("2");
        aaa.add("3");

        Map map = new HashMap<>();
        map.put("xxx",aaa);
        map.put("yyy",aaa);
        System.out.println(map);
    }
}
