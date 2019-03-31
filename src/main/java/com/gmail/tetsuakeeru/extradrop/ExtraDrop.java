package com.gmail.tetsuakeeru.extradrop;

import java.nio.file.Path;
/*
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
import org.spongepowered.api.plugin.Plugin;*/

import com.gmail.tetsuakeeru.extradrop.config.ExtraDropsConfig;
import com.gmail.tetsuakeeru.extradrop.manager.CommandManager;
import com.gmail.tetsuakeeru.extradrop.manager.DropsManager;
import com.gmail.tetsuakeeru.extradrop.manager.EventManager;
import com.google.inject.Inject;

@Plugin(id = "extradrop", name = "ExtraDrop", version = "0.1.3", authors = "Akeeru (TetsuAkeeru)")
public class ExtraDrop
{
	@Inject
	private Logger logger;

	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;

	public static ExtraDrop plugin;

	private Game game = Sponge.getGame();

	private ExtraDropsConfig edsconfig;

	public EventManager managerEvent;
	public DropsManager managerDrops;

	@Listener
	public void preInit(GamePreInitializationEvent event)
	{
		plugin = this;

		managerDrops = new DropsManager();

		edsconfig = new ExtraDropsConfig("extradrop.conf");
		edsconfig.setup();
	}

	@Listener
	public void init(GameInitializationEvent event)
	{
		managerEvent = new EventManager(plugin);
		CommandManager.load(plugin);

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
	{
		edsconfig.reload();
	}

	public Game getGame()
	{
		return game;
	}

	public static ExtraDrop getPlugin()
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

	public void reload()
	{
		edsconfig.reload();
	}
}
