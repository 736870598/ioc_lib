package com.sunxiaoyu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.view.View;

/**
 * ��ע�����ڼ������пؼ������¼���Դ�����趨����û���Ƴ�Ҳû���ɿ�500ms�󴥷������¼���
 * 
 * ʹ�ó�����
 * 	@InjectOnLongClick({R.id.xx, R.id.xx})
 *	public boolean ������(View view){ 
 *		...............
 *		return true;
 * 	}
 * 	ע�� �����¼��ķ������뷵��boolean���͵�ֵ
 * 		
 * @author ������
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //����
@InjectEventBus(listenerSetter="setOnLongClickListener", listenerType=View.OnLongClickListener.class, callBackMethod="onLongClick")
public @interface InjectOnLongClick {
	int[] value();
}

