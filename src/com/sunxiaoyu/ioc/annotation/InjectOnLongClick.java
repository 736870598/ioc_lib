package com.sunxiaoyu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.view.View;

/**
 * 此注解用于监听所有控件长按事件（源码中设定按下没有移除也没有松开500ms后触发长按事件）
 * 
 * 使用场景：
 * 	@InjectOnLongClick({R.id.xx, R.id.xx})
 *	public boolean 方法名(View view){ 
 *		...............
 *		return true;
 * 	}
 * 	注： 长按事件的方法必须返回boolean类型的值
 * 		
 * @author 孙晓宇
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //方法
@InjectEventBus(listenerSetter="setOnLongClickListener", listenerType=View.OnLongClickListener.class, callBackMethod="onLongClick")
public @interface InjectOnLongClick {
	int[] value();
}

