/**
 * This file is part of aion-emu.
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
package com.aionemu.commons.network.nio;

import com.aionemu.commons.network.IAcceptor;

/**
 * @author -Nemesiss-
 */
public class ServerCfg
{
	public final String		hostName;
	public final int		port;
	public final IAcceptor	acceptor;

	public ServerCfg(String hostName, int port, IAcceptor acceptor)
	{
		this.hostName = hostName;
		this.port = port;
		this.acceptor = acceptor;
	}

}