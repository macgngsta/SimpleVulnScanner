package com.gregtam.scanner.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gregtam.scanner.ScannerConstants;
import com.gregtam.scanner.php.PhpUtil;

public class FileUtil
{
	static Logger logger = Logger.getLogger(FileUtil.class.getName());

	public FileUtil()
	{

	}

	public static List<FileObject> getAllFiles(String filePath)
	{
		List<FileObject> myFiles = new ArrayList<FileObject>();

		if (ValidationUtil.isNotNullAndEmpty(filePath))
		{
			File rootFile = new File(filePath);
			readFile(rootFile, myFiles);
		}

		return myFiles;
	}

	private static void readFile(File testFile, List<FileObject> myList)
	{
		if (testFile != null)
		{
			if (testFile.isDirectory())
			{
				File[] files = testFile.listFiles();
				if (files != null && files.length > 0)
				{
					for (File f : files)
					{
						readFile(f, myList);
					}
				}
			}
			else
			{

				String path = testFile.getAbsolutePath();
				String name = testFile.getName();
				String ext = FileUtil.getExtension(testFile);

				if (PhpUtil.isCode(ext))
				{
					FileObject fo = new FileObject(path, name, ext);
					myList.add(fo);
				}
			}
		}
	}

	public static String getExtension(File f)
	{
		StringBuilder sb = new StringBuilder();

		if (f != null)
		{
			if (!f.isDirectory())
			{
				String temp = f.getName();
				if (ValidationUtil.isNotNullAndEmpty(temp))
				{
					String[] split = temp.split(ScannerConstants.ESCAPE_REGEX
							+ ScannerConstants.PERIOD);
					if (split != null && split.length > 1)
					{
						int lastItem = split.length - 1;
						if (lastItem > 0)
						{
							String item = split[lastItem];
							if (ValidationUtil.isNotNullAndEmpty(item))
							{
								sb.append(item);
							}
						}
					}
				}
			}
		}

		return sb.toString();
	}

}
