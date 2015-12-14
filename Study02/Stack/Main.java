import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

class Main {
  static Data d;

  public static boolean solve() {
    d = new Data(); 
    System.out.println(d.toString());

    Stack<Node> st = new Stack<Node>();
    
    Node first = new Node(d);
    st.push(first);

    while(st.size() > 0) {
      //pop
      Node cur = st.pop();
      //process for poped node
      if(cur.rest < 0) continue;
      if(cur.rest == 0) return true;

      //push children's node
      if(cur.CURRENT_INDEX+1 < d.a.length) {
        st.push(new Node(cur,true));
        st.push(new Node(cur,false));
      }
    }
    return false;
  }

  public static void main(String[]args) {
    System.out.println(solve()?"Yes":"No");
  }
}

class Node {
  Data d;
  int CURRENT_INDEX;
  int rest;

  public Node(Data d) {
    this.d = d;
    CURRENT_INDEX = -1;
    rest = d.k; 
  }

  public Node(Node parent, boolean buy) {
    this.d = parent.d;
    CURRENT_INDEX = parent.CURRENT_INDEX+1;
    rest = buy?parent.rest - d.a[CURRENT_INDEX] : parent.rest;
  }
}

class Data {
  Scanner stdIn = new Scanner(System.in);
  int n;
  int[] a;
  int k;

  public Data() {
    if(stdIn.hasNextInt())
      n = stdIn.nextInt();

    a = new int[n];
    
    for(int i=0;i<n;i++) {
      if(stdIn.hasNextInt())
        a[i] = stdIn.nextInt();
    }
    
    if(stdIn.hasNextInt())
      k = stdIn.nextInt();
  }

  @Override
  public String toString() {
    String t = "";

    t += "n=" + n + "\n";

    t += "a=";
    for(int i:a)
      t += "" + i + " ";
    t += "\n";

    t += "k=" + k;

    return t;
  }
}
