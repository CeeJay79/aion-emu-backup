/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.skillengine;

import java.util.List;

import com.aionemu.gameserver.model.gameobjects.Creature;

/**
 * @author xavier
 *
 */
public class HealSimple extends SkillHandler
{

	/**
	 * @param skillId
	 */
	public HealSimple(int skillId)
	{
		super(skillId);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.aionemu.gameserver.skillengine.SkillHandler#useSkill(com.aionemu.gameserver.model.gameobjects.Creature, java.util.List)
	 */
	@Override
	public void useSkill(Creature creature, List<Creature> targets)
	{
		// TODO Auto-generated method stub

	}

}