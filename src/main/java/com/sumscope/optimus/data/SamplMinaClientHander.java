package com.sumscope.optimus.data;

/**
 * Created by wenshuai.li on 2016/6/29.
 */
import com.sumscope.optimus.ibroker.IBMsgLogin;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.concurrent.TimeUnit;

/**
 * 消息处理类

 *
 */
public class SamplMinaClientHander extends IoHandlerAdapter {

    @Override
    public void exceptionCaught(IoSession arg0, Throwable arg1)
            throws Exception {
        // TODO Auto-generated method stub
        arg1.printStackTrace();
    }

    /**
     * 当客户端接受到消息时
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("messageReceived");
        //我们已设定了服务器的消息规则是一行一行读取，这里就可以转为String:
        String s = (String)message;

        //Writer the received data back to remote peer
        System.out.println("服务器发来的收到消息: " + s);

        //测试将消息回送给客户端
        session.write(s);

    }

    @Override
    public void messageSent(IoSession arg0, Object arg1) throws Exception {
        System.out.println("messageSent");
        // TODO Auto-generated method stub

    }

    /**
     * 当一个客户端被关闭时
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("one client Disconnect");

    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("sessionCreated");
        /*short version = 2;
        byte isCompressed = 0x00;
        int size = 100;

        short fieldCount = 3;

        String fieldName1 = "CommandID";
        byte dataType1 = 0x03;

        String fieldName2 = "LoginName";
        byte dataType2 = 0x07;

        String fieldNam3 = "Password";
        byte dataType3 = 0x07;


        int rowCount = 1;

        int CommandID = 1001;
        String LoginName = "sump1";
        String Password = "sump1";

        byte[] aa = new byte[1000];*/


        IBMsgLogin aaa = new IBMsgLogin();
        aaa.setLoginPwd("sump1");
        aaa.setLoginName("sump1");

        System.out.println("login");
        WriteFuture writeFuture = session.write(aaa.bytes());
        writeFuture.await(3, TimeUnit.SECONDS);
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
        System.out.println("sessionIdle");
        // TODO Auto-generated method stub

    }

    /**
     * 当一个客户端连接进入时
     */
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("sessionOpened");


    }

}
