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
package com.l2jmobius.gameserver.network.clientpackets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jmobius.L2DatabaseFactory;
import com.l2jmobius.gameserver.model.L2World;
import com.l2jmobius.gameserver.model.actor.instance.L2PcInstance;
import com.l2jmobius.gameserver.model.actor.instance.L2PcInstance.Friend;
import com.l2jmobius.gameserver.network.serverpackets.FriendList;
import com.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * This class ...
 * @version $Revision: 1.3.4.2 $ $Date: 2005/03/27 15:29:30 $
 */
public class RequestFriendDel extends L2GameClientPacket
{
	
	private static final String _C__61_REQUESTFRIENDDEL = "[C] 61 RequestFriendDel";
	private static Logger _log = Logger.getLogger(RequestFriendDel.class.getName());
	
	private String _name;
	
	@Override
	protected void readImpl()
	{
		_name = readS();
	}
	
	@Override
	public void runImpl()
	{
		SystemMessage sm;
		
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}
		
		// Check if target is friend and delete him from friends list
		final Friend friend = activeChar.getFriend(_name);
		if (friend == null)
		{
			// Player is not in your friendlist
			sm = new SystemMessage(SystemMessage.S1_NOT_ON_YOUR_FRIENDS_LIST);
			sm.addString(_name);
			activeChar.sendPacket(sm);
			sm = null;
			return;
		}
		
		// Remove friend from friend list
		activeChar.getFriendList().remove(friend);
		
		activeChar.sendPacket(new FriendList(activeChar));
		
		final L2PcInstance otherPlayer = L2World.getInstance().getPlayer(_name);
		if (otherPlayer != null)
		{
			final Friend requestor = otherPlayer.getFriend(activeChar.getName());
			if (requestor != null)
			{
				otherPlayer.getFriendList().remove(requestor);
			}
			
			otherPlayer.sendPacket(new FriendList(otherPlayer));
		}
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("DELETE FROM character_friends WHERE (char_id=? AND friend_id=?) OR (char_id=? AND friend_id=?)"))
		{
			statement.setInt(1, activeChar.getObjectId());
			statement.setInt(2, friend.getObjectId());
			statement.setInt(3, friend.getObjectId());
			statement.setInt(4, activeChar.getObjectId());
			statement.execute();
			
			// Player deleted from your friendlist
			sm = new SystemMessage(SystemMessage.S1_HAS_BEEN_DELETED_FROM_YOUR_FRIENDS_LIST);
			sm.addString(_name);
			activeChar.sendPacket(sm);
			sm = null;
		}
		catch (final Exception e)
		{
			_log.log(Level.WARNING, "could not del friend objectid: ", e);
		}
	}
	
	@Override
	public String getType()
	{
		return _C__61_REQUESTFRIENDDEL;
	}
}