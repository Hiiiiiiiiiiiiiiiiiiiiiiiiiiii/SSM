package com.kaishengit.MQ;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class JmsMQ {
    @JmsListener(destination = "queue1")
    public void queue(Message message){
        System.out.println(1);
    }
}
