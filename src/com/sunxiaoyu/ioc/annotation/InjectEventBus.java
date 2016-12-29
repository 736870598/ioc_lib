package com.sunxiaoyu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解为事件添加类型声明，用于注解上
 * @author 孙晓宇
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE) //注解
public @interface InjectEventBus {

	/**
	 * 设置事件监听方式
	 */
	String listenerSetter();
	/**
	 * 事件监听类型
	 */
	Class<?> listenerType();
	/**
	 * 事件触发后回调方法
	 */
	String callBackMethod();
}
