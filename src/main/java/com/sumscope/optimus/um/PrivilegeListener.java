package com.sumscope.optimus.um;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wenshuai.li on 2016/6/27.
 */
//@Service
public class PrivilegeListener extends BaseListener{
    /*@Autowired
    private MessageHandler messageHandler;

    public static ExecutorService senderExecutor = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());

    @PostConstruct
    public void start(){
        senderExecutor.execute(new Runnable() {
            @Override
            public void run() {
                connAndReceive("api.request.privelege",messageHandler);
            }
        });
    }*/
}
