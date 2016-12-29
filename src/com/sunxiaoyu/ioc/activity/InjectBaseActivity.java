package com.sunxiaoyu.ioc.activity;

import android.app.Activity;
import android.os.Bundle;

import com.sunxiaoyu.ioc.utils.InjectUtils;


/**
 * 所有使用ioc的activity必须继承该Activity或者在onCreate中实现InjectUtils.inject(this);
 * @author 孙晓宇
 */
public class InjectBaseActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		InjectUtils.inject(this);
	}

}
