package com.gregtam.scanner.graph;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.RelationshipType;

public class GraphConstants
{
	public static final Map<String, RelType> relationshipMap = new HashMap<String, RelType>();

	static
	{
		// load all the possible realistic maps

		relationshipMap.put("01", RelType.CONTAINS);
		relationshipMap.put("12", RelType.CONTAINS);
		relationshipMap.put("23", RelType.IMPLEMENTS);
		relationshipMap.put("34", RelType.DECLARES);

		// file to function
		relationshipMap.put("24", RelType.DECLARES);

		// file to variable
		relationshipMap.put("25_1", RelType.MODIFIES);
		relationshipMap.put("25_0", RelType.USES);

		// class to variable
		relationshipMap.put("35_1", RelType.MODIFIES);
		relationshipMap.put("35_0", RelType.USES);

		// function to variable
		relationshipMap.put("45_1", RelType.MODIFIES);
		relationshipMap.put("45_0", RelType.USES);

	}

	public static enum RelType implements RelationshipType
	{
		CONTAINS, IMPLEMENTS, DECLARES, MODIFIES, USES
	}

	// project CONTAINS
	// directory CONTAINS
	// a file IMPLEMENTS
	// class DECLARES
	// function MODIFIES, USES
	// variable

	// track the name
	public static final String KEY_NAME = "name";

	public static final int TYPE_INVALID = -1;
	public static final int TYPE_PROJECT = 0;
	public static final int TYPE_DIRECTORY = 1;
	public static final int TYPE_FILE = 2;
	public static final int TYPE_CLASS = 3;
	public static final int TYPE_FUNCTION = 4;
	public static final int TYPE_VARIABLE = 5;

	public static final String TAG_PROJECT = "proj";
	public static final String TAG_DIRECTORY = "dir";
	public static final String TAG_FILE = "file";
	public static final String TAG_CLASS = "class";
	public static final String TAG_FUNCTION = "func";
	public static final String TAG_VARIABLE = "var";

	public static final String KEY_DELIMITER = "_";

}
