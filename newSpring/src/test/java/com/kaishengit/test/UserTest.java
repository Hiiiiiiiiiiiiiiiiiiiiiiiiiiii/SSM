package com.kaishengit.test;

import com.kaishengit.service.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
    @Test
    public void test(){
//      读取配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//      获取spring容器中存放的目标bean
        User user = (User) applicationContext.getBean("user");
        user.say();
    }

}
