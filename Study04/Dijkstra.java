import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Dijkstra {

	public static Node[] nodes = null;
	public static PriorityQueue p_queue = null;
	public static int start;
	public static int goal;

	public static void main(String[] args) {
		// 入力の読み込み
		start = Integer.parseInt(args[0]);
		goal = Integer.parseInt(args[1]);
		nodes = getNodeList();
		p_queue = new PriorityQueue();

		p_queue.enqueue(nodes[start]);
		// 各ノードに注目しながら隣接ノードのコストを更新していく
		while(!p_queue.isEmpty()) {
			Node focused_node = p_queue.dequeue();
			if(focused_node.isGoal()) break;
			updateCost(focused_node);
		}
		System.out.println(getRoute(nodes[goal]));
	}

	private static void updateCost(Node node) {
		// 注目済みでないことをチェック
		if(!node.is_used) {
			// 各エッジについて順次に調べる
			for(int key : node.edge_cost.keySet()) {
				// エッジの接続先ノードが注目済みであるか
				if(!nodes[key].is_used) {
					int tmp = node.node_cost + node.edge_cost.get(key);
					if(nodes[key].node_cost > tmp) {
						nodes[key].node_cost = tmp;
						nodes[key].prev_node = node;
					}
					p_queue.enqueue(nodes[key]);
				}
			}
			node.is_used = true;
		}
		System.out.println(p_queue.toString());
	}

	// 出発ノードから目的ノードまでのルートを文字列として得る
	private static String getRoute(Node node) {
		if(node.isStart()) return node.id + "";
		else {
			if(node.prev_node != null) {
				if(node.isGoal()) return "最小コスト : " + node.node_cost + "\n" + getRoute(node.prev_node) + " - "+ node.id;
				else return getRoute(node.prev_node) + " - " + node.id;
			} else { 
				return "目的地に到達できません";
			}
		}
	}

	// ファイルからデータを読み取る
	private static Node[] getNodeList() {
		Scanner scanner = new Scanner(System.in);
		LinkedList<Node> tmp_nodes = new LinkedList<Node>();
		int i = 0, count_edge = 0;
		while(scanner.hasNextLine()) {
			String[] edges = scanner.nextLine().split(",");
			HashMap<Integer, Integer> tmp_hash = new HashMap<Integer, Integer>();
			for(String edge : edges) {
				String[] pair = edge.split(":");
				tmp_hash.put(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
				count_edge++;
			}
			tmp_nodes.add(new Node(i, tmp_hash, 2));
			i++;
		}
    System.out.println("ノード数 : " + tmp_nodes.size());
    System.out.println("エッジ数 : " + count_edge);
    tmp_nodes.get(start).kind = 0;
    tmp_nodes.get(start).node_cost = 0;
    tmp_nodes.get(goal).kind = 1;
		return tmp_nodes.toArray(new Node[tmp_nodes.size()]);
	}

}

// ノードを表現するクラス
class Node {

	int id, node_cost;
	// <エッジの接続先, エッジのコスト>
	HashMap<Integer, Integer> edge_cost;
	// 0:出発ノード, 1:ゴールノード, 2:その他, -1:空(優先度付きキューで使用)
	int kind;
	boolean is_used;
	Node prev_node;

	public Node(int id, HashMap<Integer, Integer> edge_cost, int kind) {
		this.id = id;
		this.edge_cost = edge_cost;
		this.node_cost = Integer.MAX_VALUE;
		this.kind = kind;
		this.is_used = false;
		this.prev_node = null;
	}
	public boolean isStart() { return this.kind == 0; }
	public boolean isGoal() { return this.kind == 1; }
	public boolean isNone() { return this.kind == -1; }

}

// 優先度付きキュー
class PriorityQueue {

	// リスト構造でヒープを再現
	private LinkedList<Node> heap = new LinkedList<Node>();

	public static final Node NONE = new Node(-1, null, -1);

	public void enqueue(Node node) {
		if(!heap.contains(NONE)) {
			heap.add(node);
			upheap(node, heap.size()-1);
		} else {
			int index = heap.indexOf(NONE);
			upheap(node, index);
		}
	}
	private void upheap(Node node, int i) {
		if(i == 0) heap.set(i, node);
		else {
			Node parent = heap.get((i+1)/2-1);
			if(node.node_cost >= parent.node_cost) heap.set(i, node);
			else {
				heap.set(i, parent);
				upheap(node, (i+1)/2-1);
			}
		}
	}

	public Node dequeue() {
		Node head = heap.get(0);
		heap.set(0, NONE);
		downheap(0);
		return head;
	}
	private void downheap(int i) {
		int j  = (i+1)*2-1;
		if(j < heap.size()) {
			if(j+1 < heap.size()) {
				if(heap.get(j).node_cost > heap.get(j+1).node_cost)  j++;
			}
			if(heap.get(i).node_cost > heap.get(j).node_cost) {
				Node tmp = heap.get(i);
				heap.set(i, heap.get(j));
				heap.set(j, tmp);
				downheap(j);
			}
		}
	}

	public boolean isEmpty() { 
		for(Node node : heap) { if(!node.isNone()) return false; }
		return true;
	}
	@Override
	public String toString() {
		String str = "";
		for(Node node : heap) {
			str += !node.isNone() ? (node.id+":"+node.node_cost+", ") : "none, ";
		}
		return str;
	}
}
