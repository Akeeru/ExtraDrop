package com.gmail.tetsuakeeru.extradrop.manager;

import java.util.HashSet;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;

import com.gmail.tetsuakeeru.extradrop.ExtraDrop;
import com.gmail.tetsuakeeru.extradrop.api.DropArgs;
import com.gmail.tetsuakeeru.extradrop.api.DropType;
import com.gmail.tetsuakeeru.extradrop.api.DropValue;

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
		
		Entity ent = event.getCause().first(Entity.class).orElse(null);
		
		if (ent != null)
		{
			if (ent instanceof Player)
			{
				Player p = (Player) ent;
				String itemName = p.getItemInHand(HandTypes.MAIN_HAND).get().getType().getName();
				
				plugin.getLogger().info("Cause Entity: " + ent.getType().getName() + "(" + itemName + ")"  + " -> " + name + " in " + world);
			}
			else
			{
				plugin.getLogger().info("Cause Entity: " + ent.getType().getName()  + " -> " + name + " in " + world);
			}
			
			
		}
		
		
		for (Object ob : event.getCause().all())
		{
			if (!(ob instanceof Entity))		
			{
				plugin.getLogger().info("Cause: " + ob.toString()  + " -> " + name + " in " + world);
			}
		}

		if (event.getCause().first(Player.class).isPresent())
		{
			Set<DropValue> values = new HashSet<>();
			values.add(new DropValue(DropArgs.TYPE, DropType.BLOCK));
			values.add(new DropValue(DropArgs.WORLD, world));
			values.add(new DropValue(DropArgs.NAME, name));
			
			plugin.managerDrops.exeDrop(block.getLocation().get(), values);
		}
	}
}
