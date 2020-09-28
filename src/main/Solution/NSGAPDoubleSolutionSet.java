package main.Solution;

import java.util.ArrayList;

public class NSGAPDoubleSolutionSet extends solutionSet {

    //NSGAP的解集类
    public NSGAPDoubleSolutionSet(int n) {
        super(n);
        this.array=new ArrayList<>();
    }
    public ArrayList<NSGAPDoubleSolution> array;
    public void add(NSGAPDoubleSolution s){
        if (realsize<size){
            array.add(s);
            realsize++;
        }
    } //添加

    public void remove(double s){
        array.remove(s);
        realsize--;
    }//删除

    public int size(){
        return array.size();
    }//显示大小

    public boolean isFull(){
        if (realsize==size){
            return true;
        }else{
            return false;
        }
    }

    public void replace(NSGAPDoubleSolution s1,NSGAPDoubleSolution s2){
        int i=0;
        for (NSGAPDoubleSolution e:array){
            if (e.equals(s1)){
                array.set(i,s2);
            }
            i++;
        }
    }//s2替换s1

}
