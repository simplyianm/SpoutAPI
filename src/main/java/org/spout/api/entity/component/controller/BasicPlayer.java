package org.spout.api.entity.component.controller;

import org.spout.api.entity.component.controller.type.ControllerType;
import org.spout.api.player.Player;
import org.spout.api.player.PlayerController;

public abstract class BasicPlayer extends BasicController implements PlayerController {

	protected BasicPlayer(ControllerType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public final Player getParent() {
		return (Player) super.getParent();
	}

	@Override
	public abstract void onTick(float dt);

	@Override
	public void onAttached() {
		// TODO Auto-generated method stub
	}

}
