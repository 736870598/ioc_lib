package com.sunxiaoyu.ioc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import android.app.Activity;

/**
 * 代理模式中的InvocationHandler
 * @author 孙晓宇
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
		
		//这里判断如果回调方法是我们被代理的方法，就执行我们的代理方法
		if(method.getName() == MethodName){
			return proxyMethod.invoke(activity, args);
		}
		return method.invoke(obj, args);
	}

}
