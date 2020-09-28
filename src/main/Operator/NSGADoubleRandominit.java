package main.Operator;

import main.Solution.NSGADoubleSolution;
import main.Solution.NSGAPDoubleSolutionSet;
import main.problem.*;

public class NSGADoubleRandominit extends Randominit{
    //继承了随机初始化的NSGA算法的随机初始化类
    public NSGAPDoubleSolutionSet execute(NSGAPDoubleSolutionSet solutionS, Multiproblem p ){

          ZDT1problem p1=(ZDT1problem)p;
//        ZDT2problem p1=(ZDT2problem)p;
//        ZDT3problem p1=(ZDT3problem)p;
//        ZDT4problem p1=(ZDT4problem)p;
//        ZDT6problem p1=(ZDT6problem)p;


        while (!solutionS.isFull()){
            NSGADoubleSolution NDS=new NSGADoubleSolution(p);
            for (int i=0;i<NDS.variables.length;i++){
                NDS.variables[i].setDoubleVariable(p.lowerlimit.get(i)+
                        Math.random()*(p.upperlimit.get(i)-p.lowerlimit.get(i)));
            }

            NDS= (NSGADoubleSolution) p1.evalute(NDS);
            solutionS.add(NDS);
        }
        return solutionS;
    }
}
