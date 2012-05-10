package com.gregtam.scanner.model;

public class MetaData implements Cloneable
{
	private boolean isModified;
	private boolean isModifier;

	private String relativePath;

	private String fileName;
	private String className;
	private String functionName;

	private String variableName;

	public MetaData()
	{
		this.isModified = false;
		this.isModifier = false;
		this.fileName = "";
		this.className = "";
		this.functionName = "";
		this.variableName = "";
		this.relativePath = "";
	}

	public String getRelativePath()
	{
		return relativePath;
	}

	public void setRelativePath(String relativePath)
	{
		this.relativePath = relativePath;
	}

	public boolean isModified()
	{
		return isModified;
	}

	public void setModified(boolean isModified)
	{
		this.isModified = isModified;
	}

	public boolean isModifier()
	{
		return isModifier;
	}

	public void setModifier(boolean isModifier)
	{
		this.isModifier = isModifier;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public String getFunctionName()
	{
		return functionName;
	}

	public void setFunctionName(String functionName)
	{
		this.functionName = functionName;
	}

	public String getVariableName()
	{
		return variableName;
	}

	public void setVariableName(String variableName)
	{
		this.variableName = variableName;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((functionName == null) ? 0 : functionName.hashCode());
		result = prime * result + (isModified ? 1231 : 1237);
		result = prime * result + (isModifier ? 1231 : 1237);
		result = prime * result
				+ ((relativePath == null) ? 0 : relativePath.hashCode());
		result = prime * result
				+ ((variableName == null) ? 0 : variableName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MetaData other = (MetaData) obj;
		if (className == null)
		{
			if (other.className != null)
				return false;
		}
		else if (!className.equals(other.className))
			return false;
		if (fileName == null)
		{
			if (other.fileName != null)
				return false;
		}
		else if (!fileName.equals(other.fileName))
			return false;
		if (functionName == null)
		{
			if (other.functionName != null)
				return false;
		}
		else if (!functionName.equals(other.functionName))
			return false;
		if (isModified != other.isModified)
			return false;
		if (isModifier != other.isModifier)
			return false;
		if (relativePath == null)
		{
			if (other.relativePath != null)
				return false;
		}
		else if (!relativePath.equals(other.relativePath))
			return false;
		if (variableName == null)
		{
			if (other.variableName != null)
				return false;
		}
		else if (!variableName.equals(other.variableName))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "MetaData [isModified="
				+ isModified
				+ ", isModifier="
				+ isModifier
				+ ", "
				+ (relativePath != null ? "relativePath=" + relativePath + ", "
						: "")
				+ (fileName != null ? "fileName=" + fileName + ", " : "")
				+ (className != null ? "className=" + className + ", " : "")
				+ (functionName != null ? "functionName=" + functionName + ", "
						: "")
				+ (variableName != null ? "variableName=" + variableName : "")
				+ "]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

}
