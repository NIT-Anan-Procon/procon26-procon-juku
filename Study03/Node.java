import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node implements Comparable<Node> {
	
	private final String nodeValue;
	private final String goalValue;
	private final int heuristicAmount;
	private int passedAmount = 0;
	private Node parent;
	
	public Node(String nodeValue, String goalValue) {
		this.nodeValue = nodeValue;
		this.goalValue = goalValue;
		this.heuristicAmount = calculateHeuristicAmount();
	}
	
	@Override
	public int compareTo(Node node) {
		return this.getPriority() - node.getPriority();
	}
	
	@Override
	public String toString() {
		String table = "-------\n";
		for (int i = 0; i < 3; ++i) {
			table += "|";
			for (int j = i * 3; j < i * 3 + 3; ++j) {
				table += nodeValue.charAt(j) + "|";
			}
			table += "\n-------\n";
		}
		return table;
	}
	
	public String toCompleteString() {
		List<String> states = new ArrayList<>();
		Node node = this;
		do {
			states.add(node.toString());
		} while ((node = node.parent) != null);
		Collections.reverse(states);
		String str = "0.\n" + states.get(0);
		for (int i = 1; i < states.size(); ++i) {
			str += "\n" + i + ".\n" + states.get(i);
		}
		return str;
	}
	
	public int getHeuristicAmount() {
		return heuristicAmount;
	}
	
	public int getPriority() {
	return passedAmount + heuristicAmount;
	}
	
	public String getNodeValue() {
		return nodeValue;
	}
	
	public List<Node> openChildNodes() {
		int from = nodeValue.indexOf("x");
		int[] offsets = {from - 3, from + 3, from - 1, from + 1};
		List<Node> childNodes = new ArrayList<>();
		for (int to : offsets) {
			if (
				to >= 0 &&
				to <= 8 &&
				!(from == 2 && to == 3) &&
				!(from == 3 && to == 2) &&
				!(from == 5 && to == 6) &&
				!(from == 6 && to == 5)
			) {
			Node node = new Node(swapOffset(from, to), goalValue);
			node.parent = this;
			node.passedAmount = (parent != null ? parent.passedAmount : 0) + 2;
			childNodes.add(node);
			}
		}
		return childNodes;
	}
	
	private String swapOffset(int from, int to) {
		char[] chars = nodeValue.toCharArray();
		char tmp = chars[from];
		chars[from] = chars[to];
		chars[to] = tmp;
		return String.valueOf(chars);
	}
	
	private int calculateHeuristicAmount() {
		int sum = 0;
		for (int i = 0; i < 9; ++i) {
			int j = goalValue.indexOf(String.valueOf(nodeValue.charAt(i)));
			sum += (int)(Math.abs(i - j) / 3) + Math.abs(i - j) % 3;
		}
		return sum;
	}
}
