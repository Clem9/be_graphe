package org.insa.graphs.algorithm.shortestpath;

import java.util.List;
import java.util.ArrayList;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;



public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        Graph graph = data.getGraph();
        List<Node> nodes = graph.getNodes();
        
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        Label[] labels = new Label[graph.size()];
        
        Node origine = data.getOrigin();
        
        
        // initialisation de tous les labels
        for (Node node : nodes ) {
        		labels[node.getId()] = new Label(node,false,Float.POSITIVE_INFINITY,null);
        }
        
        labels[origine.getId()].setCost(0);
        heap.insert(labels[origine.getId()]);
        
        while(!heap.isEmpty()) {
        	Label label_courrant = heap.deleteMin();
        	label_courrant.marquer();
        	Node noeud_courant = label_courrant.getNode();
        	
        	for (Arc arc : noeud_courant.getSuccessors()) {
        		Node noeud_destination = arc.getDestination();
        		Label label_destination = labels[noeud_destination.getId()];
        		
        		double cout = data.getCost(arc) + label_courrant.getCost();
        		
        		if (!label_destination.isMarque()) // noeud jamais rencontré
        		{
        			label_destination.setCost(cout);
        			label_destination.setPere(arc);
        			heap.insert(label_destination);
        		}
        		else if (label_destination.getCost()>cout) // noeud rencontré mais cout meilleur
        		{
        			heap.remove(label_destination);
        			label_destination.setCost(cout);
            		label_destination.setPere(arc);
            		heap.insert(label_destination);
        		}	
        		
        			
        	}// end for
        			
        }// end while
        	
        
        
        return solution;
    }

}

