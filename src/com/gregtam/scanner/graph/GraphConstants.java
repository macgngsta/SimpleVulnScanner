package com.gregtam.scanner.graph;

import org.neo4j.graphdb.RelationshipType;

public class GraphConstants
{
	public static enum RelType implements RelationshipType
	{
		CONTAINS, IMPLEMENTS, DECLARES, MODIFIES, USES
	}

	// directory CONTAINS
	// a file IMPLEMENTS
	// class DECLARES
	// function MODIFIES, USES
	// variable
}
