package com.gregtam.scanner.work;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.gregtam.scanner.model.MetaData;
import com.gregtam.scanner.util.ValidationUtil;

public class OperatorTask extends AbstractTask
{
	public static final List<String> SEARCH_TOKENS = Arrays.asList("=");

	public OperatorTask(CommonTree t, MetaData m)
	{
		super(t, m);
	}

	public void process()
	{

		List<CommonTree> results = new ArrayList<CommonTree>();
		CommonTree prunedTree = pruneByToken(pTree, 0, results, SEARCH_TOKENS);

		// do something
		if (ValidationUtil.isNotNullAndEmpty(results))
		{
			for (CommonTree t : results)
			{
				// check to see what the operator is
				// modify the meta accordingly
				try
				{
					MetaData mMeta = (MetaData) meta.clone();
					mMeta.setModified(true);

					SessionVariableTask svt = new SessionVariableTask(t, mMeta);
					svt.process();
				}
				catch (CloneNotSupportedException e)
				{
					e.printStackTrace();
				}

			}
		}

		// set the next task as a new one of
		setNextTask(new SessionVariableTask(prunedTree, this.meta));

		next();
	}
}