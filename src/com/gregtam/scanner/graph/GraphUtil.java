package com.gregtam.scanner.graph;

import org.apache.log4j.Logger;

import com.gregtam.scanner.graph.GraphConstants.RelType;
import com.gregtam.scanner.model.DataObject;
import com.gregtam.scanner.util.ValidationUtil;

public class GraphUtil
{

	static Logger logger = Logger.getLogger(GraphUtil.class.getName());

	public static String createKey(DataObject d)
	{
		if (d != null)
		{
			return createKey(d.getType(), d.getName());
		}

		return "";
	}

	public static RelType determineRelationship(DataObject parent,
			DataObject child)
	{
		if (parent != null && child != null)
		{
			if (parent.getType() == child.getType())
			{
				logger.debug("same type, no relationship");
				return null;
			}
			else
			{

				if (parent.getType() < child.getType())
				{
					// correct format
				}
				else
				{
					logger.debug("wrong order, switching");

					DataObject temp = child;
					child = parent;
					parent = temp;
				}

				String key = createRelationshipKey(parent, child);
				if (GraphConstants.relationshipMap.containsKey(key))
				{
					return GraphConstants.relationshipMap.get(key);
				}
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

		if (child.getType() == GraphConstants.TYPE_VARIABLE)
		{
			sb.append(GraphConstants.KEY_DELIMITER);
			if (child.isModify())
			{
				sb.append("1");
			}
			else
			{
				sb.append("0");
			}
		}

		return sb.toString();
	}

	public static String createKey(int type, String name)
	{
		StringBuilder sb = new StringBuilder();

		if (ValidationUtil.isNotNullAndEmpty(name))
		{
			switch (type)
			{
			case GraphConstants.TYPE_DIRECTORY:
				sb.append(GraphConstants.TAG_DIRECTORY);
				break;
			case GraphConstants.TYPE_FILE:
				sb.append(GraphConstants.TAG_FILE);
				break;
			case GraphConstants.TYPE_CLASS:
				sb.append(GraphConstants.TAG_CLASS);
				break;
			case GraphConstants.TYPE_FUNCTION:
				sb.append(GraphConstants.TAG_FUNCTION);
				break;
			case GraphConstants.TYPE_VARIABLE:
				sb.append(GraphConstants.TAG_VARIABLE);
				break;
			default:

			}

			sb.append(GraphConstants.KEY_DELIMITER);
			sb.append(name);
		}

		return sb.toString();
	}
}
