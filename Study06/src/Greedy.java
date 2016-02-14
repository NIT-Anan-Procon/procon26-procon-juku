
public class Greedy<V extends Comparable<V>, E> implements Algorithm<Graph<V, E>, Graph<V, E>> {
	
	private int mSteps = 100;
	private int mSwaps = 10;
	private boolean verbose = false;
	
	public Greedy<V, E> maxSteps(int mSteps) {
		this.mSteps = mSteps;
		return this;
	}
	
	public Greedy<V, E> numSwaps(int mSwaps) {
		this.mSwaps = mSwaps;
		return this;
	}
	
	public Greedy<V, E> verbose() {
		this.verbose = true;
		return this;
	}
	
	public Greedy<V, E> quiet() {
		this.verbose = false;
		return this;
	}
	
	@Override
	public Graph<V, E> maximize(Graph<V, E> input,
			ObjectiveFunction<Graph<V, E>> function) {
		Graph<V, E> G = input.clone();
		for(int i = 0; i < mSteps; i++) {
			Graph<V, E> maxGraph = G.clone();
			double maxFunc = function.f(maxGraph);
			for(int j = 0; j < mSwaps; j++) {
				Graph<V, E> g = G.clone();
				g.rewire();
				double f = function.f(g);
				if(f > maxFunc) {
					maxGraph = g;
					maxFunc = f;
				}
			}
			if(verbose) System.out.println((i+1)+","+maxFunc);
			G = maxGraph;
		}
		return G;
	}
}
