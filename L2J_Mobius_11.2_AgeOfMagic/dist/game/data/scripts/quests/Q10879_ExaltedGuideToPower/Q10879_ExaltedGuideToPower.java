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
package quests.Q10879_ExaltedGuideToPower;

import java.util.HashSet;
import java.util.Set;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.holders.NpcLogListHolder;
import org.l2jmobius.gameserver.model.quest.Quest;
import org.l2jmobius.gameserver.model.quest.QuestState;
import org.l2jmobius.gameserver.model.quest.State;
import org.l2jmobius.gameserver.network.NpcStringId;

import quests.Q10873_ExaltedReachingAnotherLevel.Q10873_ExaltedReachingAnotherLevel;

/**
 * Exalted, Guide to Power (10879)
 * @URL https://l2wiki.com/Exalted,_Guide_to_Power
 * @author Dmitri
 */
public class Q10879_ExaltedGuideToPower extends Quest
{
	// NPC
	private static final int LIONEL = 33907;
	// Items
	private static final int PROOF_OF_PRIDE = 80827;
	private static final int LIONEL_MISSION_LIST_5 = 47834;
	// Rewards
	private static final int DIGNITY_OF_THE_EXALTED_LV5 = 47853;
	private static final int VITALITY_OF_THE_EXALTED_LV2 = 47855;
	// Misc
	private static final int MIN_LEVEL = 105;
	private static final int MIN_COMPLETE_LEVEL = 107;
	private static final int REACH_LV_107 = NpcStringId.REACH_LV_107.getId();
	private static final int PROOF_OF_PRIDE_NEEDED = 80000;
	// Monsters
	private static final int[] MONSTERS =
	{
		// Blazing Swamp
		23487, // Magma Ailith
		23488, // Magma Apophis
		23491, // Lava Wendigo
		23494, // Magma Salamander
		23495, // Magma Dre Vanul
		23496, // Magma Ifrit
		23500, // Flame Crow
		23501, // Flame Rael
		23502, // Flame Salamander
		23503, // Flame Drake
		23504, // Flame Votis
		// War-Torn Plains
		24585, // Vanor Silenos Mercenary
		24586, // Vanor Silenos Guardian
		// Abandoned Coal Mines
		24577, // Black Hammer Artisan
		24578, // Black Hammer Collector
		24579, // Black Hammer Protector
		// Beleth's Magic Circle
		23361, // Mutated Fly
		23360, // Bizuard
		23357, // Disorder Warrior
		23356, // Klein Soldier
		23355, // Armor Beast
		23354, // Decay Hannibal
		// Desert Quarry
		23811, // Cantera Tanya
		23812, // Cantera Deathmoz
		23813, // Cantera Floxis
		23814, // Cantera Belika
		23815, // Cantera Bridget
		// Phantasmal Ridge
		24511, // Lunatikan
		24515, // Kandiloth
		24512, // Garion Neti
		24513, // Desert Wendigo
		24514, // Koraza
		// Enchanted Valley
		23581, // Apherus
		23568, // Nymph Lily
		23569, // Nymph Lily
		23570, // Nymph Tulip
		23571, // Nymph Tulip
		23572, // Nymph Cosmos
		23573, // Nymph Cosmos
		19600, // Flower Bud
		23566, // Nymph Rose
		23567, // Nymph Rose
		23578, // Nymph Guardian
		// Ivory Tower Crater
		24422, // Stone Golem
		24425, // Steel Golem
		24421, // Stone Gargoyle
		24424, // Gargoyle Hunter
		24426, // Stone Cube
		24423, // Monster Eye
		// Silent Valley
		24506, // Silence Witch
		24508, // Silence Warrior
		24510, // Silence Hannibal
		24509, // Silence Slave
		24507, // Silence Preacle
		// Alligator Island
		24377, // Swamp Tribe
		24378, // Swamp Alligator
		24379, // Swamp Warrior
		24373, // Dailaon Lad
		24376, // Nos Lad
		// Tanor Canyon
		20941, // Tanor Silenos Chieftain
		20939, // Tanor Silenos Warrior
		20937, // Tanor Silenos Soldier
		20942, // Nightmare Guide
		20938, // Tanor Silenos Scout
		20943, // Nightmare Watchman
		24587, // Tanor Silenos
		// Neutral Zone
		24641, // Tel Mahum Wizard
		24642, // Tel Mahum Legionary
		24643, // Tel Mahum Footman
		24644, // Tel Mahum Lieutenant
		// Forest of Mirrors
		24466, // Demonic Mirror
		24465, // Forest Evil Spirit
		24461, // Forest Ghost
		24464, // Bewildered Dwarf Adventurer
		24463, // Bewildered Patrol
		24462, // Bewildered Expedition Member
		// Field of Silence
		24523, // Krotany
		24520, // Krotania
		24521, // Krophy
		24522, // Spiz Krophy
		// Isle of Prayer
		24451, // Lizardman Defender
		24449, // Lizardman Warrior
		24448, // Lizardman Archer
		24450, // Lizardmen Wizard
		24447, // Niasis
		24445, // Lizardman Rogue
		24446, // Island Guard
		// Field of Whispers
		24304, // Groz Kropiora
		24305, // Groz Krotania
		24306, // Groz Krophy
		24307, // Groz Krotany
		// Ketra Orc Outpost
		24631, // Ketra Orc Shaman
		24632, // Ketra Orc Prophet
		24633, // Ketra Orc Warrior
		24634, // Ketra Orc Lieutenant
		24635, // Ketra Orc Battalion Commander
		// Breka's Stronghold
		24420, // Breka Orc Prefect
		24416, // Breka Orc Scout Captain
		24419, // Breka Orc Slaughterer
		24415, // Breka Orc Scout
		24417, // Breka Orc Archer
		24418, // Breka Orc Shaman
		// Plains of the Lizardmen
		24496, // Tanta Lizardman Warrior
		24498, // Tanta Lizardman Wizard
		24499, // Priest Ugoros
		24497, // Tanta Lizardman Archer
		// Varka Silenos Barracks
		24636, // Varka Silenos Magus
		24637, // Varka Silenos Shaman
		24638, // Varka Silenos Footman
		24639, // Varka Silenos Sergeant
		24640, // Varka Silenos Officer
		// Sel Mahum Training Grounds
		24492, // Sel Mahum Soldier
		24494, // Sel Mahum Warrior
		24493, // Sel Mahum Squad Leader
		24495, // Keltron
		// Fields of Massacre
		24486, // Dismal Pole
		24487, // Graveyard Predator
		24489, // Doom Scout
		24491, // Doom Knight
		24490, // Doom Soldier
		24488, // Doom Archer
		// Sea Of Spores
		24226, // Aranea
		24227, // Keros
		24228, // Falena
		24229, // Atrofa
		24230, // Nuba
		24231, // Torfedo
		24234, // Lesatanas
		24235, // Arbor
		24236, // Tergus
		24237, // Skeletus
		24238, // Atrofine
		// Wall of Argos
		24606, // Captive Antelope
		24607, // Captive Bandersnatch
		24608, // Captive Buffalo
		24609, // Captive Grendel
		24610, // Eye of Watchman
		24611, // Elder Homunculus
		// Wasteland
		24501, // Centaur Fighter
		24504, // Centaur Warlord
		24505, // Earth Elemental Lord
		24503, // Centaur Wizard
		24500, // Sand Golem
		24502, // Centaur Marksman
		// Beast Farm
		24651, // Red Kookaburra
		24652, // Blue Kookaburra
		24653, // White Cougar
		24654, // Cougar
		24655, // Black Buffalo
		24656, // White Buffalo
		24657, // Grandel
		24658, // Black Grandel
		// Cemetery
		19455, // Aden Raider
		19456, // Te Ochdumann
		19457, // Travis
		20668, // Grave Guard
		23290, // Royal Knight
		23291, // Personal Magician
		23292, // Royal Guard
		23293, // Royal Guard Captain
		23294, // Chief Magician
		23295, // Operations Manager
		23296, // Chief Quartermaster
		23297, // Escort
		23298, // Royal Quartermaster
		23299, // Operations Chief of the 7th Division
		23300, // Commander of Operations
		// Valley of Saints
		24876, // Guide of Splendor
		24877, // Herald of Splendor
		24878, // Believer of Splendor
		24879, // Observer of Splendor
		24880, // Wiseman of Splendor
		// Hot Springs
		24881, // Springs Dwarf Hero
		24882, // Springs Scavenger
		24883, // Springs Dwarf Defender
		24884, // Springs Dwarf Berserker
		24885, // Springs Dwarf Priest
		24886, // Springs Yeti
		// Cruma Marshlands
		24930, // Black Demon Knight
		24931, // Black Demon Warrior
		24932, // Black Demon Scout
		24933, // Black Demon Wizard
		// Frozen Labyrinth
		24934, // Frozen Soldier
		24935, // Frozen Defender
		24936, // Ice Knight
		24937, // Glacier Golem
		24938, // Ice Fairy
		// Sel Mahum Base
		24961, // Sel Mahum Footman
		24962, // Sel Mahum Elite Soldier
		24963, // Sel Mahum Shaman
		24964, // Sel Mahum Wizard
		// Fafurion Temple
		24329, // Starving Water Dragon
		24318, // Temple Guard Captain
		24325, // Temple Wizard
		24324, // Temple Guardian Warrior
		24326, // Temple Guardian Wizard
		24323, // Temple Guard
		24321, // Temple Patrol Guard
		24322, // Temple Knight Recruit
		// Dragon Valley West
		24664, // Graveyard Death Lich
		24665, // Graveyard Death Berserker
		24666, // Graveyard Death Soldier
		24667, // Graveyard Death Knight
		// Dragon Valley East
		24669, // Dragon Officer
		24670, // Dragon Beast
		24671, // Dragon Centurion
		24672, // Elite Dragon Guard
		// Shadow Of The Mother Tree
		24965, // Creeper Rampike
		24966, // Fila Aprias
		24967, // Flush Teasle
		24968, // Treant Blossom
		24969, // Arsos Butterfly
		// Execution Grounds
		24673, // Zombie Orc
		24674, // Zombie Dark Elf
		24675, // Zombie Dwarf
		24676, // Schnabel Stalker
		24677, // Henker Hacker
		24678, // Schnabel Doctor
		24679, // Henker Anatomist
	};
	
	public Q10879_ExaltedGuideToPower()
	{
		super(10879);
		addStartNpc(LIONEL);
		addTalkId(LIONEL);
		addKillId(MONSTERS);
		addCondMinLevel(MIN_LEVEL, "33907-00.html");
		addCondCompletedQuest(Q10873_ExaltedReachingAnotherLevel.class.getSimpleName(), "33907-00.html");
		registerQuestItems(LIONEL_MISSION_LIST_5, PROOF_OF_PRIDE);
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		String htmltext = null;
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return htmltext;
		}
		switch (event)
		{
			case "33907-03.htm":
			case "33907-04.htm":
			{
				htmltext = event;
				break;
			}
			case "33907-05.html":
			{
				if (qs.isCreated())
				{
					giveItems(player, LIONEL_MISSION_LIST_5, 1);
					qs.startQuest();
					qs.setMemoState(1);
					htmltext = event;
				}
				break;
			}
			case "33907-05a.html":
			{
				qs.setMemoState(2);
				htmltext = event;
				break;
			}
			case "33907-08.html":
			{
				if (qs.isCond(2) && (player.getLevel() >= MIN_COMPLETE_LEVEL))
				{
					giveItems(player, DIGNITY_OF_THE_EXALTED_LV5, 1);
					giveItems(player, VITALITY_OF_THE_EXALTED_LV2, 1);
					qs.exitQuest(false, true);
					htmltext = event;
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(Npc npc, Player player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState qs = getQuestState(player, true);
		switch (qs.getState())
		{
			case State.CREATED:
			{
				htmltext = "33907-01.htm";
				break;
			}
			case State.STARTED:
			{
				switch (qs.getMemoState())
				{
					case 1:
					{
						if (qs.isCond(2) && (player.getLevel() >= MIN_COMPLETE_LEVEL))
						{
							htmltext = "33907-07.html";
						}
						else
						{
							htmltext = "33907-06.html";
						}
						break;
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				htmltext = getAlreadyCompletedMsg(player);
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(Npc npc, Player player, boolean isSummon)
	{
		executeForEachPlayer(player, npc, isSummon, true, false);
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public void actionForEachPlayer(Player player, Npc npc, boolean isSummon)
	{
		final QuestState qs = getQuestState(player, false);
		if ((qs != null) && qs.isCond(1) && player.isInsideRadius3D(npc, Config.ALT_PARTY_RANGE))
		{
			if (getQuestItemsCount(player, PROOF_OF_PRIDE) < PROOF_OF_PRIDE_NEEDED)
			{
				giveItemRandomly(player, PROOF_OF_PRIDE, 1, PROOF_OF_PRIDE_NEEDED, 1, true);
			}
			if ((getQuestItemsCount(player, PROOF_OF_PRIDE) >= PROOF_OF_PRIDE_NEEDED) && (player.getLevel() >= MIN_COMPLETE_LEVEL))
			{
				qs.setCond(2, true);
			}
			sendNpcLogList(player);
		}
	}
	
	@Override
	public Set<NpcLogListHolder> getNpcLogList(Player player)
	{
		final QuestState qs = getQuestState(player, false);
		if ((qs != null) && qs.isCond(1))
		{
			final Set<NpcLogListHolder> holder = new HashSet<>();
			if (player.getLevel() >= MIN_COMPLETE_LEVEL)
			{
				holder.add(new NpcLogListHolder(REACH_LV_107, true, 1));
			}
			return holder;
		}
		return super.getNpcLogList(player);
	}
}
