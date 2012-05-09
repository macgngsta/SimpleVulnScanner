package com.gregtam.scanner.php;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.apache.log4j.Logger;

import com.gregtam.scanner.graph.GraphService;
import com.gregtam.scanner.model.InterestingInfo;

public class PhpAST extends CommonTree
{
	private static InterestingInfo previous = null;
	static Logger logger = Logger.getLogger(GraphService.class.getName());

	public PhpAST(Token t)
	{
		super(t);
		if (t != null)
		{
			String tokenKey = PhpUtil.getInstance().findToken(t.getType());

			InterestingInfo input = new InterestingInfo();

			if (previous != null)
			{
				input.setPreviousTokenIndex(previous.getTokenIndex());
			}

			input.setCharPosition(t.getCharPositionInLine());
			input.setLine(t.getLine());
			input.setTokenIndex(t.getTokenIndex());
			input.setValue(t.getText());
			input.setTokenKey(tokenKey);

			// logger.debug(input);

			PhpUtil.getInstance().addInterestingInfo(input);

			previous = input;

			// if (input != null && input.isValid())
			// {
			// previous = input;
			// }
		}
	}
}
