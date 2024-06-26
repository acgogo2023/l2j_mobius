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
package handlers;

import java.util.logging.Logger;

import org.l2jmobius.gameserver.handler.DailyMissionHandler;

import handlers.dailymissionhandlers.AuctionDailyMissionHandler;
import handlers.dailymissionhandlers.AugmentationDailyMissionHandler;
import handlers.dailymissionhandlers.BossDailyMissionHandler;
import handlers.dailymissionhandlers.CeremonyOfChaosDailyMissionHandler;
import handlers.dailymissionhandlers.CombinationDailyMissionHandler;
import handlers.dailymissionhandlers.CompoundDailyMissionHandler;
import handlers.dailymissionhandlers.EnchantDailyMissionHandler;
import handlers.dailymissionhandlers.EnsoulDailyMissionHandler;
import handlers.dailymissionhandlers.ExaltedDailyMissionHandler;
import handlers.dailymissionhandlers.FishingDailyMissionHandler;
import handlers.dailymissionhandlers.JoinClanDailyMissionHandler;
import handlers.dailymissionhandlers.LevelDailyMissionHandler;
import handlers.dailymissionhandlers.LoginMonthDailyMissionHandler;
import handlers.dailymissionhandlers.LoginWeekendDailyMissionHandler;
import handlers.dailymissionhandlers.MentorDailyMissionHandler;
import handlers.dailymissionhandlers.MonsterDailyMissionHandler;
import handlers.dailymissionhandlers.NoblesseDailyMissionHandler;
import handlers.dailymissionhandlers.OlympiadDailyMissionHandler;
import handlers.dailymissionhandlers.OlympiadHeroDailyMissionHandler;
import handlers.dailymissionhandlers.QuestDailyMissionHandler;
import handlers.dailymissionhandlers.SiegeDailyMissionHandler;
import handlers.dailymissionhandlers.UseItemDailyMissionHandler;

/**
 * @author UnAfraid
 */
public class DailyMissionMasterHandler
{
	private static final Logger LOGGER = Logger.getLogger(DailyMissionMasterHandler.class.getName());
	
	public static void main(String[] args)
	{
		DailyMissionHandler.getInstance().registerHandler("auction", AuctionDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("augment", AugmentationDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("boss", BossDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("ceremonyofchaos", CeremonyOfChaosDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("combine", CombinationDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("compound", CompoundDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("enchant", EnchantDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("ensoul", EnsoulDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("exalted", ExaltedDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("fishing", FishingDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("joinclan", JoinClanDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("level", LevelDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("loginmonth", LoginMonthDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("loginweekend", LoginWeekendDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("mentor", MentorDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("monster", MonsterDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("noblesse", NoblesseDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("olympiad", OlympiadDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("olympiadhero", OlympiadHeroDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("quest", QuestDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("siege", SiegeDailyMissionHandler::new);
		DailyMissionHandler.getInstance().registerHandler("useitem", UseItemDailyMissionHandler::new);
		LOGGER.info(DailyMissionMasterHandler.class.getSimpleName() + ": Loaded " + DailyMissionHandler.getInstance().size() + " handlers.");
	}
}
