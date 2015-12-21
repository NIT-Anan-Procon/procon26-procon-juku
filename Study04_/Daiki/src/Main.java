import java.util.Scanner;

class Main {
    //解答する問題のデータ
  static Data data;

    //再帰関数　漸化式の形で表す
  public static boolean recursion(int i, int rest) {
      //基底
    if(i == data.item_prices.length) {
      return rest == 0;
    }

      //一般項
    else {
      /*	こっちを使うと全て使い切れるかのみ分かる
      return recursion(i+1, rest) ||
             recursion(i+1, rest-data.item_prices[i]);
      */

        //i番目の商品を買わなかった場合
      if(recursion(i+1, rest)) {
        return true;
      }
        //i番目の商品を買った場合
      if(recursion(i+1, rest-data.item_prices[i])) {
        System.out.print(data.item_prices[i]+" ");
        return true;
      }

        //全て探索しても答えがなかった場合
      return false;
    }
  }
		//mainメソッド
  public static void main(String[]args) {
    data = new Data(); 
    System.out.println(data.toString());
    System.out.printf("%s%n",recursion(0,data.pocket_money)?"\nYes":"\nNo");
  }
}

class Data {
		//商品の値段を持つ配列
  public final int[] item_prices;
		//使える最大のお金
  public final int pocket_money;

		//コンストラクタにて標準入力から与えられたデータを取得
  public Data() {
    Scanner stdIn = new Scanner(System.in);

    int[] a = new int[stdIn.nextInt()];
    
    for(int i=0;i<a.length;i++) {
      a[i] = stdIn.nextInt();
    }
    item_prices = a.clone();

    pocket_money = stdIn.nextInt();
  }

  @Override
  public String toString() {
    String t = "";

    t += "number=" + item_prices.length + "\n";

    t += "prices=";
    for(int i : item_prices)
      t += i + " ";
    t += "\n";

    t += "pocket_money=" + pocket_money;

    return t;
  }
}
