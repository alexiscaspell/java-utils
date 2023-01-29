package io.blacktoast.utils.bean;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class BeanUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	@SuppressWarnings("unused")
	@Autowired
	private Environment env;

	public static void setBean(String beanName, Object bean) {
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) context).getBeanFactory();
		beanFactory.registerSingleton(beanName, bean);
	}

	public static void setBean(Object bean) {
		setBean(bean.getClass().getCanonicalName(), bean);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName, Class<T> type) {
		return (T) context.getBean(beanName);
	}

	public static <T> T getBean(Class<T> type) {
		return (T) context.getBean(type);
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public static Object getEnvironmentProperty(String key) {
		return getBean(Environment.class).getProperty(key);
	}

	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		context = ac;

	}

}
