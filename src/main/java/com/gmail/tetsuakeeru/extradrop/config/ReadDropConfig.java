package com.gmail.tetsuakeeru.extradrop.config;

import java.util.Map.Entry;
import java.util.Optional;

import org.spongepowered.api.item.ItemType;

import com.gmail.tetsuakeeru.extradrop.ExtraDrop;
import com.gmail.tetsuakeeru.extradrop.api.BaseConfig;
import com.gmail.tetsuakeeru.extradrop.drops.BlockDrop;

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
			
			Optional<ItemType> it = ExtraDrop.getPlugin().getGame().getRegistry().getType(ItemType.class, trigger);
			
			if (!it.isPresent())
			{
				ExtraDrop.getPlugin().getLogger().error("This item or block doesn't exist! (" + trigger + ")");
				continue;
			}
			else
			{
				BlockDrop bd = new BlockDrop(trigger);
				
				for (Entry<Object, ? extends CommentedConfigurationNode> v : s.getValue().getChildrenMap().entrySet())
				{
					String dropBlock = v.getKey().toString();
					double chance = v.getValue().getNode("chance").getDouble();
					
					bd.addDrop(chance, dropBlock);
					
				}
				
				ExtraDrop.getPlugin().managerDrops.addDrop(bd);
			}
		}
	}

}
