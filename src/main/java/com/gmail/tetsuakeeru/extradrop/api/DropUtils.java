package com.gmail.tetsuakeeru.extradrop.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DropUtils
{
	public static List<DropValue> findValue(DropArgs arg, Set<DropValue> values)
	{
		List<DropValue> temp = new ArrayList<>();
		
		values.forEach(item -> {
			if (item.arg.equals(arg))
			{
				temp.add(item);
			}
		});
		
		return temp;
	}
	
	public static boolean comparateValues(Set<DropValue> main, Set<DropValue> side)
	{
		boolean bool = true;
		
		for (DropValue dv : side)
		{
			List<DropValue> s = findValue(dv.arg, main);
			
			if(!s.isEmpty())
			{
				if(!s.get(0).val.equals(dv.val))
				{
					bool = false;
				}
			}
		}
		
		
		
		return bool;
	}
	
	public static Set<DropValue> clearValues(Set<DropValue> values, DropLevel lvl)
	{
		Set<DropValue> set = new HashSet<>();
		
		values.forEach(item -> {
			if (!item.arg.checkLevel(lvl) && item.arg.isInherit())
			{
				set.add(item);
			}
		});
		
		return set;
		
	}
}
