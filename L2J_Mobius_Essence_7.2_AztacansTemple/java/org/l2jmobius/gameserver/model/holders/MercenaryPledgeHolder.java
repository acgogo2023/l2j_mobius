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
package org.l2jmobius.gameserver.model.holders;

public class MercenaryPledgeHolder
{
	private final int _playerId;
	private final String _name;
	private final int _classId;
	private final int _clanId;
	
	public MercenaryPledgeHolder(int playerId, String name, int classId, int clanId)
	{
		_playerId = playerId;
		_name = name;
		_classId = classId;
		_clanId = clanId;
	}
	
	public int getPlayerId()
	{
		return _playerId;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public int getClassId()
	{
		return _classId;
	}
	
	public int getClanId()
	{
		return _clanId;
	}
}
