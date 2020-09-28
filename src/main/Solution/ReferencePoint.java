package main.Solution;

import javafx.util.Pair;

import java.util.*;

public class ReferencePoint<S extends solution> {

    public List<Double> position;
    private int memberSize;
    private List<HashMap<S, Double>> potentialMembers;


    public ReferencePoint() {
    }

    public ReferencePoint(int size) {
        position = new ArrayList<>();
        for (int i = 0; i < size; i++)
            position.add(0.0);
        memberSize = 0;
        potentialMembers = new ArrayList<>();
    }



    public ReferencePoint(ReferencePoint<S> point) {
        position = new ArrayList<>(point.position.size());
        for (Double d : point.position) {
            position.add(new Double(d));
        }
        memberSize = 0;
        potentialMembers = new ArrayList<>();
    }

    public void RemovePotentialMember(S solution) {
        Iterator<HashMap<S, Double>> it = this.potentialMembers.iterator();
        while (it.hasNext()) {
            HashMap<S,Double> hmap=it.next();
            Iterator<S> it1=hmap.keySet().iterator();
            if (it1.next().equals(solution)) {
                it.remove();
                break;
            }
        }
    }
     //参考点
    public void generateReferencePoints(
            List<ReferencePoint<S>> referencePoints,
            int numberOfObjectives,  //目标维度
            List<Integer> numberOfDivisions) {

        ReferencePoint<S> refPoint = new ReferencePoint<>(numberOfObjectives);//目标数

        generateRecursive(referencePoints, refPoint, numberOfObjectives, numberOfDivisions.get(0), numberOfDivisions.get(0), 0);

    }




    //递归   划分参考点个数：C（M+H-1,H）M维数，H划分段数，
    public void generateRecursive(
            List<ReferencePoint<S>> referencePoints,
            ReferencePoint<S> refPoint,
            int numberOfObjectives,
            int left,
            int total,
            int element) {
        if (element == (numberOfObjectives - 1)) {
            refPoint.position.set(element, (double) left / total);
            referencePoints.add(new ReferencePoint<>(refPoint));
        } else {
            for (int i = 0; i <= left; i += 1) {
                refPoint.position.set(element, (double) i / total);
                generateRecursive(referencePoints, refPoint, numberOfObjectives, left - i, total, element + 1);
            }
        }
        //return referencePoints;
    }





    public S RandomMember() {
        Random random=new Random();
        int index = this.potentialMembers.size()>1 ? random.nextInt( this.potentialMembers.size()-1):0;
        Iterator<S> it=this.potentialMembers.get(index).keySet().iterator();
        S s=it.next();
        return s;
    }

    public List<Double> pos() {
        return this.position;
    }

    public int MemberSize() {
        return memberSize;
    }

    //有潜力的成员
    public boolean HasPotentialMember() {
        return potentialMembers.size() > 0;
    }

    public void clear() {
        memberSize = 0;
        this.potentialMembers.clear();
    }

    public void AddMember() {
        this.memberSize++;
    }



    public void AddPotentialMember(S member_ind, double distance) {
        //连接附近的解到参考点的操作
        HashMap h = new HashMap<S, Double>();
        h.put(member_ind, distance);
        this.potentialMembers.add(h);
    }



    //找到最近的个体
    public S FindClosestMember() {
        double minDistance = Double.MAX_VALUE;
        S closetMember = null;
        for (HashMap<S, Double> p : this.potentialMembers) {
            Iterator<S> it = p.keySet().iterator();
            S s = it.next();

            if (p.get(s) < minDistance) {
                minDistance = p.get(s);

                closetMember = s;

            }
        }
        return closetMember;
    }

}
