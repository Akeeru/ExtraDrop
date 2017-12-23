package com.gmail.tetsuakeeru.extradrop.cmds;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import com.gmail.tetsuakeeru.extradrop.ExtraDrop;


public class CMD_RELOAD implements CommandExecutor
{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		ExtraDrop.getPlugin().reload();
		src.sendMessage(Text.of("[ExtraDrop] Plugin is reloaded"));
		return CommandResult.success();
	}

}
