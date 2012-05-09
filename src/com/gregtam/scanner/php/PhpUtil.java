package com.gregtam.scanner.php;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.gregtam.scanner.graph.GraphService;
import com.gregtam.scanner.model.InterestingInfo;
import com.gregtam.scanner.util.ValidationUtil;

public class PhpUtil
{
	static Logger logger = Logger.getLogger(GraphService.class.getName());
	private Map<Integer, String> tokenLookup;
	private static PhpUtil _instance;
	private Map<Integer, InterestingInfo> variableTokens;

	private PhpUtil()
	{
		loadMap();
	}

	public void clear()
	{
		variableTokens.clear();
	}

	private void loadMap()
	{
		try
		{
			tokenLookup = getTokenTypes();
			logger.debug("loaded tokens: " + tokenLookup.size());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			tokenLookup = new HashMap<Integer, String>();
			logger.debug("failed to load tokens..");
		}

		variableTokens = new HashMap<Integer, InterestingInfo>();
	}

	public Map<Integer, InterestingInfo> getAllTokens()
	{
		return variableTokens;
	}

	public void setAllTokens(Map<Integer, InterestingInfo> allTokens)
	{
		this.variableTokens = allTokens;
	}

	public InterestingInfo getInterestingInfoByKey(int input)
	{
		if (input > 0)
		{
			Integer intBox = new Integer(input);
			if (variableTokens != null && !variableTokens.isEmpty())
			{
				if (variableTokens.containsKey(intBox))
				{
					return variableTokens.get(intBox);
				}
			}
		}

		return null;
	}

	public void addInterestingInfo(InterestingInfo input)
	{
		if (input != null)
		{
			int key = input.getTokenIndex();

			// here we discard all the applys - in this case we dont need them -
			// they might be useful later
			if (key > 0)
			{
				variableTokens.put(new Integer(key), input);
			}
		}
	}

	public void removeInterestingInfo(InterestingInfo input)
	{
		if (input != null)
		{
			int key = input.getTokenIndex();

			// here we discard all the applys - in this case we dont need them -
			// they might be useful later
			removeInterestingInfoByKey(key);
		}
	}

	public void removeInterestingInfoByKey(int key)
	{
		if (key > 0)
		{
			Integer intBox = new Integer(key);

			if (variableTokens.containsKey(intBox))
			{
				variableTokens.remove(intBox);
			}
		}
	}

	public synchronized static PhpUtil getInstance()
	{
		if (_instance == null)
		{
			_instance = new PhpUtil();
		}

		return _instance;
	}

	public Map<Integer, String> getTokenTypes() throws IOException
	{
		String str = readStream(new FileInputStream(new File(
				"properties/grammar/Php.tokens")));

		String[] lines = str.split("\\n");
		Map<Integer, String> out = new HashMap<Integer, String>();
		for (String line : lines)
		{
			line = line.trim();
			if (!line.equals(""))
			{
				Pattern pat = Pattern.compile("(\\w+|'[^']+')=(\\d+)");
				Matcher match = pat.matcher(line);
				match.find();
				String label = match.group(1);
				Integer id = Integer.parseInt(match.group(2));
				out.put(id, label);
			}

		}
		return out;
	}

	public String findToken(int index)
	{
		return tokenLookup.get(new Integer(index));
	}

	public static String readStream(InputStream is) throws IOException
	{
		Reader r = new InputStreamReader(is);
		char[] buf = new char[1024];
		int n = 0;
		StringBuilder sb = new StringBuilder();
		while ((n = r.read(buf)) != -1)
		{
			sb.append(buf, 0, n);
		}
		return sb.toString();
	}

	public static boolean isCode(String extension)
	{
		boolean isCode = false;

		if (ValidationUtil.isNotNullAndEmpty(extension))
		{
			String lower = extension.toLowerCase();

			isCode = PhpConstants.validExtentions.contains(lower);
		}

		return isCode;
	}
}
