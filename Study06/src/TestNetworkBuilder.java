
public class TestNetworkBuilder {
	public Graph<Integer, Void> build() {
		Graph<Integer, Void> G = new Graph<Integer, Void>();
		G.addEdge(1, 2);
		G.addEdge(1, 5);
		G.addEdge(2, 3);
		G.addEdge(2, 4);
		G.addEdge(2, 6);
		G.addEdge(3, 6);
		G.addEdge(4, 5);
		G.addEdge(5, 6);
		return G;
	}
}
