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

import java.util.Set;

import org.l2jmobius.commons.network.WritableBuffer;
import org.l2jmobius.gameserver.data.xml.EnchantSkillGroupsData;
import org.l2jmobius.gameserver.enums.SkillEnchantType;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.holders.EnchantSkillHolder;
import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.ServerPackets;
import org.l2jmobius.gameserver.util.SkillEnchantConverter;

/**
 * @author KenM, Mobius
 */
public class ExEnchantSkillInfoDetail extends ServerPacket
{
	private final SkillEnchantType _type;
	private final int _skillId;
	private final int _skillLevel;
	private final EnchantSkillHolder _enchantSkillHolder;
	
	public ExEnchantSkillInfoDetail(SkillEnchantType type, int skillId, int skillLevel, int skillSubLevel, Player player)
	{
		_type = type;
		_skillId = skillId;
		_skillLevel = skillSubLevel > 1000 ? SkillEnchantConverter.levelToErtheia(skillSubLevel) : skillLevel;
		_enchantSkillHolder = EnchantSkillGroupsData.getInstance().getEnchantSkillHolder(skillSubLevel % 1000);
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		if (_enchantSkillHolder == null)
		{
			return;
		}
		
		ServerPackets.EX_ENCHANT_SKILL_INFO_DETAIL.writeId(this, buffer);
		buffer.writeInt(_type.ordinal());
		buffer.writeInt(_skillId);
		buffer.writeInt(_skillLevel);
		if (_type != SkillEnchantType.UNTRAIN)
		{
			buffer.writeLong(_enchantSkillHolder.getSp(_type));
			buffer.writeInt(_enchantSkillHolder.getChance(_type));
			final Set<ItemHolder> holders = _enchantSkillHolder.getRequiredItems(_type);
			buffer.writeInt(holders.size());
			holders.forEach(holder ->
			{
				buffer.writeInt(holder.getId());
				buffer.writeInt((int) holder.getCount());
			});
		}
	}
}
