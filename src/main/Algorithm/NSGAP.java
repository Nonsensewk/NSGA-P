package main.Algorithm;

import main.Operator.*;
import main.Solution.NSGAPDoubleSolution;
import main.Solution.NSGAPDoubleSolutionSet;
import main.Solution.ReferencePoint;
import main.Solution.solutionSet;
import main.problem.*;

import java.util.List;
import java.util.Vector;

public class NSGAP extends MultiAlgorithm{

    int generation;  //进化代数
    int popsize;  //种群大小
    double pc;     //交叉概率
    double r;      //调节因子
    double pm;     //变异概率
    double k;      //变异步长

    Multiproblem p;   //问题
    Vector<Integer> numberofDivisions;//投影分割
    List<ReferencePoint<NSGAPDoubleSolution>> referencePoints = new Vector<>() ;// 参考点


    public NSGAP(int generation, int popsize, Multiproblem p){
        this.generation=generation;
        this.popsize=popsize;
        this.p=p;
        this.pc=0.8;
        this.r=0.6;
        this.k=0.4;
        this.pm=0.5;

        this.numberofDivisions=new Vector<>(1);

        numberofDivisions.add(12);//划分数
        (new ReferencePoint<NSGAPDoubleSolution>()).generateReferencePoints(referencePoints,p.getNumberOfObjectives(), numberofDivisions);
    }

    //求解过程

    public solutionSet getResult() {
        //创建集合存储解
        NSGAPDoubleSolutionSet NDS=new NSGAPDoubleSolutionSet(popsize);

        //随机初始化种群
        NSGAPDoubleRandominit NDR=new NSGAPDoubleRandominit();

        NDS=NDR.execute(NDS,p);//第一代种群集合
        //迭代次数
        for (int i=0;i<generation;i++){
            //生成子代，因为有精英保留原则

            //交叉算子
            NSGADoubleCrossover NDC=new NSGADoubleCrossover(pc,r);
            NSGAPDoubleSolutionSet childs=NDC.execute(NDS,p);//生成的子代种群childs
            System.out.println("交叉完毕！--->");
            //变异算子
            NSGADoubleMutation NDM=new NSGADoubleMutation(pm,k);
            childs=NDM.execute(childs,p);//变异后的子代
            System.out.println("变异完毕！--->");

            //System.out.println(childs.size);

            //整合两个种群
            NSGAPDoubleSolutionSet SUM=new NSGAPDoubleSolutionSet(popsize*2);
            for (int j=0;j<popsize*2;j++){
                if (j<popsize){
                    SUM.add(NDS.array.get(j));
                }else{
                    //System.out.println(j-popsize);
                    DTLZ1 mp = (DTLZ1) p;
//            ZDT1problem mp = (ZDT1problem) p;
//            ZDT2problem mp = (ZDT2problem) p;
//            ZDT3problem mp = (ZDT3problem) p;
//            ZDT4problem mp = (ZDT4problem) p;
//            ZDT6problem mp = (ZDT6problem) p;

                    for (int h=0;h<childs.array.size();h++)
                    childs.array.set(h, mp.evalute(childs.array.get(h)));
                    SUM.add(childs.array.get(j-popsize));
                }
            }
            System.out.println("种群合并完毕！--->");



            //快速非支配排序
            NSGAFastNonSort NFNS = new NSGAFastNonSort();
            SUM = NFNS.execute(SUM);



            NSGAPGeneration NG=new NSGAPGeneration(SUM);
            NDS=NG.execute(referencePoints);


            System.out.println("第"+i+"次进化--->");

        }


       // NSGAPGeneration Result=new NSGAPGeneration(NDS);
//
//        NSGAFastNonSort last = new NSGAFastNonSort();
//        NSGAPDoubleSolutionSet  result = last.execute(NDS);
//        result.array.get(1).rank = 1;



            for (int i = 0; i < NDS.size / 2; i++) {
                if(NDS.array.get(i).rank==1) {
                    for (int j = 0; j < NDS.array.get(i).fitness.length; j++)

                        System.out.print(NDS.array.get(i).fitness[j] + " ");

                    System.out.println(" ");
                }
            }


        return null;
    }

    public solutionSet run() {
        return getResult();
    }

}
