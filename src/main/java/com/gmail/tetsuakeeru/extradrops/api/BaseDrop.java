package com.gmail.tetsuakeeru.extradrops.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import com.flowpowered.math.vector.Vector3d;
import com.gmail.tetsuakeeru.extradrops.ExtraDrops;

public class BaseDrop
{
	protected Map<ItemStack, Double> drops = new HashMap<>();

	/**
	 * @param chance
	 *            0 - 1 (eg. 0.5 = 50%)
	 * @param name
	 *            eg. dirt
	 */
	public void addDrop(double chance, String name)
	{
		ItemType it = ExtraDrops.getPlugin().getGame().getRegistry().getType(ItemType.class, name).get();

		// DummyObjectProvider.createFor(ItemType.class,
		// name.toUpperCase()).getType();

		ItemStack is = ItemStack.builder().itemType(it).build();
		drops.put(is, chance);
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
		extent.spawnEntity(item, Cause.source(EntitySpawnCause.builder().entity(item).type(SpawnTypes.PLUGIN).build()).build());
	}
}
