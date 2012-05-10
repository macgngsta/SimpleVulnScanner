package com.gregtam.scanner.work;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.gregtam.scanner.model.MetaData;
import com.gregtam.scanner.util.ValidationUtil;

public class FunctionParamTask extends AbstractTask
{
	public static final List<String> SEARCH_TOKENS = Arrays.asList("Params");

	public FunctionParamTask(CommonTree t, MetaData m)
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
				try
				{
					MetaData mMeta = (MetaData) meta.clone();
					mMeta.setModifier(true);

					SessionVariableTask svt = new SessionVariableTask(t, mMeta);
					svt.process();
				}
				catch (CloneNotSupportedException e)
				{
					e.printStackTrace();
				}

			}
		}
		next();
	}
}
