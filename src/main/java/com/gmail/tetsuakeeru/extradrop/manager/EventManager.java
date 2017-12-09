package com.gmail.tetsuakeeru.extradrop.manager;

import java.util.HashSet;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;

import com.gmail.tetsuakeeru.extradrop.ExtraDrop;
import com.gmail.tetsuakeeru.extradrop.api.Trigger;
import com.gmail.tetsuakeeru.extradrop.api.Trigger.DropType;
import com.gmail.tetsuakeeru.extradrop.api.Trigger.Triggers;

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
			String name = event.getTransactions().get(0).getOriginal().getState().getType().getName();
			
			Set<Trigger> triggers = new HashSet<>();
			triggers.add(new Trigger(Triggers.DROPTYPE, DropType.BLOCK));
			triggers.add(new Trigger(Triggers.NAME,name));
			
			plugin.managerDrops.exeDropTriggers(triggers,event.getTransactions().get(0).getOriginal().getLocation().get());
			
			
		}
	}
}
