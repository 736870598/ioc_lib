package com.sunxiaoyu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解用于为Activity的布局，替代setContentView方法
 * 
 * 使用场景：
 * 	@InjectContentView(R.layout.xx)
 *	public class 类名  extends InjectBaseActivity {  
 *		..................
 * 	}
 * 
 * @author 孙晓宇
 */
@Retention(RetentionPolicy.RUNTIME) //使用场景（运行时）
@Target(ElementType.TYPE)			//使用地方（type: 类）
public @interface InjectContentView {
	
	int value();

}
