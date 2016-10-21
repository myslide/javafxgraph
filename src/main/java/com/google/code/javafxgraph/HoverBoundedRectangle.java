/*
 * (C) Copyright 2016 mysli
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3.0 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package com.google.code.javafxgraph;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

/**
 * All way points will be highlighted too if the path is hovered.
 * 
 * We cannot set the hover property externally, so these subclass of node will
 * handle the binding between Path and Point.
 * 
 * @author mysli
 * @version 20161021
 */
public class HoverBoundedRectangle extends Rectangle {

	public HoverBoundedRectangle(double width, double height) {
		super(width, height);

	}

	void bindPath(Path path) {
		path.hoverProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				setHover(newValue);
			}
		});
	}
}
