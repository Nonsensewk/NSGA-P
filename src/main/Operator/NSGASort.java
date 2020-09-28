package main.Operator;

import main.Solution.NSGADoubleSolution;
import main.Solution.NSGADoubleSolutionSet;
import main.Solution.solution;


public class NSGASort extends Sort{

    public NSGADoubleSolutionSet execute(NSGADoubleSolutionSet s){
        for(int i=0;i<s.size();i++){
            for (int j=0;j<s.array.size()-1-i;j++){
                if (s.array.get(j).rank>s.array.get(j+1).rank){
                    //帕累托等级大往后排

                    NSGADoubleSolution ss=solution.clone(s.array.get(j));
                    s.array.set(j,s.array.get(j+1));
                    s.array.set((j+1),ss);
                }else if (s.array.get(j).rank==s.array.get(j+1).rank){
                    //帕累托等级相等的情况

                    NSGADoubleSolution ss=solution.clone(s.array.get(j));
                    s.array.set(j,s.array.get(j+1));
                    s.array.set((j+1),ss);
                }
            }
        }
        return s;
    }

}
