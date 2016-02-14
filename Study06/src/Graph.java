import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class Graph<V extends Comparable<V>, E> {
	private ArrayList<Vertex<V>> mVertexList;
	private ArrayList<LinkedList<Edge<Integer, E>>> mEdgeList;
	
	public Graph() {
		mVertexList = new ArrayList<Vertex<V>>();
		mEdgeList = new ArrayList<LinkedList<Edge<Integer, E>>>();
	}
	
	public Graph(Graph<V, E> copy) {
		this();
		for(Iterator<V> i = copy.vertices().iterator(); i.hasNext();) {
			addVertex(i.next());
		}
		for(Iterator<Edge<V, E>> i = copy.edges().iterator(); i.hasNext();) {
			Edge<V, E> e = i.next();
			addEdge(e.getSource(), e.getTarget(), e.getAttr());
		}
	}
	
	public Graph<V, E> clone() {
		return new Graph<V, E>(this);
	}
	
	private int indexOf(V v) {
		int i = mVertexList.indexOf(new Vertex<V>(0, v));
		return i >= 0 && mVertexList.get(i).isValid() ? i : -1;
	}
	
	public boolean exists(V v) {
		int i = mVertexList.indexOf(new Vertex<V>(0, v));
		return i >= 0 && mVertexList.get(i).isValid();
	}
	
	public List<V> vertices() {
		LinkedList<V> vlist = new LinkedList<V>();
		for(int i = 0; i < mVertexList.size(); i++) {
			if(mVertexList.get(i).isValid())
				vlist.add(mVertexList.get(i).getAttr());
		}
		return vlist;
	}
	
	public List<V> neighbours(V v) {
		LinkedList<V> nlist = new LinkedList<V>();
		if(!exists(v)) return nlist;
		for(Iterator<Edge<Integer, E>> i = mEdgeList.get(indexOf(v)).iterator();
				i.hasNext();) {
			Edge<Integer, E> e = i.next();
			if(mVertexList.get(e.getTarget()).isValid())
				nlist.add(mVertexList.get(e.getTarget()).getAttr());
		}
		return nlist;
	}
	
	public List<Edge<V, E>> edges() {
		LinkedList<Edge<V, E>> elist = new LinkedList<Edge<V, E>>();
		for(int vi = 0; vi < mEdgeList.size(); vi++) {
			for(Iterator<Edge<Integer, E>> i = mEdgeList.get(vi).iterator();
					i.hasNext();) {
				Edge<Integer, E> e = i.next();
				Vertex<V> s = mVertexList.get(e.getSource());
				Vertex<V> t = mVertexList.get(e.getTarget());
				if(s.isValid() && t.isValid()) {
					Edge<V, E> rev = new Edge<V, E>(
							t.getAttr(), s.getAttr(), e.getAttr());
					if(elist.indexOf(rev) < 0) {
						elist.add(new Edge<V, E>(
								s.getAttr(), t.getAttr(), e.getAttr()));
					}
				}
			}
		}
		return elist;
	}
	
	public void addVertex(V v) {
		if(exists(v)) return;
		int listSize = mVertexList.size();
		mVertexList.add(new Vertex<V>(listSize, v));
		mEdgeList.add(new LinkedList<Edge<Integer, E>>());
	}
	
	public void removeVertex(V v) {
		if(!exists(v)) return;
		int i = indexOf(v);
		mVertexList.get(i).invalidate();
	}
	
	public void addEdge(V u, V v, E e) {
		if(!exists(u)) addVertex(u);
		if(!exists(v)) addVertex(v);
		int ui = indexOf(u), vi = indexOf(v);
		Edge<Integer, E> euv = new Edge<Integer, E>(ui, vi, e);
		Edge<Integer, E> evu = new Edge<Integer, E>(vi, ui, e);
		if(mEdgeList.get(ui).indexOf(euv) < 0)
			mEdgeList.get(ui).add(euv);
		if(mEdgeList.get(vi).indexOf(evu) < 0)
			mEdgeList.get(vi).add(evu);
	}
	
	public void addEdge(V u, V v) {
		addEdge(u, v, null);
	}
	
	public void removeEdge(V u, V v) {
		if(!exists(u) || !exists(v)) return;
		int ui = indexOf(u);
		int vi = indexOf(v);
		mEdgeList.get(ui).remove(new Edge<Integer, E>(ui, vi, null));
		mEdgeList.get(vi).remove(new Edge<Integer, E>(vi, ui, null));
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Graph<?, ?>))
			return false;
		@SuppressWarnings("unchecked")
		Graph<V, E> G = (Graph<V, E>) obj;
		List<V> v1 = this.vertices(), v2 = G.vertices();
		if(!v1.containsAll(v2) || !v2.containsAll(v1))
			return false;
		List<Edge<V, E>> e1 = this.edges(), e2 = G.edges();
		if(!e1.containsAll(e2) || !e2.containsAll(e1))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		List<V> v = vertices();
		v.sort(new Comparator<V>() {
			@Override
			public int compare(V o1, V o2) {
				return o1.compareTo(o2);
			}
		});
		List<Edge<V, E>> e = edges();
		e.sort(new Comparator<Edge<V, E>>() {
			@Override
			public int compare(Edge<V, E> o1, Edge<V, E> o2) {
				return o1.compareTo(o2);
			}
		});
		return vertices().hashCode() + edges().hashCode();
	}
	
	public boolean hasPath(V u, V v) {
		if(!exists(u) || !exists(v)) return false;
		int source = indexOf(u);
		int target = indexOf(v);
		boolean[] visited = new boolean[mVertexList.size()];
		visited[source] = true;
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(source);
		while(!stack.empty()) {
			int curr = stack.pop();
			if(curr == target) return true;
			for(Iterator<Edge<Integer, E>> i = mEdgeList.get(curr).iterator();
					i.hasNext();) {
				Edge<Integer, E> e = i.next();
				if(mVertexList.get(e.getTarget()).isValid() &&
						!visited[e.getTarget()]) {
					visited[e.getTarget()] = true;
					stack.push(e.getTarget());
				}
			}
		}
		return false;
	}
	
	public Map<V, Integer> degrees() {
		Map<V, Integer> degrees = new HashMap<V, Integer>();
		for(int i = 0; i < mVertexList.size(); i++) {
			if(mVertexList.get(i).isValid()) {
				int nNeighbours = 0;
				for(int j = 0; j < mEdgeList.get(i).size(); j++) {
					Edge<Integer, E> e = mEdgeList.get(i).get(j);
					if(mVertexList.get(e.getTarget()).isValid())
						nNeighbours++;
				}
				degrees.put(mVertexList.get(i).getAttr(), nNeighbours);
			}
		}
		return degrees;
	}
	
	public List<Graph<V, E>> connectedComponents() {
		List<Graph<V, E>> connectedComponents = new LinkedList<Graph<V, E>>();
		boolean[] visited = new boolean[mVertexList.size()];
		for(int i = 0; i < visited.length; i++) {
			visited[i] = !mVertexList.get(i).isValid();
		}
		boolean allVisited = false;
		while(!allVisited) { // すべて探索していない
			// 未探索ノードをスタックにプッシュ
			int index = -1;
			for(int i = 0; i < visited.length; i++) {
				if(!visited[i]) index = i;
			}
			allVisited = index < 0;
			if(allVisited) break; // could not find unvisited vertex
			visited[index] = true;
			
			Stack<Integer> stack = new Stack<Integer>();
			stack.push(index);
			Graph<V, E> connectedComponent = new Graph<V, E>();
			connectedComponent.addVertex(mVertexList.get(index).getAttr());
			// 近傍をたどることで連結要素を取得
			while(!stack.isEmpty()) {
				int v = stack.pop();
				for(Iterator<Edge<Integer, E>> i = mEdgeList.get(v).iterator();
						i.hasNext();) {
					// 訪れてないならスタックにプッシュしグラフを拡張
					Edge<Integer, E> e = i.next();
					int u = e.getTarget();
					if(!visited[u]) {
						visited[u] = true;
						stack.push(u);
						connectedComponent.addEdge(
								mVertexList.get(v).getAttr(),
								mVertexList.get(u).getAttr(),
								e.getAttr());
					}
				}
			}
			// 連結要素を追加
			connectedComponents.add(connectedComponent);
		}
		return connectedComponents;
	}
	
	public ArrayList<V> rewire() {
		return rewire(1).get(0);
	}
	
	public List<ArrayList<V>> rewire(int swapNum) {
		Graph<V, E> G = this;
		List<ArrayList<V>> swapVs = new LinkedList<ArrayList<V>>();
		Random random = new Random();
		int trial = 0;
		int maxTrial = swapNum * 1000;
		List<V> vertices = G.vertices();
		while(trial < maxTrial && swapNum > 0) {
			trial++;
			// select two vertices randomly
			V u = vertices.get(random.nextInt(vertices.size()));
			V x = vertices.get(random.nextInt(vertices.size()));
			V v = G.neighbours(u).get(random.nextInt(G.neighbours(u).size()));
			V y = G.neighbours(x).get(random.nextInt(G.neighbours(x).size()));
			// omit same vertex
			if(x.equals(u) || x.equals(v) || y.equals(u) || y.equals(v))
				continue;
			// omit parallel edge
			if(G.neighbours(u).indexOf(x) >= 0 || G.neighbours(v).indexOf(y) >= 0)
				continue;
			// swap anyway
			G.removeEdge(u, v); G.removeEdge(x, y);
			G.addEdge(u, x); G.addEdge(v, y);
			// then check connectivity
			boolean canSwap = G.hasPath(u, v);
			if(canSwap) {
				swapNum--;
				ArrayList<V> vs = new ArrayList<V>(4);
				vs.add(0, u); vs.add(1, v); vs.add(2, x); vs.add(3, y);
				swapVs.add(vs);
			} else {
				// undo swapping
				G.addEdge(u, v); G.addEdge(x, y);
				G.removeEdge(u, x); G.removeEdge(v, y);
			}
		}
		return swapVs;
	}
	
}
