package com.example.chukc.analyzeidcard.takephoto;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "YanZi";
	CameraInterface mCameraInterface;
	Context mContext;
	SurfaceHolder mSurfaceHolder;
	boolean isOpen=true;
	@SuppressWarnings("deprecation")
	public CameraSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		mSurfaceHolder = getHolder();
		mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mSurfaceHolder.addCallback(this);
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.i(TAG, "surfaceCreated...");
		isOpen=CameraInterface.getInstance().doOpenCamera(mContext);
		if(!isOpen){
			Activity activity=(Activity) mContext;
			new Toast(activity).makeText(activity, "请打开相机", Toast.LENGTH_LONG).show();;
			activity.finish();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Log.i(TAG, "surfaceChanged...");
			CameraInterface.getInstance().doStartPreview(holder, DisplayUtil.getScreenRate(mContext));
//		CameraInterface.getInstance().setAutoFocus();    //绗竴娆¤缃鐒�
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.i(TAG, "surfaceDestroyed...");
		if(isOpen){
			CameraInterface.getInstance().RemoveMessage();
		}
		CameraInterface.getInstance().doStopCamera();
	}
	public SurfaceHolder getSurfaceHolder(){
		return mSurfaceHolder;
	}

}
