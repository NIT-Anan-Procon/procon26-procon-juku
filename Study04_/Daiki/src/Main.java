import java.util.Scanner;

class Main {
    //�𓚂�����̃f�[�^
  static Data data;

    //�ċA�֐��@�Q�����̌`�ŕ\��
  public static boolean recursion(int i, int rest) {
      //���
    if(i == data.item_prices.length) {
      return rest == 0;
    }

      //��ʍ�
    else {
      /*	���������g���ƑS�Ďg���؂�邩�̂ݕ�����
      return recursion(i+1, rest) ||
             recursion(i+1, rest-data.item_prices[i]);
      */

        //i�Ԗڂ̏��i�𔃂�Ȃ������ꍇ
      if(recursion(i+1, rest)) {
        return true;
      }
        //i�Ԗڂ̏��i�𔃂����ꍇ
      if(recursion(i+1, rest-data.item_prices[i])) {
        System.out.print(data.item_prices[i]+" ");
        return true;
      }

        //�S�ĒT�����Ă��������Ȃ������ꍇ
      return false;
    }
  }
		//main���\�b�h
  public static void main(String[]args) {
    data = new Data(); 
    System.out.println(data.toString());
    System.out.printf("%s%n",recursion(0,data.pocket_money)?"\nYes":"\nNo");
  }
}

class Data {
		//���i�̒l�i�����z��
  public final int[] item_prices;
		//�g����ő�̂���
  public final int pocket_money;

		//�R���X�g���N�^�ɂĕW�����͂���^����ꂽ�f�[�^���擾
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
