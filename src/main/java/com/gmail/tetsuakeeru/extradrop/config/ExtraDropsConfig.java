package com.gmail.tetsuakeeru.extradrop.config;

import java.util.Map.Entry;

import com.gmail.tetsuakeeru.extradrop.ExtraDrop;
import com.gmail.tetsuakeeru.extradrop.api.BaseConfig;

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
		ExtraDrop.getPlugin().managerDrops.clear();

		if (!get().getNode("include").isVirtual())
		{
			for (Entry<Object, ? extends CommentedConfigurationNode> s : get().getNode("include").getChildrenMap().entrySet())
			{
				String name = s.getKey().toString();
				boolean turn = s.getValue().getBoolean();

				ExtraDrop.getPlugin().getLogger().info("Include '" + name + "':" + turn);

				if (turn)
				{
					String folders = name.substring(0, name.lastIndexOf("/") + 1);
					String fileName = name.substring(folders.length());

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
