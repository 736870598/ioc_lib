package com.sunxiaoyu.ioc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import android.app.Activity;

/**
 * ����ģʽ�е�InvocationHandler
 * @author ������
 */
public class EventInvocationHandler implements InvocationHandler {

	private Activity activity;
	private String MethodName;
	private Method proxyMethod;

	public EventInvocationHandler(Activity activity, String MethodName, Method proxyMethod) {
		this.activity = activity;
		this.MethodName = MethodName;
		this.proxyMethod = proxyMethod;

	}

	@Override
	public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
		
		//�����ж�����ص����������Ǳ�����ķ�������ִ�����ǵĴ�����
		if(method.getName() == MethodName){
			return proxyMethod.invoke(activity, args);
		}
		return method.invoke(obj, args);
	}

}
