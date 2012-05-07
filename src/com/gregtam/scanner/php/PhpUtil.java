package com.gregtam.scanner.php;

import com.gregtam.scanner.util.ValidationUtil;

public class PhpUtil
{
	public static boolean isCode(String extension)
	{
		boolean isCode = false;

		if (ValidationUtil.isNotNullAndEmpty(extension))
		{
			String lower = extension.toLowerCase();

			isCode = PhpConstants.validExtentions.contains(lower);
		}

		return isCode;
	}
}
