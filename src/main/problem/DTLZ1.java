package main.problem;


import main.Algorithm.NSGAP;
import main.Solution.DoubleVariable;
import main.Solution.NSGAPDoubleSolution;


import java.util.ArrayList;

public class DTLZ1 extends Multiproblem{
    //DTLZ1测试函数，这是一个三维函数
    public DTLZ1(){
        super();

        //变量个数设为n=m+4

        this.numberOfVariables=12;//变量维度

        this.lowerlimit=new ArrayList<>();
        this.numberOfObjectives=3;//目标维度

        this.upperlimit=new ArrayList<>();
        //this.numberOfConstraints = 1;//定义域

        for (int i=0;i<this.numberOfVariables;i++){
            lowerlimit.add(0.0);
            upperlimit.add(1.0);
        }
    }

    public NSGAPDoubleSolution evalute(NSGAPDoubleSolution solution){

        int numberOfVariables = solution.variables.length;
        int numberOfObjectives = solution.fitness.length ;

        double[] f = new double[numberOfObjectives];

        DoubleVariable[] x = new DoubleVariable[numberOfVariables] ;

        //  k = 决策变量个数-目标个数

        int k =  numberOfVariables  - numberOfObjectives ;   //-1

        for (int i = 0; i < numberOfVariables; i++) {
            x[i] = solution.variables[i] ;
        }

        double g = 0.0;
        for (int i = numberOfVariables - k; i < numberOfVariables; i++) {
            g = g + (x[i].getDoubleVariable() - 0.5) * (x[i].getDoubleVariable() - 0.5) -
                    Math.cos(20.0 * Math.PI * (x[i].getDoubleVariable() - 0.5));
        }
        g = 100 * (k + g);

        for (int i = 0; i < numberOfObjectives; i++) {
            f[i] = (1.0 + g) * 0.5;
        }

        for (int i = 0; i < numberOfObjectives; i++) {
            for (int j = 0; j < numberOfObjectives - (i + 1); j++) {
                f[i] *= x[j].getDoubleVariable();
            }
            if (i != 0) {
                int aux = numberOfObjectives - (i + 1);
                f[i] *= 1 - x[aux].getDoubleVariable();
            }
        }



        //限定第一维的目标值

        for (int i = 0; i < numberOfObjectives; i++) {
           // if(solution.fitness[i] >=0.4 && solution.fitness[i] <=0.8) {
                solution.fitness[i] = f[i];
            //}
        }


        return solution;
    }

    public static void main(String[] args) {
        DTLZ1 dtlz1 = new DTLZ1();
        NSGAP test = new NSGAP(500,300,dtlz1);
        test.run();
    }
}
