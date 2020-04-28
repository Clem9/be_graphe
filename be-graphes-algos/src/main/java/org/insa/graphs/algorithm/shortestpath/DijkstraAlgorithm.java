package org.insa.graphs.algorithm.shortestpath;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;


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
        
        notifyOriginProcessed(origine);
        
        while(!heap.isEmpty()) {
        	Label label_courrant = heap.deleteMin();
        	label_courrant.marquer();
        	Node noeud_courant = label_courrant.getNode();
        	notifyNodeMarked(noeud_courant);
        	
        	int nb_successeurs =0;
        	
        	
        	for (Arc arc : noeud_courant.getSuccessors()) {
        		
        		nb_successeurs ++;
        		
		        Node noeud_destination = arc.getDestination();
		        Label label_destination = labels[noeud_destination.getId()];
		        		
		        double cout = data.getCost(arc) + label_courrant.getCost();
		        
		        
		        // Small test to check allowed roads...
                if (!data.isAllowed(arc)) {
                    continue;
                }
		        		
		        if (!label_destination.isMarque() && label_destination.getCost()>cout) // noeud jamais rencontré
		        {
		        	if (label_destination.getPere()==null) { // noeud jamais rencontré
			        	label_destination.setCost(cout);
			            label_destination.setPere(arc);
			            heap.insert(label_destination);
			            notifyNodeReached(noeud_destination);
			            //System.out.println("cout : " + label_destination.getCost());
			        }	
		        	else {
		        		heap.remove(label_destination);
			        	label_destination.setCost(cout);
			            label_destination.setPere(arc);
			            heap.insert(label_destination);
			            notifyNodeReached(noeud_destination);
			            //System.out.println("cout : " + label_destination.getCost());
		        	}
		        }
	        		
  
        	}// end for
        	
        	System.out.println("Le noeud " + noeud_courant.getId() + " a " + nb_successeurs + "successeurs");
        			
        }// end while
        
        Node noeud_desti = data.getDestination();
        Label label_desti = labels[noeud_desti.getId()];
        
        // Destination has no predecessor, the solution is infeasible...
        if (label_desti.getPere()== null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
       
        else {
        	// The destination has been found, notify the observers.
            notifyDestinationReached(noeud_desti);
            
            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = label_desti.getPere();
            
            while (arc != null) {
                arcs.add(arc);
                label_desti = labels[arc.getOrigin().getId()];
                arc = label_desti.getPere();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        
        return solution;
    }

}

