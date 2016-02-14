
public class Main {
	public static void main(String[] args) {
		
		BANetworkBuilder BA = new BANetworkBuilder()
			.vertices(50).attachments(2);
		
		Algorithm<Graph<Integer, Void>, Graph<Integer, Void>> algorithm1 =
				new TabuSearch<Integer, Void>().maxSteps(300)
					.verbose().numGList(10).numTabuList(7);
		Algorithm<Graph<Integer, Void>, Graph<Integer, Void>> algorithm2 =
				new Greedy<Integer, Void>().verbose().maxSteps(300).numSwaps(10);
		R<Integer, Void> r = new R<Integer, Void>();
		
		//for(int i = 0; i < 100; i++) {
			Graph<Integer, Void> G = BA.build();
			Graph<Integer, Void> G1 = algorithm1.maximize(G, r);
			//Graph<Integer, Void> G2 = algorithm2.maximize(G, r);
		//}
	}
}
