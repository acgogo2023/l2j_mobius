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
package quests.Q10967_CulturedAdventurer;

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
 * Cultured Adventurer (10967)
 * @author RobikBobik
 * @Note: Based on NA server September 2019
 * @TODO: Maybe wrong NpcStringId when you killing monsters in Abandoned Camp
 */
public class Q10967_CulturedAdventurer extends Quest
{
	// NPCs
	private static final int CAPTAIN_BATHIS = 30332;
	// Monsters
	private static final int OL_MAHUM_SHOOTER = 20063;
	private static final int OL_MAHUM_SERGEANT = 20439;
	private static final int OL_MAHUM_OFFICER = 20066;
	private static final int OL_MAHUM_GENERAL = 20438;
	private static final int OL_MAHUM_COMMANDER = 20076;
	// Items
	private static final ItemHolder ADVENTURERS_BROOCH = new ItemHolder(91932, 1);
	private static final ItemHolder ADVENTURERS_BROOCH_GEMS = new ItemHolder(91936, 1);
	private static final ItemHolder SCROLL_ENCHANT_ADEN_WEAPON = new ItemHolder(93038, 2);
	// Misc
	private static final String KILL_COUNT_VAR = "KillCount";
	private static final int MAX_LEVEL = 30;
	private static final int MIN_LEVEL = 25;
	
	public Q10967_CulturedAdventurer()
	{
		super(10967);
		addStartNpc(CAPTAIN_BATHIS);
		addTalkId(CAPTAIN_BATHIS);
		addKillId(OL_MAHUM_SHOOTER, OL_MAHUM_SERGEANT, OL_MAHUM_OFFICER, OL_MAHUM_GENERAL, OL_MAHUM_COMMANDER);
		setQuestNameNpcStringId(NpcStringId.LV_25_30_MORE_EXPERIENCE);
		addCondMinLevel(MIN_LEVEL, "no_lvl.html");
		addCondMaxLevel(MAX_LEVEL, "no_lvl.html");
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
			case "30332-01.htm":
			{
				htmltext = event;
				break;
			}
			case "30332-02.htm":
			{
				htmltext = event;
				break;
			}
			case "30332-03.htm":
			{
				qs.startQuest();
				htmltext = event;
				break;
			}
			case "30332-05.html":
			{
				if (qs.isStarted())
				{
					player.sendPacket(new ExShowScreenMessage("You've obtained Adventurer's Brooch and Adventurer's Gem Fragment.#Check the tutorial to equip the gems.", 5000));
					addExpAndSp(player, 2500000, 67500);
					giveItems(player, ADVENTURERS_BROOCH);
					giveItems(player, ADVENTURERS_BROOCH_GEMS);
					giveItems(player, SCROLL_ENCHANT_ADEN_WEAPON);
					qs.exitQuest(false, true);
					htmltext = event;
					break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(Npc npc, Player player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		if (qs.isCreated())
		{
			htmltext = "30332.htm";
		}
		else if (qs.isStarted())
		{
			if (qs.isCond(2))
			{
				htmltext = "30332-04.html";
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
			if (killCount < 150)
			{
				qs.set(KILL_COUNT_VAR, killCount);
				playSound(killer, QuestSound.ITEMSOUND_QUEST_ITEMGET);
				sendNpcLogList(killer);
			}
			else
			{
				qs.setCond(2, true);
				qs.unset(KILL_COUNT_VAR);
				killer.sendPacket(new ExShowScreenMessage(NpcStringId.MONSTERS_OF_THE_ABANDONED_CAMP_ARE_KILLED_NUSE_THE_TELEPORT_TO_GET_TO_BATHIS_IN_GLUDIO, 2, 5000));
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public Set<NpcLogListHolder> getNpcLogList(Player player)
	{
		final QuestState qs = getQuestState(player, false);
		if ((qs != null) && qs.isCond(1))
		{
			final Set<NpcLogListHolder> holder = new HashSet<>();
			holder.add(new NpcLogListHolder(NpcStringId.KILL_MONSTERS_IN_THE_ABANDONED_CAMP.getId(), true, qs.getInt(KILL_COUNT_VAR)));
			return holder;
		}
		return super.getNpcLogList(player);
	}
	
	@Override
	public boolean checkPartyMember(Player member, Npc npc)
	{
		final QuestState qs = getQuestState(member, false);
		return ((qs != null) && qs.isStarted());
	}
}