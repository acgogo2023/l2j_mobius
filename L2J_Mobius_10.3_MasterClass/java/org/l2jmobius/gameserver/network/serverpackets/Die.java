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
package org.l2jmobius.gameserver.network.serverpackets;

import org.l2jmobius.commons.network.WritableBuffer;
import org.l2jmobius.gameserver.instancemanager.CastleManager;
import org.l2jmobius.gameserver.instancemanager.FortManager;
import org.l2jmobius.gameserver.model.SiegeClan;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.siege.Castle;
import org.l2jmobius.gameserver.model.siege.Fort;
import org.l2jmobius.gameserver.model.skill.BuffInfo;
import org.l2jmobius.gameserver.model.skill.CommonSkill;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.ServerPackets;

/**
 * @author Mobius
 */
public class Die extends ServerPacket
{
	private final int _objectId;
	private final boolean _isSweepable;
	private int _flags = 1; // To nearest village.
	private int _delayFeather = 0;
	
	public Die(Creature creature)
	{
		_objectId = creature.getObjectId();
		_isSweepable = creature.isAttackable() && creature.isSweepActive();
		if (creature.isPlayer())
		{
			for (BuffInfo effect : creature.getEffectList().getEffects())
			{
				if (effect.getSkill().getId() == CommonSkill.FEATHER_OF_BLESSING.getId())
				{
					_delayFeather = effect.getTime();
					break;
				}
			}
			
			final Player player = creature.getActingPlayer();
			if (!player.isInTimedHuntingZone())
			{
				final Clan clan = player.getClan();
				boolean isInCastleDefense = false;
				boolean isInFortDefense = false;
				SiegeClan siegeClan = null;
				final Castle castle = CastleManager.getInstance().getCastle(creature);
				final Fort fort = FortManager.getInstance().getFort(creature);
				if ((castle != null) && castle.getSiege().isInProgress())
				{
					siegeClan = castle.getSiege().getAttackerClan(clan);
					isInCastleDefense = (siegeClan == null) && castle.getSiege().checkIsDefender(clan);
				}
				else if ((fort != null) && fort.getSiege().isInProgress())
				{
					siegeClan = fort.getSiege().getAttackerClan(clan);
					isInFortDefense = (siegeClan == null) && fort.getSiege().checkIsDefender(clan);
				}
				
				// ClanHall check.
				if ((clan != null) && (clan.getHideoutId() > 0))
				{
					_flags += 2;
				}
				// Castle check.
				if (((clan != null) && (clan.getCastleId() > 0)) || isInCastleDefense)
				{
					_flags += 4;
				}
				// Fortress check.
				if (((clan != null) && (clan.getFortId() > 0)) || isInFortDefense)
				{
					_flags += 8;
				}
				// Outpost check.
				if (((siegeClan != null) && !isInCastleDefense && !isInFortDefense && !siegeClan.getFlag().isEmpty()))
				{
					_flags += 16;
				}
			}
			
			// Feather check.
			if (creature.getAccessLevel().allowFixedRes() || creature.getInventory().haveItemForSelfResurrection())
			{
				_flags += 32;
			}
		}
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.DIE.writeId(this, buffer);
		buffer.writeInt(_objectId);
		buffer.writeLong(_flags);
		buffer.writeInt(_isSweepable);
		buffer.writeInt(_delayFeather); // Feather item time.
		buffer.writeByte(0); // Hide die animation.
		buffer.writeInt(0);
		buffer.writeInt(0);
		buffer.writeInt(0);
		buffer.writeInt(0);
		buffer.writeInt(0);
		buffer.writeInt(0);
		buffer.writeInt(0);
	}
}
