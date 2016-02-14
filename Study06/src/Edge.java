
public class Edge<V extends Comparable<V>, E> implements Comparable<Edge<V, E>> {
	private V source, target;
	private E attr;
	
	public Edge(V source, V target, E attr) {
		this.source = source;
		this.target = target;
		this.attr = attr;
	}
	
	public V getSource() {
		return source;
	}
	
	public V getTarget() {
		return target;
	}
	
	public E getAttr() {
		return attr;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Edge<?, ?>))
			return false;
		Edge<?, ?> e = (Edge<?, ?>) o;
		return this.source.equals(e.source) &&
				this.target.equals(e.target);
	}
	
	@Override
	public int hashCode() {
		return 31 * this.source.hashCode() + this.target.hashCode();
	}
	
	@Override
	public String toString() {
		return "(" + source + ", " + target +
				(attr != null ? ", " + attr : "") + ")";
	}
	
	@Override
	public int compareTo(Edge<V, E> o) {
		if(o == null) return 1;
		int s = source == null ? (o.source == null ? 0 : -1) :
			source.compareTo(o.source);
		if(s != 0) return s;
		int t = target == null ? (o.target == null ? 0 : -1) :
			source.compareTo(o.target);
		return t;
	}
	
}
