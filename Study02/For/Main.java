import java.util.Scanner;

class Main {
  static Data d;

  public static boolean solve() {
    d = new Data(); 

    for(int i=0;i<2;i++) {
      if(sumOf(i) == d.k) return true;
      for(int j=0;j<2;j++) {
        if(sumOf(i,j) == d.k) return true;
        for(int k=0;k<2;k++) {
          if(sumOf(i,j,k) == d.k) return true;
          for(int l=0;l<2;l++) {
            if(sumOf(i,j,k,l) == d.k) return true;
          }
        }
      }
    }

    return false;
  }

  public static int sumOf(int i) {
    int sum = 0;
    sum += d.a[0] * i;
    return sum;
  }

  public static int sumOf(int i, int j) {
    int sum = 0;
    sum += d.a[0] * i;
    sum += d.a[1] * j;
    return sum;
  }

  public static int sumOf(int i, int j, int k) {
    int sum = 0;
    sum += d.a[0] * i;
    sum += d.a[1] * j;
    sum += d.a[2] * k;
    return sum;
  }

  public static int sumOf(int i, int j, int k, int l) {
    int sum = 0;
    sum += d.a[0] * i;
    sum += d.a[1] * j;
    sum += d.a[2] * k;
    sum += d.a[3] * l;
    return sum;
  }

  public static void main(String[]args) {
    System.out.println(solve());
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
