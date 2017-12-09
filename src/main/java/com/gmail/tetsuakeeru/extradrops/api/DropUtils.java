package com.gmail.tetsuakeeru.extradrops.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gmail.tetsuakeeru.extradrops.api.Trigger.Triggers;

public class DropUtils
{
	public static List<Trigger> findTrigger(Triggers key, Set<Trigger> triggers)
	{
		List<Trigger> temp = new ArrayList<>();

		triggers.forEach(item -> {
			if (item.trigger == key)
			{
				temp.add(item);
			}
		});

		return temp;
	}

	public static boolean comparateTriggers(Set<Trigger> main, Set<Trigger> side)
	{
		boolean bool = true;

		for (Trigger s : side)
		{
			Set<Trigger> temp = new HashSet<>();

			main.stream().filter(item -> item.equals(s)).forEach(item -> temp.add(item));;

			if (temp.size() <= 0)
			{
				bool = false;
			}
		}

		return bool;
	}
}
