package com.sumscope.optimus.data;

import com.sumscope.optimus.definition.LoginCommand;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by wenshuai.li on 2016/7/6.
 */
public class MainClient2 {
    public static void main(String[] args) throws Exception {
        LoginCommand loginCommand = new LoginCommand();
        ByteBuffer buffer =  loginCommand.pack();
        buffer.flip();

        byte[] aaa = new byte[buffer.remaining()];
        buffer.get(aaa);

        Socket socket = new Socket("114.80.168.110",60001);
        OutputStream os = socket.getOutputStream();//字节输出流
        os.write(aaa);
        os.flush();
        //os.close();

        byte[] bbb = new byte[1000];
        InputStream in =  socket.getInputStream();
        int i=0;

        int data = 0;
        while ((data = in.read()) != -1){
            bbb[i] = (byte)data;
        }
        System.out.println(bbb);


    }
}
