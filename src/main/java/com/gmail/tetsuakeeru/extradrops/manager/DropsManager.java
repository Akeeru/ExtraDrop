package com.gmail.tetsuakeeru.extradrops.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.gmail.tetsuakeeru.extradrops.ExtraDrops;
import com.gmail.tetsuakeeru.extradrops.api.BaseDrop;
import com.gmail.tetsuakeeru.extradrops.api.BlockDrop;

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

	public List<BaseDrop> getDrops()
	{
		List<BaseDrop> temp = new ArrayList<>();

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

	public void exeDropBlock(BlockSnapshot bs)
	{
		Random rand = new Random();
		double chance = rand.nextDouble();

		for (BaseDrop bd : getAllDrops())
		{
			if (bd instanceof BlockDrop)
			{
				BlockDrop bld = (BlockDrop) bd;

				if (bld.blockID.equalsIgnoreCase(bs.getState().getType().getId()))
				{
					bld.exe(chance, bs.getLocation().get());
				}
			}
		}
	}
}
