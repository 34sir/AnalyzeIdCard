package com.example.chukc.analyzeidcard;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chukc.analyzeidcard.takephoto.CameraInterface;
import com.example.chukc.analyzeidcard.takephoto.CameraSurfaceView;
import com.example.chukc.analyzeidcard.takephoto.DisplayUtil;


public class TakePhotoActivity extends Activity {
	private Button button_paizhao;
	private CameraSurfaceView surfaceView = null;
	private boolean isShowNotification=true;

	public TakePhotoActivity getActivity() {
		return this;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takephoto_s);
		init();
		initViewParams();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		CameraInterface.getInstance().doStopCamera();
		finish();
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@SuppressWarnings("deprecation")
	public void init() {
		backShow();
		button_paizhao=(Button) findViewById(R.id.button_paizhao);
		surfaceView = (CameraSurfaceView) findViewById(R.id.capture_preview);


		getWindowManager().getDefaultDisplay().getWidth();


		View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);


		button_paizhao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CameraInterface.getInstance().doTakePicture(0,
						0, TakePhotoActivity.this,button_paizhao,getIntent().getIntExtra("id", 0));

			}
		});
	}


	private void initViewParams() {
		LayoutParams params = surfaceView.getLayoutParams();
		Point p = DisplayUtil.getScreenMetrics(this);
		params.width = p.x;
		params.height = p.y;
		System.out.println("params.width=====================" + params.width);
		System.out.println("params.height====================" + params.height);
		DisplayUtil.getScreenRate(this);
		surfaceView.setLayoutParams(params);
	}

	@Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	  // TODO Auto-generated method stub
	  if(keyCode == KeyEvent.KEYCODE_BACK){
		  if( CameraInterface.getInstance().isFinished() ||!CameraInterface.getInstance().getIsTaked()){
			  finish();
		  }else{
			  if(isShowNotification){
				  isShowNotification=false;
			  }
			  return true;
		  }
	  }
	  return super.onKeyDown(keyCode, event);
	 }

	public void backShow() {
		LinearLayout ll_back = (LinearLayout) findViewById(R.id.ll_back);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("身份证拍照");
		ll_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				  if( CameraInterface.getInstance().isFinished()|| !CameraInterface.getInstance().getIsTaked()){
					  finish();
				  }else{
					  if(isShowNotification){
						  isShowNotification=false;
					  }
				  }
			}
		});
	}
}
