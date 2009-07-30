/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 * aion-emu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-emu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.aionemu.gameserver.ai;

import com.aionemu.gameserver.ai.desires.Desire;
import com.aionemu.gameserver.ai.desires.DesireQueue;
import com.aionemu.gameserver.ai.events.AIEvent;
import com.aionemu.gameserver.model.gameobjects.Creature;

public abstract class AI<T extends Creature>
{

	protected DesireQueue	desireQueue	= new DesireQueue();

	protected final T		creature;

	protected AI(T creature)
	{
		this.creature = creature;
	}

	protected void handleDesire(Desire desire)
	{
		desire.handleDesire(this);
	}

	public void handleEvent(AIEvent event)
	{
		event.handleEvent(this);
	}
}
