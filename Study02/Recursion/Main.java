import java.util.Scanner;

class Main {
  static Data d;

  public static void solve() {
    d = new Data(); 
    //System.out.println(d.toString());

    System.out.println(dfs(0,0)?"Yes":"No");
  }

  public static boolean dfs(int i, int sum) {
    if(i == d.n) return sum == d.k;
    if(dfs(i+1, sum)) return true;
    if(dfs(i+1, sum+d.a[i])) return true;

    return false;
  }

  public static void main(String[]args) {
    solve();
  }
}

class Data {
  int n;
  int[] a;
  int k;

  public Data() {
    Scanner stdIn = new Scanner(System.in);

    n = stdIn.nextInt();
    a = new int[n];
    
    for(int i=0;i<n;i++) {
      a[i] = stdIn.nextInt();
    }

    k = stdIn.nextInt();
  }

  @Override
  public String toString() {
    String t = "";

    t += "n=" + n + "\n";

    t += "a=";
    for(int i : a)
      t += i + " ";
    t += "\n";

    t += "k=" + k;

    return t;
  }
}
