/*
 * This file is part of SpoutAPI.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * SpoutAPI is licensed under the SpoutDev License Version 1.
 *
 * SpoutAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * SpoutAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.api.event.storage;

import org.spout.api.event.Event;
import org.spout.api.event.HandlerList;
import org.spout.api.player.PlayerController;

/**
 * Called when data about a player needs to be saved, usually right after a player session terminates.
 */
public class PlayerSaveEvent extends Event {
	private static HandlerList handlers = new HandlerList();
	private boolean saved = false;
	private PlayerController player;
	public PlayerSaveEvent(PlayerController player) {
		this.player = player;
	}

	/**
	 * Gets the player whose data is being saved.
	 * 
	 * @return player
	 */
	public PlayerController getPlayer() {
		return player;
	}

	/**
	 * True if a plugin has already saved this data.
	 * 
	 * @return saved
	 */
	public boolean isSaved() {
		return saved;
	}

	/**
	 * Sets the saved state of this event. 
	 * 
	 * If the data is not reported saved after it has been called, it will be saved by the default save handler.
	 * 
	 * @param save
	 */
	public void setSaved(boolean save) {
		saved = save;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
