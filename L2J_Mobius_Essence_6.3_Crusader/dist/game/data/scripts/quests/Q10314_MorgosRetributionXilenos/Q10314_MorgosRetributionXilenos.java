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
package quests.Q10314_MorgosRetributionXilenos;

import java.util.HashSet;
import java.util.Set;

import org.l2jmobius.gameserver.enums.QuestSound;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.model.holders.NpcLogListHolder;
import org.l2jmobius.gameserver.model.quest.Quest;
import org.l2jmobius.gameserver.model.quest.QuestState;
import org.l2jmobius.gameserver.network.NpcStringId;
import org.l2jmobius.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * @author Serenitty
 */
public class Q10314_MorgosRetributionXilenos extends Quest
{
	// NPC
	private static final int ASHIN = 34322;
	// Monsters
	private static final int XILENOS_WARRIOR = 22479;
	private static final int XILENOS_SHOOTER = 22481;
	private static final int XILENOS_SCOUT = 22485;
	private static final int XILENOS_ELDER = 22483;
	// Items
	private static final ItemHolder SAYHA_COOKIE = new ItemHolder(93274, 20);
	private static final ItemHolder SAYHA_STORM = new ItemHolder(91712, 12);
	private static final ItemHolder MAGIC_LAMP_CHARGING_POTION = new ItemHolder(91757, 2);
	// Misc
	private static final int MIN_LEVEL = 90;
	private static final String KILL_COUNT_VAR = "KillCount";
	
	public Q10314_MorgosRetributionXilenos()
	{
		super(10314);
		addStartNpc(ASHIN);
		addTalkId(ASHIN);
		addKillId(XILENOS_WARRIOR, XILENOS_SHOOTER, XILENOS_SCOUT, XILENOS_ELDER);
		setQuestNameNpcStringId(NpcStringId.LV_90_MORGOS_RETRIBUTION_XILENOS);
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "StartMorgosRetribution":
			{
				qs.startQuest();
				htmltext = event;
				break;
			}
			case "34322-02.htm":
			{
				htmltext = event;
				break;
			}
			case "reward":
			{
				if (qs.isStarted())
				{
					addExpAndSp(player, 25000000000L, 675000000);
					giveItems(player, SAYHA_COOKIE);
					giveItems(player, SAYHA_STORM);
					giveItems(player, MAGIC_LAMP_CHARGING_POTION);
					qs.exitQuest(false, true);
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(Npc npc, Player player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		if (qs.isCreated() && (player.getLevel() < MIN_LEVEL))
		{
			htmltext = "noreq.htm";
			return htmltext;
		}
		if (qs.isCreated())
		{
			htmltext = "34322-01.htm";
		}
		else if (qs.isStarted())
		{
			if (qs.isCond(1))
			{
				htmltext = "34322-03.html";
			}
			else if (qs.isCond(2))
			{
				htmltext = "34322-02.htm";
			}
		}
		else if (qs.isCompleted())
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		return htmltext;
	}
	
	@Override
	public String onKill(Npc npc, Player killer, boolean isSummon)
	{
		final QuestState qs = getQuestState(killer, false);
		if ((qs != null) && qs.isCond(1))
		{
			final int killCount = qs.getInt(KILL_COUNT_VAR) + 1;
			if (killCount < 2000)
			{
				qs.set(KILL_COUNT_VAR, killCount);
				playSound(killer, QuestSound.ITEMSOUND_QUEST_ITEMGET);
				sendNpcLogList(killer);
			}
			else
			{
				qs.setCond(2, true);
				qs.unset(KILL_COUNT_VAR);
				showOnScreenMsg(killer, NpcStringId.TALK_TO_ASSISTANT_ASHIN, ExShowScreenMessage.TOP_CENTER, 10000);
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public Set<NpcLogListHolder> getNpcLogList(Player player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs != null)
		{
			if (qs.isCond(1))
			{
				final Set<NpcLogListHolder> holder = new HashSet<>();
				holder.add(new NpcLogListHolder(NpcStringId.KILL_MONSTERS_IN_THE_XILENOS_FORTRESS.getId(), true, qs.getInt(KILL_COUNT_VAR)));
				return holder;
			}
			else if (qs.isCond(2))
			{
				final Set<NpcLogListHolder> holder = new HashSet<>();
				holder.add(new NpcLogListHolder(NpcStringId.LV_90_MORGOS_RETRIBUTION_XILENOS_COMPLETED.getId(), true, qs.getInt(KILL_COUNT_VAR)));
				return holder;
			}
		}
		return super.getNpcLogList(player);
	}
}
