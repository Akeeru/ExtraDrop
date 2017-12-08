package com.gmail.tetsuakeeru.extradrops;

import com.gmail.tetsuakeeru.extradrops.api.BaseConfig;

public class ExtraDropsConfig extends BaseConfig
{

	public ExtraDropsConfig(String name)
	{
		super(name);
	}

	@Override
	public void init()
	{
		put(false, "inlude", "drops/blocks.conf");
		put(false, "inlude", "drops/tools.conf");
		put(false, "inlude", "drops/mobs.conf");
	}

	@Override
	public void populate()
	{
		
	}

}
