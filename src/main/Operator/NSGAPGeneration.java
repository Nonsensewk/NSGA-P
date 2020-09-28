package main.Operator;

import main.Solution.NSGAPDoubleSolution;
import main.Solution.NSGAPDoubleSolutionSet;
import main.Solution.ReferencePoint;
import main.Solution.solutionSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NSGAPGeneration extends operator{

    HashMap<Integer,ArrayList<NSGAPDoubleSolution>> front;

    ArrayList<NSGAPDoubleSolution> front_l;

    List<ReferencePoint<NSGAPDoubleSolution>> referencePoints;

    NSGAPDoubleSolutionSet s;

    public void execute() {}

//pareto种群分层


    public NSGAPGeneration(NSGAPDoubleSolutionSet s){
        this.s=s;
        this.front=new HashMap<>();
        for (int i=0;i<s.size();i++){
            //首先把帕累托等级给分开
            if (front.keySet(). contains ( s.array.get(i).rank)){
                front.get(s.array.get(i).rank).add(s.array.get(i));
            } else{
                front_l=new ArrayList<>();
                front_l.add(s.array.get(i));
                front.put(s.array.get(i).rank,front_l);
            }
        }
    }
    @Override
    public solutionSet execute(solutionSet s) {
        return null;
    }


    public  NSGAPDoubleSolutionSet execute(List<ReferencePoint<NSGAPDoubleSolution>> ref){
        //ArrayList<NSGAPDoubleSolution> front=new ArrayList<>();

        int maxsize=s.size()/2;
        this.referencePoints=ref;



        //这里可以使用一个HashMap来实现，根据每一个解的rank值作为key，rank值相同的作为同一个帕累托层，这样就生成了根据帕累托层分辨的种群front
        NSGAPDoubleSolutionSet newS = new NSGAPDoubleSolutionSet(s.size/2);   //初始化新的集合，用来筛选   2N-->N
        //首先把帕累托等级分开，
        //向里面放，直到放到第l-1层
        int rankingIndex=1;//表示第几层,因为是从帕累托等级为1开始的
        //候选解
        int candidateSolutions=0;
        //直到最后一个front不能完全塞进去
        while(candidateSolutions<maxsize){
           // System.out.println(front.get(rankingIndex));
            candidateSolutions += front.get(rankingIndex).size();
            if ((newS.size()+front.get(rankingIndex).size()<=maxsize)){
                //如果没有溢出就往里面添加
                front_l = front.get(rankingIndex);
                for (int i = 0 ; i < front_l. size(); i++) {
                    newS.add(front_l.get(i));
                }
                rankingIndex++;
            }

        }



        List<Double>  ideal_point;//理想点集合
        //寻找ideal point
        ideal_point=translateObjectives(s);
        //寻找extreme point
        List<NSGAPDoubleSolution> extreme_point;//额外点集合
        extreme_point=findExtremePoints(s);
        //根据extreme point 我们可以利用高斯消去得到截距
        List<Double> intercepts;

        intercepts=constructHyperplane(s,extreme_point);
        //根据截距，理想点，额外点 我们可以进行函数的标量化


        //标量化

        int numberOfObjectives = s.array.get(1).fitness.length;


        for (int t=1; t < front.size(); t = t + 1)
        {
            for (NSGAPDoubleSolution ss : front.get(t)) {
                                       //目标数
                for (int f = 0; f < numberOfObjectives; f++) {
                    //IndexOutOfBoundsException: Index: 0, Size: 0  判断
                    if(ideal_point != null  && ideal_point.size() > 0) {

                        if (Math.abs(intercepts.get(f) - ideal_point.get(f)) > 10e-10) {

                            ss.fitness[f] = (ss.fitness[f] - ideal_point.get(f)) / (intercepts.get(f) - ideal_point.get(f));
                        } else {
                            ss.fitness[f] = (ss.fitness[f] - ideal_point.get(f)) / (intercepts.get(f) - ideal_point.get(f));
                        }
                    }

                }
            }
        }
        //划分参考点，将参考点带入
        associate(s);
        //生成参考向量，计算距离和p
        while (s.size() < maxsize)
        //当生成完参考向量之后，进行选择操作
        {
            int min_rp = FindNicheReferencePoint();

            NSGAPDoubleSolution chosen = SelectClusterMember(this.referencePoints.get(min_rp));

            if (chosen == null) // F1没有解  忽略参考点
            {
                this.referencePoints.remove(min_rp);
            }
            else
            {
                this.referencePoints.get(min_rp).AddMember();
                this.referencePoints.get(min_rp).RemovePotentialMember(chosen);
                s.add(chosen);
            }
        }
        //添加，程序结束
        return s;
    }



    //选取成员
    public NSGAPDoubleSolution SelectClusterMember(ReferencePoint<NSGAPDoubleSolution> rp)
    {
        NSGAPDoubleSolution chosen = null;
        if (rp.HasPotentialMember())
        {
            if (rp.MemberSize() == 0)
            {
                chosen =  rp.FindClosestMember();
            }
            else
            {
                chosen =  rp.RandomMember();
            }
        }
        return chosen;
    }

    int FindNicheReferencePoint()
    {
        // 找到最小集群大小
        int min_size = Integer.MAX_VALUE;
        for (ReferencePoint<NSGAPDoubleSolution> referencePoint : this.referencePoints)
            min_size = Math.min(min_size,referencePoint.MemberSize());

        // 最小集群的参考点
        List<Integer> min_rps=new ArrayList<>();
        for (int r=0; r<this.referencePoints.size(); r+=1)
        {
            if (this.referencePoints.get(r).MemberSize() == min_size)
            {
                min_rps.add(r);
            }
        }
        // 返回随机参考点
        Random random = new Random();
        return min_rps.get(min_rps.size() > 1 ? random.nextInt(min_rps.size()-1) :0);
    }


    //关联操作
    public void associate(NSGAPDoubleSolutionSet population) {
        for (int t = 1; t < front.size(); t++) {
            for (NSGAPDoubleSolution s : front.get(t)) {
                int min_rp = -1;//
                double min_dist = Double.MAX_VALUE;
                for (int r = 0; r < this.referencePoints.size(); r++) {
                    //d代表参考点到  具体解的距离

                    double d = perpendicularDistance(this.referencePoints.get(r).position, s);

                    if (d < min_dist) {
                        min_dist=d;
                        min_rp = r;
                    }
                }
                if (t != front.size()) {
                    this.referencePoints.get(min_rp).AddMember();//
                } else {
                    this.referencePoints.get(min_rp).AddPotentialMember(s, min_dist);
                }
            }
        }
    }


    //垂直距离                                        参考点                        个体解
    public double perpendicularDistance(List<Double> direction, NSGAPDoubleSolution point) {
        double numerator = 0, denominator = 0;
        for (int i=0; i<direction.size(); i+=1)
        {
            //分子
            numerator = numerator + direction.get(i)*point.fitness[i];

            //分母
            denominator = denominator+ Math.pow(direction.get(i),2.0);
        }
        double k = numerator/denominator;

        double d = 0;
        for (int i=0; i<direction.size(); i+=1)
        {
            d += Math.pow(k*direction.get(i) - point.fitness[i],2.0);
        }
        return Math.sqrt(d);
    }




    //构造超平面
    public List<Double> constructHyperplane(NSGAPDoubleSolutionSet population, List<NSGAPDoubleSolution> extreme_points) {

        int numberOfObjectives=population.array.get(1).fitness.length;

        boolean duplicate = false;

        for (int i=0; !duplicate && i< extreme_points.size(); i+=1)
        {
            for (int j=i+1; !duplicate && j<extreme_points.size(); j+=1)
            {
                duplicate = extreme_points.get(i).equals(extreme_points.get(j));
            }
        }

        List<Double> intercepts = new ArrayList<>();

        if (duplicate) // 无法构造唯一的超平面（这是处理条件的临时方法）
        {
            for (int f=0; f<numberOfObjectives; f+=1)
            {
                // extreme_points[f] 最大值
                intercepts.add(extreme_points.get(f).fitness[f]);
            }
        }
        else
        {
            // 求超平面的
            List<Double> b = new ArrayList<>(); //(pop[0].objs().size(), 1.0);
            for (int i =0; i < numberOfObjectives;i++)
                b.add(1.0);

            List<List<Double>> A=new ArrayList<>();
            for (NSGAPDoubleSolution s : extreme_points)
            {
                List<Double> aux = new ArrayList<>();
                for (int i = 0; i < numberOfObjectives; i++)
                    aux.add(s.fitness[i]);
                A.add(aux);//A是极端点的极端值的集合的集合
            }
            List<Double> x = guassianElimination(A, b);

            // 找到终点
            for (int f=0; f<numberOfObjectives; f+=1)
            {
                intercepts.add(1.0/x.get(f));

            }
        }
        return intercepts;
    }
    private List<NSGAPDoubleSolution> findExtremePoints(NSGAPDoubleSolutionSet population) {

        //寻找极值点

        List<NSGAPDoubleSolution> extremePoints = new ArrayList<>();
        int numberOfObjectives=population.array.get(1).fitness.length;

        NSGAPDoubleSolution min_indv = null;//最小极值

        for (int f=0; f < numberOfObjectives; f+=1)
        {
            double min_ASF = Double.MAX_VALUE;
            for (NSGAPDoubleSolution s : front.get(1)) {
                double asf = ASF(s, f);  //ASF函数求解
                if ( asf < min_ASF ) {
                    min_ASF = asf;
                    min_indv = s;
                }
            }
            extremePoints.add(min_indv);
        }
        return extremePoints;
    }


    //ASF函数   寻找额外点     将所有个体固定在一个维度，其他维度同时除以一个极小的数，然后取这些个体上的最大值  作为AFS函数值
    private double ASF(NSGAPDoubleSolution s, int index) {
        double max_ratio = Double.NEGATIVE_INFINITY;//负无穷
        //找到极值点
        for (int i = 0; i < s.fitness.length; i++) {
            double weight = (index == i) ? 1.0 : 0.000001;
            //把当前值置为1，其他方向置为很小的数
            max_ratio = Math.max(max_ratio, s.fitness[i]/weight);
        }
        return max_ratio;
    }



    //寻找理想点   所有目标维度上的目标值最小
    public List<Double> translateObjectives(NSGAPDoubleSolutionSet pop) {

        List<Double> ideal_point;

        int numberOfObjectives=pop.array.get(1).fitness.length;

        ideal_point = new ArrayList<>(numberOfObjectives);



        //numberOfObjectives-1 代表找前两维目标最小的最优点
        for (int f=0; f<numberOfObjectives ; f+=1) {
           // if (pop.array.get(f).fitness[0]>=0.4  && pop.array.get(f).fitness[0]<= 0.8) {
                double minf = Double.MAX_VALUE;
                for (int i = 0; i < front.get(1).size(); i += 1) //最小值必须出现在front1等级上
                {

                    //  if(pop.array.get(f).fitness[1] >= 0.4 && pop.array.get(f).fitness[1] <= 0.8) {

                        minf = Math.min(minf, front.get(1).get(i).fitness[f]);//各个目标的最小值
                    // }
                    //     返回一个解集的arraylist   返回某一个目标函数    目标函数具体的值
                }
                ideal_point.add(minf);
           // }
        }
        return ideal_point;
    }




    //这里实际上就是高斯消元
    public List<Double> guassianElimination(List<List<Double>> A, List<Double> b) {

        List<Double> x = new ArrayList<>();

        int N = A.size();
        for (int i=0; i<N; i+=1)
        {
            A.get(i).add(b.get(i));
        }

        for (int base=0; base<N-1; base+=1)
        {
            for (int target=base+1; target<N; target+=1)
            {
                double ratio = A.get(target).get(base)/A.get(base).get(base);
                for (int term=0; term<A.get(base).size(); term+=1)
                {

                    A.get(target).set(term, A.get(target).get(term) - A.get(base).get(term)*ratio);
                }
            }
        }

        for (int i = 0; i < N; i++)
            x.add(0.0);

        for (int i=N-1; i>=0; i-=1)
        {
            for (int known=i+1; known<N; known+=1)
            {
                A.get(i).set(N, A.get(i).get(N) - A.get(i).get(known)*x.get(known));
            }
            x.set(i, A.get(i).get(N)/A.get(i).get(i));
        }
        return x;
    }
}
