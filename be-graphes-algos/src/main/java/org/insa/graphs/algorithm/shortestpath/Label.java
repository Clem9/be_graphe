package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label{
	
	private Node sommet_courant;
	private boolean marque;
	private float cout;
	private Arc pere;
	
	
	public Label(Node a, boolean m,  Arc p, float c) {
		this.sommet_courant = a;
		this.marque = m;
		this.cout = c;
		this.pere =p;
	}
	
	
	public float getCost() {
		return this.cout;
	}
	
}