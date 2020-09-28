//package main.Algorithm;
//
//
//import main.Operator.*;
//import main.Solution.NSGADoubleSolutionSet;
//import main.Solution.NSGAPDoubleSolutionSet;
//import main.Solution.solutionSet;
//import main.problem.*;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class NSGA extends MultiAlgorithm{
////多目标遗传算法，NSGA2算法类实现
//    int humans;//种群个数
//    int generation;//进化代数
//
//    public solutionSet run(Multiproblem p) {
//        return getResult(p);
//    }
//
//    public NSGA(int humans,int generation){
//        this.humans=humans;
//        this.generation=generation;
//    }
//
//    public solutionSet getResult(Multiproblem p) {
//        NSGADoubleSolutionSet s= new NSGADoubleSolutionSet(humans);  //问题解集
//        NSGADoubleRandominit NDR = new NSGADoubleRandominit();    //随机生成种群
//        s=NDR.execute(s,p);  //返回执行解集
//        for (int i=0;i<generation;i++){
//            NSGADoubleSolutionSet child=solutionSet.clone(s);//child是子类，通过序列化深度克隆父类得到。
//
//            //快速非支配排序
//            NSGAFastNonSort NFNS=new NSGAFastNonSort();
//            NFNS.execute(child);
//
//            //计算种群拥挤度
//            CalDistance NSCD=new CalDistance();
//            NSCD.execute(child);
//
//
//            //选择
//            NSGASelection NSS=new NSGASelection();
//            NSS.execute(child);
//
//
//            System.out.println("交叉中...");
//            //交叉
//            NSGADoubleCrossover NSDC=new NSGADoubleCrossover(0.8,0.6);
//            NSDC.execute(child,p);
//
//
//            System.out.println("变异中...");
//            //变异
//            NSGADoubleMutation NSDM=new NSGADoubleMutation(0.3,0.4);
//            NSDM.execute(child,p);
//
//            System.out.println("迭代中...");
//
//            //迭代
//            NSGADoubleGeneration NSDG=new NSGADoubleGeneration();
//            s=NSDG.execute(s,child,p);
//
//
//            System.out.println("结果:第"+i+"代");
//            for (int m=0;m<s.size();m++){
//                if (s.array.get(m).rank==1){//输出pareto等级为一的即为当前最优
//                    /*
//                    File file = new File("D:\\test.txt");
//                    if(!file.exists()){
//                        try {
//                            file.createNewFile();
//                            FileWriter fileWriter = null;
//                            fileWriter = new FileWriter(file.getAbsoluteFile());
//                            BufferedWriter bw = new BufferedWriter(fileWriter);
//                            bw.write(s.array.get(m).fitness[0] + " " + s.array.get(m).fitness[1]);
//                            bw.close();
//                        }catch (IOException e){
//                            e.printStackTrace();
//                        }
//                    }
//                    System.out.println("finish");*/
//
//                    System.out.println(s.array.get(m).fitness[0]+" "+s.array.get(m).fitness[1]);
//                }
//            }
//
//        }
//        return s;
//    }
////    public static void main(String args[]){
////
////        NSGA test=new NSGA(10,20);
////
////        ZDT3problem p=new ZDT3problem();
////
////
////        test.run(p);
////    }
//}
