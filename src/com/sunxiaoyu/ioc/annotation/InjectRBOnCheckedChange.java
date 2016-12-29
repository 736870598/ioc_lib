package com.sunxiaoyu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.widget.RadioGroup;


/**
 * 此注解用于监听CompoundButton及其值控件（CheckBox，ToggleButton等）的状态改变事件
 * 	
 * 使用场景：
 * 	InjectRBOnCheckedChange({R.id.xx,R.id.xx})
 *	public void 方法名(RadioGroup rg, int radioButtonId){ 
 *		...............................
 *  }
 * 
 * @author 孙晓宇
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //方法
@InjectEventBus(listenerSetter="setOnCheckedChangeListener", listenerType=RadioGroup.OnCheckedChangeListener.class, callBackMethod="onCheckedChanged")
public @interface InjectRBOnCheckedChange {
	int[] value();
}
