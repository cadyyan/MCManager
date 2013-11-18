package com.theisleoffavalon.mcmanager.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;

import com.theisleoffavalon.mcmanager.MCManager;

/**
 * Heals the current player or a targeted player to full health.
 * 
 * @author Cadyyan
 *
 */
public class CommandHeal extends Command
{
	/**
	 * Creates a heal command instance.
	 */
	public CommandHeal()
	{
		super("heal");
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/" + name + "[player]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		String username = args.length < 1 ? null : args[0];
		EntityPlayer player = username == null ? getCommandSenderAsPlayer(sender) :
												 MCManager.instance.getServer().getConfigurationManager().getPlayerForUsername(username);
		
		if(player == null)
			throw new PlayerNotFoundException();
		
		float healthAmount = player.getMaxHealth() - player.getHealth();
		int foodAmount = (int)player.getMaxHealth() - player.getFoodStats().getFoodLevel();
		
		player.heal(healthAmount);
		player.getFoodStats().addStats(foodAmount, 20.0F);
		notifyAdmins(sender, "Healing " + player.username);
	}
}
