package com.gregtam.scanner.exception;

public class ParseException extends Exception
{
	public ParseException(Throwable throwable)
	{
		super(throwable);
	}

	public ParseException(String string, Throwable throwable)
	{
		super(string, throwable);
	}

	public ParseException(String string)
	{
		super(string);
	}

	public ParseException()
	{
		super();
	}
}
