package com.sumscope.optimus.um;

import com.latencybusters.lbm.LBMException;
import com.latencybusters.lbm.LBMMessage;
import com.sumscope.ums.connection.UMConnection;
import com.sumscope.ums.constants.MsgPackUtils;
import com.sumscope.ums.consum.Consumer;
import com.sumscope.ums.listener.UMMessageListener;
import com.sumscope.ums.message.Message;
import com.sumscope.ums.session.Session;
import com.sumscope.ums.topic.Topic;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by simon.mao on 2015/11/24.
 */
public abstract class BaseListener {
    /*private static final Logger logger = LoggerFactory.getLogger("daily");

    protected void connAndReceive(String topicName,MessageHandler messageHandler){
        UMConnection connection = null;
        Session session = null;
        try{
            connection = new UMConnection();
            System.out.println("new connection ");
            connection.start();
            session = connection.createSession();
            Topic topic = new Topic(session.getContext(), topicName);
            Consumer consumer = session.createConsumer(topic);
            consumer.setMessageListener(new UMMessageListener() {
                @Override
                public int onReceive(Object o, LBMMessage msg) {
                    switch (msg.type()) {
                        case 0:
                            try {
                                byte[] msgb = msg.data();
                                logger.debug(new String(msgb,"UTF-8"));
                                messageHandler.handle(msgb);
                            } catch (Exception var6) {
                                logger.info("This system doesn\'t support the UTF-8 code page.");
                            }
                            break;
                    }
                    msg.dispose();
                    return 0;
                }
            });
        }catch (LBMException e){
            logger.info("LBMException, session.close");
            session.close();
            connection.close();
            e.printStackTrace();
        }
    }*/
}
