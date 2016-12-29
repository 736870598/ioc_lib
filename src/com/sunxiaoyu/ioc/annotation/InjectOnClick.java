package com.sunxiaoyu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.view.View;


/**
 * 此注解用于监听所有控件点击事件
 * 
 * 使用场景：
 * 	@InjectOnClick({R.id.xx, R.id.xx})
 *	public void 方法名(View view){ 
 *		......................
 * 	}
 * 
 * @author 孙晓宇
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //方法
@InjectEventBus(listenerSetter="setOnClickListener", listenerType=View.OnClickListener.class, callBackMethod="onClick")
public @interface InjectOnClick {
	int[] value();
}
