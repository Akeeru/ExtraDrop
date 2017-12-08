package com.gmail.tetsuakeeru.extradrops.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.gmail.tetsuakeeru.extradrops.ExtraDrops;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public abstract class BaseConfig
{
	protected Path configFile;
	protected Path configDir = ExtraDrops.getPlugin().getConfigDir();
	protected ConfigurationLoader<CommentedConfigurationNode> configLoader;
	protected CommentedConfigurationNode configNode;

	public BaseConfig(String name)
	{
		this(name, ".conf");
	}

	public BaseConfig(String name, String suffix)
	{
		this.configFile = Paths.get(this.configDir + "/" + name + suffix, new String[0]);
		this.configLoader = HoconConfigurationLoader.builder().setPath(this.configFile).build();
	}

	public BaseConfig(String name, String suffix, String folder)
	{
		this.configDir = Paths.get(this.configDir + "/" + folder, new String[0]);
		this.configFile = Paths.get(this.configDir + "/" + name + suffix, new String[0]);
		this.configLoader = HoconConfigurationLoader.builder().setPath(this.configFile).build();
	}

	public void setup()
	{
		if (!Files.exists(this.configDir))
		{
			try
			{
				Files.createDirectories(this.configDir);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		if (!Files.exists(this.configFile))
		{
			try
			{
				Files.createFile(this.configFile);
				load();
				populate();
				init();
				save();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			load();
			init();
		}
	}

	public void load()
	{
		try
		{
			this.configNode = this.configLoader.load();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public abstract void init();
	
	public void save()
	{
		try
		{
			this.configLoader.save(this.configNode);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public abstract void populate();

	public CommentedConfigurationNode get()
	{
		return this.configNode;
	}

	public void put(Object value, Object... args)
	{
		get().getNode(args).setValue(value);
	}

	public void put(String coment, Object value, Object... args)
	{
		get().getNode(args).setValue(value).setComment(coment);
	}

	public CommentedConfigurationNode get(Object args)
	{
		return get().getNode(args);
	}
}
