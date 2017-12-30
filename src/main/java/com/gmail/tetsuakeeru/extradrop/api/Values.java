package com.gmail.tetsuakeeru.extradrop.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ninja.leaping.configurate.ConfigurationNode;

public class Values
{
	// protected Set<DropValue> data = new HashSet<>();

	protected Map<DropArgs, Object> mapData = new HashMap<>();

	public Values()
	{}

	public Object findValue(DropArgs arg)
	{
		Object ob = null;

		if (mapData.containsKey(arg))
		{
			ob = mapData.get(arg);
		}

		return ob;
	}

	public void addValue(DropArgs arg, Object val)
	{
		if (mapData.containsKey(arg))
		{
			mapData.replace(arg, val);
		}
		else
		{
			mapData.put(arg, val);
		}
	}

	public void addValue(DropArgs arg, ConfigurationNode node)
	{
		Object ob = null;

		if (arg == DropArgs.TYPE)
		{
			ob = node.getString().toUpperCase();
		}
		else
		{
			ob = node.getValue(arg.getClazz());
		}

		if (ob != null)
		{
			if (mapData.containsKey(arg))
			{
				mapData.replace(arg, ob);
			}
			else
			{
				mapData.put(arg, ob);
			}
		}
	}

	public boolean comparate(Values side)
	{
		boolean bool = true;

		for (Entry<DropArgs, Object> s : side.mapData.entrySet())
		{
			Object ob = null;
			ob = findValue(s.getKey());

			if (ob != null)
			{
				if (!ob.equals(s.getValue()))
				{
					bool = false;
					break;
				}
			}
		}

		return bool;
	}

	public Values extract(DropLevel lvl)
	{
		Values val = new Values();

		mapData.forEach((k, v) -> {

			if (k.checkLevel(lvl) && k.isInherit())
			{
				val.addValue(k, v);
			}
		});

		return val;

	}
}
