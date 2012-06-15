package org.spout.api.player;

import java.net.InetAddress;

import org.spout.api.command.CommandSource;
import org.spout.api.entity.Entity;
import org.spout.api.entity.component.Controller;
import org.spout.api.protocol.NetworkSynchronizer;
import org.spout.api.protocol.Session;
import org.spout.api.util.thread.Threadsafe;

public interface Player extends CommandSource, Entity {
	/**
	 * Gets the player's name. This method is thread-safe.
	 * 
	 * @return the player's name
	 */
	@Threadsafe
	public String getName();

	/**
	 * Gets the player's display name. This method is thread-safe.
	 * 
	 * @return the player's display name
	 */
	@Threadsafe
	public String getDisplayName();

	/**
	 * Sets the player's display name. This method is thread-safe.
	 * 
	 * @param name the new player's display name
	 */
	@Threadsafe
	public void setDisplayName(String name);

	/**
	 * Sends a message as if the player had typed it into their chat gui.
	 * 
	 * @param message the message to send
	 */
	public void chat(String message);

	/**
	 * Sets the NetworkSynchronizer associated with this player.<br>
	 * <br>
	 * This can only be called once per player login.
	 * 
	 * @param synchronizer the synchronizer
	 */
	public void setNetworkSynchronizer(NetworkSynchronizer synchronizer);

	/**
	 * Gets the NetworkSynchronizer associated with this player.<br>
	 * 
	 * @return the synchronizer
	 */
	public NetworkSynchronizer getNetworkSynchronizer();

	/**
	 * Gets the session associated with the Player.
	 * 
	 * @return the session, or null if the player is offline
	 */
	public Session getSession();

	/**
	 * Gets if the player is online
	 * 
	 * @return true if online
	 */
	public boolean isOnline();

	/**
	 * Gets the sessions address This is equivalent to
	 * getSession().getAddress().getAddress();
	 * 
	 * @return The session's address
	 */
	public InetAddress getAddress();

	/**
	 * Kicks the player without giving a reason, or forcing it.
	 */
	public void kick();

	/**
	 * Kicks the player for the given reason.
	 * 
	 * @param reason the message to send to the player.
	 */
	public void kick(String reason);

	/**
	 * Gets the current input state of the player
	 * 
	 * @return current input state
	 */
	public PlayerInputState input();
	
	@Override
	public PlayerController getController();
}
