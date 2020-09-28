package main.problem;

//import main.Algorithm.NSGA;
import main.Algorithm.NSGAP;
import main.Solution.NSGADoubleSolution;
import main.Solution.NSGAPDoubleSolution;

import java.util.ArrayList;

public class ZDT4problem extends Multiproblem{
    //多目标问题zdt4
    public ZDT4problem(){
        super();
        this.numberOfObjectives=2;
        this.numberOfVariables=10;
        this.lowerlimit=new ArrayList<>();
        lowerlimit.add(0.0);
        this.upperlimit=new ArrayList<>();
        upperlimit.add(1.0);
        for (int i=1;i<this.numberOfVariables;i++){
            lowerlimit.add(-5.0);
            upperlimit.add(5.0);
        }
    }
    public NSGAPDoubleSolution evalute(NSGAPDoubleSolution s){
        double dou =s.variables[0].getDoubleVariable();
        s.fitness[0]=dou;
        double g=0,sum=0;
        for (int i=1;i<s.variables.length;i++){
            sum += Math.pow(dou, 2.0) -10.0 * Math.cos(4.0 * Math.PI * dou);
        }
        g=1.0 + 10.0 * (s.variables.length - 1)+sum;
        double h = 1.0 - Math.sqrt(dou/g);
        s.fitness[1]=h*g;
        return s;
    }
    public static void main(String[] args) {

        ZDT4problem p = new ZDT4problem();
        NSGAP test = new NSGAP(100,500,p);
        test.run();
    }

}


