# Creating a Graph with GraphStream
1) Read the adjacency list of an undirected graph from a text file called "graph.txt".  
• The edge weights are 1.  
• The node IDs are integers.  
For example, with the following graph, the file will contain the following information:  

1 2 3  
2 4 5  
3 5  
4 5  

2) Create a graph similar to your tree in Assignment 3 but this time using GraphStream.  
• Note that there is no root in a graph.  
• If there is an edge between nodes a and b, do not add a new edge for nodes b and a.  

3) Display the graph.  
  
  
# Finding Shortest Paths  
1) Ask the user to provide two node IDs as inputs and you would calculate the shortest path between them.  

2) Implement Dijkstra's algorithm (covered in class on 17th Nov.) to calculate the shortest path between any pair of nodes in the above graph.  

3) Keep asking the user for such inputs until the user decides to stop.  
• You can implement this using a do-while loop.  

4) Display the shortest path found by Dijkstra's algorithm along with Node IDs so that Dr. Dutta can verify the correctness of the solution.  
• You can use the code provided by Dr. Dutta in the classroom.  
• GraphStream's example to color nodes on the shortest path: https://graphstream-project.org/doc/Algorithms/Shortest-path/Dijkstra/ (Links to an external site.) 

5) You can only use the topics discussed in the classroom so far to implement this.  
