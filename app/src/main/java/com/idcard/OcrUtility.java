package com.idcard;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class OcrUtility {
	protected Context context = null;
	public static Demo ocrEngine = new Demo();
	protected static String mCurrentPhotoFile;

	public static CardInfo cardinfo ;

	public OcrUtility(Context context) {
		this.context = context;
	}

	public void ocrIDCard(Bitmap bitmap) {
		System.out.println("========ocrUtility:bitmap:"+bitmap);
		int result = 0;
		try {
			int rtn = ocrEngine.initengine(context);
			if (rtn != 1) {
				Toast.makeText(context, "engine init error:" + rtn, Toast.LENGTH_LONG).show();
				Log.e("IDCARD", "engine init error:" + rtn);
			}
			result = ocrEngine.RunOCR(bitmap, null);
			System.out.println("========ocrUtility::"+result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (result == 1) {
			String s = "";
			cardinfo = new CardInfo();
			cardinfo.setName(ocrEngine.GetFieldString(GlobalData.NAME));
			cardinfo.setSex(ocrEngine.GetFieldString(GlobalData.SEX));
			cardinfo.setFolk(ocrEngine.GetFieldString(GlobalData.FOLK));
			cardinfo.setBirthDay(ocrEngine.GetFieldString(GlobalData.BIRTHDAY));
			cardinfo.setAddress(ocrEngine.GetFieldString(GlobalData.ADDRESS));
			cardinfo.setCardNum(ocrEngine.GetFieldString(GlobalData.NUM));
			cardinfo.setIssue(ocrEngine.GetFieldString(GlobalData.issue));
			cardinfo.setPeriod(ocrEngine.GetFieldString(GlobalData.period));
			cardinfo.setHeadPath(s);
		} else {
		}
	}

}
