package com.gregtam.scanner.file;

import java.io.File;

import com.gregtam.scanner.graph.GraphConstants;
import com.gregtam.scanner.model.DataNode;
import com.gregtam.scanner.model.FileObject;

public class FileDataNodeBuilder
{
	public static DataNode buildDataNode(File f, int type)
	{
		DataNode dNode = new DataNode();

		// the type drives what data i want to pull
		switch (type)
		{
		case GraphConstants.TYPE_PROJECT:
		case GraphConstants.TYPE_DIRECTORY:
			break;
		case GraphConstants.TYPE_FILE:
			dNode.setFileName(f.getName());
		default:
		}
		// pull global stuff no matter what
		dNode.setType(type);
		dNode.setRelativePath(f.getAbsolutePath());

		return dNode;
	}

	public static DataNode buildDataNode(FileObject fo, int type)
	{
		DataNode dNode = new DataNode();

		// the type drives what data i want to pull
		switch (type)
		{
		case GraphConstants.TYPE_PROJECT:
		case GraphConstants.TYPE_DIRECTORY:
			break;
		case GraphConstants.TYPE_FILE:
			dNode.setFileName(fo.getName());

		default:
		}

		// pull global stuff no matter what
		dNode.setType(type);
		dNode.setRelativePath(fo.getRelPath());

		return dNode;
	}
}
