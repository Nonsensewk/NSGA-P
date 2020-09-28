package main.Operator;


import main.Solution.*;

import java.util.ArrayList;

public class NSGAFastNonSort extends Sort{
    //NSGA的快速非支配排序算法
    @Override
    public void execute() {

    }
    @Override
    public solutionSet execute(solutionSet s) {
        return null;
    }




    public NSGAPDoubleSolutionSet execute(NSGAPDoubleSolutionSet s){

        ArrayList<NSGAPDoubleSolutionSet> F= new ArrayList<>();
        NSGAPDoubleSolutionSet fx=new NSGAPDoubleSolutionSet(s.size);

        for (int i=0;i<s.size;i++){
            s.array.get(i).nq=0;
            s.array.get(i).sp=new ArrayList<>();
            for (int j=0;j<s.size;j++){
                int flag1=0;
                int flag2=0;
                if (j!=i){
                    //快速非支配排序
                    for (int m=0;m<s.array.get(j).fitness.length;m++){
                        if (s.array.get(i).fitness[m]<s.array.get(j).fitness[m]){
                            flag1++;
                        }else if(s.array.get(i).fitness[m]>s.array.get(j).fitness[m]){
                            flag2++;
                        }
                    }
                    if (flag1==s.array.get(i).fitness.length){
                        s.array.get(i).sp.add(s.array.get(j));
                    }else if(flag2==s.array.get(i).fitness.length){
                        s.array.get(i).nq++;
                    }
                }
            }
            if(s.array.get(i).nq==0){
                s.array.get(i).rank=1;
                fx.add(s.array.get(i));
            }
        }
        F.add(fx);
        int i=0;
        while (F.get(i).array.size()!=0){
            fx=new NSGAPDoubleSolutionSet(s.array.size());
            for (int j=0;j<F.get(i).size();j++){
                //对F进行迭代
                for (int m=0;m<F.get(i).array.get(j).sp.size();m++){
                    //对F中的个体支配个体集进行迭代
                    F.get(i).array.get(j).sp.get(m).nq--;
                    if (F.get(i).array.get(j).sp.get(m).nq==0){
                        F.get(i).array.get(j).sp.get(m).rank=i+2;
                        fx.add(F.get(i).array.get(j).sp.get(m));

                    }
                }
            }
            F.add(fx);
            i++;
        }
        System.out.println("快速非支配排序结束！--->");
        return s;
    }














    /**
     * o(n)时间复杂度的解法
     * nq   被支配的个数
     * sp   所支配其他个体的集合
     */
    public NSGADoubleSolutionSet execute(NSGADoubleSolutionSet s){
        ArrayList<NSGADoubleSolutionSet> F=new ArrayList<NSGADoubleSolutionSet>();   //存放解的数组集合
        NSGADoubleSolutionSet fx=new NSGADoubleSolutionSet(s.size);  //解集
        for (int i=0;i<s.size;i++){
            s.array.get(i).nq=0;//初始设置：被支配个数为0
            for (int j=0;j<s.size;j++){
                if (j!=i){
                    if (s.array.get(i).fitness[0]<=s.array.get(j).fitness[0] && s.array.get(i).fitness[1]
                            <=s.array.get(j).fitness[1]){//两个目标函数函数均小于
                        //支配关系 i支配j
                        if (s.array.get(i).fitness[0]==s.array.get(j).fitness[0] && s.array.get(i).fitness[1]
                                ==s.array.get(j).fitness[1]){
                            //相等无操作；
                        }else{
                            //将j添加到i的集合sp，表示被解i所支配
                            s.array.get(i).sp.add(s.array.get(j));
                        }
                    }else if(s.array.get(i).fitness[0]>=s.array.get(j).fitness[0] && s.array.get(i).fitness[1]
                            >=s.array.get(j).fitness[1]){//两个函数均大于
                        //被支配关系 i被j支配
                        if (s.array.get(i).fitness[0]==s.array.get(j).fitness[0] && s.array.get(i).fitness[1]
                                ==s.array.get(j).fitness[1]){
                        }else{
                            //j能够支配i，因此，i的nq++；
                            s.array.get(i).nq++;
                        }
                    }
                }
            }
            //nq表示被支配的个数，如果=0，则说明i是一个非支配解，此时将其rank属性置为等级1    即pareto第一等级
            if(s.array.get(i).nq==0){
                s.array.get(i).rank=1;
                fx.add(s.array.get(i));
            }
        }
        F.add(fx);
        int i=0;
        while (F.get(i).array.size()!=0){
            fx=new NSGADoubleSolutionSet(s.array.size());
            for (int j=0;j<F.get(i).size();j++){//对F进行迭代
                for (int m=0;m<F.get(i).array.get(j).sp.size();m++){
                    //对F中的个体支配个体集进行迭代
                    F.get(i).array.get(j).sp.get(m).nq--;
                    if (F.get(i).array.get(j).sp.get(m).nq==0){
                        F.get(i).array.get(j).sp.get(m).rank=i+2;
                        fx.add(F.get(i).array.get(j).sp.get(m));
                    }
                }
            }
            F.add(fx);
            i++;
        }
        System.out.println("快排结束");
        return s;
    }

}
