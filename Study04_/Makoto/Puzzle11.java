import java.util.*;
import java.io.*;
class Puzzle11 {
	public static void main(String[] args) {
		PriorityQueue<Node> open = new PriorityQueue<>();
		boolean answer = false;
		String goalValue = "abcdefghijkl";
		String startValue = "";
		String fileName = "";
		Scanner sc = new Scanner(System.in);
		System.out.print("ファイル名：");
		try {
			fileName = sc.next();
		} catch(Exception e) {}
		try {
			sc = new Scanner(new File(fileName));
		} catch(Exception e) {}
		while(sc.hasNext()) startValue += sc.next();
		Node first = new Node(startValue, goalValue);
		System.out.println("ステージ初期状態");
		System.out.println(first.toString());
		open.add(first);
		Node node;
		while(open.size() > 0) {
			node = open.poll();
			if(node.hValue == 0) {
				System.out.println(node.resultString());
				answer = true;
				break;
			}
			for(Node child : node.openChild()) {
				open.add(child);
			}
		}
		if(!answer) System.out.println("解なし");
	}
}

class Node implements Comparable<Node> {
	int gValue = 0;
	int hValue = 0;
	String stageValue = "";
	String goalValue = "";
	Node parent;
	public Node(String stageValue, String goalValue) {
		this.stageValue = stageValue;
		this.goalValue = goalValue;
		this.hValue = calculateH();
	}
	@Override
	public int compareTo(Node node) {
		return this.getCost() - node.getCost();
	}
	public String resultString() {
		int count = 0;
		String str = "";
		Node node = this;
		List<Node> list = new ArrayList<>();
		do {
			list.add(node);
		} while((node=node.parent) != null);
		Collections.reverse(list);
		for(Node n : list) {
			str += count + "回目\n";
			count++;
			str += n.toString();
		}
		return str;
	}
	public List<Node> openChild() {
		List<Node> child = new ArrayList<>(4);
		int from = this.stageValue.indexOf("l");
		int[] d = {from-4, from-1, from+1, from+4};
		for(int to : d) {
			if(
				to >= 0 && to < 12 &&
				!(to==3 && from==4) &&
				!(to==4 && from==3) &&
				!(to==7 && from==8) &&
				!(to==8 && from==7) 
			) {
				Node n = new Node(swap(from, to, this.stageValue), goalValue);
				n.parent = this;
				if(this.parent != null) {
					if(n.stageValue == this.parent.stageValue) continue;
				} else {
					if(n.stageValue == this.stageValue) continue;
				}
				n.gValue = (this.parent!=null ? this.gValue : 0) + 2;
				child.add(n);
			}
		}
		return child;
	}
	public String swap(int from, int to, String s) {
		char[] chars = s.toCharArray();
		char tmp = chars[from];
		chars[from] = chars[to];
		chars[to] = tmp;
		return String.valueOf(chars);
	}
	public String toString() {
		String str = "---------------\n|";
		int dt = 0;
		char[] chars = stageValue.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			dt = convStr(chars[i]+"");
			if(dt != 12) str += dt+"";
			else str += "❏ ";
			if(dt < 10) str += " ";
			if(i%4 == 3) str += "|\n---------------\n";
			else str += " ";
			if(!(i==chars.length-1)) str += "|";
		}
		return str;
	}
	public int getCost() {
		return gValue + hValue;
	}
	public int calculateH() {
		int h = 0;
		for(int i = 0; i < goalValue.length(); i++) {
			int j = goalValue.indexOf(stageValue.charAt(i)+"");
			h += Math.abs(i-j)/4 + Math.abs(i-j)%4;
		}
		return h;
	}
	public static int convStr(String s) {
		int i = 0;
		switch(s) {
			case "a": i = 1; break;
			case "b": i = 2; break; 
			case "c": i = 3; break; 
			case "d": i = 4; break; 
			case "e": i = 5; break; 
			case "f": i = 6; break; 
			case "g": i = 7; break; 
			case "h": i = 8; break; 
			case "i": i = 9; break; 
			case "j": i = 10;break; 
			case "k": i = 11;break;
			case "l": i = 12;break;
		}
		if(i == 0) {
			System.out.println("error:ステージおかしい");
			System.exit(1);
		}
		return i;
	}
}
