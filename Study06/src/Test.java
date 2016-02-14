
public class Test {
	
	public static void main(String[] args) {
		
	}
	
	static void test1() {
		// Test 1 for vertex insertion and deletion
		System.out.println("Test1");
		Graph<Integer, Void> G1 = new Graph<Integer, Void>();
		for(int i = 0; i < 10; i++) {
			G1.addVertex(i);
		}
		System.out.println(G1.vertices());
		G1.removeVertex(5);
		System.out.println(G1.vertices());
	}
	
	static void test2() {
		// Test 2 for edge insertion and deletion
		System.out.println("Test2");
		Graph<Integer, Void> G2 = new Graph<Integer, Void>();
		G2.addEdge(1, 2);
		G2.addEdge(1, 3);
		G2.addEdge(1, 4);
		G2.addEdge(2, 3);
		System.out.println(G2.neighbours(1));
		System.out.println(G2.neighbours(2));
		System.out.println(G2.neighbours(3));
		System.out.println(G2.edges());
		
		// should be [(1, 2), (1, 4), (2, 3)]
		G2.removeEdge(1, 3);
		System.out.println("after edge deletion(1, 3): "+G2.edges());
		
		// should be [(1, 4)]
		G2.removeVertex(2);
		System.out.println("after vertex deletion(2):"+G2.edges());
		
		// should be [(1, 4), (2, 3)]
		G2.addEdge(2, 3);
		System.out.println("after edge insertion(2, 3): "+G2.edges());
	}
	
	static void test3() {
		// Test 3 for hasPath
		System.out.println("Test3");
		Graph<Integer, Void> G = new Graph<Integer, Void>();
		G.addEdge(1, 2);
		G.addEdge(1, 3);
		G.addEdge(1, 4);
		G.addEdge(2, 3);
		G.addVertex(5);
		G.addVertex(6);
		G.addEdge(4, 5);
		System.out.println(G.neighbours(1));
		System.out.println(G.neighbours(5));
		System.out.println(G.hasPath(1, 4)); // Should return true
		System.out.println(G.hasPath(1, 5)); // Should return true
		System.out.println(G.hasPath(1, 6)); // Should return false
		System.out.println(G.edges());
	}
	
	static void test4() {
		// Test 4 for BA Network
		Graph<Integer, Void> G = new BANetworkBuilder()
			.vertices(20)
			.attachments(2)
			.build();
		printNX(G);
	}
	
	static void test5() {
		// Test 5 for network equality
		Graph<Integer, Void> G1 = new TestNetworkBuilder().build();
		Graph<Integer, Void> G2 = new TestNetworkBuilder().build();
		Graph<Integer, Void> G3 = new TestNetworkBuilder().build();
		G3.addVertex(100); G3.addEdge(6, 100);
		Graph<Integer, Void> G4 = G3.clone();
		G4.removeVertex(100);
		System.out.println(G1.equals(G2)); // should print true
		System.out.println(G1.equals(G3)); // should print false
		System.out.println(G1.equals(G4)); // should print true
	}
	
	static void printNX(Graph<?, ?> G) {
		// # printing NetworkX-friendly
		System.out.println("G = nx.Graph()");
		System.out.println("G.add_nodes_from("+G.vertices()+")");
		System.out.println("G.add_edges_from("+G.edges()+")");
	}
	
}
