package com.gregtam.scanner.model;

import java.util.ArrayList;
import java.util.List;

public class DataNode
{
	private int type;

	private String relativePath;

	// the file its in
	private String fileName;
	private String fileInclude;

	private String className;

	private String functionName;
	private String functionCall;

	private String variableName;

	private int line;

	// location gives us line number
	private String location;

	private boolean isPossibleAttackVector;
	private boolean isModified;
	private boolean isModifier;

	private List<DataNode> children;

	public DataNode()
	{
		super();
		this.type = 0;
		this.relativePath = "";
		this.fileName = "";
		this.className = "";
		this.functionName = "";
		this.variableName = "";
		this.functionCall = "";
		this.fileInclude = "";
		this.line = 0;
		this.location = "";
		this.isModified = false;
		this.isModifier = false;
		this.isPossibleAttackVector = false;
		this.children = new ArrayList<DataNode>();
	}

	public int getType()
	{
		return type;
	}

	public String getFileInclude()
	{
		return fileInclude;
	}

	public void setFileInclude(String fileInclude)
	{
		this.fileInclude = fileInclude;
	}

	public String getFunctionCall()
	{
		return functionCall;
	}

	public void setFunctionCall(String functionCall)
	{
		this.functionCall = functionCall;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getRelativePath()
	{
		return relativePath;
	}

	public void setRelativePath(String relativePath)
	{
		this.relativePath = relativePath;
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

	public int getLine()
	{
		return line;
	}

	public void setLine(int line)
	{
		this.line = line;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public boolean isPossibleAttackVector()
	{
		return isPossibleAttackVector;
	}

	public void setPossibleAttackVector(boolean isPossibleAttackVector)
	{
		this.isPossibleAttackVector = isPossibleAttackVector;
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

	public void addChild(DataNode d)
	{
		if (d != null)
		{
			if (!this.children.contains(d))
			{
				this.children.add(d);
			}
		}
	}

	public void removeChild(DataNode d)
	{
		if (d != null)
		{
			if (this.children.contains(d))
			{
				this.children.remove(d);
			}
		}

	}

	/**
	 * @return the children
	 */
	public List<DataNode> getChildren()
	{
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<DataNode> children)
	{
		this.children = children;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result
				+ ((fileInclude == null) ? 0 : fileInclude.hashCode());
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((functionCall == null) ? 0 : functionCall.hashCode());
		result = prime * result
				+ ((functionName == null) ? 0 : functionName.hashCode());
		result = prime * result + (isModified ? 1231 : 1237);
		result = prime * result + (isModifier ? 1231 : 1237);
		result = prime * result + (isPossibleAttackVector ? 1231 : 1237);
		result = prime * result + line;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((relativePath == null) ? 0 : relativePath.hashCode());
		result = prime * result + type;
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
		DataNode other = (DataNode) obj;
		if (children == null)
		{
			if (other.children != null)
				return false;
		}
		else if (!children.equals(other.children))
			return false;
		if (className == null)
		{
			if (other.className != null)
				return false;
		}
		else if (!className.equals(other.className))
			return false;
		if (fileInclude == null)
		{
			if (other.fileInclude != null)
				return false;
		}
		else if (!fileInclude.equals(other.fileInclude))
			return false;
		if (fileName == null)
		{
			if (other.fileName != null)
				return false;
		}
		else if (!fileName.equals(other.fileName))
			return false;
		if (functionCall == null)
		{
			if (other.functionCall != null)
				return false;
		}
		else if (!functionCall.equals(other.functionCall))
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
		if (relativePath == null)
		{
			if (other.relativePath != null)
				return false;
		}
		else if (!relativePath.equals(other.relativePath))
			return false;
		if (type != other.type)
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
		return "DataNode [type="
				+ type
				+ ", "
				+ (relativePath != null ? "relativePath=" + relativePath + ", "
						: "")
				+ (fileName != null ? "fileName=" + fileName + ", " : "")
				+ (fileInclude != null ? "fileInclude=" + fileInclude + ", "
						: "")
				+ (className != null ? "className=" + className + ", " : "")
				+ (functionName != null ? "functionName=" + functionName + ", "
						: "")
				+ (functionCall != null ? "functionCall=" + functionCall + ", "
						: "")
				+ (variableName != null ? "variableName=" + variableName + ", "
						: "") + "line=" + line + ", "
				+ (location != null ? "location=" + location + ", " : "")
				+ "isPossibleAttackVector=" + isPossibleAttackVector
				+ ", isModified=" + isModified + ", isModifier=" + isModifier
				+ ", " + (children != null ? "children=" + children : "") + "]";
	}

}
