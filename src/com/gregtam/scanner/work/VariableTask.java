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

public class VariableTask extends AbstractTask
{
	public static final List<String> SEARCH_TOKENS = Arrays.asList("$");

	public VariableTask(CommonTree t, MetaData m)
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
						GraphConstants.TYPE_VARIABLE);
				GraphProcessor.getInstance().addDataNode(d);

			}
		}

		// set the next task as a new one of
		setNextTask(null);

		next();
	}
}