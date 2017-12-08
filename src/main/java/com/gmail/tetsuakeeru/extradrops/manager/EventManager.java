package com.gmail.tetsuakeeru.extradrops.manager;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.text.Text;

import com.gmail.tetsuakeeru.extradrops.ExtraDrops;

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
			Player p = event.getCause().first(Player.class).get();

			String name = event.getTransactions().get(0).getOriginal().getState().getType().getName();
			p.sendMessage(Text.of("Zniszczyłeś: " + name));

			plugin.managerDrops.exeDropBlock(event.getTransactions().get(0).getOriginal());
		}
	}
}
