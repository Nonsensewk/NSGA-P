package main.Operator;

import main.Solution.solutionSet;

public class Crossover extends operator{

    double pc;//发生交叉的概率

    public Crossover(double pc){
        this.pc=pc;
    }
    @Override

    public void execute() {

    }

    @Override
    public solutionSet execute(solutionSet s) {
        return null;
    }
}
