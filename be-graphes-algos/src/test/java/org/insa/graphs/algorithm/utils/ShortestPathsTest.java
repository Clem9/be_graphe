package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;

import java.io.*;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.*;
import org.junit.Test;



public class ShortestPathsTest {
	
	
	 @Test
	 public void testSolution() throws IOException  {
		 
		 /*---------------------Lecture de trois cartes-------------------*/
		 
		 final String hauteGaronne = "/Users/Clementine/Documents/graphes/maps/haute-garonne.mapgr";
		 final String bretagne = "/Users/Clementine/Documents/graphes/maps/bretagne.mapgr";
		 final String insa = "/Users/Clementine/Documents/graphes/maps/insa.mapgr";
			
		 
		 final GraphReader readerHG = new BinaryGraphReader(new DataInputStream
				(new BufferedInputStream(new FileInputStream(hauteGaronne))));
			
  		 final GraphReader readerBretagne = new BinaryGraphReader(new DataInputStream
				(new BufferedInputStream(new FileInputStream(bretagne))));
			
		 final GraphReader readerInsa = new BinaryGraphReader(new DataInputStream
				(new BufferedInputStream(new FileInputStream(insa))));
		 
		 Graph graphHauteGaronne = readerHG.read();
		 Graph graphBretagne = readerBretagne.read();
		 Graph graphInsa = readerInsa.read();
		 
		 
		 
		 /*-------------------------Création des données------------------------*/
		 
		 final ArcInspector allRoads = ArcInspectorFactory.getAllFilters().get(0);
		 final ArcInspector carAndLength = ArcInspectorFactory.getAllFilters().get(1);
		 final ArcInspector carsAndTime = ArcInspectorFactory.getAllFilters().get(2);
		 final ArcInspector pedestrian = ArcInspectorFactory.getAllFilters().get(4);
		 
		 ShortestPathData data[] = new ShortestPathData[6];
		 
		 // Trajet d'un point à lui-même
		 data[0] = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(10991), graphHauteGaronne.get(10991), allRoads);
		 
		 // Trajet bikini/Canal toutes routes
		 data[1] = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(10991), graphHauteGaronne.get(63104), allRoads);
		 
		 // Trajet bikini/Canal en voiture et en temps
		 data[2] = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(535), graphHauteGaronne.get(535), carsAndTime);
		 
		 // Trajet bikini/Canal en voiture et en distance
		 data[3] = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(535), graphHauteGaronne.get(535), carAndLength);
		 
		 // Trajet bikini/Canal à pied
		 data[4] = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(535), graphHauteGaronne.get(535), pedestrian);
		 
		 // Trajet entre deux îles non reliées (trajet non existant)
		 data[5] = new ShortestPathData(graphBretagne, graphBretagne.get(417195), graphBretagne.get(116100), allRoads);

		 
		
		 
		 /*-------------------Execution des trois algorithmes-------------------*/
		 
		 
		  BellmanFordAlgorithm bellman[]= new BellmanFordAlgorithm[6];
	      DijkstraAlgorithm dijkstra[]= new DijkstraAlgorithm[6];
	      AStarAlgorithm astar[]= new AStarAlgorithm[6];
	        
	      for(int i=0; i<6;i++) {
	    	  bellman[i] = new BellmanFordAlgorithm(data[i]);
	    	  dijkstra[i] = new DijkstraAlgorithm(data[i]);
	    	  astar[i] = new AStarAlgorithm(data[i]);
	      }
	        
	      
	      ShortestPathSolution solutionBF[] = new ShortestPathSolution[6];
	      ShortestPathSolution solutionDijkstra[] = new ShortestPathSolution[6];
	      ShortestPathSolution solutionAStar[] = new ShortestPathSolution[6];
	      
	      for(int i=0; i<6;i++) {
	    	  solutionBF[i] = bellman[i].run();
	    	  solutionDijkstra[i] = dijkstra[i].run();
	    	  solutionAStar[i] = astar[i].run();
	      }

	      /*---------------------Comparaison des solutions---------------------*/
	      
	      for(int i=0; i<6;i++) {
	    	  assertEquals(solutionBF[i],solutionDijkstra[i]);
	    	  assertEquals(solutionBF[i],solutionAStar[i]);
	      }
	      
	      
	      
	 }

}
