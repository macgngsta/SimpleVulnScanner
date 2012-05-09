package com.gregtam.scanner.php;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import com.gregtam.scanner.graph.GraphConstants;
import com.gregtam.scanner.model.DataObject;
import com.gregtam.scanner.model.InterestingInfo;
import com.gregtam.scanner.util.ValidationUtil;

public class DataProcessor
{
	// given a node, we want to check to see if its octal - if its it then its a
	// variable or function name of some sort
	// once we find an octal - we will want to go visit the previous nodes
	// stop when its not an Apply, break, or -1
	// if its a empty and the value is function then we have a function name

	private Set<Integer> usedTokens;

	public DataProcessor()
	{
		this.usedTokens = new TreeSet<Integer>();
	}

	public DataObject processToken(InterestingInfo input)
	{
		DataObject data = new DataObject();

		data = processForPrimitive(input);

		if (data == null || data.getType() == GraphConstants.TYPE_INVALID)
		{
			data = processForVariablesFunctions(input);
		}

		return data;
	}

	private DataObject processForVariablesFunctions(InterestingInfo input)
	{
		DataObject data = new DataObject();

		Stack<String> varName = new Stack<String>();
		List<Integer> used = new ArrayList<Integer>();

		boolean foundSomething = false;
		boolean continueSearching = true;

		StringBuilder location = new StringBuilder();
		int start = 0;
		int end = 0;

		if (input != null)
		{
			// check to see if we've already used this token - if we have dont
			// run
			Integer intBox = new Integer(input.getTokenIndex());
			if (this.usedTokens.contains(intBox))
			{
				return null;
			}

			// maint tasks
			used.add(intBox);

			if (input.getTokenKey().equalsIgnoreCase("Octal"))
			{
				varName.push(input.getValue());

				location.append(input.getLine());
				location.append(":");

				end = input.getCharPosition();

				// this is a variable or function
				// lets go backwards in the node
				int prevKey = input.getPreviousTokenIndex();

				// we continue

				while (continueSearching)
				{
					if (prevKey > 0)
					{
						if (this.usedTokens.contains(new Integer(prevKey)))
						{
							return null;
						}

						InterestingInfo ii = PhpUtil.getInstance()
								.getInterestingInfoByKey(prevKey);

						if (ii != null)
						{
							if (ii.getTokenKey().equalsIgnoreCase("apply")
									|| ii.getTokenKey().equalsIgnoreCase(
											"'break'"))
							{

								varName.push(ii.getValue());
								used.add(new Integer(prevKey));

								if (ii.getTokenKey()
										.equalsIgnoreCase("'break'"))
								{
									data.setType(GraphConstants.TYPE_VARIABLE);
									start = ii.getCharPosition();
									foundSomething = true;
									continueSearching = false;
								}
							}
							else if (ii.getTokenKey().equalsIgnoreCase("empty"))
							{
								// found what we are looking for
								data.setType(GraphConstants.TYPE_FUNCTION);

								varName.push(" ");
								varName.push(ii.getValue());
								used.add(new Integer(prevKey));

								start = ii.getCharPosition();
								foundSomething = true;
								continueSearching = false;
							}
							else
							{
								// not one of our criteria
								continueSearching = false;
							}
						}
						else
						{
							// nothing left
							continueSearching = false;
						}

						prevKey = ii.getPreviousTokenIndex();
					}
					else
					{
						// key already used
						continueSearching = false;
					}
				}
			}
		}

		if (foundSomething)
		{
			// write the location
			location.append(start);
			location.append("-");
			location.append(end);

			data = saveWork(data, varName, location, used);

		}

		return data;
	}

	private DataObject processForPrimitive(InterestingInfo input)
	{
		DataObject data = new DataObject();

		// this will eventually store the obj name
		Stack<String> objName = new Stack<String>();

		// this will store the tokens used in this search
		List<Integer> used = new ArrayList<Integer>();

		// flags to short circuit this process
		boolean foundSomething = false;
		boolean continueSearching = true;

		// this will store the location of the code of interest
		StringBuilder location = new StringBuilder();
		int start = 0;
		int end = 0;

		if (input != null)
		{
			// check to see if we've already used this token - if we have dont
			// run
			Integer intBox = new Integer(input.getTokenIndex());
			if (this.usedTokens.contains(intBox))
			{
				return null;
			}

			// add the key to used just in case we end up finding something
			used.add(intBox);

			// we are checking for
			if (input.getTokenKey().equalsIgnoreCase("PrimitiveType"))
			{
				objName.push(input.getValue());

				location.append(input.getLine());
				location.append(":");

				end = input.getCharPosition();

				// this is a variable or function
				// lets go backwards in the node
				int prevKey = input.getPreviousTokenIndex();

				// we continue

				while (continueSearching)
				{
					if (prevKey > 0)
					{
						if (this.usedTokens.contains(new Integer(prevKey)))
						{
							return null;
						}

						InterestingInfo ii = PhpUtil.getInstance()
								.getInterestingInfoByKey(prevKey);

						if (ii != null)
						{
							if (ii.getTokenKey().equalsIgnoreCase("apply")
									|| ii.getTokenKey().equalsIgnoreCase(
											"'break'")
									|| ii.getTokenKey().equalsIgnoreCase(
											"Octal"))
							{

								objName.push(ii.getValue());
								used.add(new Integer(prevKey));

								if (ii.getTokenKey()
										.equalsIgnoreCase("'break'"))
								{
									data.setType(GraphConstants.TYPE_VARIABLE);
									start = ii.getCharPosition();
									foundSomething = true;
									continueSearching = false;
								}
							}
							else if (ii.getValue().equalsIgnoreCase(
									"require once")
									|| ii.getValue()
											.equalsIgnoreCase("include"))
							{
								// found what we are looking for
								data.setType(GraphConstants.TYPE_FILE);

								objName.push(" ");
								objName.push(ii.getValue());
								used.add(new Integer(prevKey));

								start = ii.getCharPosition();
								foundSomething = true;
								continueSearching = false;
							}
							else
							{
								// not one of our criteria
								continueSearching = false;
							}
						}
						else
						{
							// nothing left
							continueSearching = false;
						}

						prevKey = ii.getPreviousTokenIndex();
					}
					else
					{
						// key already used
						continueSearching = false;
					}
				}
			}
		}

		if (foundSomething)
		{
			// write the location
			location.append(start);
			location.append("-");
			location.append(end);

			data = saveWork(data, objName, location, used);

		}

		return data;
	}

	private DataObject saveWork(DataObject data, Stack<String> objName,
			StringBuilder location, List<Integer> usedTokens)
	{
		if (data != null)
		{
			// create name of the object
			StringBuilder sb = new StringBuilder();

			if (objName != null && !objName.isEmpty())
			{
				while (!objName.isEmpty())
				{
					sb.append(objName.pop());
				}
			}
			data.setName(sb.toString());

			// location of where it lives
			data.setLocation(location.toString());

			// store the used tokens into map - so we dont have to search on
			// them again
			if (ValidationUtil.isNotNullAndEmpty(usedTokens))
			{
				for (Integer integ : usedTokens)
				{
					if (!this.usedTokens.contains(integ))
					{
						this.usedTokens.add(integ);
					}
				}
			}
		}

		return data;
	}
}
