package com.gregtam.scanner.util;

import java.util.List;

public class ValidationUtil
{
	public ValidationUtil()
	{

	}

	public static boolean isNotNullAndEmpty(String s)
	{
		if (s != null && !s.isEmpty())
		{
			return true;
		}

		return false;
	}

	public static boolean isNotNullAndEmptyTrim(String s)
	{
		if (s != null)
		{
			return isNotNullAndEmpty(s.trim());
		}

		return false;
	}

	public static boolean isNotNullAndEmpty(List<?> myList)
	{
		if (myList != null && !myList.isEmpty())
		{
			return true;
		}

		return false;
	}
}
