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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class FXEdge {

	FXGraph graph;
	FXNode source;
	FXNode destination;
	Node displayShape;
	private Tooltip toolTipPath;
	private Tooltip toolTipPoint;
	private static final String DEFAULT_STYLEID = "path";
	private static final String DEFAULT_POINTSTYLEID = "point";
	final static String STANDARD_POINT_STYLE = "-fx--fx-fill:transparent;";
	final static String HOVERED_POINT_STYLE = "-fx-stroke-width:1;-fx-stroke:black;";
	/*makes the rectangle properties clear*/
	private final static double WIDTH = 4;
	private final static double HALFWIDTH = WIDTH / 2;
	/**
	 * A css file of related scene should contain a entry for edge color and its
	 * hover. default is black.
	 */
	private String pathStyleId = DEFAULT_STYLEID;
	private String pointStyleId = DEFAULT_POINTSTYLEID;
	List<FXEdgeWayPoint> wayPoints = new ArrayList<FXEdgeWayPoint>();
	Map<FXEdgeWayPoint, Node> wayPointHandles = new HashMap<FXEdgeWayPoint, Node>();

	public FXEdge(FXGraph aGraph, FXNode aSource, FXNode aDestination) {
		graph = aGraph;
		source = aSource;
		destination = aDestination;
		toolTipPath = new Tooltip();// java.(, ));
		toolTipPath.setText(I18N.getDefaultInstance().getString("edge.tip", "Shift+Mouse click"));
		toolTipPoint = new Tooltip(I18N.getDefaultInstance().getString("edge.point.tip", "Shift+Mouse double click"));
	}

	public void addWayPoint(FXEdgeWayPoint aWayPoint) {
		wayPoints.add(aWayPoint);

		graph.updateEdge(this, graph.zoomHandler.currentZoomLevel);
	}

	public void removeWayPoint(FXEdgeWayPoint aWayPoint) {
		wayPoints.remove(aWayPoint);

		graph.updateEdge(this, graph.zoomHandler.currentZoomLevel);
	}

	public HoverBoundedRectangle compileDisplayShapeFor(FXEdgeWayPoint aWayPoint, double aZoomLevel) {
		HoverBoundedRectangle theNode = new HoverBoundedRectangle(WIDTH, WIDTH);
		theNode.setScaleX(aZoomLevel);
		theNode.setScaleY(aZoomLevel);
		theNode.setLayoutX((aWayPoint.positionX - HALFWIDTH) * aZoomLevel);
		theNode.setLayoutY((aWayPoint.positionY - HALFWIDTH) * aZoomLevel);
		theNode.setUserData(aWayPoint);
		theNode.setId(pointStyleId);
		Tooltip.install(theNode, toolTipPoint);
		return theNode;
	}

	public void computeDisplayShape(double aCurrentZoomLevel) {
		Path thePath = new Path();
		thePath.setUserData(this);
		Tooltip.install(thePath, toolTipPath);
		// From the middle of the source
		Bounds theSourceBounds = source.wrappedNode.getBoundsInParent();
		MoveTo theMoveTo = new MoveTo(theSourceBounds.getMinX() + theSourceBounds.getWidth() / 2,
				theSourceBounds.getMinY() + theSourceBounds.getHeight() / 2);
		thePath.getElements().add(theMoveTo);

		wayPointHandles.clear();

		// Thru the waypoints
		for (FXEdgeWayPoint theWayPoint : wayPoints) {
			HoverBoundedRectangle wayNode = compileDisplayShapeFor(theWayPoint, aCurrentZoomLevel);
			wayPointHandles.put(theWayPoint, wayNode);

			LineTo theLineTo = new LineTo(theWayPoint.positionX * aCurrentZoomLevel,
					theWayPoint.positionY * aCurrentZoomLevel);
			thePath.getElements().add(theLineTo);
			wayNode.bindPath(thePath);
		}

		// To the middle of the destination
		Bounds theDestinationBounds = destination.wrappedNode.getBoundsInParent();
		LineTo theLineTo = new LineTo(theDestinationBounds.getMinX() + theDestinationBounds.getWidth() / 2,
				theDestinationBounds.getMinY() + theDestinationBounds.getHeight() / 2);
		thePath.getElements().add(theLineTo);

		thePath.setId(pathStyleId);
		displayShape = thePath;
	}

	public void removeAllNodes(Pane aPane) {
		aPane.getChildren().remove(displayShape);
		aPane.getChildren().removeAll(wayPointHandles.values());
	}

	public void addAllNodes(Pane aPane, double aZIndex) {
		aPane.getChildren().add(displayShape);
		displayShape.setTranslateZ(aZIndex);
		displayShape.toBack();

		for (Node theNode : wayPointHandles.values()) {
			theNode.setTranslateZ(aZIndex);
			aPane.getChildren().add(theNode);
			theNode.toBack();
		}
	}

	public final void setStyleId(String styleId) {
		this.pathStyleId = (styleId == null || styleId.length() == 0) ? DEFAULT_STYLEID : styleId;
	}
}
