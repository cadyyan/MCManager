package com.theisleoffavalon.mcmanager.network;

import cpw.mods.fml.common.network.IPacketHandler;

public class PacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager network, Packet250CustomPayload packet, Player player)
	{
		// TODO: send custom packet data
	}
}

