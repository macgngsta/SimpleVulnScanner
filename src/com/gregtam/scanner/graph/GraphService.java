package com.gregtam.scanner.graph;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.impl.util.FileUtils;

import com.gregtam.scanner.graph.GraphConstants.RelType;
import com.gregtam.scanner.model.DataObject;

public class GraphService
{
	public static final String DB_PATH = "target/neo-db";
	static Logger logger = Logger.getLogger(GraphService.class.getName());

	private static GraphService _instance;
	private GraphDatabaseService graphDb;
	private Index<Node> nodeIndex;

	private Node rootNode;

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

	public void createRoot(DataObject d)
	{
		Transaction tx = graphDb.beginTx();
		try
		{
			rootNode = createAndIndex(d);
			logger.debug("created root node");
			tx.success();
		}
		finally
		{
			tx.finish();
		}
	}

	// all these functions need to be wrapped by a transaction

	private Node createAndIndex(DataObject d)
	{
		Node node = graphDb.createNode();
		String key = GraphUtil.createKey(d);
		node.setProperty(GraphConstants.KEY_NAME, key);
		nodeIndex.add(node, GraphConstants.KEY_NAME, key);

		int type = d.getType();

		switch (type)
		{
		case GraphConstants.TYPE_DIRECTORY:
			break;
		case GraphConstants.TYPE_FILE:
			break;
		case GraphConstants.TYPE_CLASS:
			node.setProperty(GraphConstants.PROP_FILENAME, d.getFileName());
			node.setProperty(GraphConstants.PROP_LINE, d.getLine());
			node.setProperty(GraphConstants.PROP_LOCATION, d.getLocation());
			break;
		case GraphConstants.TYPE_FUNCTION:
			node.setProperty(GraphConstants.PROP_FILENAME, d.getFileName());
			node.setProperty(GraphConstants.PROP_LINE, d.getLine());
			node.setProperty(GraphConstants.PROP_LOCATION, d.getLocation());
			break;
		case GraphConstants.TYPE_VARIABLE:
			node.setProperty(GraphConstants.PROP_FILENAME, d.getFileName());
			node.setProperty(GraphConstants.PROP_LINE, d.getLine());
			node.setProperty(GraphConstants.PROP_LOCATION, d.getLocation());
			node.setProperty(GraphConstants.PROP_ATTACK_VECTOR,
					d.isPossibleAttackVector());
			break;
		default:
		}

		// global set
		node.setProperty(GraphConstants.PROP_TYPE, type);
		node.setProperty(GraphConstants.PROP_RELPATH, d.getRelativePath());
		node.setProperty(GraphConstants.PROP_NAME, d.getName());

		return node;
	}

	private Node findNode(DataObject d)
	{
		Node foundNode = nodeIndex.get(GraphConstants.KEY_NAME,
				GraphUtil.createKey(d)).getSingle();

		if (foundNode == null)
		{
			logger.debug("could not find");
		}

		return foundNode;
	}

	private Relationship createRelationship(Node n1, Node n2, RelType r)
	{
		Relationship rel = n1.createRelationshipTo(n2, r);
		return rel;
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
