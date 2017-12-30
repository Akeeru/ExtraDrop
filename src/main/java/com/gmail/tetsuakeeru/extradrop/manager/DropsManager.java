package com.gmail.tetsuakeeru.extradrop.manager;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.gmail.tetsuakeeru.extradrop.ExtraDrop;
import com.gmail.tetsuakeeru.extradrop.api.DropArgs;
import com.gmail.tetsuakeeru.extradrop.api.DropElement;
import com.gmail.tetsuakeeru.extradrop.api.DropGroup;
import com.gmail.tetsuakeeru.extradrop.api.DropLevel;
import com.gmail.tetsuakeeru.extradrop.api.DropType;
import com.gmail.tetsuakeeru.extradrop.api.Values;

public class DropsManager
{
	private List<DropGroup> groups = new ArrayList<>();

	public DropsManager()
	{}

	public List<DropGroup> getAllDropGroups()
	{
		return groups;
	}

	public void addDrop(DropGroup group)
	{
		if(checkGroup(group))
			groups.add(group);
	}

	private boolean checkGroup(DropGroup group)
	{
		DropType type = (DropType) group.getValues().get(DropArgs.TYPE);
		String name = (String) group.getValues().get(DropArgs.NAME);
		
		boolean temp = false;
		
		switch (type)
		{
			case BLOCK:
			case ITEM:
				temp = ExtraDrop.getPlugin().getGame().getRegistry().getType(ItemType.class, name).isPresent();
				
				
				break;
			case ENTITY:
				temp = ExtraDrop.getPlugin().getGame().getRegistry().getType(EntityType.class, name).isPresent();
				break;
		}
		
		if (temp)
		{
			boolean temp2 = true;
			
			for (DropElement s : group.getDrops())
			{
				if (checkItem(s) == false) 
				{
					temp2 = false;
				}
			}
			
			return temp2;
		}
		else
		{
			ExtraDrop.getPlugin().getLogger().error("This '" + name + "' doesn't exist!");
			return false;
		}
	}

	private boolean checkItem(DropElement item)
	{
		boolean temp = true;
		
		DropType type = (DropType) item.getValues().get(DropArgs.TYPE);
		String name = (String) item.getValues().get(DropArgs.NAME);
		
		switch (type)
		{
			case BLOCK:
			case ITEM:
				temp = ExtraDrop.getPlugin().getGame().getRegistry().getType(ItemType.class, name).isPresent();
				
				
				break;
			case ENTITY:
				temp = ExtraDrop.getPlugin().getGame().getRegistry().getType(EntityType.class, name).isPresent();
				break;
		}
		
		if (temp == false) ExtraDrop.getPlugin().getLogger().error("This '" + name + "' doesn't exist!");;
		
		return temp;
	}

	// public void exeDrop(Location<World> loc, Set<DropValue> values)
	// {
	// groups.forEach(item -> {
	//
	// if (DropUtils.comparateValues(values, item.getValues()))
	// {
	// item.exe(loc, DropUtils.clearValues(values, DropLevel.GROUP));
	// }
	//
	// });
	// }

	public void clear()
	{
		groups.clear();
	}

	public void exeDrop(Location<World> loc, Values vals)
	{
		groups.forEach(item ->{
			if (vals.comparate(item.getValues()))
			{
				item.exe(loc, vals.extract(DropLevel.GROUP));
			}
		});
	}
}
