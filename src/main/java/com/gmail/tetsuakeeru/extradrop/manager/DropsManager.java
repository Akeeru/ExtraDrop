package com.gmail.tetsuakeeru.extradrop.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.gmail.tetsuakeeru.extradrop.api.DropGroup;
import com.gmail.tetsuakeeru.extradrop.api.DropUtils;
import com.gmail.tetsuakeeru.extradrop.api.DropValue;
import com.gmail.tetsuakeeru.extradrop.api.DropValue.DropLevel;

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
		groups.add(group);
	}
	
	public void exeDrop(Location<World> loc, Set<DropValue> values)
	{
		groups.forEach(item -> {
			
			if(DropUtils.comparateValues(values, item.getValues()))
			{
				item.exe(loc, DropUtils.clearValues(values, DropLevel.GROUP));
			}
			
		});
	}

	public void clear()
	{
		groups.clear();
	}
}
