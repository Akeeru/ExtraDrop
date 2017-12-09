package com.gmail.tetsuakeeru.extradrops.drops;

import com.gmail.tetsuakeeru.extradrops.api.BaseDrop;
import com.gmail.tetsuakeeru.extradrops.api.Trigger.DropType;

public class BlockDrop extends BaseDrop
{
	public BlockDrop(String block)
	{
		super(block, DropType.BLOCK);
	}

	public String getName()
	{
		return getTrigger();
	}
}
