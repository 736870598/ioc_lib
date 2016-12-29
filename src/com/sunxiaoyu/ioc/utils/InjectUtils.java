package com.sunxiaoyu.ioc.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import android.app.Activity;
import android.view.View;

import com.sunxiaoyu.ioc.annotation.InjectContentView;
import com.sunxiaoyu.ioc.annotation.InjectEventBus;
import com.sunxiaoyu.ioc.annotation.InjectView;
import com.sunxiaoyu.ioc.proxy.EventInvocationHandler;

public class InjectUtils {

	public static void inject(Activity activity){

		injectLayout(activity);
		injectViews(activity);
		injectEvent(activity);
	}

	/**
	 * 注入布局
	 * 这里传入activity要比传入context处理起来简单些
	 * 因为activity有setContentView方法，而context要通过反射获取setContentView方法
	 */
	private static void injectLayout(Activity activity){
		Class<? extends Activity> clazz = activity.getClass();
		InjectContentView contentView = clazz.getAnnotation(InjectContentView.class);
		if(contentView == null){
			return ;
		}
		int layoutId = contentView.value();
		//方法一：activity直接调用setContentView
		activity.setContentView(layoutId);
		//方法二：context通过反射获取setContentView方法后进行绑定
		//Method mth = clazz.getMethod("setContentView", int.class);
		//mth.invoke(activity, layoutId);

	}

	/**
	 * 注入控件
	 * 同理activity有findViewById方法，而context要通过反射获取findViewById方法
	 */
	private static void injectViews(Activity activity) {
		Class<? extends Activity> clazz = activity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		InjectView viewInject;
		View view;
		for (Field field : fields) {
			viewInject = field.getAnnotation(InjectView.class);
			if(viewInject != null){
				//方法一：activity直接调用findViewById
				view = activity.findViewById(viewInject.value());
				//方法二：context通过反射获取findViewById方法后进行赋值
				//method = clazz.getMethod("findViewById", int.class);
				//View view  = (View) method.invoke(context, ViewId);
				try {
					field.setAccessible(true);
					field.set(activity, view);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 注入事件
	 */
	private static void injectEvent(Activity activity) {
		Class<? extends Activity> clazz = activity.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			Annotation[] annotations = method.getAnnotations();

			for (Annotation annotation : annotations) {
				Class<?> annotationType = annotation.annotationType();
				InjectEventBus eventBus = annotationType.getAnnotation(InjectEventBus.class);
				if(eventBus == null){
					continue;
				}

				//获取三要素，即InjectEventBus中的三个参数，这三个参数为绑定事件的三要素。
				String listenerSetter = eventBus.listenerSetter();
				Class<?> listenerType = eventBus.listenerType();
				String callBackMethod = eventBus.callBackMethod();

				//获取控件
				try {
					Method valueMethod = annotationType.getDeclaredMethod("value");
					int[] viewIds = (int[]) valueMethod.invoke(annotation);
					for (int viewId : viewIds) {
						//这里传入activity和context处理方法不一样，可参考injectViews中
						View view = activity.findViewById(viewId);
						if(view == null){
							continue;
						}
						
						//获取原本的设置接口方法，点击时原本要响应接口的回调方法，现在通过代理模式让事件响应我们设置的方法。
						Method setListenerMtd = view.getClass().getMethod(listenerSetter, listenerType);
						//eventInvocationHandler中实现代理模式的核心
						EventInvocationHandler eventInvocationHandler = new EventInvocationHandler(activity, callBackMethod, method);
						Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, eventInvocationHandler);
						setListenerMtd.invoke(view, proxy);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}


}
