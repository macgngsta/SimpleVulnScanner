package com.gregtam.scanner.php;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.gregtam.scanner.graph.GraphService;
import com.gregtam.scanner.model.DataObject;
import com.gregtam.scanner.model.FileObject;
import com.gregtam.scanner.model.MetaData;
import com.gregtam.scanner.work.ITask;
import com.gregtam.scanner.work.IncludesTask;

public class PhpAntlrParser
{
	static Logger logger = Logger.getLogger(GraphService.class.getName());

	static final TreeAdaptor adaptor = new CommonTreeAdaptor()
	{
		public Object create(Token payload)
		{
			return new PhpAST(payload);
		}
	};

	public PhpAntlrParser()
	{

	}

	public void parse(FileObject fo) throws ParseException
	{

		if (fo != null)
		{
			try
			{

				ANTLRFileStream fs = new ANTLRFileStream(fo.getAbsPath());
				if (fs.size() > 0)
				{

					PhpLexer lex = new PhpLexer(fs);

					TokenRewriteStream tokens = new TokenRewriteStream(lex);
					PhpParser grammar = new PhpParser(tokens);

					grammar.setTreeAdaptor(adaptor);
					PhpParser.prog_return content = grammar.prog();

					CommonTree fullTree = (CommonTree) content.getTree();

					printTree(fullTree, 1);

					MetaData m = new MetaData();
					// populate with relevent data
					m.setFileName(fo.getName());

					String relPath = fo.getRelPath();
					if (relPath.endsWith(fo.getName()))
					{
						m.setRelativePath(relPath.substring(0,
								relPath.indexOf(fo.getName())));
					}
					else
					{
						m.setRelativePath(relPath);
					}

					ITask startTask = new IncludesTask(fullTree, m);
					startTask.process();

					// // the algorithm here is to prune by high level items
					// // function/class, this will leave the global code
					// List<CommonTree> classes = new ArrayList<CommonTree>();
					// CommonTree classPruned = pruneByToken(fullTree, 0,
					// classes,
					// "class");
					//
					// // function
					// List<CommonTree> functions = new ArrayList<CommonTree>();
					// CommonTree funcPruned = pruneByToken(classPruned, 0,
					// functions, "function");
					//
					// // operator
					// List<CommonTree> operators = new ArrayList<CommonTree>();
					// CommonTree opPruned = pruneByToken(funcPruned, 0,
					// operators, "=");
					//
					// // session variables
					// List<CommonTree> sesVars = new ArrayList<CommonTree>();
					// CommonTree sesVarPruned = pruneByToken(opPruned, 0,
					// sesVars, "[");
					//
					// // variables
					// List<CommonTree> variables = new ArrayList<CommonTree>();
					// CommonTree theRest = pruneByToken(sesVarPruned, 0,
					// variables, "$");

					//
					// List<CommonTree> myVariables = findVariables(tree);
					//
					// for (CommonTree t : myVariables)
					// {
					// logger.debug(getVariableName(t));
					//
					// logger.debug(t.toStringTree());
					// }

					// logger.debug(myVariables);

					//
					// // globalSb = new StringBuilder();
					// printTree(tree, 1, "");
					// // parseTree(tree);
					//
					// PhpDataProcessor dp = new PhpDataProcessor();
					//
					// // build a map of all the tokens
					// Map<Integer, InterestingInfo> tMap =
					// PhpUtil.getInstance()
					// .getAllTokens();
					//
					// if (tMap != null && !tMap.isEmpty())
					// {
					// TreeSet<Integer> keys = new TreeSet<Integer>(
					// tMap.keySet());
					//
					// // there is some issue - have to do it twice
					// // process primitive types
					// for (Integer k : keys)
					// {
					// if (k != null)
					// {
					// InterestingInfo info = tMap.get(k);
					// // logger.debug(info);
					// // look for variables
					// DataObject data = dp.processForPrimitive(info);
					//
					// if (data != null
					// && data.getType() != GraphConstants.TYPE_INVALID)
					// {
					//
					// data = setFileInformation(data, fo);
					//
					// // check to see if its a quick surface
					// // vector
					// data.setPossibleAttackVector(SurfaceVectorUtil
					// .isPossibleSurfaceVector(data
					// .getName()));
					//
					// myData.add(data);
					// }
					// }
					// }
					//
					// // process variables
					// for (Integer k : keys)
					// {
					// if (k != null)
					// {
					// InterestingInfo info = tMap.get(k);
					// // logger.debug(info);
					// // look for variables
					// DataObject data = dp
					// .processForVariablesFunctions(info);
					//
					// if (data != null
					// && data.getType() != GraphConstants.TYPE_INVALID)
					// {
					//
					// data = setFileInformation(data, fo);
					//
					// // check to see if its a quick surface
					// // vector
					// data.setPossibleAttackVector(SurfaceVectorUtil
					// .isPossibleSurfaceVector(data
					// .getName()));
					//
					// myData.add(data);
					// }
					// }
					// }
					// }

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

		// clean up for the next file
		// cleanUpForNextFile();

		// we've now created all the data objects
		// need to insert into our db
		// return myData;
	}

	private void cleanUpForNextFile()
	{
		// clear the map that contains tokens read from file
		// the used token map gets auto recreated
		PhpUtil.getInstance().clear();
	}

	private CommonTree pruneByToken(CommonTree t, int index,
			List<CommonTree> c, String sToken)
	{
		if (t != null)
		{
			String token = t.toString();
			if (token.equalsIgnoreCase(sToken))
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

	private void findAll(CommonTree t, List<CommonTree> v, List<CommonTree> f,
			List<CommonTree> c)
	{

		String token = t.toString();

		if (t != null)
		{

			for (int i = 0; i < t.getChildCount(); i++)
			{
				if (token.equalsIgnoreCase("[") || token.equalsIgnoreCase("$"))
				{
					v.add(t);
				}
				else if (token.equalsIgnoreCase("function")
						|| token.equalsIgnoreCase("="))
				{
					f.add(t);
				}
				else if (token.equalsIgnoreCase("class"))
				{
					c.add(t);
				}

				findAll((CommonTree) t.getChild(i), v, f, c);
			}
		}
	}

	private List<CommonTree> findVariables(CommonTree t)
	{
		List<CommonTree> variables = new ArrayList<CommonTree>();

		if (t.toString().equalsIgnoreCase("[")
				|| t.toString().equalsIgnoreCase("$"))
		{
			variables.add(t);
		}
		else
		{
			for (int i = 0; i < t.getChildCount(); i++)
			{
				variables.addAll(findVariables((CommonTree) t.getChild(i)));
			}
		}

		return variables;
	}

	private String getVariableName(CommonTree t)
	{
		StringBuilder realName = new StringBuilder();
		StringBuilder sb = new StringBuilder();
		String temp = getVariableName(t, sb);

		if (t != null)
		{
			realName.append(t.toString());
		}

		realName.append(temp);

		return realName.toString();
	}

	private String getVariableName(CommonTree t, StringBuilder sb)
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

				printTree((CommonTree) t.getChild(i), indent + 2);
			}
		}
	}

	private void printTree(PhpAST t, int indent, String test)
	{
		if (t != null)
		{
			StringBuffer sb = new StringBuffer(indent);

			for (int i = 0; i < indent; i++)
				sb = sb.append("   ");

			for (int i = 0; i < t.getChildCount(); i++)
			{
				logger.debug((sb.toString() + test + ":" + t.getChild(i)
						.toString()));

				if (t.getChild(i).toString().equalsIgnoreCase("function"))
				{
					printTree((PhpAST) t.getChild(i), indent + 2, test + "f");
				}
				else if (t.getChild(i).toString().equalsIgnoreCase("$"))
				{
					printTree((PhpAST) t.getChild(i), indent + 2, test + "v");
				}
				else
				{
					printTree((PhpAST) t.getChild(i), indent + 2, test);
				}

				//
			}
		}
	}

	private DataObject setFileInformation(DataObject dObj, FileObject fo)
	{
		if (dObj != null && fo != null)
		{
			dObj.setFileName(fo.getName());

			String relPath = fo.getRelPath();
			if (relPath.endsWith(fo.getName()))
			{
				dObj.setRelativePath(relPath.substring(0,
						relPath.indexOf(fo.getName())));
			}
			else
			{
				dObj.setRelativePath(relPath);
			}
		}

		return dObj;
	}
}
