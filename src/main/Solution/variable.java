package main.Solution;

import java.io.Serializable;

public abstract class  variable implements Serializable {
    //自变量类
    public abstract String getBinaryVariable();
    public abstract void setBinaryVariable(String bv);
    public abstract int getRealVariable();
    public abstract void setRealVariable(int rv);
    public abstract double getDoubleVariable();
    public abstract void setDoubleVariable(double dv);
}
