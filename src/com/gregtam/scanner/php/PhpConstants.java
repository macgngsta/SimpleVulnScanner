package com.gregtam.scanner.php;

import java.util.Arrays;
import java.util.List;

public class PhpConstants
{
	public static final List<String> validExtentions = Arrays.asList("php",
			"php5", "inc");

	public static final List<String> possibleSurfaceVectors = Arrays.asList(
			"$_POST", "$_GET");
}
