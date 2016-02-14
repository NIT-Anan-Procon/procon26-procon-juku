import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class R<V extends Comparable<V>, E> implements ObjectiveFunction<Graph<V, E>> {
	
	@Override
	public double f(Graph<V, E> G) {
		Graph<V, E> g = G.clone();
		int sizeSum = 0;
		// 最初にもっとも次数が高い頂点を一つ取り除く
		g.removeVertex(maxDegVertex(g));
		while(g.vertices().size() > 0) {
			// この時点での最大の連結要素の大きさを加算
			sizeSum += sizeOfLCC(g);
			// もっとも次数が高い頂点を一つ取り除く
			g.removeVertex(maxDegVertex(g));
		}
		return (double)sizeSum / (G.vertices().size() * G.vertices().size());
	}
	
	private static <V extends Comparable<V>, E> V maxDegVertex(Graph<V, E> G) {
		Map<V, Integer> degrees = G.degrees();
		V highestDegreeVertex = null;
		int highestDegree = -1;
		for(Iterator<V> i = degrees.keySet().iterator(); i.hasNext();) {
			V v = i.next();
			if(degrees.get(v) > highestDegree) {
				highestDegreeVertex = v;
				highestDegree = degrees.get(v);
			}
		}
		return highestDegreeVertex;
	}
	
	// LCC: Largest Connected Component
	private static <V extends Comparable<V>, E> int sizeOfLCC(Graph<V, E> G) {
		List<Graph<V, E>> cc = G.connectedComponents();
		int maxSize = -1;
		for(int i = 0; i < cc.size(); i++) {
			if(cc.get(i).vertices().size() > maxSize) {
				maxSize = cc.get(i).vertices().size();
			}
		}
		return maxSize;
	}
	
}
