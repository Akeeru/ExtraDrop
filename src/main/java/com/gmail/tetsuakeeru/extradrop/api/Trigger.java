package com.gmail.tetsuakeeru.extradrop.api;

public class Trigger
{
	public final Triggers trigger;
	public final Object value;

	public Trigger(Triggers trigger, Object value)
	{
		this.trigger = trigger;
		this.value = value;
	}

	public enum Triggers
	{
		WORLD, TOOL, DROPTYPE, NAME;
	}

	public enum DropType
	{
		BLOCK, ENTITY;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Trigger)
		{
			Trigger t2 = (Trigger) obj;
			
			return trigger.equals(t2.trigger) && value.equals(t2.value);
		}

		return false;
	}
}