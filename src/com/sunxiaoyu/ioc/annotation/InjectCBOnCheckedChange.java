package com.sunxiaoyu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.widget.CompoundButton;


/**
 * ��ע�����ڼ���CompoundButton����ֵ�ؼ���CheckBox��ToggleButton�ȣ���״̬�ı��¼�
 * 	
 * ʹ�ó�����
 * 	@InjectCBOnCheckedChange({R.id.xx,R.id.xx})
 *	public void ������(CompoundButton cButton, boolean isChecked){ 
 *		...............................
 *  }
 * 
 * @author ������
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) 			//����
@InjectEventBus(listenerSetter="setOnCheckedChangeListener", listenerType=CompoundButton.OnCheckedChangeListener.class, callBackMethod="onCheckedChanged")
public @interface InjectCBOnCheckedChange {
	int[] value();
}
