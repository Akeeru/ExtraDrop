package com.gmail.tetsuakeeru.extradrop.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

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

	public void add(DropArgs arg, Object val)
	{
		if (val instanceof CommentedConfigurationNode)
		{
			add(arg, (CommentedConfigurationNode)val);
		}
		else
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
	}

	public boolean hasArg(DropArgs arg)
	{
		return mapData.containsKey(arg);
	}
	
	public Object get(DropArgs arg)
	{
		if (hasArg(arg))
		{
			return mapData.get(arg);
		}
		else
		{
			return null;
		}
	}

	private void add(DropArgs arg, CommentedConfigurationNode node)
	{
		Object ob = null;

		if (arg == DropArgs.TYPE)
		{
			ob = DropType.valueOf(node.getString().toUpperCase());
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
				
				if (s.getKey().getClazz() == List.class)
				{
					bool = comparateList(side.get(DropArgs.TOOL), ob);
					break;
				}
				else
				{
					if (!ob.equals(s.getValue()))
					{
						bool = false;
						break;
					}
					
					break;
				}
			}
		}

		return bool;
	}
	
	@SuppressWarnings("unchecked")
	private boolean comparateList(Object main, Object side)
	{
		List<Object> l1 = (List<Object>) main;
		List<Object> l2 = (List<Object>) side;
		
		boolean bool = false;
		
		for (Object ob : l2)
		{
			if (l1.contains(ob))
			{
				bool = true;
				break;
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
				val.add(k, v);
			}
		});

		return val;

	}
}
