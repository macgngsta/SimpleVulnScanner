package com.gregtam.scanner.model;

public class FileObject
{
	private String relPath;
	private String absPath;
	private String name;
	private String ext;

	public FileObject()
	{
		this.absPath = "";
		this.name = "";
		this.ext = "";
	}

	public FileObject(String absPath, String relPath, String name, String ext)
	{
		super();
		this.absPath = absPath;
		this.relPath = relPath;
		this.name = name;
		this.ext = ext;
	}

	/**
	 * @return the relPath
	 */
	public String getRelPath()
	{
		return relPath;
	}

	/**
	 * @param relPath
	 *            the relPath to set
	 */
	public void setRelPath(String relPath)
	{
		this.relPath = relPath;
	}

	/**
	 * @return the absPath
	 */
	public String getAbsPath()
	{
		return absPath;
	}

	/**
	 * @param absPath
	 *            the absPath to set
	 */
	public void setAbsPath(String absPath)
	{
		this.absPath = absPath;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((absPath == null) ? 0 : absPath.hashCode());
		result = prime * result + ((ext == null) ? 0 : ext.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((relPath == null) ? 0 : relPath.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileObject other = (FileObject) obj;
		if (absPath == null)
		{
			if (other.absPath != null)
				return false;
		}
		else if (!absPath.equals(other.absPath))
			return false;
		if (ext == null)
		{
			if (other.ext != null)
				return false;
		}
		else if (!ext.equals(other.ext))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (relPath == null)
		{
			if (other.relPath != null)
				return false;
		}
		else if (!relPath.equals(other.relPath))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "FileObject ["
				+ (relPath != null ? "relPath=" + relPath + ", " : "")
				+ (absPath != null ? "absPath=" + absPath + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (ext != null ? "ext=" + ext : "") + "]";
	}

}
