package com.xht.utils;

import com.xht.pojo.Mission;
import com.xht.pojo.Worker;
import com.xht.service.WorkerService;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TaskDistribution {
    private List<Worker> workers;
    //当前派发任务
    private Mission mission;
    //有效任务集合
    private List<List<Worker>> VTC;
    //最大信息增益有效任务集合
    private List<Worker> maxIG_VTC;
    //最大信息增益
    private double max_IG;
    //有效任务集合长度
    private int len_VTC;
    //是否存在最大增益有效任务集合
    private boolean result;

    //邻居工作者集合
    private LinkedList<Worker> neighbor;

    private Calculations calculations=new Calculations();

    public TaskDistribution(Mission mission, List<Worker> workers,LinkedList<Worker> neighbor) {
        if (workers != null&&workers.size() > 1) {
            this.workers = new LinkedList<>(workers);
        } else {
            this.workers = new LinkedList<>();
        }
        this.mission = mission;
        this.VTC = new LinkedList<>();
        this.maxIG_VTC = new LinkedList<>();
        this.len_VTC = 0;
        this.max_IG = 0;
        this.result = false;
        this.neighbor=neighbor;
    }
    //初步形成有效工作者集合
    public List<Worker> getWorker(int codeLen) {
        //清空已有列表
        this.workers.clear();
        //获取附近工作者
        Iterator<Worker> workerIterator =this.neighbor.iterator();
        List<Worker> res=new LinkedList<>();
        //判断是否可达,移除不可达工作者
        while (workerIterator.hasNext()) {
            Worker worker = workerIterator.next();
            double dis = calculations.getDistance(
                    worker.getLatitude(), worker.getLongtitude(), this.mission.getLatitude(), this.mission.getLongtitude());
            double time=(mission.getDeadline().getTime()-System.currentTimeMillis())/(3600000);
            if (dis<=worker.getSpeed()*time*2){
                res.add(worker);
            }
            //设置上限，避免数量太多导致运算量过大
            if(res.size()>=mission.getMembers()*5){
                this.neighbor=new LinkedList<>(res);
                return res;
            }
        }
        this.neighbor=new LinkedList<>(res);
        return res;
    }
    //基于信息增益下限计算合适的工作者排列
    public void getVTC_enumeration(double avg_ig,int codeLen){
        //相关集合清空
        this.VTC.clear();
        this.maxIG_VTC.clear();
        //获取可达任务集合
        List<Worker> validSet=this.getWorker(codeLen);
        //可达任务书小于需求人数时，直接输出
        if(validSet.size()<=this.mission.getMembers()){
            this.VTC.add(new LinkedList<>(validSet));
            this.len_VTC=this.VTC.size();
            this.result=false;
            return;
        }
        else {
            bulid_candidateVTC(0,this.mission.getMembers(),0,validSet,avg_ig,new LinkedList<>());
            this.len_VTC=this.VTC.size();
        }
        if(this.len_VTC==0){
            this.VTC.add(this.maxIG_VTC);
            this.len_VTC=this.VTC.size();
            this.result=false;
        }
        else {
            this.result=true;
        }

    }
    //递归计算信息增益符合集合
    public void bulid_candidateVTC(int index,int limit,int length,List<Worker> workers,double avg_ig,LinkedList<Worker> current_VTC){

        if(index>=workers.size()){
            return;
        }
        while (index<workers.size()-limit+length){
            current_VTC.add(workers.get(index));
            length+=1;
            if(length<limit&&index<workers.size()){
                bulid_candidateVTC(index+1,limit,length,workers,avg_ig,current_VTC);
            }
            if(length==limit){
                double IG=calculations.information_gain(current_VTC,this.mission.getLatitude(),this.mission.getLongtitude());
                if(IG>=this.max_IG){
                    this.max_IG=IG;
                    this.maxIG_VTC=new LinkedList<>(current_VTC);
                }
                if(IG/limit>=avg_ig){
                    this.VTC.add(new LinkedList<>(current_VTC));
                }
                current_VTC.removeLast();
                length-=1;
            }
            else {
                current_VTC.removeLast();
                length-=1;
            }
            index+=1;
        }
        return;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public List<List<Worker>> getVTC() {
        return VTC;
    }

    public void setVTC(List<List<Worker>> VTC) {
        this.VTC = VTC;
    }

    public List<Worker> getMaxIG_VTC() {
        return maxIG_VTC;
    }

    public void setMaxIG_VTC(List<Worker> maxIG_VTC) {
        this.maxIG_VTC = maxIG_VTC;
    }

    public double getMax_IG() {
        return max_IG;
    }

    public void setMax_IG(double max_IG) {
        this.max_IG = max_IG;
    }

    public int getLen_VTC() {
        return len_VTC;
    }

    public void setLen_VTC(int len_VTC) {
        this.len_VTC = len_VTC;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }


    public Calculations getCalculations() {
        return calculations;
    }

    public void setCalculations(Calculations calculations) {
        this.calculations = calculations;
    }

    @Override
    public String toString() {
        return "TaskDistribution{" +
                "workers=" + workers +
                '}';
    }

    public LinkedList<Worker> getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(LinkedList<Worker> neighbor) {
        this.neighbor = neighbor;
    }
}
