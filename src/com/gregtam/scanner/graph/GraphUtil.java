package com.gregtam.scanner.graph;

import org.apache.log4j.Logger;

import com.gregtam.scanner.model.DataNode;
import com.gregtam.scanner.util.ValidationUtil;

public class GraphUtil
{

	static Logger logger = Logger.getLogger(GraphUtil.class.getName());

	public static String createKey(DataNode d)
	{
		if (d != null)
		{
			return createKey(d.getType(), d);
		}

		return "";
	}

	public static String createRelationshipKey(DataNode parent, DataNode child)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(parent.getType());
		sb.append(child.getType());

		return sb.toString();
	}

	public static String createKey(int type, DataNode dObj)
	{
		StringBuilder sb = new StringBuilder();

		if (dObj != null)
		{
			switch (type)
			{

			// directory
			// d-/
			case GraphConstants.TYPE_DIRECTORY:
				sb.append(GraphConstants.TAG_DIRECTORY);
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getRelativePath());
				break;
			// file
			// f-/abc.php
			case GraphConstants.TYPE_FILE:
				sb.append(GraphConstants.TAG_FILE);
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getRelativePath());
				sb.append(dObj.getFileName());
				break;
			// class
			// c-/abc.php-A
			case GraphConstants.TYPE_CLASS:
				sb.append(GraphConstants.TAG_CLASS);
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getRelativePath());
				sb.append(dObj.getFileName());
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getClassName());
				break;
			// function (method)
			// m-/abc.php-insert
			case GraphConstants.TYPE_FUNCTION:
				sb.append(GraphConstants.TAG_FUNCTION);
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getRelativePath());
				sb.append(dObj.getFileName());
				sb.append(GraphConstants.KEY_DELIMITER);

				if (ValidationUtil.isNotNullAndEmpty(dObj.getClassName()))
				{
					sb.append(dObj.getClassName());
					sb.append(GraphConstants.KEY_DELIMITER);
				}

				sb.append(dObj.getFunctionName());
				break;
			// variable
			// v-/abc.php-name
			case GraphConstants.TYPE_VARIABLE:
				sb.append(GraphConstants.TAG_VARIABLE);
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getRelativePath());
				sb.append(dObj.getFileName());
				sb.append(GraphConstants.KEY_DELIMITER);

				if (ValidationUtil.isNotNullAndEmpty(dObj.getClassName()))
				{
					sb.append(dObj.getClassName());
					sb.append(GraphConstants.KEY_DELIMITER);
				}

				if (ValidationUtil.isNotNullAndEmpty(dObj.getFunctionName()))
				{
					sb.append(dObj.getFunctionName());
					sb.append(GraphConstants.KEY_DELIMITER);
				}

				sb.append(dObj.getVariableName());
				break;
			default:
			}
		}

		return sb.toString();
	}
}
