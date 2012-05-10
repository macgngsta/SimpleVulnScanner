package com.gregtam.scanner.work;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.gregtam.scanner.graph.GraphConstants;
import com.gregtam.scanner.graph.GraphProcessor;
import com.gregtam.scanner.model.DataNode;
import com.gregtam.scanner.model.MetaData;
import com.gregtam.scanner.php.PhpDataNodeBuilder;
import com.gregtam.scanner.util.ValidationUtil;

public class ApplyTask extends AbstractTask
{
	public static final List<String> SEARCH_TOKENS = Arrays.asList("Apply");

	public ApplyTask(CommonTree t, MetaData m)
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

				DataNode d = PhpDataNodeBuilder.buildDataNode(t, meta,
						GraphConstants.TYPE_FUNCTION_CALL);
				GraphProcessor.getInstance().addDataNode(d);

				try
				{
					MetaData mMeta = (MetaData) meta.clone();
					mMeta.setFunctionCall(d.getFunctionCall());

					OperatorTask svt = new OperatorTask(t, mMeta);
					svt.process();
				}
				catch (CloneNotSupportedException e)
				{
					e.printStackTrace();
				}

			}
		}
		// set the next task as a new one of
		setNextTask(new OperatorTask(prunedTree, this.meta));

		next();
	}
}
