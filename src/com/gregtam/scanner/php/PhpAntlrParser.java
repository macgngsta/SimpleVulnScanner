package com.gregtam.scanner.php;

import java.io.FileNotFoundException;
import java.io.IOException;

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
import com.gregtam.scanner.model.FileObject;
import com.gregtam.scanner.model.MetaData;
import com.gregtam.scanner.work.ITask;
import com.gregtam.scanner.work.IncludesTask;

public class PhpAntlrParser
{
	static Logger logger = Logger.getLogger(GraphService.class.getName());

	private static final TreeAdaptor adaptor = new CommonTreeAdaptor()
	{
		public Object create(Token payload)
		{
			return new CommonTree(payload);
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

					// dont print
					// printTree(fullTree, 1);

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

				}
				else
				{
					logger.debug("file was empty");
				}
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
				throw new ParseException("file not found: " + e);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				throw new ParseException("io issue: " + e);
			}
			catch (RecognitionException e)
			{
				e.printStackTrace();
				throw new ParseException("rule not recognized: " + e);
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
}
