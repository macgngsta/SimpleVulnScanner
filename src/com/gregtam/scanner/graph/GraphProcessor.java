package com.gregtam.scanner.graph;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gregtam.scanner.model.DataNode;
import com.gregtam.scanner.util.ValidationUtil;

public class GraphProcessor
{
	static Logger logger = Logger.getLogger(GraphProcessor.class.getName());
	private static GraphProcessor _instance;
	private List<DataNode> dataNodes;

	private GraphProcessor()
	{
		this.dataNodes = new ArrayList<DataNode>();
	}

	public synchronized static GraphProcessor getInstance()
	{
		if (_instance == null)
		{
			_instance = new GraphProcessor();
		}

		return _instance;
	}

	public List<DataNode> getDataNodes()
	{
		return dataNodes;
	}

	public void setDataNodes(List<DataNode> dataNodes)
	{
		this.dataNodes = dataNodes;
	}

	public void addDataNode(DataNode d)
	{
		if (d != null)
		{
			if (!this.dataNodes.contains(d))
			{
				this.dataNodes.add(d);
			}
		}
	}

	public void removeDataNode(DataNode d)
	{
		if (d != null)
		{
			if (this.dataNodes.contains(d))
			{
				this.dataNodes.remove(d);
			}
		}
	}

	public void print()
	{
		if (ValidationUtil.isNotNullAndEmpty(this.dataNodes))
		{
			for (DataNode dn : this.dataNodes)
			{
				logger.debug(dn);
			}
		}
	}
}
