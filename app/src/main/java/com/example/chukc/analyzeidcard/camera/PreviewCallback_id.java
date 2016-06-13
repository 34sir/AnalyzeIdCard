package com.example.chukc.analyzeidcard.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.idcard.OcrUtility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@SuppressWarnings("deprecation")
final  class PreviewCallback_id implements Camera.PreviewCallback {
//	private static final String TAG = PreviewCallback.class.getSimpleName();

	OcrUtility ocrEngin;

	int framecount = 0;
	String rootPath;
	private Handler previewHandler;
	private int previewMessage;
	private final boolean useOneShotPreviewCallback;
	private final CameraConfigurationManager_id configManager_id;

	PreviewCallback_id(CameraConfigurationManager_id configManager_id,
			boolean useOneShotPreviewCallback, Context context) {
		this.configManager_id = configManager_id;
		ocrEngin = new OcrUtility(context);
		this.useOneShotPreviewCallback = useOneShotPreviewCallback;

	}

	void setHandler(Handler previewHandler, int previewMessage) {
		this.previewHandler = previewHandler;
		this.previewMessage = previewMessage;
	}



	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
	    Point cameraResolution = configManager_id.getCameraResolution();
	    if (!useOneShotPreviewCallback) {
	      camera.setPreviewCallback(null);
	    }
	    if (previewHandler != null) {
	    	getbit(cameraResolution,data,camera);
//	      Message message = previewHandler.obtainMessage(previewMessage, cameraResolution.x,
//	          cameraResolution.y, data);
	      Message message = previewHandler.obtainMessage(previewMessage, cameraResolution.x,
	    		  cameraResolution.y, getbit(cameraResolution,data,camera));
	      message.sendToTarget();
	      System.out.println("message.sendToTarget()====================");
	      previewHandler = null;
	    } else {
	    	System.out.println("Got preview callback, but no handler for it");
//	      Log.d(TAG, "Got preview callback, but no handler for it");
	    }

	  }
	private Bitmap getbit(Point cameraResolution,byte[] data, Camera camera) {
		Long time1 = new Date().getTime();
		Bitmap bitmap = null;
		Size size = camera.getParameters().getPreviewSize();
        try{
            YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
            if(image!=null){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);
//                bitmap = Bitmap.createBitmap(width, height, config)
                bitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                System.out.println("=======bitmap锛氾細"+bitmap);
                stream.close();

                baocun(bitmap);
            }
        }catch(Exception ex){
            Log.e("Sys","Error:"+ex.getMessage());
        }

			Long time2 = new Date().getTime();
	    System.out.println("瑙ｆ瀽鏃堕棿锛氾細"+(time2-time1));
        return bitmap;
	}


	private void baocun(Bitmap bm){
		String rootPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/Qridcode/";
		String  filePath = rootPath + "Yuv.jpg";
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
			// Bitmap bm=BitmapFactory.decodeByteArray(data, 0,
			// data.length);
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
