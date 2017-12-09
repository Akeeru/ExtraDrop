package com.gmail.tetsuakeeru.extradrops.config;

import java.util.Map.Entry;

import com.gmail.tetsuakeeru.extradrops.ExtraDrops;
import com.gmail.tetsuakeeru.extradrops.api.BaseConfig;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ExtraDropsConfig extends BaseConfig
{

	public ExtraDropsConfig(String name)
	{
		super(name);
	}

	@Override
	public void init()
	{
		ExtraDrops.getPlugin().getLogger().info("ASGBSFDAB");
		
		if (!get().getNode("include").isVirtual())
		{
			ExtraDrops.getPlugin().getLogger().info("Test aD ASD: ");
			
			for (Entry<Object, ? extends CommentedConfigurationNode> s: get().getNode("include").getChildrenMap().entrySet())
			{
				String name = s.getKey().toString();
				boolean turn = s.getValue().getBoolean();
				
				ExtraDrops.getPlugin().getLogger().info("Test: " + name + "   " + turn);
				
				if (turn)
				{
					ExtraDrops.getPlugin().getLogger().info("Turnning on:: " + name);
					
					String folders = name.substring(0, name.lastIndexOf("/")+1);
					String fileName = name.substring(folders.length());
					
					ExtraDrops.getPlugin().getLogger().info("Turnning on:: " + folders + "  ||  " + fileName);
					
					new ReadDropConfig(fileName, folders).setup();
				}
			}
		}
	}

	@Override
	public void populate()
	{
		put(false, "include", "drops/blocks.conf");
		put(false, "include", "drops/tools.conf");
		put(false, "include", "drops/mobs.conf");
	}

}
