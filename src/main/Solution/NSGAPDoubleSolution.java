package main.Solution;

import main.Algorithm.MultiAlgorithm;
import main.problem.Multiproblem;

import java.util.ArrayList;

public class NSGAPDoubleSolution extends solution {
    //NSGAP的解类

    public DoubleVariable[] variables;//自变量集合

    public int nq;//个体被支配数
    public int rank;//帕累托等级
    public ArrayList<NSGAPDoubleSolution> sp;//支配集合

    public NSGAPDoubleSolution(Multiproblem p){
        super(p);
        variables=new DoubleVariable[p.getNumberOfVariables()];
        for (int i=0;i<p.getNumberOfVariables();i++){
            variables[i]=new DoubleVariable();//初始化解
        }
        this.sp=new ArrayList<>();
        this.nq=0;
    }


    //NSGAP解的复制
    public NSGAPDoubleSolution copy(NSGAPDoubleSolution s, Multiproblem p){

        NSGAPDoubleSolution newS = new NSGAPDoubleSolution(p);

        for (int i=0;i<variables.length;i++){
            //浮点数自变量
            newS.variables[i].doubleVariable=s.variables[i].doubleVariable;

        }
        return  newS;

    }
}
