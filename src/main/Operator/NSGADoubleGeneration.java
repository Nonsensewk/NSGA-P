package main.Operator;


import main.Solution.NSGADoubleSolution;
import main.Solution.NSGADoubleSolutionSet;
import main.Solution.solution;
import main.problem.*;

import java.util.HashMap;

public class NSGADoubleGeneration extends Selection {
    public NSGADoubleSolutionSet execute(NSGADoubleSolutionSet s, NSGADoubleSolutionSet s1, Multiproblem p){
          ZDT1problem p1=(ZDT1problem)p;
        //ZDT2problem p1=(ZDT2problem)p;
        //ZDT3problem p1=(ZDT3problem)p;
        //ZDT4problem p1=(ZDT4problem)p;
        //ZDT6problem p1=(ZDT6problem)p;

        NSGADoubleSolutionSet news=new NSGADoubleSolutionSet(s.size());

        //两代合并
        NSGADoubleSolutionSet totalS=new NSGADoubleSolutionSet(2*s.size());
        for (int i=0;i<2*s.size();i++){
            if (i<s.size()){
                totalS.add(s.array.get(i));
            }else{
                int n=i-s.size();
                totalS.add(s1.array.get(n));
            }
        }

        for (int i=0;i<totalS.size();i++){
            totalS.array.set(i, (NSGADoubleSolution) p1.evalute(totalS.array.get(i)));

          //输出合并两代的个体
          System.out.println(totalS.array.get(i).fitness[0]+" "+totalS.array.get(i).fitness[1]);

        }
        System.out.println("-----------------------------------------------");
        System.out.println("合并结束！");
        NSGAFastNonSort Nfns=new NSGAFastNonSort();
        totalS=Nfns.execute(totalS);

        CalDistance Nscd=new CalDistance();
        totalS=Nscd.execute(totalS);

        NSGASort NS=new NSGASort();
        totalS=NS.execute(totalS);//重新进行排序

        for (int i=0;i<s.size();i++){
            news.add(totalS.array.get(i));
        }
        return news;//返回合并后排完序的种群
    }
}
