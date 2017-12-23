package com.gmail.tetsuakeeru.extradrop.manager;

import java.util.HashSet;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;

import com.gmail.tetsuakeeru.extradrop.ExtraDrop;
import com.gmail.tetsuakeeru.extradrop.api.DropValue;
import com.gmail.tetsuakeeru.extradrop.api.DropValue.DropArgs;
import com.gmail.tetsuakeeru.extradrop.api.DropValue.DropType;

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

		if (event.getCause().first(Player.class).isPresent())
		{
			BlockSnapshot block = event.getTransactions().get(0).getOriginal();
			String name = block.getState().getType().getName();
			String world = block.getLocation().get().getExtent().getName();
			
			Set<DropValue> values = new HashSet<>();
			values.add(new DropValue(DropArgs.TYPE, DropType.BLOCK));
			values.add(new DropValue(DropArgs.WORLD, world));
			values.add(new DropValue(DropArgs.NAME, name));
			
			plugin.managerDrops.exeDrop(block.getLocation().get(), values);
		}
	}
}
