package com.gregtam.scanner.php;

import com.gregtam.scanner.util.ValidationUtil;

public class SurfaceVectorUtil
{
	public static boolean isPossibleSurfaceVector(String input)
	{
		if (ValidationUtil.isNotNullAndEmpty(input))
		{
			for (String s : PhpConstants.possibleSurfaceVectors)
			{
				if (input.startsWith(s))
				{
					return true;
				}
			}
		}

		return false;
	}
}
