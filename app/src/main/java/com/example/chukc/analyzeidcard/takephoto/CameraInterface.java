package com.example.chukc.analyzeidcard.takephoto;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Button;
import android.widget.Toast;

import com.example.chukc.analyzeidcard.camera.AutoFocusCallback;
import com.example.chukc.analyzeidcard.camera.CameraConfigurationManager;
import com.idcard.OcrUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("deprecation")
public class CameraInterface {
	private static final String TAG = "YanZi";
	private Camera mCamera;
	private boolean isPreviewing = false;
	private static CameraInterface mCameraInterface;
	private Context context;
	private AutoFocusCallback autoFocusCallback;
	private AutoFocusHandler autoFocusHandler;
    private CameraConfigurationManager configurationManager;
    private int id;
    private Thread openThread;
    private boolean isFinished=false;
    private  String name;
    private String idcode;

    private boolean isTaked;
	/**
	 * 闇�瑕佸鐒�
	 * @param handler
	 * @param message
	 */
	public void requestAutoFocus(Handler handler, int message) {

		if (mCamera != null ) {
			autoFocusCallback.setHandler(handler, message);
			mCamera.autoFocus(autoFocusCallback);

			try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

		}
	}

	public boolean getIsTaked(){
		return isTaked;
	}



	public interface CamOpenOverCallback {
		public void cameraHasOpened();
	}

	private CameraInterface() {

	}
	public Camera getCamera(){
		return mCamera;

	}

	public static synchronized CameraInterface getInstance() {
		if (mCameraInterface == null) {
			mCameraInterface = new CameraInterface();
		}
		return mCameraInterface;
	}


	public boolean doOpenCamera(Context context) {
		boolean isopen = false;
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
		this.context = context;

		if(mCamera==null){
			try {
				mCamera = Camera.open();
				this.configurationManager=new CameraConfigurationManager(context);
				configurationManager.initFromCameraParameters(mCamera);
				configurationManager.setDesiredCameraParameters(mCamera);
				autoFocusCallback=new AutoFocusCallback();
				isopen=true;
			} catch (Exception e) {
				// TODO: handle exception

				mCamera=null;
				isopen=false;
			}

		}

		return isopen;

	}

	public void doStartPreview(SurfaceHolder holder, float previewRate) {
		if (isPreviewing) {
			mCamera.stopPreview();
			return;
		}
		if (mCamera != null) {
			try {
				mCamera.setPreviewDisplay(holder);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initCamera(previewRate);
		}

	}

	@SuppressLint("NewApi")
	public void doStartPreview(SurfaceTexture surface, float previewRate) {
		Log.i(TAG, "doStartPreview...");
		if (isPreviewing) {
			mCamera.stopPreview();
			return;
		}
		if (mCamera != null) {
			try {
				mCamera.setPreviewTexture(surface);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initCamera(previewRate);
		}

	}

	public void doStopCamera() {
		if (null != mCamera) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			isPreviewing = false;
			mCamera.release();
			mCamera = null;
		}
	}


	int DST_RECT_WIDTH, DST_RECT_HEIGHT;

	public void doTakePicture(int w, int h, Context context,Button button,int id) {
		this.id=id;
		autoFocusHandler.quitSynchronously();
		if (isPreviewing && (mCamera != null)) {
			DST_RECT_WIDTH = w;
			DST_RECT_HEIGHT = h;
			button.setText("解析中...");
			button.setEnabled(false);
			isTaked=true;
			mCamera.takePicture(mShutterCallback, null,
					mRectJpegPictureCallback);
		}
	}

	public Point doGetPrictureSize() {
		Size s = mCamera.getParameters().getPictureSize();
		return new Point(s.width, s.height);
	}


	private void initCamera(float previewRate) {
		if (mCamera != null) {
			mCamera.startPreview();
			autoFocusHandler=new AutoFocusHandler();
			isPreviewing = true;
		}
	}

	ShutterCallback mShutterCallback = new ShutterCallback() {
		public void onShutter() {
			// TODO Auto-generated method stub
			Log.i(TAG, "myShutterCallback:onShutter...");
		}
	};
	PictureCallback mRawCallback = new PictureCallback() {

		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			Log.i(TAG, "myRawCallback:onPictureTaken...");

		}
	};
	PictureCallback mJpegPictureCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			Log.i(TAG, "myJpegCallback:onPictureTaken...");
			Bitmap b = null;
			if (null != data) {
				b = BitmapFactory.decodeByteArray(data, 0, data.length);
				mCamera.stopPreview();
				isPreviewing = false;
			}
			if (null != b) {
				ImageUtil.getRotateBitmap(b, 90.0f);
			}
			mCamera.startPreview();
			isPreviewing = true;
		}
	};

	public void RemoveMessage(){
		autoFocusHandler.quitSynchronously();
	}

	PictureCallback mRectJpegPictureCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			Log.i(TAG, "myJpegCallback:onPictureTaken...");
			Bitmap b = null;
			if (null != data) {
				b = BitmapFactory.decodeByteArray(data, 0, data.length);
				mCamera.stopPreview();
				isPreviewing = false;
			}
			if (null != b) {
				Matrix matrix = new Matrix();
				matrix.postRotate((float) 90.0);
				Bitmap rectBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), matrix, false);
				// savePhoto(rectBitmap); //
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				rectBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds=false;
				options.inSampleSize=4;//
				options.inPreferredConfig = Bitmap.Config.RGB_565;
				final Bitmap bitmap = BitmapFactory.decodeStream(sbs, null, options);

				final OcrUtility ocrEngin = new OcrUtility(context);


				 openThread = new Thread(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Looper.prepare();
						isFinished=false;
						ocrEngin.ocrIDCard(bitmap);
						idcode = OcrUtility.cardinfo.getCardNum();
						 name = OcrUtility.cardinfo.getName();
						if (idcode != null && !"".equals(idcode)) {

						    getMemberTrueName(idcode, name);
						    Toast.makeText((Activity) context, "idcode="+idcode+"name"+name, Toast.LENGTH_LONG).show();;

						}else{
							((Activity) context).finish();
						}
//						mCamera.startPreview();
						isFinished=true;
						Looper.loop();
					}
				};
				openThread.start();
//				ocrEngin.ocrIDCard(bitmap);
//
//				final String idcode = OcrUtility.cardinfo.getCardNum();
//				final String name = OcrUtility.cardinfo.getName();
//
//				System.out.println("idcode=====================" + idcode);
//
//				if (idcode != null && !"".equals(idcode)) {
//					Intent intent = new Intent();
//					intent.putExtra("id", id);
//					intent.putExtra("name", name);
//					intent.putExtra("idcode", idcode);
//					((Activity)context).setResult(((Activity)context).RESULT_OK, intent);
//					((Activity)context).finish();
//				}else{
//					((BaseActivity) context).showToast("鍥剧墖鏃犳晥锛岃閲嶆柊鎷嶆憚");
//					((Activity) context).finish();
//				}
			}

			isPreviewing = true;
			if (!b.isRecycled()) {
				b.recycle();
				b = null;
				 System.gc();  //鎻愰啋绯荤粺鍙婃椂鍥炴敹
			}

		}
	};

	public boolean isFinished(){
		return isFinished;
	}

	/**
	 * 楠岃瘉鐢ㄦ埛鐪熷疄濮撳悕
	 * @param idCardNumber
	 * @param name_saomiao
	 */
	public void getMemberTrueName(final String idCardNumber,final String name_saomiao ){
	    JSONObject params=new JSONObject();
	    try {
		params.put("idCardNumber",idCardNumber);
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}

	public void savePhoto(Bitmap bm) {
		String rootPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/Qridcode/";
		String filePath = rootPath + "Qridcode.jpg";
		File root = new File(rootPath);
		if (!root.exists()) {
			root.mkdirs();
		}
		File f = new File(filePath);
		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
