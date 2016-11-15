/**
 * 
 */
package com.google.code.javafxgraph;

import javafx.scene.Node;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 * Defines, how its implementation provides the end points of the edge on a node.
 * @known {@link FXEdge#DEFAULT_ENDPOINTCALCULATOR}
 * @author mysli
 * @version 20160929
 */
public interface EndPointProviderI {
	public MoveTo createLineFrom(Node sourceNode);
	public LineTo createLineTo(Node endNode);
}
