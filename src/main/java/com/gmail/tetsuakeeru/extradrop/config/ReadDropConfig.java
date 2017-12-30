package com.gmail.tetsuakeeru.extradrop.config;

import java.util.HashSet;
import java.util.Set;

import com.gmail.tetsuakeeru.extradrop.ExtraDrop;
import com.gmail.tetsuakeeru.extradrop.api.BaseConfig;
import com.gmail.tetsuakeeru.extradrop.api.DropArgs;
import com.gmail.tetsuakeeru.extradrop.api.DropElement;
import com.gmail.tetsuakeeru.extradrop.api.DropGroup;
import com.gmail.tetsuakeeru.extradrop.api.DropLevel;
import com.gmail.tetsuakeeru.extradrop.api.DropValue;

public class ReadDropConfig extends BaseConfig
{

	public ReadDropConfig(String name, String folder)
	{
		super(name, "", folder);
	}

	@Override
	public void populate()
	{

	}

	@Override
	public void init()
	{
		Set<DropValue> global = new HashSet<>();

		ExtraDrop.getPlugin().getLogger().info("GLOBAL");

		if (!get().getNode("global").isVirtual())
		{
			get().getNode("global").getChildrenMap().forEach((k, v) -> {

				DropArgs da = DropArgs.valueOf(k.toString().toUpperCase());

				if (da.checkLevel(DropLevel.GLOBAL))
				{
					global.add(DropValue.builder().key(k).value(v).build());
				}
			});
		}

		get().getChildrenMap().forEach((k, v) -> {
			if (!k.equals("global"))
			{
				DropGroup dg = new DropGroup(k.toString(), global);

				ExtraDrop.getPlugin().getLogger().info("GROUP");

				v.getChildrenMap().forEach((gk, gv) -> {
					if (!gv.hasMapChildren())
					{
						DropArgs da = DropArgs.valueOf(gk.toString().toUpperCase());

						if (da.checkLevel(DropLevel.GROUP))
						{
							dg.addValue(DropValue.builder().key(gk).value(gv).build());
						}
					}
				});

				ExtraDrop.getPlugin().getLogger().info("ITEM");
				v.getChildrenMap().forEach((gk, gv) -> {
					if (gv.hasMapChildren())
					{
						DropElement de = new DropElement(gk.toString());

						gv.getChildrenMap().forEach((ik, iv) -> {
							if (!iv.hasMapChildren())
							{
								DropArgs da = DropArgs.valueOf(ik.toString().toUpperCase());

								if (da.checkLevel(DropLevel.ITEM))
								{
									de.addValue(DropValue.builder().key(ik).value(iv).build());

								}
							}
						});

						dg.addDrop(de);

					}
				});

				ExtraDrop.getPlugin().managerDrops.addDrop(dg);

			}
		});

	}

}
