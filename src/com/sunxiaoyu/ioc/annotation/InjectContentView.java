package com.sunxiaoyu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ��ע������ΪActivity�Ĳ��֣����setContentView����
 * 
 * ʹ�ó�����
 * 	@InjectContentView(R.layout.xx)
 *	public class ����  extends InjectBaseActivity {  
 *		..................
 * 	}
 * 
 * @author ������
 */
@Retention(RetentionPolicy.RUNTIME) //ʹ�ó���������ʱ��
@Target(ElementType.TYPE)			//ʹ�õط���type: �ࣩ
public @interface InjectContentView {
	
	int value();

}
