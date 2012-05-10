package com.gregtam.scanner.graph;

import org.apache.log4j.Logger;

import com.gregtam.scanner.graph.GraphConstants.RelType;
import com.gregtam.scanner.model.DataObject;

public class GraphUtil
{

	static Logger logger = Logger.getLogger(GraphUtil.class.getName());

	public static String createKey(DataObject d)
	{
		if (d != null)
		{
			return createKey(d.getType(), d);
		}

		return "";
	}

	public static RelType determineRelationship(DataObject parent,
			DataObject child)
	{
		if (parent != null && child != null)
		{
			String key = createRelationshipKey(parent, child);
			if (GraphConstants.relationshipMap.containsKey(key))
			{
				return GraphConstants.relationshipMap.get(key);
			}
		}
		return null;
	}

	private static String createRelationshipKey(DataObject parent,
			DataObject child)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(parent.getType());
		sb.append(child.getType());

		return sb.toString();
	}

	public static String createKey(int type, DataObject dObj)
	{
		StringBuilder sb = new StringBuilder();

		if (dObj != null)
		{
			switch (type)
			{
			case GraphConstants.TYPE_DIRECTORY:
				sb.append(GraphConstants.TAG_DIRECTORY);
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getRelativePath());
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getName());
				break;
			case GraphConstants.TYPE_FILE:
				sb.append(GraphConstants.TAG_FILE);
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getRelativePath());
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getName());
				break;
			case GraphConstants.TYPE_CLASS:
				sb.append(GraphConstants.TAG_CLASS);
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getRelativePath());
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getFileName());
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getName());
				break;
			case GraphConstants.TYPE_FUNCTION:
				sb.append(GraphConstants.TAG_FUNCTION);
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getRelativePath());
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getFileName());
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getName());
				break;
			case GraphConstants.TYPE_VARIABLE:
				sb.append(GraphConstants.TAG_VARIABLE);
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getRelativePath());
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getFileName());
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getLine());
				sb.append(GraphConstants.KEY_DELIMITER);
				sb.append(dObj.getName());
				break;
			default:

			}
		}

		return sb.toString();
	}
}
