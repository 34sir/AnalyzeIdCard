// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.idcard;

import android.os.Environment;

public class GlobalData
{

	public static int ADDRESS = 4;
	public static int BIRTHDAY = 3;
	public static int FOLK = 2;
	public static String LOGPath;
	public static int NAME = 0;
	public static int NUM = 5;
	static String SDCARD_ROOT_PATH;
	public static int SEX = 1;
	public static int T_ONLY_CARD_NUM = 1;
	public static int T_SET_HEADIMG = 2;
	public static int T_SET_HEADIMGBUFMODE = 6;
	public static int T_SET_LOGPATH = 4;
	public static int T_SET_OPENORCLOSE_LOGPATH = 5;
	public static int T_SET_PRINTFLOG = 3;
	public static int isfirst = 0;
	public static int issue = 6;
	public static int m = 0;
	public static int n = 0;
	public static int period = 7;
	public static int x = 0;
	public static int y = 0;

	public GlobalData()
	{
	}

	static 
	{
		SDCARD_ROOT_PATH = Environment.getExternalStorageDirectory().getPath();
		LOGPath = (new StringBuilder(String.valueOf(SDCARD_ROOT_PATH))).append("/IDImage/slog.txt").toString();
	}
}
