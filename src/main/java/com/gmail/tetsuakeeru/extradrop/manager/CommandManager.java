package com.gmail.tetsuakeeru.extradrop.manager;

import static org.spongepowered.api.text.Text.of;

import org.spongepowered.api.command.spec.CommandSpec;

import com.gmail.tetsuakeeru.extradrop.ExtraDrop;
import com.gmail.tetsuakeeru.extradrop.cmds.CMD_RELOAD;

public class CommandManager
{
	public static CommandSpec cmd_RELOAD = CommandSpec.builder()
			.executor(new CMD_RELOAD())
			.description(of("Reload this plugin"))
			.permission("extradrop.extradrop.reload")
			.build();

	public static CommandSpec cmd_BASE = CommandSpec.builder().
			description(of("Base command"))
			.child(cmd_RELOAD, "reload", "rl")
			.permission("extradrop.extradrop")
			.build();

	public static void load(ExtraDrop plugin)
	{
		plugin.getGame().getCommandManager().register(plugin, cmd_BASE, "extradrop", "ed");
	}
}
