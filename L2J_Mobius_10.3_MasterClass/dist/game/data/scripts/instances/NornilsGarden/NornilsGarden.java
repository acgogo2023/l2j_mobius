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
package instances.NornilsGarden;

import org.l2jmobius.commons.util.CommonUtil;
import org.l2jmobius.gameserver.ai.CtrlIntention;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.Attackable;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.holders.SkillHolder;
import org.l2jmobius.gameserver.model.instancezone.Instance;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

import instances.AbstractInstance;

/**
 * Nornils Garden Instance
 * @URL https://l2wiki.com/Nornils_Garden
 * @Video https://www.youtube.com/watch?v=6QKzzmJ5GUs
 * @author Gigi, Mobius
 * @date 2017-02-22 - [15:22:27]
 */
public class NornilsGarden extends AbstractInstance
{
	// NPCs
	private static final int BOZ_CORE = 33781;
	private static final int SPICULA_ZERO = 25901;
	// Monsters
	private static final int BOZ_STAGE1 = 19298;
	private static final int BOZ_STAGE2 = 19305;
	private static final int BOZ_STAGE3 = 19403;
	private static final int BOZ_STAGE4 = BOZ_STAGE2;
	private static final int SPICULA_ELITE_CAPTAIN = 19299;
	private static final int SPICULA_ELITE_LIEUTNANT = 19300;
	private static final int ELITE_SOLDIER_CLONE_1 = 19301;
	private static final int SPICULA_ELITE_GUARD_1 = 19302;
	private static final int ELITE_SOLDIER_CLONE_2 = 19303;
	private static final int SPICULA_ELITE_GUARD_2 = 19304;
	private static final int[] ATTACABLE_MONSTERS =
	{
		SPICULA_ELITE_CAPTAIN,
		SPICULA_ELITE_CAPTAIN,
		SPICULA_ELITE_LIEUTNANT,
		ELITE_SOLDIER_CLONE_1,
		SPICULA_ELITE_GUARD_1,
		ELITE_SOLDIER_CLONE_2,
		SPICULA_ELITE_GUARD_2
	};
	// Skills
	private static final SkillHolder DARK_SPHERES = new SkillHolder(15234, 1);
	private static final SkillHolder DARK_WIND = new SkillHolder(15235, 1);
	private static final SkillHolder DARK_THRUST = new SkillHolder(15236, 1);
	private static final SkillHolder DARK_BUSTER = new SkillHolder(15237, 1);
	private static final SkillHolder DARK_BREATH = new SkillHolder(15238, 1);
	// Chance
	private static final int CHANCE_DARK_SPHERES = 15;
	private static final int CHANCE_DARK_WIND = 30;
	private static final int CHANCE_DARK_THRUST = 15;
	private static final int CHANCE_DARK_BUSTER = 15;
	private static final int CHANCE_DARK_BREATH = 30;
	// Misc
	private static final int TEMPLATE_ID = 231;
	
	public NornilsGarden()
	{
		super(TEMPLATE_ID);
		addStartNpc(BOZ_CORE);
		addTalkId(BOZ_CORE);
		addFirstTalkId(BOZ_CORE);
		addAttackId(SPICULA_ZERO);
		addSpawnId(ATTACABLE_MONSTERS);
		addKillId(ATTACABLE_MONSTERS);
		addKillId(SPICULA_ZERO);
		addKillId(BOZ_STAGE1, BOZ_STAGE2, BOZ_STAGE3, BOZ_STAGE4);
		addCreatureSeeId(BOZ_STAGE1);
	}
	
	@Override
	public String onEvent(String event, Npc npc, Player player)
	{
		final Instance world = npc.getInstanceWorld();
		if (isInInstance(world))
		{
			switch (event)
			{
				case "stage1":
				{
					world.spawnGroup("wave_1");
					startQuestTimer("stage1_1", 20000, npc, null, true);
					break;
				}
				case "stage1_1":
				{
					if (!npc.isDead() && (world.getAliveNpcCount(ATTACABLE_MONSTERS) == 0))
					{
						world.spawnGroup("wave_1");
						npc.setTargetable(true);
					}
					break;
				}
				case "stage2":
				{
					if (world.isStatus(5) && (world.getAliveNpcCount(ATTACABLE_MONSTERS) == 0))
					{
						world.openCloseDoor(16200016, true);
						cancelQuestTimer("stage2", npc, null);
						world.spawnGroup("wave_3");
					}
					break;
				}
				case "stage3":
				{
					if (world.isStatus(6) && (world.getAliveNpcCount(ATTACABLE_MONSTERS) == 0))
					{
						world.openCloseDoor(16200201, true);
						cancelQuestTimer("stage3", npc, null);
					}
					break;
				}
				case "check_agrro":
				{
					if ((world != null) && !npc.isDead() && !npc.isInCombat() && !world.getDoor(16200201).isOpen())
					{
						World.getInstance().forEachVisibleObjectInRange(npc, Player.class, 1500, knownChar ->
						{
							if (CommonUtil.contains(ATTACABLE_MONSTERS, npc.getId()) && !npc.isInCombat())
							{
								npc.setRunning();
								((Attackable) npc).addDamageHate(knownChar, 0, 99999);
								npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, knownChar);
							}
						});
						startQuestTimer("check_agrro", 1000, npc, null);
					}
					break;
				}
			}
		}
		else if (event.equals("enterInstance"))
		{
			enterInstance(player, npc, TEMPLATE_ID);
		}
		return super.onEvent(event, npc, player);
	}
	
	@Override
	public String onAttack(Npc npc, Player attacker, int damage, boolean isSummon)
	{
		final Instance world = npc.getInstanceWorld();
		if (isInInstance(world))
		{
			world.openCloseDoor(16200201, false);
		}
		if ((attacker != null) && !attacker.isDead() && !npc.isCastingNow())
		{
			if ((getRandom(100) < CHANCE_DARK_SPHERES) && (npc.getCurrentHpPercent() <= 95))
			{
				npc.setTarget(attacker);
				npc.doCast(DARK_SPHERES.getSkill());
			}
			else if ((getRandom(100) < CHANCE_DARK_WIND) && (npc.getCurrentHpPercent() <= 75))
			{
				npc.setTarget(attacker);
				npc.doCast(DARK_WIND.getSkill());
			}
			else if ((getRandom(100) < CHANCE_DARK_THRUST) && (npc.getCurrentHpPercent() <= 50))
			{
				npc.setTarget(attacker);
				npc.doCast(DARK_THRUST.getSkill());
			}
			else if ((getRandom(100) < CHANCE_DARK_BUSTER) && (npc.getCurrentHpPercent() <= 25))
			{
				npc.setTarget(attacker);
				npc.doCast(DARK_BUSTER.getSkill());
			}
			else if ((getRandom(100) < CHANCE_DARK_BREATH) && (npc.getCurrentHpPercent() <= 10))
			{
				npc.setTarget(attacker);
				npc.doCast(DARK_BREATH.getSkill());
			}
		}
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public String onKill(Npc npc, Player killer, boolean isSummon)
	{
		final Instance world = npc.getInstanceWorld();
		switch (world.getStatus())
		{
			case 0:
			{
				if (npc.getId() == BOZ_STAGE1)
				{
					cancelQuestTimer("stage1_1", npc, null);
					world.openCloseDoor(16200015, true);
					world.setStatus(1);
				}
				break;
			}
			case 1:
			{
				if ((npc.getId() == BOZ_STAGE2) && (world.getAliveNpcCount(BOZ_STAGE2) < 6))
				{
					world.spawnGroup("wave_2_1");
					world.setStatus(2);
				}
				break;
			}
			case 2:
			{
				if ((npc.getId() == BOZ_STAGE2) && (world.getAliveNpcCount(BOZ_STAGE2) < 5))
				{
					world.spawnGroup("wave_2_2");
					world.setStatus(3);
				}
				break;
			}
			case 3:
			{
				if ((npc.getId() == BOZ_STAGE2) && (world.getAliveNpcCount(BOZ_STAGE2) < 4))
				{
					world.spawnGroup("wave_2_3");
					world.setStatus(4);
				}
				break;
			}
			case 4:
			{
				if ((npc.getId() == BOZ_STAGE2) && (world.getAliveNpcCount(BOZ_STAGE2) < 3))
				{
					world.spawnGroup("wave_2_4");
					startQuestTimer("stage2", 15000, npc, null, true);
					world.setStatus(5);
				}
				break;
			}
			case 5:
			{
				if (npc.getId() == BOZ_STAGE3)
				{
					startQuestTimer("stage3", 10000, npc, null, true);
					world.setStatus(6);
				}
				break;
			}
			case 6:
			{
				if (npc.getId() == SPICULA_ZERO)
				{
					world.getAliveNpcs(BOZ_STAGE4).forEach(boz -> boz.doDie(null));
					world.spawnGroup("wave_4");
					world.finishInstance();
					world.broadcastPacket(new SystemMessage(SystemMessageId.THE_INSTANCE_ZONE_EXPIRES_IN_S1_MIN_AFTER_THAT_YOU_WILL_BE_TELEPORTED_OUTSIDE).addInt((int) 5.0D));
				}
				break;
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onFirstTalk(Npc npc, Player player)
	{
		return "33781.html";
	}
	
	@Override
	public String onCreatureSee(Npc npc, Creature creature)
	{
		if (creature.isPlayable())
		{
			if (npc.isScriptValue(0))
			{
				startQuestTimer("stage1", 3000, npc, null);
				npc.setTargetable(false);
				npc.setScriptValue(1);
			}
		}
		return super.onCreatureSee(npc, creature);
	}
	
	@Override
	public String onSpawn(Npc npc)
	{
		final Instance world = npc.getInstanceWorld();
		if (isInInstance(world) && (CommonUtil.contains(ATTACABLE_MONSTERS, npc.getId())))
		{
			((Attackable) npc).setCanReturnToSpawnPoint(false);
			startQuestTimer("check_agrro", 1000, npc, null);
		}
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new NornilsGarden();
	}
}
