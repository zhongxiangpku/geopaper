package cn.edu.hfut.dmic.webcollector.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * spring获取bean
 * 
 * @author Lee
 *
 * 2012-3-22 下午4:29:07
 */
public class SpringUtil {
	/**
	 * spring的上下文
	 */
	private static ApplicationContext ctx;
	static
	{
		 ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		 if(ctx!=null)
			 System.out.println("true");
	}
	/**
	 * 根据指定bean名称，获取对应的实例对象
	 * @param name bean的名�?
	 * @return 对应bean的实例对�?
	 */
	public static Object getBean(String name) {
		if (ctx == null) {
			throw new RuntimeException("spring 上下文对象未初始化，无法完成bean的查找！");
		}
		return ctx.getBean(name);
	}
	/**
	 * @return the ctx
	 */
	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	/**
	 * @param ctx the ctx to set
	 */
	public static void setApplicationContext(ApplicationContext applicationContext) {
		ctx = applicationContext;
	}
}
