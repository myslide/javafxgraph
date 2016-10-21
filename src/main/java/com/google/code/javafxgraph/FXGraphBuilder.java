/*
 * (C) Copyright 2012 JavaFXGraph (http://code.google.com/p/javafxgraph/).
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

public class FXGraphBuilder {

	private int grid;

	public static FXGraphBuilder create() {
		return new FXGraphBuilder();
	}

	public FXGraphBuilder grid(int grid){
    	this.grid=grid;
    	return this;
    }

	private FXGraphBuilder() {
	}

	public FXGraph build() {
		FXGraph theGraph= new FXGraph();
		theGraph.setGrid(grid);
		return theGraph;
	}
}
