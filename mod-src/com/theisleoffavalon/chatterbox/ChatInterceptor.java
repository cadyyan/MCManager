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
package com.theisleoffavalon.chatterbox;

import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet3Chat;
import cpw.mods.fml.common.network.*;

/**
 * This class grabs all the chat code from Minecraft and
 * hands it off to who ever wants it.
 * @author SgtHotshot09
 *
 */
public class ChatInterceptor implements IChatListener{

    /**
     * Holds an instance of the ChatRelayManager
     */
    private ChatRelayManager CRM = null;
    
    /**
     * Contructs a ChatInterceptor which is an IChatListener.
     * It then registers the ChatInterceptor with the NetworkRegistry
     */
    public ChatInterceptor(){
        NetworkRegistry.instance().registerChatListener(this);
        CRM = ChatRelayManager.getInstance();
    }
    
    
    
    @Override
    public Packet3Chat serverChat(NetHandler handler, Packet3Chat message)
    {
        CRM.chatHasArrived(message.message);
        return null;
    }

    @Override
    public Packet3Chat clientChat(NetHandler handler, Packet3Chat message)
    {
        CRM.chatHasArrived(message.message);
        return null;
    }

    
    
}
