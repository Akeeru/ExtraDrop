package com.gmail.tetsuakeeru.extradrop.config;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;

import org.spongepowered.api.asset.Asset;

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
		put(true, "include", "drops/stone.conf");
		put(false, "include", "drops/blocks.conf");
		
		copyFromAssets("stone.conf", "drops/");
		copyFromAssets("blocks.conf", "drops/");
	}

	private void copyFromAssets(String name, String path)
	{
		Path tempDir = Paths.get(this.configDir + "/" + path, new String[0]);

		try
		{
			Asset as = ExtraDrop.getPlugin().getGame().getAssetManager().getAsset(ExtraDrop.plugin, name).get();
			
			//as.copyToFile(tempDir);
			as.copyToDirectory(tempDir);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
