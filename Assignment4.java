package assignment4;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Assignment4 {
	static String styleSheet =
            "node{" +
                    "fill-color: black;"+
                    "size: 20px;"+
                    "text-size: 30;"+ "text-alignment: at-left;"+ "text-color: red;" +
                    "}";	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("graph.txt");
		Scanner graphInput = new Scanner(file);	
		Scanner input = new Scanner(System.in);			
		ArrayList<Integer> path = new ArrayList<>();
		ArrayList<Integer> nodes = new ArrayList<>();
		Graph graph = new  SingleGraph("Graph");
				
		graph = graphGenerator(graphInput);
		nodes = getNodes(graph);
				
		
		while(true) {
			
			System.out.println("1. Calculate the shortest path");
			System.out.println("0. Exit");
			
			int userInput = input.nextInt();
						
			if(userInput == 1) {
				System.out.println("Enter 1st node: ");
				int firstNode = input.nextInt();				
				System.out.println("Enter 2nd node: ");
				int secondNode = input.nextInt();
				
				if(!nodes.contains(firstNode) || !nodes.contains(secondNode)) {
					System.out.println("Pick valid nodes!");					
				} else {
					defaultGraphColors(nodes, graph);
					path = dijkstra(graph, firstNode, secondNode);	
					printPath(path, firstNode,secondNode);
					printGraphShortestPath(path, graph);
					
				}
			
			} else if(userInput == 0) {
				System.exit(0);
				input.close();
				graphInput.close();	
			} else {
				System.out.println("Invalid input, enter 1 or 0!");
			}
			
		}
	
	}				
			

    private static Graph graphGenerator(Scanner input) {        
        Graph graph = new  SingleGraph("Graph");               
        ArrayList<String> listLines = new ArrayList<>();
        ArrayList<String> tempList = new ArrayList<>();
        
        
        
        while(input.hasNextLine()) {
			listLines.add(input.nextLine());
		}        
        
        for(int i=0; i<listLines.size();i++) {
        	
        	String[] elements = listLines.get(i).split(" ");
            
            for(int j=0; j<elements.length;j++) {
            	String builder = "";
            	if(tempList.isEmpty() || !tempList.contains(elements[j])) {
            		tempList.add(elements[j]);
            		graph.addNode(elements[j]);
            	}
            	
            	if(j !=0) {
            		builder = builder + (elements[0]);
            		builder = builder + (elements[j]);
            		
            		graph.addEdge(builder, graph.getNode(elements[0]), graph.getNode(elements[j])).setAttribute("distance", 1);;
            	}             
               
            }
         }     
        
        for(Node n: graph) {
        	graph.getNode(n.getId()).setAttribute("ui.label", n.getId());
        }
        
        System.setProperty("org.graphstream.ui", "swing");
        graph.setAttribute("ui.stylesheet", styleSheet);
        graph.display();
        
        return graph;
    }    
    
    

   static class Vertex{
    	int vertex;
    	int weight;
    	
    	public Vertex(int v, int w) {
    		this.vertex = v;
    		this.weight = w;
    	}
    }
   

   
   	private static ArrayList<Integer> getNeighbors(int n, Graph graph) {
   		ArrayList<Integer> neighbors = new ArrayList<>();
   		String node = Integer.toString(n);
   		for (Object o : graph.getNode(node).neighborNodes().toArray()) {
   			int neighbor = Integer.parseInt(o.toString());
   			neighbors.add(neighbor);
   		}
   		return neighbors;
   	}
   	
   	private static ArrayList<Integer> getNodes(Graph graph){
   		ArrayList<Integer> nodes = new ArrayList<>();
   		
   		for(Object o : graph.nodes().toArray()) {
   			int node = Integer.parseInt(o.toString());
   			nodes.add(node);
   		}
   		
   		return nodes;
   	}
   	

	private static ArrayList<Integer> dijkstra (Graph graph, int firstNode, int secondNode) {
		
        PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));
        HashMap<Integer, Vertex> dist =  new HashMap<>();
        ArrayList<Integer> neighbors =  new ArrayList<>();
        int[] prevNodes = new int[graph.getNodeCount()+1];
        ArrayList<Integer> visited = new ArrayList<>();
        
        prevNodes[firstNode] = -1;
        
        pq.add(new Vertex(firstNode, 0));
        dist.put(firstNode, new Vertex(firstNode, 0));
        visited.add(firstNode);
        
        for(int node : getNodes(graph)) {
        	if(node != firstNode) {
        		dist.put(node, new Vertex(node, Integer.MAX_VALUE));
        	}
        }
        
        while(!pq.isEmpty()) {
        	Vertex currNode = pq.poll();
        	
        	neighbors = getNeighbors(currNode.vertex, graph);
        	
        	for(int neighbor : neighbors) {        	
	        		
            		if(!visited.contains(neighbor)) {
            			int path = currNode.weight + 1;
                		//System.out.println("Path: " + path);       		
                		     	            		            		    		
                		if(path < dist.get(neighbor).weight) {
                			Vertex neighborV = new Vertex(neighbor, path);
                			dist.replace(neighbor, neighborV);                			
                			visited.add(neighbor);
                			prevNodes[neighbor] = currNode.vertex;
                			pq.add(neighborV);
                		}
            		}
            		        		
            }
        }
        
        ArrayList<Integer> shortestPath =  new ArrayList<>();
        int times = dist.get(secondNode).weight-1;
        shortestPath.add(secondNode);
        int currNode = secondNode;
        for(int i=0; i<times; i++) {        	
        	int nextNode = prevNodes[currNode];
        	shortestPath.add(nextNode);
        	currNode = nextNode;
        }
        shortestPath.add(firstNode);
        
        return shortestPath;
        
	}
	
	public static void printPath(ArrayList<Integer> shortestPath, int firstNode, int secondNode) {
		System.out.printf("(%s -> %s) : ", firstNode, secondNode);
        System.out.print("[");        
        for(int i = shortestPath.size() - 1; i > 0; i--) {
        	System.out.print(shortestPath.get(i) + "-");
        } 
        System.out.println(shortestPath.get(0) + "]");
	}
	
	public static void printGraphShortestPath(ArrayList<Integer> path, Graph graph)  {
		for (int nodeId : path) {
			String node =  Integer.toString(nodeId);
			graph.getNode(node).setAttribute("ui.style", "fill-color: blue;");
		}
	}
	
	public static void defaultGraphColors(ArrayList<Integer> nodes, Graph graph) {
		for (int nodeId : nodes) {
			String node =  Integer.toString(nodeId);
			graph.getNode(node).setAttribute("ui.style", "fill-color: black;");
		}
	}
}
