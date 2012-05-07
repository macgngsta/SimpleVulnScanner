package com.gregtam.scanner.util;

public class FileObject
{

	private String path;
	private String name;
	private String ext;
	private String nodeName;

	public FileObject()
	{
		this.path = "";
		this.name = "";
		this.ext = "";
	}

	public FileObject(String path, String name, String ext)
	{
		super();
		this.path = path;
		this.name = name;
		this.ext = ext;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getExt()
	{
		return ext;
	}

	public void setExt(String ext)
	{
		this.ext = ext;
	}

}
