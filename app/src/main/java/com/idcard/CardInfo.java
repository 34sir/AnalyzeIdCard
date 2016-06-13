// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.idcard;

import java.io.Serializable;

public class CardInfo
	implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7328459486719291300L;
	String Address;
	String BirthDay;
	String CardNum;
	String folk;
	String headPath;
	boolean isSelect;
	boolean isShow;
	boolean isUpdate;
	String issue;
	String name;
	String period;
	String sex;
	String smallHeadPath;

	public CardInfo()
	{
	}

	public String getAddress()
	{
		return Address;
	}

	public String getBirthDay()
	{
		return BirthDay;
	}

	public String getCardNum()
	{
		return CardNum;
	}

	public String getFolk()
	{
		return folk;
	}

	public String getHeadPath()
	{
		return headPath;
	}

	public String getIssue()
	{
		return issue;
	}

	public String getName()
	{
		return name;
	}

	public String getPeriod()
	{
		return period;
	}

	public String getSex()
	{
		return sex;
	}

	public String getSmallHeadPath()
	{
		return smallHeadPath;
	}

	public boolean isSelect()
	{
		return isSelect;
	}

	public boolean isShow()
	{
		return isShow;
	}

	public boolean isUpdate()
	{
		return isUpdate;
	}

	public void setAddress(String s)
	{
		Address = s;
	}

	public void setBirthDay(String s)
	{
		BirthDay = s;
	}

	public void setCardNum(String s)
	{
		CardNum = s;
	}

	public void setFolk(String s)
	{
		folk = s;
	}

	public void setHeadPath(String s)
	{
		headPath = s;
	}

	public void setIssue(String s)
	{
		issue = s;
	}

	public void setName(String s)
	{
		name = s;
	}

	public void setPeriod(String s)
	{
		period = s;
	}

	public void setSelect(boolean flag)
	{
		isSelect = flag;
	}

	public void setSex(String s)
	{
		sex = s;
	}

	public void setShow(boolean flag)
	{
		isShow = flag;
	}

	public void setSmallHeadPath(String s)
	{
		smallHeadPath = s;
	}

	public void setUpdate(boolean flag)
	{
		isUpdate = flag;
	}
}
