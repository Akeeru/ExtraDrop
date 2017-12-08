package com.gmail.tetsuakeeru.extradrops;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import com.gmail.tetsuakeeru.extradrops.manager.DropsManager;
import com.gmail.tetsuakeeru.extradrops.manager.EventManager;
import com.google.inject.Inject;

@Plugin(id = "extradrops", name = "ExtraDrops", version = "0.0.1", authors = "Akeeru (TetsuAkeeru)")
public class ExtraDrops
{
	@Inject
	private Logger logger;

	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;

	public static ExtraDrops plugin;

	private Game game = Sponge.getGame();

	private ExtraDropsConfig edsconfig;
	
	public EventManager managerEvent;
	public DropsManager managerDrops;

	@Listener
	public void preInit(GamePreInitializationEvent event)
	{
		plugin = this;

		edsconfig = new ExtraDropsConfig("extradrops");
		edsconfig.setup();
	}

	@Listener
	public void init(GameInitializationEvent event)
	{
		managerEvent = new EventManager(plugin);
		managerDrops = new DropsManager();
	}

	@Listener
	public void postInit(GamePostInitializationEvent event)
	{

	}

	@Listener
	public void onServerStart(GameStartedServerEvent event)
	{
		this.logger.info("This plugin has started!");
	}

	@Listener
	public void onServerStop(GameStoppedServerEvent event)
	{
		this.logger.info("This plugin has stopped!");
	}

	@Listener
	public void onReload(GameReloadEvent event)
	{}

	public Game getGame()
	{
		return game;
	}

	public static ExtraDrops getPlugin()
	{
		return plugin;
	}

	public Path getConfigDir()
	{
		return configDir;
	}
	
	public Logger getLogger()
	{
		return logger;
	}
}
