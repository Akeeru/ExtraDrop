package com.gmail.tetsuakeeru.extradrop.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class DropGroup
{
	protected List<DropElement> drops = new ArrayList<>();
	// protected Set<DropValue> values = new HashSet<>();

	protected Values vals = new Values();

	public DropGroup(String name, Values global)
	{
		vals = global;

		vals.add(DropArgs.NAME, name);

	}

	public void addDrop(DropElement element)
	{
		DropElement temp = element;

		// values.stream().filter(item -> item.arg.isInherit()).forEach(item ->
		// {
		// DropArgs as = item.arg;
		//
		// if (temp.find(as) == null)
		// {
		// temp.addValue(item);
		// }
		// });

		drops.add(temp);
	}

	// public Set<DropValue> getValues()
	// {
	// return values;
	// }

	public List<DropElement> getDrops()
	{
		return drops;
	}

	public void addValue(DropArgs arg, Object ob)
	{
		vals.add(arg, ob);
	}

	public Values getValues()
	{
		return vals;
	}

	public void exe(Location<World> loc, Values vals)
	{
		drops.forEach(item -> {
			if (vals.comparate(item.getValues()))
			{
				if (!item.vals.hasArg(DropArgs.CHANCE))
				{
					item.exe(loc, vals.extract(DropLevel.ITEM));
				}
				else
				{
					Random rand = new Random();

					double chn = rand.nextDouble();

					double chance = (double) item.getValues().get(DropArgs.CHANCE);

					if (chance >= chn)
					{
						item.exe(loc, vals.extract(DropLevel.ITEM));
					}
				}
			}
		});
	}

	// public void removeValue(DropArgs arg)
	// {
	// values.forEach(item -> {
	// if (item.arg.equals(arg))
	// {
	// values.remove(item);
	// }
	// });
	// }
	//
	// public void addValue(DropValue dv)
	// {
	// if (DropUtils.findValue(dv.arg, values).isEmpty())
	// {
	// values.add(dv);
	// }
	// else
	// {
	// removeValue(dv.arg);
	// values.add(dv);
	// }
	// }

	// public void exe(Location<World> loc, Set<DropValue> set)
	// {
	// drops.forEach(item -> {
	// if (DropUtils.comparateValues(set, item.values))
	// {
	// if (DropUtils.findValue(DropArgs.CHANCE, item.values).isEmpty())
	// {
	// item.exe(loc, DropUtils.clearValues(set, DropLevel.ITEM));
	// }
	// else
	// {
	// Random rand = new Random();
	//
	// double chn = rand.nextDouble();
	//
	// double chance = (double) DropUtils.findValue(DropArgs.CHANCE,
	// item.values).get(0).val;
	//
	// if (chance >= chn)
	// {
	// item.exe(loc, DropUtils.clearValues(set, DropLevel.ITEM));
	// }
	// }
	//
	// }
	// });
	// }
}
