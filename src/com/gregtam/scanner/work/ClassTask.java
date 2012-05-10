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

public class ClassTask extends AbstractTask
{
	public static final List<String> SEARCH_TOKENS = Arrays.asList("class");

	public ClassTask(CommonTree t, MetaData m)
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
						GraphConstants.TYPE_CLASS);
				GraphProcessor.getInstance().addDataNode(d);

				try
				{
					MetaData mMeta = (MetaData) meta.clone();
					mMeta.setClassName(d.getClassName());

					FunctionTask svt = new FunctionTask(t, mMeta);
					svt.process();
				}
				catch (CloneNotSupportedException e)
				{
					e.printStackTrace();
				}

			}
		}
		// set the next task as a new one of
		setNextTask(new FunctionTask(prunedTree, this.meta));

		next();
	}
}
