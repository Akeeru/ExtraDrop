package com.gmail.tetsuakeeru.extradrop.drops;

import com.gmail.tetsuakeeru.extradrop.api.BaseDrop;
import com.gmail.tetsuakeeru.extradrop.api.Trigger.DropType;

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
