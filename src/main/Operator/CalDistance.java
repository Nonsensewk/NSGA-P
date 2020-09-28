package main.Operator;

import main.Solution.NSGADoubleSolution;
import main.Solution.NSGADoubleSolutionSet;
import main.Solution.solutionSet;

public class CalDistance extends operator{

    //NSGA2算法的拥挤度计算算法类


    /**
     * nsga2使用   新算法不使用
     */
    @Override
    public void execute() {
    }
    @Override
    public solutionSet execute(solutionSet s) {
        return null;
    }



    public NSGADoubleSolutionSet execute(NSGADoubleSolutionSet s){
        //多目标遗传算法距离值计算算子

        // 冒泡排序
        for (int i=0;i<s.array.size();i++){
            for (int j=0;j<s.array.size()-i-1;i++){
                if (s.array.get(j).fitness[0]>s.array.get(j+1).fitness[0]){
                    NSGADoubleSolution temp=s.array.get(j);
                    s.array.set(j,s.array.get(j+1));
                    s.array.set(j+1,temp);
                }
            }
        }
        //将第一位与最后一位设为无限大
        s.array.get(s.array.size()-1).distance=s.array.get(0).distance=Integer.MAX_VALUE;
        for(int i=1;i<s.array.size()-1;i++){
            s.array.get(i).distance=0;
            //计算函数1拥挤度的部分
            s.array.get(i).distance=(s.array.get(i+1).fitness[0]-s.array.get(i-1).fitness[0])/
                    (s.array.get(s.size()-1).fitness[0]-s.array.get(0).fitness[0]);

            //前后距离除以最大
        }



        for (int i=0;i<s.array.size();i++){
            for (int j=0;j<s.array.size()-i-1;i++){
                if (s.array.get(j).fitness[1]>s.array.get(j+1).fitness[1]){
                    NSGADoubleSolution temp=s.array.get(j);
                    s.array.set(j,s.array.get(j+1));
                    s.array.set(j+1,temp);
                }
            }
        }
        s.array.get(s.array.size()-1).distance=s.array.get(0).distance=Integer.MAX_VALUE;
        for(int i=1;i<s.array.size()-1;i++){
            s.array.get(i).distance=0;
            //计算函数2拥挤度的部分
            s.array.get(i).distance=(s.array.get(i+1).fitness[1]-s.array.get(i-1).fitness[1])/
                    (s.array.get(s.size()-1).fitness[1]-s.array.get(0).fitness[1]);
        }

        return s;
    }
}
