package main.Operator;

import main.Algorithm.MultiAlgorithm;
import main.Solution.NSGAPDoubleSolution;
import main.Solution.NSGAPDoubleSolutionSet;
import main.problem.*;

public class NSGAPDoubleRandominit extends Randominit {
    //随机初始化种群
    public NSGAPDoubleSolutionSet execute(NSGAPDoubleSolutionSet solutionS, Multiproblem p) {
        while (!solutionS.isFull()) {

            NSGAPDoubleSolution NDS = new NSGAPDoubleSolution(p);
            //自变量   zdt3  30个
            for (int i = 0; i < NDS.variables.length; i++) {
                NDS.variables[i].setDoubleVariable(p.lowerlimit.get(i) + Math.random() *
                        (p.upperlimit.get(i) - p.lowerlimit.get(i)));
            }
            DTLZ1 z1 = (DTLZ1) p;

//            ZDT1problem z1 = (ZDT1problem) p;
//            ZDT2problem z1 = (ZDT2problem) p;
//            ZDT3problem z1 = (ZDT3problem) p;
//            ZDT4problem z1 = (ZDT4problem) p;
//            ZDT6problem z1 = (ZDT6problem) p;

            NDS = (NSGAPDoubleSolution) z1.evalute(NDS);

            solutionS.add(NDS);
        }
        return solutionS;
    }

}
