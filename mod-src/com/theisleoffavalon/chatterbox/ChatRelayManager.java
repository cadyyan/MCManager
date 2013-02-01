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

import java.util.ArrayList;
import java.util.List;

/**
 * This class Handles all the ChatRelay observers
 * @author SgtHotshot09
 *
 */
public class ChatRelayManager {

    /**
     * Holds a List of ChatRelays
     */
    private List<ChatRelay> chatRelays = new ArrayList<ChatRelay>();
    
    /**
     * Stores the instance for the ChatRelayManager
     */
    private static ChatRelayManager instance = null;
    
    /**
     * Constructor for the ChatRelayManager
     */
    private ChatRelayManager(){
        
    }
    
    /**
     * Gets the instance of ChatRelayManager
     * @return ChatRelayManager
     */
    public static ChatRelayManager getInstance(){
        if(instance==null){
            instance = new ChatRelayManager();
        }
        return instance;
    }
    
    /**
     * Register a ChatRelay with the system
     * @param cr ChatRelay
     */
    public void registerChatRelay(ChatRelay cr){
        chatRelays.add(cr);
    }
    
    /**
     * Is called when a chat Has arrived to be distributed
     * @param message A message to send
     */
    public void chatHasArrived(String message){
        for(ChatRelay cr : chatRelays){
            cr.chatHasArrived(message);
        }
    }
    
    
}
