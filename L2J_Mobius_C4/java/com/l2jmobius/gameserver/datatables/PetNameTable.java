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
package com.l2jmobius.gameserver.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.l2jmobius.Config;
import com.l2jmobius.L2DatabaseFactory;
import com.l2jmobius.gameserver.model.L2PetDataTable;

public class PetNameTable
{
	private static Logger _log = Logger.getLogger(PetNameTable.class.getName());
	
	private static PetNameTable _instance;
	
	public static PetNameTable getInstance()
	{
		if (_instance == null)
		{
			_instance = new PetNameTable();
		}
		return _instance;
	}
	
	public boolean doesPetNameExist(String name, int petNpcId)
	{
		boolean result = true;
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT name FROM pets p, items i WHERE p.item_obj_id = i.object_id AND name=? AND i.item_id IN (?)"))
		{
			statement.setString(1, name);
			
			String cond = "";
			for (final int it : L2PetDataTable.getPetItemsAsNpc(petNpcId))
			{
				if (!cond.isEmpty())
				{
					cond += ", ";
				}
				cond += it;
			}
			
			statement.setString(2, cond);
			try (ResultSet rset = statement.executeQuery())
			{
				result = rset.next();
			}
		}
		catch (final SQLException e)
		{
			_log.warning("could not check existing petname:" + e.getMessage());
		}
		return result;
	}
	
	public boolean isValidPetName(String name)
	{
		boolean result = true;
		
		if (!isAlphaNumeric(name))
		{
			return result;
		}
		
		Pattern pattern;
		try
		{
			pattern = Pattern.compile(Config.PET_NAME_TEMPLATE);
		}
		catch (final PatternSyntaxException e) // case of illegal pattern
		{
			_log.warning("ERROR : Pet name pattern of config is wrong!");
			pattern = Pattern.compile(".*");
		}
		final Matcher regexp = pattern.matcher(name);
		if (!regexp.matches())
		{
			result = false;
		}
		return result;
	}
	
	private boolean isAlphaNumeric(String text)
	{
		boolean result = true;
		final char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (!Character.isLetterOrDigit(chars[i]))
			{
				result = false;
				break;
			}
		}
		return result;
	}
}