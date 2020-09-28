package main.problem;


//import main.Algorithm.NSGA;
import main.Algorithm.NSGAP;
import main.Solution.NSGADoubleSolution;
import main.Solution.NSGAPDoubleSolution;

import java.util.ArrayList;

public class ZDT6problem extends Multiproblem{
    public ZDT6problem() {
        super();
        this.numberOfObjectives=2;
        this.numberOfVariables=10;
        this.lowerlimit=new ArrayList<>();
        this.upperlimit=new ArrayList<>();
        for (int i=0;i<this.numberOfVariables;i++){
            lowerlimit.add(0.0);
            upperlimit.add(1.0);
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
        ZDT6problem p = new ZDT6problem();
        NSGAP test = new NSGAP(100,300,p);
        test.run();
    }
}
