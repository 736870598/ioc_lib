package com.sunxiaoyu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.view.View;


/**
 * ��ע�����ڼ������пؼ�����¼�
 * 
 * ʹ�ó�����
 * 	@InjectOnClick({R.id.xx, R.id.xx})
 *	public void ������(View view){ 
 *		......................
 * 	}
 * 
 * @author ������
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //����
@InjectEventBus(listenerSetter="setOnClickListener", listenerType=View.OnClickListener.class, callBackMethod="onClick")
public @interface InjectOnClick {
	int[] value();
}
