/*
 * This file is part of aion-unique <aion-unique.smfnew.com>.
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
package com.aionemu.gameserver.model.gameobjects.stats;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.templates.stats.NpcStatsTemplate;


/**
 * @author ATracer
 *
 */
public class MonsterGameStats extends NpcGameStats
{
	public MonsterGameStats (Npc npc, NpcStatsTemplate nst) {
		super(npc,nst);
	}
	
	private MonsterGameStats (Creature owner, int power, int health, int agility, int accuracy, int knowledge, int will, int mainHandAttack, int mainHandCritRate, int offHandAttack, int offHandCritRate) {
		super(null,power,health,agility,accuracy,knowledge,will,mainHandAttack,mainHandCritRate,offHandAttack,offHandCritRate);
	}
}
