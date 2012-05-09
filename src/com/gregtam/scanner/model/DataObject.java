package com.gregtam.scanner.model;

import com.gregtam.scanner.graph.GraphConstants;

public class DataObject
{
	private int type;
	private String name;
	private String fileName;
	private String location;

	/**
	 * @return the location
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}

	private boolean modifiesChild;

	public DataObject()
	{
		this.type = GraphConstants.TYPE_INVALID;
		this.name = "";
		this.fileName = "";
		this.modifiesChild = false;
		this.location = "";
	}

	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * @return the modifiesChild
	 */
	public boolean isModifiesChild()
	{
		return modifiesChild;
	}

	/**
	 * @param modifiesChild
	 *            the modifiesChild to set
	 */
	public void setModifiesChild(boolean modifiesChild)
	{
		this.modifiesChild = modifiesChild;
	}

	public boolean isModify()
	{
		return modifiesChild;
	}

	public void setModify(boolean isModify)
	{
		this.modifiesChild = isModify;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + (modifiesChild ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + type;
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
		DataObject other = (DataObject) obj;
		if (fileName == null)
		{
			if (other.fileName != null)
				return false;
		}
		else if (!fileName.equals(other.fileName))
			return false;
		if (location == null)
		{
			if (other.location != null)
				return false;
		}
		else if (!location.equals(other.location))
			return false;
		if (modifiesChild != other.modifiesChild)
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (type != other.type)
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
		return "DataObject [type=" + type + ", "
				+ (name != null ? "name=" + name + ", " : "")
				+ (fileName != null ? "fileName=" + fileName + ", " : "")
				+ (location != null ? "location=" + location + ", " : "")
				+ "modifiesChild=" + modifiesChild + "]";
	}

}
