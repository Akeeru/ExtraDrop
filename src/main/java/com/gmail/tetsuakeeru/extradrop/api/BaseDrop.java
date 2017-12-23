package com.gmail.tetsuakeeru.extradrop.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import com.flowpowered.math.vector.Vector3d;
import com.gmail.tetsuakeeru.extradrop.ExtraDrop;
import com.gmail.tetsuakeeru.extradrop.api.Trigger.DropType;
import com.gmail.tetsuakeeru.extradrop.api.Trigger.Triggers;

public class BaseDrop
{
	protected Map<ItemStack, Double> drops = new HashMap<>();
	
	protected Set<Trigger> triggers = new HashSet<>();

	public BaseDrop(String trigger, DropType type)
	{
		triggers.add(new Trigger(Triggers.NAME, trigger));
		triggers.add(new Trigger(Triggers.DROPTYPE, type));

		for (Triggers t : Triggers.values())
		{
			if ((t == Triggers.DROPTYPE) || (t == Triggers.NAME))
			{
				continue;
			}

			triggers.add(new Trigger(t, "all"));
		}
	}

	/**
	 * @param chance
	 *            0 - 1 (eg. 0.5 = 50%)
	 * @param name
	 *            eg. dirt
	 */
	public void addDrop(double chance, String name)
	{
		ItemType it = ExtraDrop.getPlugin().getGame().getRegistry().getType(ItemType.class, name).get();
		
		ItemStack is = ItemStack.builder().itemType(it).build();
		drops.put(is, chance);
	}

	public void addTrigger(Triggers key, Object value)
	{
		triggers.add(new Trigger(key, value));
	}

	public void removeTrigger(Triggers key, Object value)
	{
		triggers.remove(new Trigger(key, value));
	}

	public void setTrigger(Triggers key, Object value)
	{
		triggers.forEach(item -> {
			if (item.trigger.equals(key))
			{
				triggers.remove(item);
			}
		});

		triggers.add(new Trigger(key, value));
	}

	public void replaceTrigger(Triggers key, Object oldValue, Object newValue)
	{
		triggers.stream().filter(item -> item.trigger.equals(key)).forEach(item -> {
			if (item.value.equals(oldValue))
			{
				triggers.remove(item);
			}
		});

		triggers.add(new Trigger(key, newValue));
	}

	public void exe(double chance, Location<World> loc)
	{
		for (Entry<ItemStack, Double> s : drops.entrySet())
		{
			ItemStack is = s.getKey();
			double ch = s.getValue();

			if (ch >= chance)
			{
				spawnItem(is, loc);
			}
		}
	}

	private void spawnItem(ItemStack is, Location<World> location)
	{
		Location<World> loc = location.add(0.5, 0.3, 0.5);

		Extent extent = loc.getExtent();
		Entity item = extent.createEntity(EntityTypes.ITEM, loc.getPosition());
		item.offer(Keys.REPRESENTED_ITEM, is.createSnapshot());
		item.setVelocity(new Vector3d(0, 0, 0));
		extent.spawnEntity(item);
	}

	public boolean checkSet(Set<Trigger> set)
	{
		boolean bool = true;
		
		bool = DropUtils.comparateTriggers(triggers, set);
		
		return bool;
	}

	public Trigger find(Triggers key)
	{
		return DropUtils.findTrigger(key, triggers).get(0);
	}

	protected String getTrigger()
	{
		return DropUtils.findTrigger(Triggers.NAME, triggers).get(0).value.toString();
	}
}
