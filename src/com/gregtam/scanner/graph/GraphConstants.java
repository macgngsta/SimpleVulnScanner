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

		// project -> folder
		relationshipMap.put("01", RelType.CONTAINS);

		// folder -> folder
		relationshipMap.put("00", RelType.CONTAINS);

		// folder -> file
		relationshipMap.put("12", RelType.CONTAINS);

		// file -> class
		relationshipMap.put("23", RelType.IMPLEMENTS);

		// file -> function
		relationshipMap.put("24", RelType.DECLARES);

		// file -> variable
		relationshipMap.put("25", RelType.USES);

		// class -> class
		relationshipMap.put("33", RelType.USES);

		// class -> function
		relationshipMap.put("34", RelType.DECLARES);

		// class -> variable
		relationshipMap.put("35", RelType.USES);

		// function -> function
		relationshipMap.put("44", RelType.USES);

		// function -> variable
		relationshipMap.put("45", RelType.USES);

		// variable -> variable
		relationshipMap.put("55", RelType.MODIFIES);

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
	public static final String KEY_NAME = "fullname";
	public static final String PROP_TYPE = "type";
	public static final String PROP_RELPATH = "relpath";
	public static final String PROP_FILENAME = "file";
	public static final String PROP_NAME = "name";
	public static final String PROP_LINE = "line";
	public static final String PROP_LOCATION = "location";
	public static final String PROP_ATTACK_VECTOR = "attack";

	public static final int TYPE_INVALID = -1;
	public static final int TYPE_PROJECT = 0;
	public static final int TYPE_DIRECTORY = 1;
	public static final int TYPE_FILE = 2;
	public static final int TYPE_CLASS = 3;
	public static final int TYPE_FUNCTION = 4;
	public static final int TYPE_VARIABLE = 5;
	public static final int TYPE_FILE_INCLUDE = 6;
	public static final int TYPE_OPERATOR = 7;

	public static final String TAG_PROJECT = "proj";
	public static final String TAG_DIRECTORY = "dir";
	public static final String TAG_FILE = "file";
	public static final String TAG_CLASS = "class";
	public static final String TAG_FUNCTION = "func";
	public static final String TAG_VARIABLE = "var";

	public static final String KEY_DELIMITER = "_";

}
