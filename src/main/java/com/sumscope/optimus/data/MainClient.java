package com.sumscope.optimus.data;

/**
 * Created by wenshuai.li on 2016/6/29.
 */
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * 启动客户端
 * @author 何明
 *
 */
public class MainClient {

    public static void main(String []args)throws Exception{

        //Create TCP/IP connection
        NioSocketConnector connector = new NioSocketConnector();

        //创建接受数据的过滤器
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();

        //设定这个过滤器将一行一行(/r/n)的读取数据
        //chain.addLast("myChin", new ProtocolCodecFilter(new TextLineCodecFactory()));

        //服务器的消息处理器：一个SamplMinaServerHander对象
        connector.setHandler(new SamplMinaClientHander());

        //set connect timeout
        connector.setConnectTimeoutMillis(300*1000);

        //连接到服务器：
        //ConnectFuture cf = connector.connect(new InetSocketAddress("114.80.168.110",60001));
        ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1",60001));
        //Wait for the connection attempt to be finished.
        cf.awaitUninterruptibly();


        cf.getSession().getCloseFuture().awaitUninterruptibly();

        connector.dispose();
    }

}
