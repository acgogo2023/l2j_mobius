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
package com.l2jmobius.gameserver.model.actor.instance;

import com.l2jmobius.Config;
import com.l2jmobius.gameserver.datatables.SkillTable;
import com.l2jmobius.gameserver.datatables.SkillTreeTable;
import com.l2jmobius.gameserver.model.L2EnchantSkillLearn;
import com.l2jmobius.gameserver.model.L2Skill;
import com.l2jmobius.gameserver.model.L2SkillLearn;
import com.l2jmobius.gameserver.model.base.ClassId;
import com.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import com.l2jmobius.gameserver.network.serverpackets.AquireSkillList;
import com.l2jmobius.gameserver.network.serverpackets.ExEnchantSkillList;
import com.l2jmobius.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jmobius.gameserver.network.serverpackets.SystemMessage;
import com.l2jmobius.gameserver.templates.L2NpcTemplate;

import javolution.text.TextBuilder;

public class L2FolkInstance extends L2NpcInstance
{
	private final ClassId[] _classesToTeach;
	
	public L2FolkInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
		_classesToTeach = template.getTeachInfo();
	}
	
	@Override
	public void onAction(L2PcInstance player)
	{
		player.setLastFolkNPC(this);
		super.onAction(player);
	}
	
	/**
	 * this displays SkillList to the player.
	 * @param player
	 * @param classId
	 */
	public void showSkillList(L2PcInstance player, ClassId classId)
	{
		if (Config.DEBUG)
		{
			_log.fine("SkillList activated on: " + getObjectId());
		}
		
		final int npcId = getTemplate().npcId;
		
		if (_classesToTeach == null)
		{
			final NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			final TextBuilder sb = new TextBuilder();
			sb.append("<html><body>");
			sb.append("I cannot teach you. My class list is empty.<br> Ask admin to fix it. Need add my npcid and classes to skill_learn.sql.<br>NpcId:" + npcId + ", Your classId:" + player.getClassId().getId() + "<br>");
			sb.append("</body></html>");
			html.setHtml(sb.toString());
			player.sendPacket(html);
			
			return;
		}
		
		if (!getTemplate().canTeach(classId))
		{
			final NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			final TextBuilder sb = new TextBuilder();
			sb.append("<html><body>");
			sb.append("I cannot teach you any skills.<br> You must find your current class teachers.");
			sb.append("</body></html>");
			html.setHtml(sb.toString());
			player.sendPacket(html);
			return;
		}
		
		final L2SkillLearn[] skills = SkillTreeTable.getInstance().getAvailableSkills(player, classId);
		final AquireSkillList asl = new AquireSkillList(false);
		int counts = 0;
		
		for (final L2SkillLearn s : skills)
		{
			final L2Skill sk = SkillTable.getInstance().getInfo(s.getId(), s.getLevel());
			
			if ((sk == null) || !sk.getCanLearn(player.getClassId()) || !sk.canTeachBy(npcId))
			{
				continue;
			}
			
			final int cost = SkillTreeTable.getInstance().getSkillCost(player, sk);
			counts++;
			
			asl.addSkill(s.getId(), s.getLevel(), s.getLevel(), cost, 0);
		}
		
		if (counts == 0)
		{
			final int minlevel = SkillTreeTable.getInstance().getMinLevelForNewSkill(player, classId);
			if (minlevel > 0)
			{
				final SystemMessage sm = new SystemMessage(SystemMessage.DO_NOT_HAVE_FURTHER_SKILLS_TO_LEARN);
				sm.addNumber(minlevel);
				player.sendPacket(sm);
			}
			else
			{
				player.sendPacket(new SystemMessage(750));
			}
		}
		else
		{
			player.sendPacket(asl);
		}
		
		player.sendPacket(new ActionFailed());
	}
	
	/**
	 * this displays EnchantSkillList to the player.
	 * @param player
	 * @param classId
	 */
	public void showEnchantSkillList(L2PcInstance player, ClassId classId)
	{
		if (Config.DEBUG)
		{
			_log.fine("EnchantSkillList activated on: " + getObjectId());
		}
		
		final int npcId = getTemplate().npcId;
		
		if (_classesToTeach == null)
		{
			final NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			final TextBuilder sb = new TextBuilder();
			sb.append("<html><body>");
			sb.append("I cannot teach you. My class list is empty.<br> Ask admin to fix it. Need add my npcid and classes to skill_learn.sql.<br>NpcId:" + npcId + ", Your classId:" + player.getClassId().getId() + "<br>");
			sb.append("</body></html>");
			html.setHtml(sb.toString());
			player.sendPacket(html);
			
			return;
		}
		
		if (!getTemplate().canTeach(classId))
		{
			final NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			final TextBuilder sb = new TextBuilder();
			sb.append("<html><body>");
			sb.append("I cannot teach you any skills.<br> You must find your current class teachers.");
			sb.append("</body></html>");
			html.setHtml(sb.toString());
			player.sendPacket(html);
			return;
		}
		
		if (player.getClassId().getId() < 88)
		{
			final NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			final TextBuilder sb = new TextBuilder();
			sb.append("<html><body>");
			sb.append("You must have 3rd class change quest completed.");
			sb.append("</body></html>");
			html.setHtml(sb.toString());
			player.sendPacket(html);
			
			return;
		}
		
		final L2EnchantSkillLearn[] skills = SkillTreeTable.getInstance().getAvailableEnchantSkills(player);
		final ExEnchantSkillList esl = new ExEnchantSkillList();
		int counts = 0;
		
		for (final L2EnchantSkillLearn s : skills)
		{
			final L2Skill sk = SkillTable.getInstance().getInfo(s.getId(), s.getLevel());
			if (sk == null)
			{
				continue;
			}
			counts++;
			esl.addSkill(s.getId(), s.getLevel(), s.getSpCost(), s.getExp());
		}
		
		if (counts == 0)
		{
			player.sendPacket(new SystemMessage(SystemMessage.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT));
			final NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			
			final int level = player.getLevel();
			if (level < 74)
			{
				final SystemMessage sm = new SystemMessage(607);
				sm.addNumber(level);
				player.sendPacket(sm);
			}
			else
			{
				final TextBuilder sb = new TextBuilder();
				sb.append("<html><body>");
				sb.append("You've learned all skills for your class.<br>");
				sb.append("</body></html>");
				html.setHtml(sb.toString());
				player.sendPacket(html);
			}
		}
		else
		{
			player.sendPacket(esl);
		}
		
		player.sendPacket(new ActionFailed());
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("SkillList"))
		{
			if (Config.ALT_GAME_SKILL_LEARN)
			{
				final String id = command.substring(9).trim();
				
				if (id.length() != 0)
				{
					player.setSkillLearningClassId(ClassId.values()[Integer.parseInt(id)]);
					showSkillList(player, ClassId.values()[Integer.parseInt(id)]);
				}
				else
				{
					boolean own_class = false;
					
					if (_classesToTeach != null)
					{
						for (final ClassId cid : _classesToTeach)
						{
							if (cid.equalsOrChildOf(player.getClassId()))
							{
								own_class = true;
								break;
							}
						}
					}
					
					String text = "<html>\n" + "<body>\n" + "<center>Skill learning:</center>\n" + "<br>\n";
					
					if (!own_class)
					{
						final String mages = player.getClassId().isMage() ? "fighters" : "mages";
						text += "Skills of your class are the easiest to learn.<br>\n" + "Skills of another class are harder.<br>\n" + "Skills for another race are even more harder to learn.<br>\n" + "You can also learn skills of " + mages + ", and they are" + " the hardest to learn!<br>\n" + "<br>\n";
					}
					
					// make a list of classes
					if (_classesToTeach != null)
					{
						int count = 0;
						
						ClassId classCheck = player.getClassId();
						
						while ((count == 0) && (classCheck != null))
						
						{
							
							for (final ClassId cid : _classesToTeach)
							
							{
								
								if (cid.level() != classCheck.level())
								{
									continue;
								}
								
								if (SkillTreeTable.getInstance().getAvailableSkills(player, cid).length == 0)
								{
									continue;
								}
								
								text += "<a action=\"bypass -h npc_%objectId%_SkillList " + cid.getId() + "\">Learn " + cid + "'s class Skills</a><br>\n";
								
								count++;
								
							}
							
							classCheck = classCheck.getParent();
							
						}
						
						classCheck = null;
					}
					else
					{
						text += "No Skills.<br>\n";
					}
					
					text += "</body>\n" + "</html>";
					
					insertObjectIdAndShowChatWindow(player, text);
					player.sendPacket(new ActionFailed());
				}
			}
			else
			{
				player.setSkillLearningClassId(player.getClassId());
				showSkillList(player, player.getClassId());
			}
		}
		else if (command.startsWith("EnchantSkillList"))
		{
			showEnchantSkillList(player, player.getClassId());
		}
		else
		{
			// this class dont know any other commands, let forward
			// the command to the parent class
			
			super.onBypassFeedback(player, command);
		}
	}
}