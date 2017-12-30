package com.gmail.tetsuakeeru.extradrop.api;

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
