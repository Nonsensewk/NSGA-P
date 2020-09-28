package main.problem;


//import main.Algorithm.NSGA;
import main.Algorithm.NSGAP;
import main.Solution.NSGADoubleSolution;
import main.Solution.NSGAPDoubleSolution;

import java.util.ArrayList;

public class ZDT3problem extends Multiproblem {
    //ZDT3
    public ZDT3problem(){
        super();
        this.numberOfObjectives=2;

        this.numberOfVariables=30;

        this.lowerlimit=new ArrayList<>();

        this.upperlimit=new ArrayList<>();

        for (int i=0;i<this.numberOfVariables;i++){
            lowerlimit.add(0.0);
            upperlimit.add(1.0);
        }
    }
    public NSGAPDoubleSolution evalute(NSGAPDoubleSolution s){
        //dou  x1   函数1
        double dou =s.variables[0].getDoubleVariable();

        s.fitness[0]=dou;
        double g=0,sum=0;

        //variables  决策变量个数
        for (int i=1;i<s.variables.length;i++){
            g+=s.variables[i].getDoubleVariable();
        }
        sum=9/ (s.variables.length-1);

        //            n=30
        g=1.0+sum*g;
        //g(x)

        double h = 1.0 - Math.sqrt(dou/g) - (dou/g) * Math.sin(10.0 * Math.PI * dou);
        // x2   函数2
        s.fitness[1]=h*g;

        return s;
    }
    public static void main(String[] args) {
        ZDT3problem p = new ZDT3problem();
        NSGAP test = new NSGAP(100,200,p);
        test.run();
    }


}
