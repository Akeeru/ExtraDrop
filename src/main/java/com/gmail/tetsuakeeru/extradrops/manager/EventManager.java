package com.gmail.tetsuakeeru.extradrops.manager;

import java.util.HashSet;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;

import com.gmail.tetsuakeeru.extradrops.ExtraDrops;
import com.gmail.tetsuakeeru.extradrops.api.Trigger;
import com.gmail.tetsuakeeru.extradrops.api.Trigger.DropType;
import com.gmail.tetsuakeeru.extradrops.api.Trigger.Triggers;

public class EventManager
{
	ExtraDrops plugin;

	public EventManager(ExtraDrops origin)
	{
		this.plugin = origin;
		Sponge.getEventManager().registerListeners(origin, this);
	}

	@Listener
	public void onBreakBlock(ChangeBlockEvent.Break event)
	{

		if (event.getCause().first(Player.class).isPresent())
		{
			// Player p = event.getCause().first(Player.class).get();

			String name = event.getTransactions().get(0).getOriginal().getState().getType().getName();
			// p.sendMessage(Text.of("Zniszczyłeś: " + name));

			//plugin.managerDrops.exeDropBlock(event.getTransactions().get(0).getOriginal());
			
			Set<Trigger> triggers = new HashSet<>();
			triggers.add(new Trigger(Triggers.DROPTYPE, DropType.BLOCK));
			triggers.add(new Trigger(Triggers.NAME,name));
			
			plugin.managerDrops.exeDropTriggers(triggers,event.getTransactions().get(0).getOriginal().getLocation().get());
			
			
		}
	}
}
