package com.imooc;

//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.imooc.service.WelcomeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

//@Configuration
//@EnableAspectJAutoProxy
//@Import(OutSide.class)
//@ComponentScan("com.imooc")
public class Entrance {


    public static void main(String[] args) {


    	// 测试是否启动成功
		System.out.println(">>>>>>>>>>>>>>>Heelo World!!!!!!!!");

        // 通过FileSystemXmlAppli
		// cationContext获取bean容器实例
        String xmlPath = "E:\\WorkSpace\\Imooc\\Spring\\github20230624\\spring-framework-5.2.20.RELEASE\\springdemo\\src\\main\\java\\com\\imooc\\service\\WelcomeService.java";
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(xmlPath);
        WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeService");
        welcomeService.sayHello("强大的spring框架");


        /**
         * 获取bean容器的实例
         */
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Entrance.class);
//		HiService hiService = (HiService)applicationContext.getBean("hiServiceImpl");
//		hiService.sayHi();
//		System.out.println("---------------------------分割线以下执行HelloService-------------------------------");
//		HelloService helloService = (HelloService)applicationContext.getBean("helloServiceImpl");
//		helloService.sayHello();


        //得到beanFactory创建的对象∶
//        User user4a = (User) applicationContext.getBean("userFactoryBean");
//        User user4b = (User) applicationContext.getBean("userFactoryBean");


//		System.out.println("无参构造函数创建的对象:"+user1a);
//		System.out.println("无参构造函数创建的对象:"+user1b);
//		System.out.println("静态工厂创建的对象:"+ user2a);
//		System.out.println("静态工厂创建的对象︰"+ user2c);
//		System.out.println("实例工厂创建的对象∶"+ user3a);
//		System.out.println("实例工厂创建的对象∶" + user3b);
//        System.out.println("factorybean创建的对象:" + user4a);
//        System.out.println("factorybean创建的对象∶" + user4b);


    }
}



















