package com.sunxiaoyu.ioc.activity;

import android.app.Activity;
import android.os.Bundle;

import com.sunxiaoyu.ioc.utils.InjectUtils;


/**
 * ����ʹ��ioc��activity����̳и�Activity������onCreate��ʵ��InjectUtils.inject(this);
 * @author ������
 */
public class InjectBaseActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		InjectUtils.inject(this);
	}

}
