// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.idcard;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.UnsupportedEncodingException;

public class Demo {

	public Demo() {
	}

	public static native int FreeImage();

	public static native byte[] GetCopyrightInfo();

	public static native byte[] GetHeadImgBuf();

	public static native int GetHeadImgBufSize();

	public static native byte[] GetOCRFieldStringBuf(int i);

	public static native byte[] GetOCRStringBuf();

	public static native int LoadImage(String s);

	public static native int LoadMemBitMap(Bitmap bitmap);

	public static native int RECOCR();

	public static native int RECOCRBoot(Context context);

	public static native int SaveImage(String s);

	public static native int SetLOGPath(String s);

	public static native int SetParam(int i, int j);

	public static native int TerminateOCRHandle();

	public String GetFieldString(int i) {
		byte abyte0[] = GetOCRFieldStringBuf(i);
		String s;
		try {
			s = new String(abyte0, "GB2312");
		} catch (UnsupportedEncodingException unsupportedencodingexception) {
			unsupportedencodingexception.printStackTrace();
			return null;
		}
		return s;
	}

	public String GetTheIDcardEngineCopyrightInfo() {
		byte abyte0[] = GetCopyrightInfo();
		String s;
		try {
			s = new String(abyte0, "GB2312");
		} catch (UnsupportedEncodingException unsupportedencodingexception) {
			unsupportedencodingexception.printStackTrace();
			return null;
		}
		return s;
	}

	public int RunOCR(Bitmap bitmap, byte abyte0[]) throws UnsupportedEncodingException {
		if (LoadMemBitMap(bitmap) != 1) {
			return 0;
		} else {
			int i = RECOCR();
			Log.d("IDCARD", "RECOCR:" + i);
			FreeImage();
			return i;
		}
	}

	public String TestEngineDemo(Context context, Bitmap bitmap) throws UnsupportedEncodingException {
		Demo demo = new Demo();
		if (demo.initengine(context) != 1) {
			System.out.println("????????????");
			return new String("????????????");
		}
		if (demo.RunOCR(bitmap, null) == 1) {
			byte abyte0[] = GetOCRStringBuf();
			demo.uinitengine();
			return new String(abyte0, "GB2312");
		} else {
			System.out.println("???????");
			return new String("???????");
		}
	}

	public int initengine(Context context) {
		return RECOCRBoot(context);
	}

	public int uinitengine() {
		return TerminateOCRHandle();
	}

	static {
		System.loadLibrary("IDCARDDLL");
	}
}
