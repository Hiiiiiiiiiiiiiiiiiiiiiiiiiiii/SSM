package com.kaishengit.MQ;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-activeMQ.xml")
public class SendQueueMessage {
    @Autowired
    JmsTemplate jmsTemplate;
    //发起queue信息
    @Test
    public void sendQueueMessage() throws IOException {
        jmsTemplate.send("queue1", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("快递到了");
            }
        });
        System.in.read();
    }
}
