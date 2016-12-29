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
	 * ע�벼��
	 * ���ﴫ��activityҪ�ȴ���context����������Щ
	 * ��Ϊactivity��setContentView��������contextҪͨ�������ȡsetContentView����
	 */
	private static void injectLayout(Activity activity){
		Class<? extends Activity> clazz = activity.getClass();
		InjectContentView contentView = clazz.getAnnotation(InjectContentView.class);
		if(contentView == null){
			return ;
		}
		int layoutId = contentView.value();
		//����һ��activityֱ�ӵ���setContentView
		activity.setContentView(layoutId);
		//��������contextͨ�������ȡsetContentView��������а�
		//Method mth = clazz.getMethod("setContentView", int.class);
		//mth.invoke(activity, layoutId);

	}

	/**
	 * ע��ؼ�
	 * ͬ��activity��findViewById��������contextҪͨ�������ȡfindViewById����
	 */
	private static void injectViews(Activity activity) {
		Class<? extends Activity> clazz = activity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		InjectView viewInject;
		View view;
		for (Field field : fields) {
			viewInject = field.getAnnotation(InjectView.class);
			if(viewInject != null){
				//����һ��activityֱ�ӵ���findViewById
				view = activity.findViewById(viewInject.value());
				//��������contextͨ�������ȡfindViewById��������и�ֵ
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
	 * ע���¼�
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

				//��ȡ��Ҫ�أ���InjectEventBus�е���������������������Ϊ���¼�����Ҫ�ء�
				String listenerSetter = eventBus.listenerSetter();
				Class<?> listenerType = eventBus.listenerType();
				String callBackMethod = eventBus.callBackMethod();

				//��ȡ�ؼ�
				try {
					Method valueMethod = annotationType.getDeclaredMethod("value");
					int[] viewIds = (int[]) valueMethod.invoke(annotation);
					for (int viewId : viewIds) {
						//���ﴫ��activity��context��������һ�����ɲο�injectViews��
						View view = activity.findViewById(viewId);
						if(view == null){
							continue;
						}
						
						//��ȡԭ�������ýӿڷ��������ʱԭ��Ҫ��Ӧ�ӿڵĻص�����������ͨ������ģʽ���¼���Ӧ�������õķ�����
						Method setListenerMtd = view.getClass().getMethod(listenerSetter, listenerType);
						//eventInvocationHandler��ʵ�ִ���ģʽ�ĺ���
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
