package com.sumscope.optimus.data;

/**
 * Created by wenshuai.li on 2016/6/29.
 */
import com.sumscope.optimus.definition.LoginCommand;
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
        System.out.println(arg1.toString());
        // TODO Auto-generated method stub

    }

    /**
     * 当一个客户端被关闭时
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("sessionClosed");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("sessionCreated");

        LoginCommand loginCommand = new LoginCommand((short)2,(byte)0);
        session.write(loginCommand);
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
        /*System.out.println("sessionOpened");
        IBMsgLogin aaa = new IBMsgLogin();
        aaa.setLoginPwd("sump1");
        aaa.setLoginName("sump1");

        System.out.println("login");
        WriteFuture writeFuture = session.write(aaa.bytes());
        writeFuture.await(3, TimeUnit.SECONDS);*/

    }

}
