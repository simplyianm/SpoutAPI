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
package org.spout.api.protocol;

import java.util.Collection;
import java.util.Set;

import org.spout.api.Spout;
import org.spout.api.entity.Entity;
import org.spout.api.geo.discrete.Point;
import org.spout.api.player.Player;
import org.spout.api.protocol.event.ProtocolEvent;
import org.spout.api.util.ParamCallback;

/**
 * Various utility methods to make protocoly tasks easier
 */
public class ProtocolUtil {

	public static void executeWithAllPlayers(ParamCallback<Player> callback) {
		executeWithPlayers(Spout.getEngine().getOnlinePlayers(), callback);
	}

	public static void executeWithPlayers(Player[] players, ParamCallback<Player> callback) {
		for (Player player : players) {
			if (player.isOnline()) {
				callback.call(player);
			}
		}
	}

	public static void executeWithPlayers(Collection<Player> players, ParamCallback<Player> callback) {
		for (Player player : players) {
			if (player.isOnline()) {
				callback.call(player);
			}
		}
	}

	/**
	 * This method performs the provided action for all nearby players
	 * @param position The position that the packet relates to. It will be used as the central point to send packets in a range from.
	 * @param range	The range (circular) from the entity in-which the nearest player should be searched for.
	 * @param callback The messages that should be sent to the discovered nearest player.
	 */
	public static void executeWithNearbyPlayers(Point position, Entity ignore, int range, ParamCallback<Player> callback) {
		Set<Player> players = position.getWorld().getNearbyPlayers(position, ignore, range);
		for (Player plr : players) {
			callback.call(plr);
		}
	}

	/**
	 * This method sends any amount of packets to all nearby players of a position (within a specified range).
	 * @param position The position that the packet relates to. It will be used as the central point to send packets in a range from.
	 * @param range	The range (circular) from the entity in-which the nearest player should be searched for.
	 * @param callback The messages that should be sent to the discovered nearest player.
	 */
	public static void executeWithNearbyPlayers(Point position, int range, ParamCallback<Player> callback) {
		Set<Player> players = position.getWorld().getNearbyPlayers(position, range);
		for (Player plr : players) {
			callback.call(plr);
		}
	}

	/**
	 * This method sends any amount of packets to all nearby players of an entity (within a specified range).
	 * @param entity   The entity that the packet relates to. It will be used as the central point to send packets in a range from.
	 * @param range	The range (circular) from the entity in-which the nearest player should be searched for.
	 * @param callback The action that will be performed on every applicable player
	 */
	public static void executeWithNearbyPlayers(Entity entity, int range, ParamCallback<Player> callback) {
		if (entity == null || entity.getRegion() == null) {
			return;
		}
		executeWithNearbyPlayers(entity.getPosition(), range, callback);
	}

	/**
	 * This method sends any amount of packets and sends them to the nearest player from the entity specified.
	 * @param entity The entity that the packet relates to. It will be used as the central point to send packets in a range from.
	 * @param range The range (circular) from the entity in-which the nearest player should be searched for.
	 * @param callback The messages that should be sent to the discovered nearest player.
	 */
	public static void executeWithNearestPlayer(Entity entity, int range, ParamCallback<Player> callback) {
		if (entity == null || entity.getRegion() == null) {
			return;
		}

		Player plr = entity.getWorld().getNearestPlayer(entity, range);
		//Only send if we have a player nearby.
		if (plr != null) {
			callback.call(plr);
		}
	}

	public static class CallProtocolEvent implements ParamCallback<Player> {
		private final ProtocolEvent event;

		public CallProtocolEvent(ProtocolEvent event) {
			this.event = event;
		}

		public void call(Player p) {
			p.getNetworkSynchronizer().callProtocolEvent(event);
		}
	}
}
