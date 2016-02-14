
public class Vertex<V extends Comparable<V>> implements Comparable<Vertex<V>> {
	public static final int INVALID_INDEX = -1;
	private int index;
	private V attr;
	
	public Vertex(int index, V attr) {
		this.index = index;
		this.attr = attr;
	}
	
	public int getIndex() {
		return index;
	}
	
	public V getAttr() {
		return attr;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean isValid() {
		return index != INVALID_INDEX && this.attr != null;
	}
	
	public void invalidate() {
		this.index = INVALID_INDEX;
		this.attr = null;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Vertex<?>))
			return false;
		Vertex<?> v = (Vertex<?>) o;
		if(this.attr == null) return v.attr == null;
		else return this.attr.equals(v.attr);
	}
	
	@Override
	public int hashCode() {
		if(attr == null) return -1;
		else return attr.hashCode();
	}

	@Override
	public int compareTo(Vertex<V> o) {
		if(o == null) return 1;
		if(attr == null) return o.attr == null ? 0 : -1;
		return attr.compareTo(o.attr);
	}
}
