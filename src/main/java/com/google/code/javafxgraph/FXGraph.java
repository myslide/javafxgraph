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

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class FXGraph extends ScrollPane {

	static final double NODES_Z_OFFSET = 10;
	static final double EDGES_Z_OFFSET = 10;

	Pane contentPane;

	FXGraphModel model;
	FXGraphSelectionTool selectionTool;
	FXGraphZoomHandler zoomHandler;

	FXTool currentTool;

	private FXGraphMouseHandler mouseHandler;

	public FXGraph() {

		model = new FXGraphModel();

		setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		contentPane = new Pane();
		setContent(contentPane);

		zoomHandler = new FXGraphZoomHandler(this);
		selectionTool = new FXGraphSelectionTool(contentPane, model, zoomHandler);
		mouseHandler = new FXGraphMouseHandler(this);

		mouseHandler.registerHandlerFor(contentPane);

		currentTool = selectionTool;
	}

	/**
	 * Set a grid >1 to align the nodes easy.
	 * 
	 * @param grid
	 *            a value btw 1 ... pos.infinity
	 * @see FXGraphSelectionTool#setGrid(double)
	 */
	public void setGrid(int grid) {
		selectionTool.setGrid(grid);
	}

	void updateEdge(FXEdge aEdge, double aZoomLevel) {
		aEdge.removeAllNodes(contentPane);
		aEdge.computeDisplayShape(aZoomLevel);
		aEdge.addAllNodes(contentPane, EDGES_Z_OFFSET);
		mouseHandler.registerNewEdge(aEdge);
	}

	void updateEdgeNodesFor(FXNode aNode, double aZoomLevel) {
		for (FXEdge theEdge : model.getEdges()) {
			if (theEdge.source == aNode || theEdge.destination == aNode) {
				updateEdge(theEdge, aZoomLevel);
			}
		}
	}

	void updateEdgeNodesFor(FXNode aNode) {
		updateEdgeNodesFor(aNode, zoomHandler.currentZoomLevel);
	}

	public void updateSelectionInScene() {
		selectionTool.updateSelectionInScene();
	}

	public void addNode(FXNode aNode) {

		aNode.wrappedNode.setTranslateZ(NODES_Z_OFFSET);

		contentPane.getChildren().add(aNode.wrappedNode);

		model.registerNewNode(aNode);
		mouseHandler.registerNewNode(aNode);
	}

	public void addEdge(FXEdge aEdge) {

		aEdge.computeDisplayShape(zoomHandler.currentZoomLevel);

		aEdge.addAllNodes(contentPane, EDGES_Z_OFFSET);
		model.registerNewEdge(aEdge);

		mouseHandler.registerNewEdge(aEdge);
	}

	public String getStylesheet() throws NullPointerException {
		return getClass().getResource("fxgraph.css").toExternalForm();
	}
}