package com.gregtam.scanner.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gregtam.scanner.model.FileObject;
import com.gregtam.scanner.util.ValidationUtil;

public class FileLookup
{
	private Map<String, FileObject> myMap;

	private static FileLookup _instance;

	private FileLookup()
	{
		myMap = new HashMap<String, FileObject>();
	}

	public synchronized static FileLookup getInstance()
	{
		if (_instance == null)
		{
			_instance = new FileLookup();
		}

		return _instance;
	}

	public void loadLookup(List<FileObject> input)
	{
		if (ValidationUtil.isNotNullAndEmpty(input))
		{
			for (FileObject fo : input)
			{
				if (fo != null)
				{
					String name = fo.getName();
					myMap.put(name, fo);
				}
			}
		}
	}

	public Map<String, FileObject> getMyMap()
	{
		return myMap;
	}

	public void setMyMap(Map<String, FileObject> myMap)
	{
		this.myMap = myMap;
	}

}
