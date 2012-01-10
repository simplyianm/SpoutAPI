/*
 * This file is part of SpoutAPI (http://wwwi.getspout.org/).
 *
 * Spout API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Spout API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spout.api.gui;

import org.spout.api.ClientOnly;

public class GenericOverlayScreen extends GenericScreen implements OverlayScreen {

	ScreenType screenType;
	
	public GenericOverlayScreen(int entityId, ScreenType type) {
		super(entityId);
		screenType = type;
	}
	
	@Override
	public WidgetType getType() {
		return WidgetType.OverlayScreen;
	}

	@Override
	public ScreenType getScreenType() {
		return screenType;
	}

	@Override
	@ClientOnly
	public void render() {
//		Spoutcraft.getClient().getRenderDelegate().render(this);
	}
}