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
		relationshipMap.put("23", RelType.CONTAINS);

		// file -> function
		relationshipMap.put("24", RelType.CONTAINS);

		// file -> variable
		relationshipMap.put("25", RelType.CONTAINS);

		// class -> class
		relationshipMap.put("33", RelType.CONTAINS);

		// class -> function
		relationshipMap.put("34", RelType.CONTAINS);

		// class -> variable
		relationshipMap.put("35", RelType.CONTAINS);

		// function -> function
		relationshipMap.put("44", RelType.CONTAINS);

		// function -> variable
		relationshipMap.put("45", RelType.CONTAINS);

		// variable -> function
		relationshipMap.put("54", RelType.MODIFIES);

		// variable -> variable
		relationshipMap.put("55", RelType.MODIFIES);

	}

	public static enum RelType implements RelationshipType
	{
		CONTAINS, USES, MODIFIES
	}

	// track the name
	public static final String KEY_NAME = "nodeKey";

	public static final String PROP_TYPE = "type";
	public static final String PROP_RELPATH = "relpath";
	public static final String PROP_FILENAME = "file";

	public static final String PROP_CLASS_NAME = "className";
	public static final String PROP_FUNCTION_NAME = "functionName";
	public static final String PROP_VARIABLE_NAME = "variableName";

	public static final String PROP_LINE = "line";
	public static final String PROP_LOCATION = "location";

	public static final String PROP_ATTACK_VECTOR = "surface";

	// this is the case when we create a node based on another node
	// because it doesnt exist yet.
	// we want to make sure to come back and populate the properties
	public static final String PROP_IS_TOUCHED = "isTouched";

	public static final int TYPE_INVALID = -1;
	public static final int TYPE_PROJECT = 0;
	public static final int TYPE_DIRECTORY = 1;
	public static final int TYPE_FILE = 2;
	public static final int TYPE_CLASS = 3;
	public static final int TYPE_FUNCTION = 4;
	public static final int TYPE_VARIABLE = 5;

	// relationships
	public static final int TYPE_FILE_INCLUDE = 6;
	public static final int TYPE_OPERATOR = 7;
	public static final int TYPE_FUNCTION_CALL = 8;

	public static final String TAG_DIRECTORY = "d";
	public static final String TAG_FILE = "f";
	public static final String TAG_CLASS = "c";
	public static final String TAG_FUNCTION = "m";
	public static final String TAG_VARIABLE = "v";

	public static final String KEY_DELIMITER = "+";

}
