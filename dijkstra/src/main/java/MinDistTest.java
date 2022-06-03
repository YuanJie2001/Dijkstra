
public class MinDistTest {

	public static void main(String[] args) {
		Dijkstra<Character> directNet = new  Dijkstra<>(10);
		
		directNet.addVertex('A');
		directNet.addVertex('B');
		directNet.addVertex('C');
		directNet.addVertex('D');
		directNet.addVertex('E');
		directNet.addVertex('F');
		directNet.addVertex('G');
		
		directNet.addEdge('A', 'B', 4);
		directNet.addEdge('A', 'C', 6);
		directNet.addEdge('A', 'D', 6);
		directNet.addEdge('B', 'C', 1);
		directNet.addEdge('B', 'E', 7);
		directNet.addEdge('C', 'E', 6);
		directNet.addEdge('C', 'F', 4);
		directNet.addEdge('D', 'C', 2);
		directNet.addEdge('D', 'F', 5);
		directNet.addEdge('E', 'G', 6);
		directNet.addEdge('F', 'E', 1);
		directNet.addEdge('F', 'G', 8);
		
		directNet.displayGraph();
		
		directNet.dijkstra('B', 'G');

	}

}