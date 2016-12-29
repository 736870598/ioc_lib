package com.sunxiaoyu.ioc.test;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sunxiaoyu.ioc.activity.InjectBaseActivity;
import com.sunxiaoyu.ioc.annotation.InjectCBOnCheckedChange;
import com.sunxiaoyu.ioc.annotation.InjectContentView;
import com.sunxiaoyu.ioc.annotation.InjectOnClick;
import com.sunxiaoyu.ioc.annotation.InjectOnLongClick;
import com.sunxiaoyu.ioc.annotation.InjectRBOnCheckedChange;
import com.sunxiaoyu.ioc.annotation.InjectView;
import com.sunxiaoyu.ioc_lib.R;

@InjectContentView(R.layout.activity_main)
public class MainActivity extends InjectBaseActivity {

	@InjectView(R.id.tv)
	private TextView tv;
	@InjectView(R.id.cb)
	private CheckBox cb;


	@InjectRBOnCheckedChange({R.id.radiobtn1,R.id.radiobtn2})
	public void radioGroupClick(RadioGroup arg0, int arg1){
		tv.setText("点击了：" + arg1);
	}
	
	@InjectCBOnCheckedChange({R.id.cb,R.id.tb})
	public void CompoundButtonClick(CompoundButton arg0, boolean arg1){
		tv.setText(arg0.getId() + "状态：" + arg1);
	}
	
	@InjectOnLongClick({R.id.btn, R.id.btn_long})
	public boolean onLongClick(View view){
		switch (view.getId()) {
		case R.id.btn:
			tv.setText("点击了：Long btn");
			break;
		default:
			tv.setText("点击了：Long btn_long");
			break;
		}
		return true;
	}
	
	@InjectOnClick({R.id.btn, R.id.btn_long})
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.btn:
			tv.setText("点击了：btn");
			break;
		default:
			tv.setText("点击了：btn_long");
			break;
		}
	}
	
	
	
	
	
	



}
