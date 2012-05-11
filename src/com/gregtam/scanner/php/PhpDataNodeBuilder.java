package com.gregtam.scanner.php;

import org.antlr.runtime.tree.CommonTree;

import com.gregtam.scanner.graph.GraphConstants;
import com.gregtam.scanner.model.DataNode;
import com.gregtam.scanner.model.MetaData;

public class PhpDataNodeBuilder
{
	public PhpDataNodeBuilder()
	{

	}

	// tasks in this order
	// Includes -> Class ->Function ->FunctionParamTask -> SessionVariableTask
	// -> VariableTask
	// |-> ApplyTask -> OperatorTask -> SessionVariableTask -> VariableTask

	public static DataNode buildDataNode(CommonTree t, MetaData meta, int type)
	{
		DataNode dNode = new DataNode();

		// the type drives what data i want to pull
		switch (type)
		{
		case GraphConstants.TYPE_DIRECTORY:
			break;

		case GraphConstants.TYPE_FILE_INCLUDE:
			dNode.setFileName(meta.getFileName());
			dNode.setFileInclude(getOneLevel(t));
			break;

		case GraphConstants.TYPE_FILE:
			dNode.setFileName(meta.getFileName());
			break;
		case GraphConstants.TYPE_CLASS:

			dNode.setClassName(getOneLevel(t));
			dNode.setFileName(meta.getFileName());
			dNode.setLine(t.getLine());
			dNode.setLocation(Integer.toString(t.getCharPositionInLine()));
			break;
		case GraphConstants.TYPE_FUNCTION:
			dNode.setClassName(meta.getClassName());
			dNode.setFileName(meta.getFileName());

			dNode.setFunctionName(getOneLevel(t));
			dNode.setLine(t.getLine());
			dNode.setLocation(Integer.toString(t.getCharPositionInLine()));
			break;
		case GraphConstants.TYPE_FUNCTION_CALL:
			dNode.setClassName(meta.getClassName());
			dNode.setFileName(meta.getFileName());
			dNode.setFunctionCall(getOneLevel(t));
			dNode.setLine(t.getLine());
			dNode.setLocation(Integer.toString(t.getCharPositionInLine()));
			break;
		case GraphConstants.TYPE_VARIABLE:
			// set the var name
			StringBuilder sb = new StringBuilder();
			String varName = getVariableName(t, sb);
			dNode.setVariableName(varName);

			dNode.setClassName(meta.getClassName());
			dNode.setFileName(meta.getFileName());
			dNode.setFunctionName(meta.getFunctionName());
			dNode.setLine(t.getLine());
			dNode.setLocation(Integer.toString(t.getCharPositionInLine()));
			dNode.setModified(meta.isModified());
			dNode.setModifier(meta.isModifier());

			dNode.setPossibleAttackVector(SurfaceVectorUtil
					.isPossibleSurfaceVector(varName));

			break;
		default:
		}

		// pull global stuff no matter what
		dNode.setType(type);
		dNode.setRelativePath(meta.getRelativePath());

		return dNode;
	}

	private static String getOneLevel(CommonTree t)
	{
		StringBuilder sb = new StringBuilder();

		if (t != null)
		{
			for (int i = 0; i < t.getChildCount(); i++)
			{
				sb.append(t.getChild(i).toString());
			}

		}

		return sb.toString();
	}

	private static String getVariableName(CommonTree t, StringBuilder sb)
	{
		if (t != null)
		{
			for (int i = 0; i < t.getChildCount(); i++)
			{
				sb.append(t.getChild(i).toString());
				getVariableName((CommonTree) t.getChild(i), sb);
			}

		}

		return sb.toString();
	}

}
