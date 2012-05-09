package com.gregtam.scanner.model;

public class InterestingInfo
{
	public static final int INVALID_INT = -1;

	private int tokenIndex;
	private int line;
	private int charPosition;
	private String tokenKey;
	private String value;
	private int previousTokenIndex;

	public InterestingInfo()
	{
		this.tokenIndex = INVALID_INT;
		this.line = INVALID_INT;
		this.charPosition = INVALID_INT;
		this.previousTokenIndex = INVALID_INT;
		this.tokenKey = "";
		this.value = "";
	}

	public int getPreviousTokenIndex()
	{
		return previousTokenIndex;
	}

	public void setPreviousTokenIndex(int previousTokenIndex)
	{
		this.previousTokenIndex = previousTokenIndex;
	}

	public int getTokenIndex()
	{
		return tokenIndex;
	}

	public void setTokenIndex(int tokenIndex)
	{
		this.tokenIndex = tokenIndex;
	}

	public int getLine()
	{
		return line;
	}

	public void setLine(int line)
	{
		this.line = line;
	}

	public int getCharPosition()
	{
		return charPosition;
	}

	public void setCharPosition(int charPosition)
	{
		this.charPosition = charPosition;
	}

	public String getTokenKey()
	{
		return tokenKey;
	}

	public void setTokenKey(String tokenKey)
	{
		this.tokenKey = tokenKey;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + charPosition;
		result = prime * result + line;
		result = prime * result + previousTokenIndex;
		result = prime * result + tokenIndex;
		result = prime * result
				+ ((tokenKey == null) ? 0 : tokenKey.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		InterestingInfo other = (InterestingInfo) obj;
		if (charPosition != other.charPosition)
			return false;
		if (line != other.line)
			return false;
		if (previousTokenIndex != other.previousTokenIndex)
			return false;
		if (tokenIndex != other.tokenIndex)
			return false;
		if (tokenKey == null)
		{
			if (other.tokenKey != null)
				return false;
		}
		else if (!tokenKey.equals(other.tokenKey))
			return false;
		if (value == null)
		{
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))
			return false;
		return true;
	}

	public boolean isValid()
	{
		return this.tokenIndex > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "InterestingInfo [tokenIndex=" + tokenIndex + ", line=" + line
				+ ", charPosition=" + charPosition + ", "
				+ (tokenKey != null ? "tokenKey=" + tokenKey + ", " : "")
				+ (value != null ? "value=" + value + ", " : "")
				+ "previousTokenIndex=" + previousTokenIndex + "]";
	}
}
