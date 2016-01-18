class Simplex{
    double[][] simplex;
    
    public Simplex(){
	//init();
	ex02();
    }

    void init(){
	double[] p = {0,4,1,1,0,0,72};  //     4x +  y + p         = 72
	double[] q = {0,1,1,0,1,0,24};  //      x +  y     + q     = 24
	double[] r = {0,1,3,0,0,1,48};  //      x + 3y         + r = 48
	double[] z = {1,-3,-2,0,0,0,0}; // z - 3x - 2y             =  0

	simplex = new double[4][];
	simplex[0] = p;
	simplex[1] = q;
	simplex[2] = r;
	simplex[3] = z;
    }

    void ex01(){
	double[] p = {0,1,2,1,0,0,14};  //      x + 2y + p         = 14
	double[] q = {0,1,1,0,1,0,8};   //      x +  y     + q     =  8
	double[] r = {0,3,1,0,0,1,18};  //     3x +  y         + r = 18
	double[] z = {1,-2,-1,0,0,0,0}; // z - 2x -  y             =  0

	simplex = new double[4][];
	simplex[0] = p;
	simplex[1] = q;
	simplex[2] = r;
	simplex[3] = z;
    }

    void ex02(){
	double[] p = {0,5,0,3,1,0,0,8};    //     5a      + 3c + p         = 14
	double[] q = {0,0,0,2,0,1,0,2};    //             + 2c     + q     =  2
	double[] r = {0,0,4,1,0,0,1,9};    //        + 4b +  c         + r =  9
	double[] z = {1,-2,-2,-3,0,0,0,0}; // z - 2a - 2b - 3c             =  0

	simplex = new double[4][];
	simplex[0] = p;
	simplex[1] = q;
	simplex[2] = r;
	simplex[3] = z;
    }

    void printTable(){
	for(int j = 0; j < simplex.length; j++){
	    for(int i = 0; i < simplex[j].length; i++){
		System.out.printf(" %6.2f ", simplex[j][i]);
	    }
	    System.out.println();
	}
	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    int getMinMinusCoefficientColumn(){
	int j = simplex.length-1; //zの行
	int minColumn = -1;
	double min = 0;
	for(int i = 0; i < simplex[j].length; i++){
	    if(simplex[j][i] < min){
		min = simplex[j][i];
		minColumn = i;
	    }
	}
	return minColumn; //全て正か0なら-1を返す
    }

    int getMinimumLine(int column2){
	int column1 = simplex[0].length-1;
	int minLine = 0;
	double min = simplex[0][column1]/simplex[0][column2];
	for(int i = 1; i < simplex.length-1; i++){
	    double w = simplex[i][column1]/simplex[i][column2];
	    if(w < min){
		min = w;
		minLine = i;
	    }
	}
	return minLine;
    }

    void divideByPivot(int line, int column){
	double pivot = simplex[line][column];
	for(int i = 0; i < simplex[line].length; i++){
	    simplex[line][i] /= pivot;
	}
    }

    void updateTable(int line, int column){
	for(int j = 0; j < simplex.length; j++){
	    if(j == line) continue;
	    
	    double a = simplex[j][column];
	    for(int i = 0; i < simplex[j].length; i++){
		simplex[j][i] -= a*simplex[line][i];
	    }
	}
	printTable();
    }


    void answer(){
	double answer = simplex[simplex.length-1][simplex[0].length-1];
	System.out.println("最大値は" + answer);
    }

    void execute(){
	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n始めのシンプレックス表");
	printTable();
	while(true){
	    int pivotColumn;
	    if((pivotColumn = getMinMinusCoefficientColumn()) >= 0){
		System.out.println("最下行に負の数がある");
		int pivotLine = getMinimumLine(pivotColumn);
		System.out.printf("ピボット変数は%d行%d列の%.1f\n", pivotLine+1, pivotColumn+1, simplex[pivotLine][pivotColumn]);
		printTable();
		System.out.printf("%d行目を%.1fで割る\n", pivotLine+1, simplex[pivotLine][pivotColumn]);
		divideByPivot(pivotLine, pivotColumn);
		printTable();
		System.out.printf("%d行目以外の%d列目を0にする\n", pivotLine+1, pivotColumn+1);
		updateTable(pivotLine, pivotColumn);
	    }else{
		System.out.println("最下行に負の数がないため終了");
		answer();
		return;
	    }
	}
    }

    public static void main(String[] args){
	Simplex s = new Simplex();
	s.execute();
    }
}
