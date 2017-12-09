package com.gmail.tetsuakeeru.extradrops.config;

import java.util.Map.Entry;
import java.util.Optional;

import org.spongepowered.api.item.ItemType;

import com.gmail.tetsuakeeru.extradrops.ExtraDrops;
import com.gmail.tetsuakeeru.extradrops.api.BaseConfig;
import com.gmail.tetsuakeeru.extradrops.drops.BlockDrop;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ReadDropConfig extends BaseConfig
{
	
	public ReadDropConfig(String name, String folder)
	{
		super(name, "", folder);
	}

	@Override
	public void populate()
	{
		get().getNode("minecraft:stone", "minecraft:iron_ingot", "chance").setValue(0.2);
		get().getNode("minecraft:stone", "minecraft:gold_ingot", "chance").setValue(0.2);
		get().getNode("minecraft:stone", "minecraft:gold_block", "chance").setValue(0.2);
	}

	@Override
	public void init()
	{
		for (Entry<Object, ? extends CommentedConfigurationNode> s : get().getChildrenMap().entrySet())
		{
			String trigger = s.getKey().toString();
			
			Optional<ItemType> it = ExtraDrops.getPlugin().getGame().getRegistry().getType(ItemType.class, trigger);
			
			if (!it.isPresent())
			{
				continue;
			}
			else
			{
				ExtraDrops.getPlugin().getLogger().info("Drop Test:   " + it.get().getType().getId());
				
				BlockDrop bd = new BlockDrop(trigger);
				
				for (Entry<Object, ? extends CommentedConfigurationNode> v : s.getValue().getChildrenMap().entrySet())
				{
					String dropBlock = v.getKey().toString();
					double chance = v.getValue().getNode("chance").getDouble();
					
					bd.addDrop(chance, dropBlock);
					
				}
				
				ExtraDrops.getPlugin().managerDrops.addDrop(bd);
			}
		}
	}

}
