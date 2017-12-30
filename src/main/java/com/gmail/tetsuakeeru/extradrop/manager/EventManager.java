package com.gmail.tetsuakeeru.extradrop.manager;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;

import com.gmail.tetsuakeeru.extradrop.ExtraDrop;
import com.gmail.tetsuakeeru.extradrop.api.DropAction;
import com.gmail.tetsuakeeru.extradrop.api.DropArgs;
import com.gmail.tetsuakeeru.extradrop.api.DropType;
import com.gmail.tetsuakeeru.extradrop.api.Values;

public class EventManager
{
	ExtraDrop plugin;

	public EventManager(ExtraDrop origin)
	{
		this.plugin = origin;
		Sponge.getEventManager().registerListeners(origin, this);
	}

	@Listener
	public void onBreakBlock(ChangeBlockEvent.Break event)
	{
		BlockSnapshot block = event.getTransactions().get(0).getOriginal();
		String name = block.getState().getType().getName();
		String world = block.getLocation().get().getExtent().getName();

		Values vals = new Values();

		vals.add(DropArgs.TYPE, DropType.BLOCK);
		vals.add(DropArgs.WORLD, world);
		vals.add(DropArgs.NAME, name);
		vals.add(DropArgs.ACTION, DropAction.BREAK);

		Entity ent = event.getCause().first(Entity.class).orElse(null);

		if (ent != null)
		{
			if (ent instanceof Player)
			{
				Player p = (Player) ent;
				// String itemName =
				// p.getItemInHand(HandTypes.MAIN_HAND).get().getType().getName();

				// plugin.getLogger().info("Cause Entity: " +
				// ent.getType().getName() + "(" + itemName + ")" + " -> " +
				// name + " in " + world);
				
				List<String> list = new ArrayList<>();
				
				if (p.getItemInHand(HandTypes.MAIN_HAND).isPresent())
				{
					list.add(p.getItemInHand(HandTypes.MAIN_HAND).get().getType().getId());
				}
				else
				{
					list.add("hand");
				}

				vals.add(DropArgs.TOOL,list);
			}
			else
			{
				// plugin.getLogger().info("Cause Entity: " +
				// ent.getType().getName() + " -> " + name + " in " + world);
				vals.add(DropArgs.TOOL, ent.getType().getId());

			}

		}
		
		plugin.managerDrops.exeDrop(block.getLocation().get(), vals);

		// for (Object ob : event.getCause().all())
		// {
		// if (!(ob instanceof Entity))
		// {
		// plugin.getLogger().info("Cause: " + ob.toString() + " -> " + name + "
		// in " + world);
		// }
		// }

		// if (event.getCause().first(Player.class).isPresent())
		// {
		// Set<DropValue> values = new HashSet<>();
		// values.add(new DropValue(DropArgs.TYPE, DropType.BLOCK));
		// values.add(new DropValue(DropArgs.WORLD, world));
		// values.add(new DropValue(DropArgs.NAME, name));
		//
		// plugin.managerDrops.exeDrop(block.getLocation().get(), values);
		// }
	}

	@Listener
	public void onPlaceBlock(ChangeBlockEvent.Place event)
	{
		BlockSnapshot block = event.getTransactions().get(0).getOriginal();
		String name = block.getState().getType().getName();
		String world = block.getLocation().get().getExtent().getName();

		Values vals = new Values();

		vals.add(DropArgs.TYPE, DropType.BLOCK);
		vals.add(DropArgs.WORLD, world);
		vals.add(DropArgs.NAME, name);
		vals.add(DropArgs.ACTION, DropAction.PLACE);
	}

	@Listener
	public void onKillEntity(DestructEntityEvent.Death event)
	{
		Entity ent = event.getTargetEntity();
		String world = ent.getWorld().getName();
		String name = ent.getType().getId();

		Values vals = new Values();

		vals.add(DropArgs.TYPE, DropType.ENTITY);
		vals.add(DropArgs.WORLD, world);
		vals.add(DropArgs.NAME, name);
		vals.add(DropArgs.ACTION, DropAction.KILL);
		
		plugin.managerDrops.exeDrop(ent.getLocation(), vals);
	}

}
