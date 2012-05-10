package com.gregtam.scanner.php;

import java.util.Iterator;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.apache.log4j.Logger;

import com.gregtam.scanner.graph.GraphService;

public class PhpAST extends CommonTree implements Iterable
{
	static Logger logger = Logger.getLogger(GraphService.class.getName());

	public PhpAST(Token t)
	{
		super(t);
	}

	public Iterator iterator()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
