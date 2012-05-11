package com.gregtam.scanner.graph;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.impl.util.FileUtils;

import com.gregtam.scanner.graph.GraphConstants.RelType;
import com.gregtam.scanner.model.DataNode;
import com.gregtam.scanner.util.ValidationUtil;

public class GraphService
{
	public static final String DB_PATH = "neo-db";
	static Logger logger = Logger.getLogger(GraphService.class.getName());

	private static GraphService _instance;
	private GraphDatabaseService graphDb;
	private Index<Node> nodeIndex;

	private Node rootNode = null;

	private GraphService()
	{
		logger.debug("starting graphdb");
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		nodeIndex = graphDb.index().forNodes("nodes");
		registerShutdownHook(graphDb);
	}

	public synchronized static GraphService getInstance()
	{
		if (_instance == null)
		{
			_instance = new GraphService();
		}

		return _instance;
	}

	public GraphDatabaseService getGraphDb()
	{
		return graphDb;
	}

	public void setGraphDb(GraphDatabaseService graphDb)
	{
		this.graphDb = graphDb;
	}

	public void clearDb()
	{
		try
		{
			FileUtils.deleteRecursively(new File(DB_PATH));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void shutdown()
	{
		logger.debug("shutting down graphdb");
		graphDb.shutdown();
	}

	public void createRoot(DataNode d)
	{
		Transaction tx = graphDb.beginTx();
		try
		{
			Node r = findNode(d);
			if (r == null)
			{
				rootNode = createAndIndex(d, null);
				logger.debug("created root node");
			}
			else
			{
				rootNode = r;
				logger.debug("root node already exists");
			}
			tx.success();
		}
		finally
		{
			tx.finish();
		}
	}

	public void addNodes(List<DataNode> listNodes, DataNode parentNode,
			boolean performRefresh)
	{
		if (ValidationUtil.isNotNullAndEmpty(listNodes))
		{
			Transaction tx = graphDb.beginTx();
			try
			{

				// first find the parentNode
				Node pNode = findNode(parentNode);
				if (pNode == null)
				{
					// create it
					pNode = createAndIndex(parentNode, this.rootNode);
					logger.debug("creating file node...");
				}

				if (pNode != null)
				{

					int counter = 0;
					int updated = 0;

					for (DataNode dn : listNodes)
					{
						Node exNode = findNode(dn);

						if (exNode == null)
						{
							createAndIndex(dn, pNode);
							counter++;
						}
						else
						{
							boolean skeleton = false;

							Boolean b = (Boolean) exNode
									.getProperty(GraphConstants.PROP_IS_TOUCHED);

							if (b != null)
							{
								if (!b)
								{
									skeleton = true;
								}
							}
							else
							{
								skeleton = true;
							}

							if (performRefresh || skeleton)
							{
								updateNode(dn, exNode);
								updated++;
							}

						}

						if (dn.getType() == GraphConstants.TYPE_FILE_INCLUDE)
						{
							createTempFileIncludeNode(dn, exNode);
						}
						else if (dn.getType() == GraphConstants.TYPE_FUNCTION_CALL)
						{
							// TODO: find more relationship
							// logger.debug("fc: " + dn.getFunctionCall());
						}
						else if (dn.getType() == GraphConstants.TYPE_OPERATOR)
						{
							// TODO: find more relationship
							// logger.debug(dn.toString());
						}
						else if (dn.getType() == GraphConstants.TYPE_VARIABLE)
						{
							if (ValidationUtil.isNotNullAndEmpty(dn
									.getFunctionName()))
							{
								// logger.debug(dn.toString());
							}

						}

					}

					logger.debug("added nodes: " + counter);
					logger.debug("updated nodes: " + updated);

				}

				tx.success();
			}
			finally
			{
				tx.finish();
			}
		}
	}

	private Node createTempFileIncludeNode(DataNode baseNode, Node existing)
	{

		if (baseNode != null && existing != null)
		{
			String fileName = baseNode.getFileInclude();

			if (fileName.contains("'"))
			{
				String cleaned = fileName.replaceAll("'", "");

				int foundIndex = cleaned.lastIndexOf("\\");

				if (foundIndex >= 0 && foundIndex < cleaned.length())
				{
					String relPath = baseNode.getRelativePath()
							+ cleaned.substring(0, foundIndex);
					String derivedFile = cleaned.substring(foundIndex);

					DataNode derivedNode = new DataNode();
					derivedNode.setType(GraphConstants.TYPE_FILE);
					derivedNode.setRelativePath(relPath);
					derivedNode.setFileName(derivedFile);

					Node created = createAndIndex(derivedNode, existing);

					// reset the touch param
					created.setProperty(GraphConstants.PROP_IS_TOUCHED, false);

					existing.createRelationshipTo(created, RelType.USES);

					return created;

				}

			}
		}

		return null;

	}

	// all these functions need to be wrapped by a transaction

	private Node createAndIndex(DataNode d, Node pNode)
	{

		Node createdNode = graphDb.createNode();
		String key = GraphUtil.createKey(d);

		createdNode.setProperty(GraphConstants.KEY_NAME, key);
		nodeIndex.add(createdNode, GraphConstants.KEY_NAME, key);

		updateNode(d, createdNode);

		// create the initial relationship
		createRelationship(pNode, createdNode);

		return createdNode;
	}

	private Node updateNode(DataNode d, Node currentNode)
	{
		// set the properties
		int type = d.getType();

		switch (type)
		{
		case GraphConstants.TYPE_DIRECTORY:
			// relpath, type
			break;
		case GraphConstants.TYPE_FILE:
			// relpath, type
			currentNode.setProperty(GraphConstants.PROP_FILENAME,
					d.getFileName());
			break;
		case GraphConstants.TYPE_CLASS:
			currentNode.setProperty(GraphConstants.PROP_FILENAME,
					d.getFileName());
			currentNode.setProperty(GraphConstants.PROP_CLASS_NAME,
					d.getClassName());
			currentNode.setProperty(GraphConstants.PROP_LINE, d.getLine());
			currentNode.setProperty(GraphConstants.PROP_LOCATION,
					d.getLocation());
			break;
		case GraphConstants.TYPE_FUNCTION:
			currentNode.setProperty(GraphConstants.PROP_FILENAME,
					d.getFileName());
			currentNode.setProperty(GraphConstants.PROP_CLASS_NAME,
					d.getClassName());
			currentNode.setProperty(GraphConstants.PROP_FUNCTION_NAME,
					d.getFunctionName());
			currentNode.setProperty(GraphConstants.PROP_LINE, d.getLine());
			currentNode.setProperty(GraphConstants.PROP_LOCATION,
					d.getLocation());
			break;
		case GraphConstants.TYPE_VARIABLE:
			currentNode.setProperty(GraphConstants.PROP_FILENAME,
					d.getFileName());
			currentNode.setProperty(GraphConstants.PROP_CLASS_NAME,
					d.getClassName());
			currentNode.setProperty(GraphConstants.PROP_FUNCTION_NAME,
					d.getFunctionName());
			currentNode.setProperty(GraphConstants.PROP_VARIABLE_NAME,
					d.getVariableName());
			currentNode.setProperty(GraphConstants.PROP_LINE, d.getLine());
			currentNode.setProperty(GraphConstants.PROP_LOCATION,
					d.getLocation());
			currentNode.setProperty(GraphConstants.PROP_ATTACK_VECTOR,
					d.isPossibleAttackVector());
			break;
		default:
		}

		// global set
		currentNode.setProperty(GraphConstants.PROP_TYPE, type);
		currentNode.setProperty(GraphConstants.PROP_RELPATH,
				d.getRelativePath());
		currentNode.setProperty(GraphConstants.PROP_IS_TOUCHED, true);

		return currentNode;
	}

	private Node findNode(DataNode d)
	{
		Node foundNode = nodeIndex.get(GraphConstants.KEY_NAME,
				GraphUtil.createKey(d)).getSingle();
		return foundNode;
	}

	private void createRelationship(Node n1, Node n2)
	{
		RelType r = determineRelationship(n1, n2);
		if (r != null)
		{
			n1.createRelationshipTo(n2, r);
		}
	}

	// HELPER FUNCTION

	private RelType determineRelationship(Node parent, Node child)
	{
		if (parent != null && child != null)
		{
			String key = createRelationshipKey(parent, child);

			if (GraphConstants.relationshipMap.containsKey(key))
			{
				return GraphConstants.relationshipMap.get(key);
			}
		}
		return null;
	}

	private String createRelationshipKey(Node parent, Node child)
	{
		StringBuilder sb = new StringBuilder();

		if (parent != null && child != null)
		{
			sb.append(parent.getProperty(GraphConstants.PROP_TYPE));
			sb.append(child.getProperty(GraphConstants.PROP_TYPE));
		}

		return sb.toString();
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb)
	{
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running example before it's completed)
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				graphDb.shutdown();
			}
		});
	}
}
