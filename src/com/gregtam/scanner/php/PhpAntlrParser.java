package com.gregtam.scanner.php;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import net.kuruvila.php.parser.PhpLexer;
import net.kuruvila.php.parser.PhpParser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;
import org.apache.log4j.Logger;

import com.gregtam.scanner.exception.ParseException;
import com.gregtam.scanner.graph.GraphConstants;
import com.gregtam.scanner.graph.GraphService;
import com.gregtam.scanner.model.DataObject;
import com.gregtam.scanner.model.InterestingInfo;
import com.gregtam.scanner.util.FileObject;

public class PhpAntlrParser
{
	static Logger logger = Logger.getLogger(GraphService.class.getName());

	public PhpAntlrParser()
	{

	}

	public void parse(FileObject fo) throws ParseException
	{
		if (fo != null)
		{
			try
			{

				ANTLRFileStream fs = new ANTLRFileStream(fo.getPath());
				if (fs.size() > 0)
				{

					PhpLexer lex = new PhpLexer(fs);

					TokenRewriteStream tokens = new TokenRewriteStream(lex);
					PhpParser grammar = new PhpParser(tokens);

					grammar.setTreeAdaptor(adaptor);
					PhpParser.prog_return content = grammar.prog();

					CommonTree tree = (CommonTree) content.getTree();

					// globalSb = new StringBuilder();
					// printTree(tree, 10);
					parseTree(tree);

					DataProcessor dp = new DataProcessor();
					List<DataObject> myData = new ArrayList<DataObject>();

					// build a map of all the tokens
					Map<Integer, InterestingInfo> tMap = PhpUtil.getInstance()
							.getAllTokens();

					if (tMap != null && !tMap.isEmpty())
					{
						TreeSet<Integer> keys = new TreeSet<Integer>(
								tMap.keySet());

						for (Integer k : keys)
						{
							if (k != null)
							{
								InterestingInfo info = tMap.get(k);
								// logger.debug(info);
								// look for variables
								DataObject data = dp.processToken(info);

								if (data != null
										&& data.getType() != GraphConstants.TYPE_INVALID)
								{
									data.setFileName(fo.getName());
									myData.add(data);
								}
							}
						}
					}

					if (myData != null && !myData.isEmpty())
					{
						for (DataObject d : myData)
						{
							logger.debug(d);
						}
					}

				}
				else
				{
					logger.debug("file was empty");
				}
			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ParseException("file not found: " + e);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ParseException("io issue: " + e);
			}
			catch (RecognitionException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ParseException("rule not recognized: " + e);
			}
		}
	}

	private void parseTree(CommonTree t)
	{
		if (t != null)
		{
			for (int i = 0; i < t.getChildCount(); i++)
			{
				parseTree((CommonTree) t.getChild(i));
			}
		}
	}

	private void printTree(CommonTree t, int indent)
	{
		if (t != null)
		{
			StringBuffer sb = new StringBuffer(indent);
			for (int i = 0; i < indent; i++)
				sb = sb.append("   ");
			for (int i = 0; i < t.getChildCount(); i++)
			{
				logger.debug((sb.toString() + t.getChild(i).toString()));
				printTree((CommonTree) t.getChild(i), indent + 1);
			}
		}
	}

	static final TreeAdaptor adaptor = new CommonTreeAdaptor()
	{
		public Object create(Token payload)
		{
			return new PhpAST(payload);
		}
	};

}
