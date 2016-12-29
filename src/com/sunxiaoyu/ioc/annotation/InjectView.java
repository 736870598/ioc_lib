package com.sunxiaoyu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ��ע���ð󶨿ؼ�
 * 
 * ʹ�ó�����
 * 	@InjectView(R.id.xx)
 *	private View view;
 * 
 * 	@author ������
 */
@Retention(RetentionPolicy.RUNTIME)  //ʹ�ó���������ʱ��
@Target(ElementType.FIELD) 			 //ʹ�õط���FIELD: ���ԣ�
public @interface InjectView {
	int value();
}
