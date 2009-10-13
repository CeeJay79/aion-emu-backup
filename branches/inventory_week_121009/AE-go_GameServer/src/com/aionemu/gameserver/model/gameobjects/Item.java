/*
 * This file is part of aion-unique <aion-unique.com>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.model.gameobjects;

import org.apache.log4j.Logger;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.templates.ItemTemplate;

/**
 * @author ATracer
 */
public class Item extends AionObject
{
	private static final Logger log = Logger.getLogger(Item.class);
	
	private int itemCount = 1;
	
	private ItemTemplate itemTemplate;
	
	private boolean isEquipped = false;
	
	private int equipmentSlot = 0;
	
	/**
	 * @param objId
	 */
	public Item(int objId)
	{
		super(objId);
	}
	
	public Item(int objId, int itemId, int itemCount, int isEquipped, int equipmentSlot)
	{
		super(objId);
		
		this.itemTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
		if(itemTemplate == null)
		{
			log.error("Item was not populated correctly. Item template is missing for item id: " + itemId);
		}
		
		this.itemCount = itemCount;
		this.isEquipped = isEquipped == 1 ? true : false;
		this.equipmentSlot = equipmentSlot;
	}

	@Override
	public String getName()
	{
		//TODO
		//item description should return probably string and not id
		return String.valueOf(itemTemplate.getDescription());
	}

	/**
	 * @return the itemTemplate
	 */
	public ItemTemplate getItemTemplate()
	{
		return itemTemplate;
	}

	/**
	 * @param itemTemplate the itemTemplate to set
	 */
	public void setItemTemplate(ItemTemplate itemTemplate)
	{
		this.itemTemplate = itemTemplate;
	}

	/**
	 * @return the itemCount
	 *  Number of this item in stack. Should be not more than template maxstackcount ?
	 */
	public int getItemCount()
	{
		return itemCount;
	}

	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(int itemCount)
	{
		this.itemCount = itemCount;
	}
	
	/**
	 * @param addCount 
	 */
	public void increaseItemCount(int addCount)
	{
		//TODO overflow check
		this.itemCount += addCount;
	}

	/**
	 * @return the isEquipped
	 */
	public boolean isEquipped()
	{
		return isEquipped;
	}

	/**
	 * @param isEquipped the isEquipped to set
	 */
	public void setEquipped(boolean isEquipped)
	{
		this.isEquipped = isEquipped;
	}

	/**
	 * @return the equipmentSlot
	 */
	public int getEquipmentSlot()
	{
		return equipmentSlot;
	}

	/**
	 * @param equipmentSlot the equipmentSlot to set
	 */
	public void setEquipmentSlot(int equipmentSlot)
	{
		this.equipmentSlot = equipmentSlot;
	}
}
