package com.gmail.tetsuakeeru.extradrop.api;

import java.util.Arrays;
import java.util.List;

public enum DropArgs
{
	CHANCE(Arrays.asList(DropLevel.GLOBAL, DropLevel.GROUP, DropLevel.ITEM), true, Double.class),
	TYPE(Arrays.asList(DropLevel.GROUP, DropLevel.ITEM), false, DropType.class),
	QUANTITY(Arrays.asList(DropLevel.GLOBAL, DropLevel.GROUP, DropLevel.ITEM), true, String.class),
	NAME(Arrays.asList(DropLevel.GROUP, DropLevel.ITEM), false, String.class),
	WORLD(Arrays.asList(DropLevel.GLOBAL, DropLevel.GROUP), true, String.class),
	ACTION(Arrays.asList(DropLevel.GLOBAL, DropLevel.GROUP, DropLevel.ITEM), true, List.class),
	TOOL(Arrays.asList(DropLevel.GLOBAL, DropLevel.GROUP, DropLevel.ITEM), true, List.class),
	Y_POS(Arrays.asList(DropLevel.GLOBAL, DropLevel.GLOBAL, DropLevel.ITEM), true, String.class),
	// CHANCE(Arrays.asList(DropLevel.GLOBAL, DropLevel.GLOBAL,
	// DropLevel.ITEM), true),
	;

	private final List<DropLevel> levels;
	private final boolean inherit;
	private final Class<?> clazz;

	private DropArgs(List<DropLevel> dps, boolean inherit, Class<?> clazz)
	{
		this.levels = dps;
		this.inherit = inherit;
		this.clazz = clazz;
	}
	
	public List<DropLevel> get()
	{
		return levels;
	}

	public boolean checkLevel(DropLevel lvl)
	{
		return levels.contains(lvl);
	}

	public boolean checkLevel(String name)
	{
		return checkLevel(DropLevel.valueOf(name));
	}

	public boolean isInherit()
	{
		return inherit;
	}
	
	public Class<?> getClazz()
	{
		return clazz;
	}
	
	@Override
	public String toString()
	{
		return name().toLowerCase();
	}
}
