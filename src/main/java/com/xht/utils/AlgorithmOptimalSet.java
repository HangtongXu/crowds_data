package com.xht.utils;

import com.xht.pojo.Mission;
import com.xht.pojo.Worker;

import java.util.*;

public class AlgorithmOptimalSet {

    private HashMap<Integer,Integer> state;
    private List<TaskDistribution> taskDistributions;
    public AlgorithmOptimalSet(List<TaskDistribution> taskDistributions){
        this.taskDistributions=taskDistributions;
        state=new HashMap<>();
        for(TaskDistribution taskDistribution:taskDistributions){
            for(Worker worker:taskDistribution.getNeighbor()){
                if(!this.state.containsKey(worker.getId())){
                    this.state.put(worker.getId(),worker.getMaxtask());
                }
            }
        }
    }

    public boolean test_worker_state(List<Worker> set) {
        for (Worker worker : set) {
            if (this.state.get(worker.getId())<= 0) {
                return false;
            }
        }
        return true;
    }

    public void change_state(List<Worker> set) {
        for (Worker worker : set) {
            this.state.put(worker.getId(),this.state.get(worker.getId())-1);
        }
    }

    public void change_state_back(List<Worker> set) {
        for (Worker worker : set) {
            this.state.put(worker.getId(),this.state.get(worker.getId())+1);
        }
    }

    public List<LinkedList<TaskDistribution>> getVTC_greedy(List<TaskDistribution> taskDistributions) {
        // 贪心策略：最大限度的使得所有的任务都能找到一个满足条件的有效任务集合
        //1：排序，先满足有效任务集合数少的集合
        // 2：开始进行贪心选择
        // 定义集合，可优化分布任务集，不可优化分布任务集
        // 可优化,但是没有相关集合
        LinkedList<TaskDistribution> optimiz_non = new LinkedList<>();
        //可优化，已有最优集合
        LinkedList<TaskDistribution> optimiz_one = new LinkedList<>();
        //不可优化
        LinkedList<TaskDistribution> unOptimiz = new LinkedList<>();
        //根据最游记和长度排序
        Collections.sort(taskDistributions, new Comparator<TaskDistribution>() {
            @Override
            public int compare(TaskDistribution o1, TaskDistribution o2) {
                return o1.getLen_VTC() - o2.getLen_VTC();
            }
        });
        for (int index = 0; index < taskDistributions.size(); index++) {
            TaskDistribution taskDistribution = taskDistributions.get(index);
            //存在最大有效任务集合，根据情况判断
            if (taskDistribution.isResult()) {
                //清空集合用于储存
                taskDistribution.getWorkers().clear();
                //检查最大信息增益集合是否可用，若可用，直接输出
                if (test_worker_state(taskDistribution.getMaxIG_VTC())) {
                    taskDistribution.setWorkers(new LinkedList<>(taskDistribution.getMaxIG_VTC()));
                    change_state(taskDistribution.getWorkers());
                    optimiz_one.add(taskDistribution);
                }
                //最大信息集合不可用，检测其他集合是否可用，若可用，直接输出
                else {
                    for (List<Worker> set : taskDistribution.getVTC()) {
                        if (test_worker_state(set)) {
                            change_state(set);
                            taskDistribution.setWorkers(new LinkedList<>(set));
                            optimiz_one.add(taskDistribution);
                            break;
                        }
                    }
                    if (taskDistribution.getWorkers().size() <= 0) {
                        for (Worker worker : taskDistribution.getMaxIG_VTC()) {
                            if (worker.getMaxtask() > 0) {
                                worker.setMaxtask(worker.getMaxtask()-1);
                                taskDistribution.getWorkers().add(worker);
                            }
                        }
                        optimiz_non.add(taskDistribution);
                    }
                }
            }
            //不存在最大有效任务集合，直接将结果输出
            else {
                taskDistribution.getWorkers().clear();
                for (Worker worker : taskDistribution.getVTC().get(0)) {
                    if (worker.getMaxtask() > 0) {
                        worker.setMaxtask(worker.getMaxtask()-1);
                        taskDistribution.getWorkers().add(worker);
                    }
                }
                unOptimiz.add(taskDistribution);
            }
        }
        List<LinkedList<TaskDistribution>> res = new LinkedList<>();
        res.add(optimiz_non);
        res.add(optimiz_one);
        res.add(unOptimiz);
        return res;
    }

    public List<List<TaskDistribution>> getVTC_extremum(List<LinkedList<TaskDistribution>> res) {
        // 从可优化已有最优集合中删除一个排列，并将其加入无最优任务集合
        //设置起始标志
        int index=res.get(1).size();
        while (index>0){
            TaskDistribution taskDistribution1=res.get(1).removeLast();
            List<Worker>copy=new LinkedList<>(taskDistribution1.getWorkers());
            change_state_back(copy);
            res.get(0).addLast(taskDistribution1);
            for(TaskDistribution taskDistribution : res.get(0)){
                taskDistribution.getVTC_enumeration(0.1,5);
            }
            List<LinkedList<TaskDistribution>> back=getVTC_greedy(res.get(0));
            if(back.get(1).size()>1){
                for(TaskDistribution taskDistribution: back.get(1)){
                    res.get(1).addLast(taskDistribution);
                }
                index=res.get(1).size();
                res.get(0).clear();
                res.get(0).addAll(back.get(0));
            }
            else {
                taskDistribution1.setWorkers(copy);
                res.get(1).addFirst(taskDistribution1);
                res.get(0).removeLast();
                index-=1;
            }
        }
        return new LinkedList<>(res);
    }


    public HashMap<Integer, Integer> getState() {
        return state;
    }
}
