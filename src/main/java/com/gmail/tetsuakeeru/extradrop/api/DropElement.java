package com.gmail.tetsuakeeru.extradrop.api;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import com.flowpowered.math.vector.Vector3d;
import com.gmail.tetsuakeeru.extradrop.ExtraDrop;

public class DropElement
{
	// protected Set<DropValue> values = new HashSet<>();

	protected Values vals = new Values();

	public DropElement(String name)
	{
		replace(DropArgs.NAME, name);
	}

	public void addValue(DropArgs arg, Object ob)
	{
		if (!vals.hasArg(arg))
		{
			vals.add(arg, ob);
		}
	}

	public void replace(DropArgs arg, Object ob)
	{
		vals.add(arg, ob);
	}

	public Values getValues()
	{
		return vals;
	}

	public void exe(Location<World> preloc, Values extract)
	{
		Location<World> loc = preloc.add(0.5, 0.5, 0.5);
		Extent ext = loc.getExtent();

		DropType type = (DropType) vals.get(DropArgs.TYPE);
		String name = (String) vals.get(DropArgs.NAME);
		
		Entity ent = null;

		switch (type)
		{
			case BLOCK:
			case ITEM:
				ItemType it = ExtraDrop.getPlugin().getGame().getRegistry().getType(ItemType.class, name).get();
				ItemStack isi = ItemStack.builder().itemType(it).build();
				ent = ext.createEntity(EntityTypes.ITEM, loc.getPosition());
				ent.offer(Keys.REPRESENTED_ITEM, isi.createSnapshot());
				break;
			case ENTITY:
				EntityType et = ExtraDrop.getPlugin().getGame().getRegistry().getType(EntityType.class, name).get();
				ent = ext.createEntity(et, loc.getPosition());

				break;
		}

		ent.setVelocity(new Vector3d(0, 0, 0));
		ext.spawnEntity(ent);
	}

	// public Set<DropValue> getValues()
	// {
	// return values;
	// }
	//
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
	// public DropValue find(DropArgs arg)
	// {
	// List<DropValue> ldv = DropUtils.findValue(arg, values);
	//
	// return ldv.isEmpty() ? null : ldv.get(0);
	// }

	// public void exe(Location<World> location, Set<DropValue> set)
	// {
	// Location<World> loc = location.add(0.5, 0.3, 0.5);
	//
	// Extent extent = loc.getExtent();
	//
	// DropType s = (DropType) DropUtils.findValue(DropArgs.TYPE,
	// values).get(0).val;
	// String name = (String) DropUtils.findValue(DropArgs.NAME,
	// values).get(0).val;
	//
	// Entity ent = null;
	//
	// switch (s)
	// {
	// case BLOCK:
	// case ITEM:
	// ItemType it =
	// ExtraDrop.getPlugin().getGame().getRegistry().getType(ItemType.class,
	// name).get();
	// ItemStack isi = ItemStack.builder().itemType(it).build();
	// ent = extent.createEntity(EntityTypes.ITEM, loc.getPosition());
	// ent.offer(Keys.REPRESENTED_ITEM, isi.createSnapshot());
	// break;
	//
	// case ENTITY:
	// EntityType et =
	// ExtraDrop.getPlugin().getGame().getRegistry().getType(EntityType.class,
	// name).get();
	// ent = extent.createEntity(et, loc.getPosition());
	//
	// break;
	// }
	//
	// ent.setVelocity(new Vector3d(0, 0, 0));
	// extent.spawnEntity(ent);
	//
	// }
}
