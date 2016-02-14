import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;


public class BANetworkBuilder {
	
	private int mNVertices;
	private int mNAttachments;
	
	public BANetworkBuilder() {
		this.mNVertices = 10;
		this.mNAttachments = 2;
	}
	
	public BANetworkBuilder vertices(int vertices) {
		this.mNVertices = vertices;
		return this;
	}
	
	public BANetworkBuilder attachments(int attachments) {
		this.mNAttachments = attachments;
		return this;
	}
	
	// http://networkx.lanl.gov/_modules/networkx/generators/random_graphs.html#barabasi_albert_graph
	public Graph<Integer, Void> build() {
		Random random = new Random();
		Graph<Integer, Void> G = new Graph<Integer, Void>();
		LinkedList<Integer> targets = new LinkedList<Integer>();
		int index = 0;
		// initialise with m vertices
		for(; index < mNAttachments; index++) {
			G.addVertex(index);
			targets.add(index);
		}
		// preferential attachment
		LinkedList<Integer> repeatedVertices = new LinkedList<Integer>();
		while(index < mNVertices) {
			for(int i = 0; i < targets.size(); i++) {
				G.addEdge(index, targets.get(i));
			}
			repeatedVertices.addAll(targets);
			for(int i = 0; i < mNAttachments; i++) {
				repeatedVertices.add(index);
			}
			Set<Integer> nextTargets = new HashSet<Integer>();
			while(nextTargets.size() < mNAttachments) {
				int i = repeatedVertices.get(random.nextInt(repeatedVertices.size()));
				nextTargets.add(i);
			}
			targets.clear();
			targets.addAll(nextTargets);
			index++;
		}
		return G;
	}
	
}
