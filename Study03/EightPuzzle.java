import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class EightPuzzle {
	
	private final Queue<Node> nodes = new PriorityQueue<>();
	private final Map<String, Node> visited = new HashMap<>();
	
	public static void main(String[] args) {
		try {
			new EightPuzzle(args).solve();
		} catch (IllegalArgumentException e) {
		System.out.println(e.getMessage());
		}
	}
	
	private EightPuzzle(String[] args) {
		validateInput(args);
		nodes.add(new Node(args[0], args[1]));
	}
	
	private void solve() {
		Node node;
		int counter = 0;
		while ((node = nodes.poll()) != null) {
			if (node.getHeuristicAmount() == 0) {
				System.out.println(String.format("解が見つかりました。(展開回数: %d)", counter));
				System.out.println();
				System.out.println(node.toCompleteString());
				break;
			}
			++counter;
			for (Node opened : node.openChildNodes()) {
				if (visited.containsKey(opened.getNodeValue())) {
					if (opened.getPriority() < visited.get(opened.getNodeValue()).getPriority()) {
						visited.remove(opened.getNodeValue());
						visited.put(opened.getNodeValue(), opened);
						nodes.add(opened);
					}
				} else {
					visited.put(opened.getNodeValue(), opened);
					nodes.add(opened);
				}
			}
		}
		if (node == null) {
			System.out.println("このパズルは解けません。");
		}
	}
	
	private void validateInput(String[] args) {
		if (args.length != 2) {
		throw new IllegalArgumentException("引数の数は2個にしてください。");
		}
		char[] srcChars, dstChars, validChars;
		srcChars = args[0].toCharArray();
		dstChars = args[1].toCharArray();
		validChars = "12345678x".toCharArray();
		Arrays.sort(srcChars);
		Arrays.sort(dstChars);
		Arrays.sort(validChars);
		if (
			!Arrays.equals(srcChars, dstChars) ||
			!Arrays.equals(srcChars, validChars)
		) {
			throw new IllegalArgumentException("各引数は「12345678x」の文字を漏れなく1回ずつ使用してください。");
		}
	}
	
}
