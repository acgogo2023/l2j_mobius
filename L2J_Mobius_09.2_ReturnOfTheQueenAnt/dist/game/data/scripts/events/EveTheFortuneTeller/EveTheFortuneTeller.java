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
package events.EveTheFortuneTeller;

import org.l2jmobius.gameserver.enums.ChatType;
import org.l2jmobius.gameserver.enums.LuckyGameType;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.quest.LongTimeEvent;
import org.l2jmobius.gameserver.network.NpcStringId;
import org.l2jmobius.gameserver.network.serverpackets.NpcSay;
import org.l2jmobius.gameserver.network.serverpackets.luckygame.ExStartLuckyGame;
import org.l2jmobius.gameserver.util.Broadcast;

/**
 * Eve the Fortune Teller Returns<br>
 * Info - http://www.lineage2.com/en/news/events/11182015-eve-the-fortune-teller-returns.php
 * @author Mobius
 */
public class EveTheFortuneTeller extends LongTimeEvent
{
	// NPCs
	private static final int EVE = 8542;
	private static final int JAYCE = 8540;
	// Items
	private static final int FORTUNE_READING_TICKET = 23767;
	private static final int LUXURY_FORTUNE_READING_TICKET = 23768;
	// Misc
	private static final NpcStringId[] JAYCE_TEXT =
	{
		NpcStringId.I_LOOK_WEST,
		NpcStringId.EVE_WILL_BRING_YOU_GREAT_FORTUNE,
		NpcStringId.YOU_WILL_ONE_DAY_ASK_ME_FOR_GUIDANCE_IN_YOUR_PATH
	};
	
	private EveTheFortuneTeller()
	{
		addStartNpc(EVE);
		addFirstTalkId(EVE);
		addTalkId(EVE);
		addSpawnId(JAYCE);
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		String htmltext = null;
		switch (event)
		{
			case "8542.htm":
			case "8542-1.htm":
			{
				htmltext = event;
				break;
			}
			case "FortuneReadingGame":
			{
				player.sendPacket(new ExStartLuckyGame(LuckyGameType.NORMAL, player.getInventory().getInventoryItemCount(FORTUNE_READING_TICKET, -1)));
				break;
			}
			case "LuxuryFortuneReadingGame":
			{
				player.sendPacket(new ExStartLuckyGame(LuckyGameType.LUXURY, player.getInventory().getInventoryItemCount(LUXURY_FORTUNE_READING_TICKET, -1)));
				break;
			}
			case "JAYCE_SHOUT":
			{
				Broadcast.toKnownPlayersInRadius(npc, new NpcSay(npc.getObjectId(), ChatType.NPC_GENERAL, npc.getId(), getRandomEntry(JAYCE_TEXT)), 1000);
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(Npc npc, Player player)
	{
		return "8542.htm";
	}
	
	@Override
	public String onSpawn(Npc npc)
	{
		startQuestTimer("JAYCE_SHOUT", 45000, npc, null, true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new EveTheFortuneTeller();
	}
}
