/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.l2jmobius.gameserver.network.clientpackets.newcrest;

import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.network.clientpackets.ClientPacket;

/**
 * @author Serenitty
 */
public class RequestSetPledgeCrestPreset extends ClientPacket
{
	private int _emblemType;
	private int _emblem;
	
	@Override
	protected void readImpl()
	{
		_emblemType = readInt();
		_emblem = readInt();
	}
	
	@Override
	protected void runImpl()
	{
		final Player player = getPlayer();
		if (player == null)
		{
			return;
		}
		
		final Clan clan = player.getClan();
		if (clan == null)
		{
			return;
		}
		
		if (clan.getLevel() < 3)
		{
			return;
		}
		
		if (clan.getLeader().getObjectId() == player.getObjectId())
		{
			if (_emblemType == 0)
			{
				clan.changeClanCrest(0);
				return;
			}
			
			if (_emblemType == 1)
			{
				clan.changeClanCrest(_emblem);
			}
		}
	}
}
