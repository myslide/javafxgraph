
package com.google.code.javafxgraph;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.stage.Stage;

/**
 * Demonstrates the use of the different Edge styles, defined in
 * com/google/code/javafxgraph/fxgraph.css And a waypoint construction.
 * 
 * @author mysli
 * @version 20161115
 */
public class FXGraphStyleDemo extends Application {
	@Override
	public void start(Stage aStage) throws Exception {
		aStage.setMinWidth(800);
		aStage.setMinHeight(600);
		aStage.setTitle(getClass().getSimpleName());

		FXGraphBuilder theBuilder = FXGraphBuilder.create();
		FXGraph theGraph = theBuilder.grid(5).build();
		List<FXNode> theNodes = new ArrayList<FXNode>();
		int centerX = 400;
		int centerY = 300;
		int numNodes = 20;
		int radius = 220;
		for (int i = 0; i < numNodes; i++) {
			Button button1 = new Button();
			button1.setText("Node " + i);

			double positionX = centerX + Math.cos(Math.toRadians(360 / numNodes * i)) * radius;
			double positionY = centerY + Math.sin(Math.toRadians(360 / numNodes * i)) * radius;

			FXNodeBuilder theNodeBuilder = new FXNodeBuilder(theGraph);
			theNodes.add(theNodeBuilder.node(button1).x(positionX).y(positionY).build());
		}
		FXNode centerNode = theNodes.get(numNodes / 2);
		FXEdgeBuilder theEdgeBuilder = new FXEdgeBuilder(theGraph);
		FXEdge edge;
		int i = 0;
		// the 1st without a style set.
		edge = theEdgeBuilder.source(theNodes.get(i)).destination(theNodes.get(i + 1)).build();
		i++;
		for (; i < theNodes.size() - 1; i++) {
			// Set a different end-connection between Node 7 and 8:
			if (i == 7) {
				//and a image
				Button bt = (Button) theNodes.get(7).wrappedNode;
				bt.setText("");
				bt.setGraphic(new ImageView(
						new Image("/com/google/code/javafxgraph/bend.png")));
				
				theEdgeBuilder.endPointProvider(new EndPointProviderI() {

					public LineTo createLineTo(Node endNode) {
						Bounds b = endNode.getBoundsInParent();
						return new LineTo(b.getMaxX(), b.getMaxY());
					}

					public MoveTo createLineFrom(Node sourceNode) {
						Bounds b = sourceNode.getBoundsInParent();
						return new MoveTo(b.getMinX(), b.getMinY());
					}
				});
			} else {
				// but the builder needs a reset to default
				theEdgeBuilder.endPointProvider(FXEdge.DEFAULT_ENDPOINTPROVIDER);
			}

			// Set different styles, defined in fxgraph.css:
			if ((i % 2) == 0) {
				edge = theEdgeBuilder.styleId("path-blue").source(theNodes.get(i)).destination(theNodes.get(i + 1))
						.build();
			} else if ((i % 3) == 0) {
				edge = theEdgeBuilder.styleId("path-red").source(theNodes.get(i)).destination(theNodes.get(i + 1))
						.build();
			} else {
				edge = theEdgeBuilder.styleId("path-green").source(theNodes.get(i)).destination(theNodes.get(i + 1))
						.build();
			}
			// Set a single waypoint:
			if (centerNode.equals(theNodes.get(i + 1))) {
				edge.addWayPoint(new FXEdgeWayPoint(edge, centerX, centerY));
			}

		}
		
		Scene scene = new Scene(theGraph);
		try {
			scene.getStylesheets().add(theGraph.getStylesheet());
		} catch (NullPointerException ex) {
			// No style sheets available..
		}
		aStage.setScene(scene);
	
		aStage.show();
		/**
		 * this workaround reinitializes all nodes to view the edges in the expected position.
		 */
		for (FXNode theNode : theGraph.model.getNodes()) {
            theNode.setZoomLevel(1);
         }
	}

	public static void main(String[] args) {
		Application.launch(FXGraphStyleDemo.class, args);
	}
}
