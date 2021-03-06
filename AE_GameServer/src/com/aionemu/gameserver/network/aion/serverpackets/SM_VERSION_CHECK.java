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

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.Version;
import com.aionemu.gameserver.configs.Config;

/**
 * @author -Nemesiss-
 * 
 */
public class SM_VERSION_CHECK extends AionServerPacket
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeImpl(AionConnection con, ByteBuffer buf)
	{
		if(Version.Chiness)
		{
			writeH(buf, 0x0600);// unk
			writeD(buf, 0x1618A);// unk
			writeD(buf, 0x1618A);// unk
			writeD(buf, 0x00);// unk
			writeD(buf, 0x1618A);// unk
			writeD(buf, 0x4A11DC04);// unk
			writeD(buf, 0x30000500);// unk
		}
		else
		{
			// Server Version Check for 1.5.0.6 NA/EU
			writeC(buf, 0x00);
			writeC(buf, Config.GAMESERVER_ID); // Server id
			writeD(buf, 0x0001631F);
			writeD(buf, 0x000162C3);
			writeD(buf, 0x00);
			writeD(buf, 0x000162C3);
			writeD(buf, 0x4AB3CB5C);
			writeC(buf, 0x00);
			writeC(buf, Config.SERVER_COUNTRY_CODE); // Server country code (cc)
			writeC(buf, 0x00);
			writeC(buf, Config.SERVER_MODE); // Server mode : 0x00 = one race / 0x01 = free race / 0x22 = Character Reservation
		}

		writeD(buf, (int) (System.currentTimeMillis() / 1000));// ServerTime in seconds

		if(Version.Chiness)
		{
			writeD(buf, 0x3A000001);// unk
			writeD(buf, 0x18372D7);// unk
			writeC(buf, 0x28);// unk
		} else {
			writeD(buf, 0x0001015E);
			writeD(buf, 0x9C7FCE00);
			writeC(buf, 0xB0);
			writeH(buf, 0x2801);			
		}
	}
}
