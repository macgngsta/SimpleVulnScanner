package com.gregtam.scanner.graph;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;

public class GraphService
{
	public static final String DB_PATH = "target/neo-db";
	static Logger logger = Logger.getLogger(GraphService.class.getName());

	private static GraphService _instance;
	private GraphDatabaseService graphDb;

	private GraphService()
	{
		logger.debug("starting graphdb");
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
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
