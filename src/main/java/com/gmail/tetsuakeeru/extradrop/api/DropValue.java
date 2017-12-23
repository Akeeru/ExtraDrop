package com.gmail.tetsuakeeru.extradrop.api;

import java.util.Arrays;
import java.util.List;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class DropValue
{
	public final DropArgs arg;
	public final Object val;

	public DropValue(DropArgs args, Object value)
	{
		this.arg = args;
		this.val = value;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof DropValue)
		{
			DropValue dv = (DropValue) obj;

			return arg.equals(dv.arg) && val.equals(dv.val);
		}

		return false;
	}

	public enum DropType
	{
		ITEM, BLOCK, ENTITY;
	}

	public enum DropLevel
	{
		GLOBAL, GROUP, ITEM;
	}

	public enum DropArgs
	{
		CHANCE(Arrays.asList(DropLevel.GLOBAL, DropLevel.GROUP, DropLevel.ITEM), true, Double.class),
		TYPE(Arrays.asList(DropLevel.GROUP, DropLevel.ITEM), false, DropType.class),
		QUANTITY(Arrays.asList(DropLevel.GLOBAL, DropLevel.GROUP, DropLevel.ITEM), true, String.class),
		NAME(Arrays.asList(DropLevel.GROUP, DropLevel.ITEM), false, String.class),
		WORLD(Arrays.asList(DropLevel.GLOBAL, DropLevel.GROUP), true, String.class),
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
	
	public static Builder builder()
	{
		return Builder.builder();
	}
	
	public static class Builder
	{
		DropArgs args;
		Object ob;

		public static Builder builder()
		{
			return new Builder();
		}
		
		public DropValue build()
		{
			DropValue temp = new DropValue(args, ob);
			return temp;
		}

		public Builder key(Object key)
		{
			args = DropArgs.valueOf(key.toString().toUpperCase());
			
			return this;
		}

		public Builder value(CommentedConfigurationNode v)
		{
			if (args == DropArgs.TYPE)
			{
				String nam = v.getString().toUpperCase();
				this.ob = DropType.valueOf(nam);
				return this;
			}
			else
			{
				Object ob = v.getValue(args.getClazz());
				
				this.ob = ob;
				
				return this;
			}
			
			
		}
		
	}

}
