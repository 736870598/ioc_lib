package com.sunxiaoyu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解用绑定控件
 * 
 * 使用场景：
 * 	@InjectView(R.id.xx)
 *	private View view;
 * 
 * 	@author 孙晓宇
 */
@Retention(RetentionPolicy.RUNTIME)  //使用场景（运行时）
@Target(ElementType.FIELD) 			 //使用地方（FIELD: 属性）
public @interface InjectView {
	int value();
}
