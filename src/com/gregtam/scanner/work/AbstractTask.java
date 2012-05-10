package com.gregtam.scanner.work;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.gregtam.scanner.model.MetaData;

public abstract class AbstractTask implements ITask
{
	protected CommonTree pTree;
	protected MetaData meta;
	protected ITask nextTask;

	public AbstractTask(CommonTree t, MetaData m)
	{
		this.pTree = t;
		this.meta = m;
		this.nextTask = null;
	}

	public CommonTree getT()
	{
		return pTree;
	}

	public void setT(CommonTree t)
	{
		this.pTree = t;
	}

	public MetaData getM()
	{
		return meta;
	}

	public void setM(MetaData m)
	{
		this.meta = m;
	}

	public void setNextTask(ITask task)
	{
		this.nextTask = task;
	}

	public void next()
	{
		if (this.nextTask != null)
		{
			nextTask.process();
		}
	}

	protected CommonTree pruneByToken(CommonTree t, int index,
			List<CommonTree> c, List<String> sToken)
	{
		if (t != null)
		{
			String token = t.toString();
			if (sToken.contains(token))
			{
				c.add(t);
				CommonTree s = (CommonTree) t.getParent();
				s.deleteChild(index);

				pruneByToken(s, 0, c, sToken);
			}

			for (int i = 0; i < t.getChildCount(); i++)
			{
				pruneByToken((CommonTree) t.getChild(i), i, c, sToken);
			}
		}

		return t;
	}
}
