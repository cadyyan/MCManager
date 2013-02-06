/*
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                    Version 2, December 2004
 * 
 * Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 * 
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this license document, and changing it is allowed as long
 * as the name is changed.
 * 
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 * 
 * 0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package com.theisleoffavalon.mcmanager.chatterbox;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet3Chat;
import cpw.mods.fml.common.network.IChatListener;

/**
 * This class grabs all the chat code from Minecraft and hands it off to who
 * ever wants it.
 * 
 * @author SgtHotshot09
 * 
 */
public class ChatIntercepter implements IChatListener
{
	/**
	 * Holds a List of ChatRelays
	 */
	private List<IChatRelay> chatRelays = new ArrayList<IChatRelay>();

	/**
	 * Constructs a ChatIntercepter which is an IChatListener.
	 */
	public ChatIntercepter()
	{
	}

	@Override
	public Packet3Chat serverChat(NetHandler handler, Packet3Chat message)
	{
		// TODO: use the chat message
		
		chatHasArrived("<" + handler.getPlayer().username + ">" + message.message);
		return message;
	}

	@Override
	public Packet3Chat clientChat(NetHandler handler, Packet3Chat message)
	{
		// TODO: use the chat message
		chatHasArrived("<" + handler.getPlayer().username + ">" + message.message);
		return message;
	}
	
	/**
	 * Register a ChatRelay with the system
	 * 
	 * @param cr - ChatRelay
	 *            
	 */
	public void registerChatRelay(IChatRelay cr)
	{
		chatRelays.add(cr);
	}
	
	/**
	 * UnRegisters a ChatRelay with the system
	 * 
	 * @param cr - ChatRelay
	 */
	public void unregisterChatRelay(IChatRelay cr)
	{
		chatRelays.remove(cr);
	}
	
	/**
	 * Is called when a chat Has arrived to be distributed
	 * 
	 * @param message - A message to send
	 *            
	 */
	public void chatHasArrived(String message)
	{
		for(IChatRelay cr : chatRelays)
			cr.chatHasArrived(message);
	}
}
