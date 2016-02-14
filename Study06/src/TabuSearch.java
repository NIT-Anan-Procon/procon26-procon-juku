import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class TabuSearch<V extends Comparable<V>, E>
implements Algorithm<Graph<V, E>, Graph<V, E>> {
	
	private int mSteps = 100;
	private int mNGList = 10;
	private int mNTabuList = 6;
	private boolean verbose = false;
	
	public TabuSearch<V, E> maxSteps(int mSteps) {
		this.mSteps = mSteps;
		return this;
	}
	
	public TabuSearch<V, E> numGList(int mNGList) {
		this.mNGList = mNGList;
		return this;
	}
	
	public TabuSearch<V, E> numTabuList(int mNTabuList) {
		this.mNTabuList = mNTabuList;
		return this;
	}
	
	public TabuSearch<V, E> verbose() {
		this.verbose = true;
		return this;
	}
	
	public TabuSearch<V, E> quiet() {
		this.verbose = false;
		return this;
	}
	
	/*
	 * 入力グラフ(input)に対して
	 * functionを最大化するようにグラフの辺をつなぎ変える
	 */
	@Override
	public Graph<V, E> maximize(Graph<V, E> input,
			ObjectiveFunction<Graph<V, E>> function) {
		// 現在の解とスコア
		Graph<V, E> GCurrent = input.clone();
		double fc = function.f(GCurrent);
		// 最良解とスコア
		Graph<V, E> GBest = GCurrent;
		double fBest = fc;
		// 近傍解を保存するリスト＋近傍解との差分を保存するリスト
		List<Graph<V, E>> gList = new LinkedList<Graph<V, E>>();
		List<SwapDiff> sList = new LinkedList<SwapDiff>();
		// タブーリスト
		List<SwapDiff> tabuList = new LinkedList<SwapDiff>();
		int step = 0;
		int trials = 0;
		int maxTrials = input.vertices().size();
		while(step < mSteps && trials < mSteps*maxTrials*10) {
			// 近傍解を追加
			gList.clear();
			int n = 0; // 試行回数
			while(n <= mNGList) {
				// 近傍の作成
				Graph<V, E> G1 = GCurrent.clone();
				SwapDiff sd1 = new SwapDiff(G1.rewire());
				// タブーリストに含まれていなければ解を追加
				//  つなぎ替えの際に同じ辺を選んではならない
				if(!tabuList.contains(sd1)) {
					gList.add(G1);
					sList.add(sd1);
				}
				n++;
			}
			
			// 近傍の中で最適な解をサーチ
			Graph<V, E> GNew = gList.get(0);
			SwapDiff sNew = sList.get(0);
			double fNew = function.f(GNew);
			for(int i = 1; i < gList.size(); i++) {
				Graph<V, E> G1 = gList.get(i);
				double f = function.f(G1);
				if(f > fNew) {
					GNew = G1;
					fNew = f;
					sNew = sList.get(i);
				}
			}
			// スコアが上回っていれば最適解を更新
			if(fNew >= fBest) {
				GBest = GNew;
				fBest = fNew;
				GCurrent = GBest;
				// 最適解をタブーリストに追加
				tabuList.add(sNew);
				if(tabuList.size() > mNTabuList) {
					tabuList.remove(0);
				}
				if(verbose) System.out.println((step+1)+","+fBest);
				step++;
			}
			trials++;
		}
		return GBest;
	}
	
	private class SwapDiff {
		private ArrayList<V> data;
		public SwapDiff(ArrayList<V> data) {
			this.data = data;
		}
		@Override
		public boolean equals(Object obj) {
			@SuppressWarnings("unchecked")
			SwapDiff sd = (SwapDiff) obj;
			V u1 = data.get(0), v1 = data.get(1), x1 = data.get(2), y1 = data.get(3);
			V u2 = sd.data.get(0), v2 = sd.data.get(1),
					x2 = sd.data.get(2), y2 = sd.data.get(3);
			// 同じ辺が選ばれたら同じとみなす
			return (u1.equals(u2) && v1.equals(v2) || u1.equals(v2) && v1.equals(u2) ||
					u1.equals(x2) && v1.equals(y2) || u1.equals(y2) && v1.equals(x2)) ||
					(x1.equals(u2) && y1.equals(v2) || x1.equals(v2) && y1.equals(u2) ||
					x1.equals(x2) && y1.equals(y2) || x1.equals(y2) && y1.equals(x2));
		}
		@Override
		public int hashCode() {
			return data.get(0).hashCode() + data.get(1).hashCode() +
					data.get(2).hashCode() + data.get(3).hashCode();
		}
		@Override
		public String toString() {
			return "(" + data.get(0) + ", " + data.get(1) + ", " +
						data.get(2) + ", " + data.get(3) + ")";
		}
	}
	
}
