package com.gregtam.scanner.model;

import com.gregtam.scanner.graph.GraphConstants;

public class DataObject
{
	// type of object
	private int type;

	// the key to this var
	private String name;

	private String relativePath;

	// the file its in
	private String fileName;

	private int line;
	// location gives us line number
	private String location;

	private boolean isPossibleAttackVector;

	// full name will be the key

	public DataObject()
	{
		this.type = GraphConstants.TYPE_INVALID;
		this.name = "";
		this.relativePath = "";
		this.fileName = "";
		this.line = 0;
		this.location = "";
		this.isPossibleAttackVector = false;
	}

	/**
	 * @return the relativePath
	 */
	public String getRelativePath()
	{
		return relativePath;
	}

	/**
	 * @param relativePath
	 *            the relativePath to set
	 */
	public void setRelativePath(String relativePath)
	{
		this.relativePath = relativePath;
	}

	/**
	 * @return the line
	 */
	public int getLine()
	{
		return line;
	}

	/**
	 * @param line
	 *            the line to set
	 */
	public void setLine(int line)
	{
		this.line = line;
	}

	/**
	 * @return the isPossibleAttackVector
	 */
	public boolean isPossibleAttackVector()
	{
		return isPossibleAttackVector;
	}

	/**
	 * @param isPossibleAttackVector
	 *            the isPossibleAttackVector to set
	 */
	public void setPossibleAttackVector(boolean isPossibleAttackVector)
	{
		this.isPossibleAttackVector = isPossibleAttackVector;
	}

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
		result = prime * result + (isPossibleAttackVector ? 1231 : 1237);
		result = prime * result + line;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((relativePath == null) ? 0 : relativePath.hashCode());
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
		if (isPossibleAttackVector != other.isPossibleAttackVector)
			return false;
		if (line != other.line)
			return false;
		if (location == null)
		{
			if (other.location != null)
				return false;
		}
		else if (!location.equals(other.location))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (relativePath == null)
		{
			if (other.relativePath != null)
				return false;
		}
		else if (!relativePath.equals(other.relativePath))
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
		return "DataObject [type="
				+ type
				+ ", "
				+ (name != null ? "name=" + name + ", " : "")
				+ (relativePath != null ? "relativePath=" + relativePath + ", "
						: "")
				+ (fileName != null ? "fileName=" + fileName + ", " : "")
				+ "line=" + line + ", "
				+ (location != null ? "location=" + location + ", " : "")
				+ "isPossibleAttackVector=" + isPossibleAttackVector + "]";
	}

}
