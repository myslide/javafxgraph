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
import java.util.List;

public class FXEdgeBuilder {

	private FXGraph graph;
	private FXNode source;
	private FXNode destination;
	private String styleId;
	private List<FXEdgeWayPoint> wayPoints = new ArrayList<FXEdgeWayPoint>();
	private EndPointProviderI endPointProvider=FXEdge.DEFAULT_ENDPOINTPROVIDER;

	public FXEdgeBuilder(FXGraph aGraph) {
		graph = aGraph;
	}

	public FXEdgeBuilder source(FXNode aSource) {
		source = aSource;
		return this;
	}

	public FXEdgeBuilder destination(FXNode aDestination) {
		destination = aDestination;
		return this;
	}
	public FXEdgeBuilder endPointProvider(EndPointProviderI endPointProvider) {
		this.endPointProvider = endPointProvider;
		return this;
	}

	public FXEdgeBuilder wayPoint(FXEdgeWayPoint aWayPoint) {
		wayPoints.add(aWayPoint);
		return this;
	}
	public FXEdgeBuilder styleId(String cssStyleElement) {
		styleId = cssStyleElement;
		return this;
	}

	public FXEdge build() {
		FXEdge theEdge = new FXEdge(graph, source, destination);
		theEdge.setEndPointProvider(endPointProvider);
		theEdge.wayPoints.addAll(wayPoints);
		theEdge.setStyleId(styleId);
		graph.addEdge(theEdge);
		return theEdge;
	}
}
