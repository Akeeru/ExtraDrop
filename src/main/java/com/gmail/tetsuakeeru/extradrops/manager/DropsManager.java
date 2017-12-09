package com.gmail.tetsuakeeru.extradrops.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.gmail.tetsuakeeru.extradrops.api.BaseDrop;
import com.gmail.tetsuakeeru.extradrops.api.Trigger;
import com.gmail.tetsuakeeru.extradrops.drops.BlockDrop;

public class DropsManager
{
	private List<BaseDrop> drops = new ArrayList<>();

	public DropsManager()
	{
		BlockDrop bd = new BlockDrop("minecraft:dirt");
		bd.addDrop(0.7, "sand");
		bd.addDrop(1, "fish");

		addDrop(bd);
	}

	public List<BaseDrop> getDrops(Set<Trigger> set)
	{
		List<BaseDrop> temp = new ArrayList<>();

		for (BaseDrop bd : drops)
		{

			if (bd.checkSet(set))
			{
				temp.add(bd);
			}
		}

		return temp;
	}

	public List<BaseDrop> getAllDrops()
	{
		return drops;
	}

	public void addDrop(BaseDrop drop)
	{
		drops.add(drop);
	}

	public void exeDrop(Location<World> loc)
	{
		Random rand = new Random();
		double chance = rand.nextDouble();

		for (BaseDrop bd : getAllDrops())
		{
			bd.exe(chance, loc);
		}
	}

	public void exeDropTriggers(Set<Trigger> triggers, Location<World> loc)
	{
		List<BaseDrop> tr = getDrops(triggers);

		Random rand = new Random();
		double chance = rand.nextDouble();

		tr.forEach(item -> item.exe(chance, loc));
	}
}
