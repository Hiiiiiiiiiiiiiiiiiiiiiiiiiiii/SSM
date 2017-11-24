package com.kaishengit.MQ;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-activeMQ.xml")
public class SendTopicMessage {
    @Autowired
    JmsTemplate jmsTemplate;
    //注入topic对象 对象中定义了发送的信息类型 以及名称
    @Autowired
    Destination destination;
//    订阅/发布
    @Test
    public void sendMessage() throws IOException {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                return session.createTextMessage("topic");
            }
        });
        System.in.read();
    }
}
