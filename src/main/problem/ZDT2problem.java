package main.problem;


//import main.Algorithm.NSGA;
import main.Algorithm.NSGAP;
import main.Solution.NSGADoubleSolution;
import main.Solution.NSGAPDoubleSolution;

import java.util.ArrayList;

public class ZDT2problem extends Multiproblem{
    public ZDT2problem(){
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
        double dou =s.variables[0].getDoubleVariable();
        s.fitness[0]=dou;
        double g=1,sum=0;
        for (int i=1;i<s.variables.length;i++){
            g+=s.variables[i].getDoubleVariable();
        }
        sum=9/ (s.variables.length-1);
        g=sum*g;
        g=1.0+g;
        double h=1.0-(dou/g)*(dou/g);
        s.fitness[1]=h*g;
        return s;
    }

    public static void main(String[] args) {
        ZDT2problem p = new ZDT2problem();
        NSGAP test = new NSGAP(100,300,p);
        test.run();
    }
}
