package com.sunxiaoyu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.widget.RadioGroup;


/**
 * ��ע�����ڼ���CompoundButton����ֵ�ؼ���CheckBox��ToggleButton�ȣ���״̬�ı��¼�
 * 	
 * ʹ�ó�����
 * 	InjectRBOnCheckedChange({R.id.xx,R.id.xx})
 *	public void ������(RadioGroup rg, int radioButtonId){ 
 *		...............................
 *  }
 * 
 * @author ������
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //����
@InjectEventBus(listenerSetter="setOnCheckedChangeListener", listenerType=RadioGroup.OnCheckedChangeListener.class, callBackMethod="onCheckedChanged")
public @interface InjectRBOnCheckedChange {
	int[] value();
}
