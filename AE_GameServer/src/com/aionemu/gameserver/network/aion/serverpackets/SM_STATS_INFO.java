/**
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
package com.aionemu.gameserver.network.aion.serverpackets;

import java.nio.ByteBuffer;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.utils.gametime.GameTimeManager;

/**
 * In this packet Server is sending User Info?
 * 
 * @author -Nemesiss-
 * @author Luno
 */
public class SM_STATS_INFO extends AionServerPacket
{

	/**
	 * Player that stats info will be send
	 */
	private Player	player;
	
	/**
	 * Constructs new <tt>SM_UI</tt> packet
	 * 
	 * @param player
	 */
	public SM_STATS_INFO(Player player)
	{
		this.player = player;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeImpl(AionConnection con, ByteBuffer buf)
	{
		PlayerCommonData pcd = player.getCommonData();
		PlayerLifeStats pls = player.getLifeStats();
		PlayerGameStats pgs = player.getGameStats();

		writeD(buf, player.getObjectId());
		writeD(buf, GameTimeManager.getGameTime().getTime()); // Minutes since 1/1/00 00:00:00

		writeH(buf, pgs.getPower());// [current power]
		writeH(buf, pgs.getHealth());// [current health]
		writeH(buf, pgs.getAccuracy());// [current accuracy]
		writeH(buf, pgs.getAgility());// [current agility]
		writeH(buf, pgs.getKnowledge());// [current knowledge]
		writeH(buf, pgs.getWill());// [current will]

		writeH(buf, pgs.getWater());// [current water]
		writeH(buf, pgs.getWind());// [current wind]
		writeH(buf, pgs.getEarth());// [current earth]
		writeH(buf, pgs.getFire());// [current fire]

		writeD(buf, 0);// [unk]
		writeH(buf, pcd.getLevel());// [level]
		writeH(buf, 0); // [unk]
		writeD(buf, pls.getHp());// [current hp]

		writeQ(buf, pcd.getExpNeed());// [xp till next lv]
		writeQ(buf, 0); // [recoverable exp]
		writeQ(buf, pcd.getExpShown()); // [current xp]

		writeD(buf, 0); // [unk]
		writeD(buf, pls.getMaxHp()); // [max hp]
		writeD(buf, 0);// [unk]

		writeD(buf, pls.getMaxMp());// [max mana]
		writeD(buf, pls.getMp());// [current mana]

		writeH(buf, pls.getMaxDp());// [max dp]
		writeH(buf, pls.getDp());// [current dp]

		writeD(buf, 0);// [unk]

		writeD(buf, pgs.getFlyTime());// [current fly time]

		writeH(buf, 0);// [unk]

		writeH(buf, pgs.getMainHandAttack()); // [current main hand attack]
		writeH(buf, pgs.getOtherHandAttack()); // [off hand attack]

		writeH(buf, pgs.getPhysicalDefense());// [current pdef]

		writeH(buf, 0);// [unk]

		writeH(buf, pgs.getMagicResistance()); // [current mres]

		writeH(buf, 0);// [unk]
		writeH(buf, 0);// [unk]attack range - do calculation
		writeH(buf, 1500);// attack speed - do calculation may be
		writeH(buf, pgs.getEvasion());// [current evasion]
		writeH(buf, pgs.getParry() );// [current parry]
		writeH(buf, pgs.getBlock());// [current block]

		writeH(buf, pgs.getMainHandCritRate());// [current main hand crit rate]
		writeH(buf, pgs.getOtherHandCritRate());// [current off hand crit rate]

		writeH(buf, pgs.getMainHandAccuracy());// [current main_hand_accuracy]
		writeH(buf, pgs.getOtherHandAccuracy());// [current off_hand_accuracy]

		writeH(buf, 0);// [unk]
		writeH(buf, pgs.getMagicAccuracy());// [current magic accuracy]
		writeH(buf, 0); // [unk]
		writeH(buf, pgs.getMagicBoost()); // [current magic boost]

		writeH(buf, 0);// [unk]
		writeH(buf, 0);// [unk]

		writeD(buf, 2);// [unk]
		writeD(buf, 0);// [unk]
		writeD(buf, 0);// [unk]
		writeD(buf, pcd.getPlayerClass().getClassId());// [Player Class id]
		writeD(buf, 0);// [unk]

		writeH(buf, 0);// [unk]
		writeH(buf, 0);// [unk]
		writeH(buf, 0);// [unk]
		writeH(buf, 0);// [unk]
		writeH(buf, 0);// [unk]
		writeH(buf, 0);// [unk]

		writeH(buf, 0);// [unk]
		writeH(buf, 0);// [unk]

		writeH(buf, pgs.getPower());// [base power]
		writeH(buf, pgs.getHealth());// [base health]

		writeH(buf, pgs.getAccuracy());// [base accuracy]
		writeH(buf, pgs.getAgility());// [base agility]

		writeH(buf, pgs.getKnowledge());// [base knowledge]
		writeH(buf, pgs.getWill());// [base water res]

		writeH(buf, pgs.getWater());// [base water res]
		writeH(buf, pgs.getWind());// [base water res]
		
		writeH(buf, pgs.getEarth());// [base earth resist]
		writeH(buf, pgs.getFire());// [base water res]

		writeD(buf, 0);// [unk]

		writeD(buf, pls.getMaxHp());// [base hp]

		writeD(buf, pls.getMaxMp());// [base mana]

		writeD(buf, 0);// [unk]
		writeD(buf, 60);// [unk]

		writeH(buf, pgs.getMainHandAttack());// [base main hand attack]
		writeH(buf, pgs.getOtherHandAttack());// [base off hand attack]

		writeH(buf, 0); // [unk] 
		writeH(buf, pgs.getPhysicalDefense()); // [base pdef]

		writeH(buf, pgs.getMagicResistance()); // [base magic res]

		writeH(buf, 0); // [unk]

		writeD(buf, 1086324736);// [unk]

		writeH(buf, pgs.getEvasion()); // [base evasion]

		writeH(buf, pgs.getParry()); // [base parry]
 
		writeH(buf, pgs.getBlock()); // [base block]

		writeH(buf, pgs.getMainHandCritRate()); // [base main hand crit rate]
		writeH(buf, pgs.getMainHandCritRate()); // [base off hand crit rate]

		writeH(buf, pgs.getMainHandAccuracy()); // [base main hand accuracy]
		writeH(buf, pgs.getMainHandAccuracy()); // [base off hand accuracy]

		writeH(buf, 0); // [unk]

		writeH(buf, pgs.getMagicAccuracy());// [base magic accuracy]

		writeH(buf, 0); // [unk]
		writeH(buf, pgs.getMagicBoost());// [base magic boost]

		writeH(buf, 0); // [unk]

	}
}
