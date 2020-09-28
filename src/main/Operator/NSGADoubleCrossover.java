package main.Operator;

import main.Solution.NSGADoubleSolutionSet;
import main.Solution.NSGAPDoubleSolution;
import main.Solution.NSGAPDoubleSolutionSet;
import main.problem.Hyperproblem;
import main.problem.Multiproblem;

public class NSGADoubleCrossover extends Crossover {
    //浮点数交叉算法

    double r;//调节因子

    public NSGADoubleCrossover(double pc, double r) {
        super(pc);
        this.r = r;
    }

    public NSGADoubleSolutionSet execute(NSGADoubleSolutionSet s, Multiproblem mp) {
        int i = 0;
        while (i < s.size() - 1) {
            double p = Math.random();

            if (p < pc) {
                for (int j = 0; j < s.array.get(0).variables.length - 1; j++) {
                    //System.out.println(j);
                    double a1 = s.array.get(i).variables[j].doubleVariable;//基因取值
                    double b1 = s.array.get(i + 1).variables[j].doubleVariable;
                    double a = Math.random() * r;//调节因子
                    double b = Math.random() * r;
                    s.array.get(i).variables[j].doubleVariable = (1-a) * a1 + b * b1;
                    s.array.get(i+1).variables[j+1].doubleVariable = (1-b) * b1 + a * a1;

                    //System.out.println(s.array.get(i).variables[j].doubleVariable);
                    if (s.array.get(i).variables[j].doubleVariable >= mp.upperlimit.get(j)) {
                        s.array.get(i).variables[j].doubleVariable = mp.upperlimit.get(j) - 0.0001;
                    }
                    if (s.array.get(i + 1).variables[j].doubleVariable >= mp.upperlimit.get(j)) {
                        s.array.get(i + 1).variables[j].doubleVariable = mp.upperlimit.get(j) - 0.0001;
                    }

                    if (s.array.get(i).variables[j].doubleVariable <= mp.lowerlimit.get(j)) {
                        s.array.get(i).variables[j].doubleVariable = mp.lowerlimit.get(j) + 0.0001;
                    }
                    if (s.array.get(i + 1).variables[j].doubleVariable <= mp.lowerlimit.get(j)) {
                        s.array.get(i + 1).variables[j].doubleVariable = mp.lowerlimit.get(j) + 0.0001;
                    }

                }
                i += 2;

            }
        }
        return s;
    }
    public NSGAPDoubleSolutionSet execute(NSGAPDoubleSolutionSet s1, Multiproblem mp){
        NSGAPDoubleSolutionSet s=new NSGAPDoubleSolutionSet(s1.size);  //s存储交叉之后的解
        int i = 0;
        while (i < s1.size() -1 ) {
            //浅拷贝  相邻的两个解
            NSGAPDoubleSolution m = s1.array.get(i).copy(s1.array.get(i),mp);//取值
            NSGAPDoubleSolution n = s1.array.get(i+1).copy(s1.array.get(i+1),mp);
            double p = Math.random();
            //判断能不能达到发生交叉的概率，随机数大于交叉概率则发生交叉，反之跳过 i+=2
            if (p < pc) {
                for (int j = 0; j < s1.array.get(0).variables.length - 1; j++) {

                    double a1 = n.variables[j].doubleVariable;//个体a1
                    double b1 = m.variables[j].doubleVariable;//个体b1
                    double a = Math.random() * r;//比例因子
                    double b = Math.random() * r;
                    /**
                     * 中值重组法：
                     * 子个体 = 父个体1+a*（父个体2-父个体1）
                     * a是个比例因子可以由[-d,1+d]上服从均匀分布的随机数产生
                     * d = 0;
                     */
                    m.variables[j].doubleVariable = (1 - a) * a1 + b * b1;
                    n.variables[j+1].doubleVariable = (1 - b) * b1 + a * a1;

                    //交叉后越出上下限就重置
                    if (m.variables[j].doubleVariable > mp.upperlimit.get(j)) {
                        m.variables[j].doubleVariable = mp.upperlimit.get(j) ;
                    }
                    if (n.variables[j].doubleVariable > mp.upperlimit.get(j)) {
                        n.variables[j].doubleVariable = mp.upperlimit.get(j) ;
                    }
                    if (n.variables[j].doubleVariable <mp.lowerlimit.get(j)) {
                        n.variables[j].doubleVariable = mp.lowerlimit.get(j) ;
                    }
                    if (m.variables[j].doubleVariable < mp.lowerlimit.get(j)) {
                        m.variables[j].doubleVariable = mp.lowerlimit.get(j) ;
                    }

                }
                //System.out.println("1");
            }
            s.add(m);//存放m，n
            s.add(n);
            i += 2;

        }

        return s;
    }
}
