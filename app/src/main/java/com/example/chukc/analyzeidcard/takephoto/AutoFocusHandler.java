package com.example.chukc.analyzeidcard.takephoto;

import android.os.Handler;
import android.os.Message;

import com.example.chukc.analyzeidcard.R;


public class AutoFocusHandler extends Handler{
	 public AutoFocusHandler() {
		// TODO Auto-generated constructor stub
		 requestAutoFocus();
	}

	@Override
	public void handleMessage(Message message) {

		switch (message.what) {
		case R.id.auto_focus:
			CameraInterface.getInstance().requestAutoFocus(this, R.id.auto_focus);

			break;
		}
	}

	public void quitSynchronously() {
		removeMessages(R.id.auto_focus);
	}

	/**
	 * 闇�瑕佸鐒�
	 */
	public void requestAutoFocus() {

			CameraInterface.getInstance().requestAutoFocus(this, R.id.auto_focus);
		}

}
