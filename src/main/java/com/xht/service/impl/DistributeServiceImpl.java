package com.xht.service.impl;

import com.xht.pojo.Mission;
import com.xht.pojo.Order;
import com.xht.pojo.Worker;
import com.xht.service.*;
import com.xht.utils.AlgorithmOptimalSet;
import com.xht.utils.AuctionDistribute;
import com.xht.utils.TaskDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class DistributeServiceImpl implements DistributeService {

    @Autowired
    MissionService missionService;
    @Autowired
    WorkerService workerService;
    @Autowired
    OrderService orderService;
    @Autowired
    MissionDetailsService missionDetailsService;
    @Autowired
    AuctionDistribute auctionDistribute;
    @Override
    @Scheduled(fixedRate = 600000)
    public void auctionDistribute() {
        auctionDistribute.decideAskerPrice();
        if(auctionDistribute.getAuctionMisssions().isEmpty()){
            System.out.println("本批次无符合条件任务");
            return;
        }
        System.out.println("本批次符合条件任务数量："+auctionDistribute.getAuctionMisssions().size());
        auctionDistribute.decideWorkerPrice();
        auctionDistribute.updateState();
        System.out.println("本批次处理结束");
    }

    //    @PostConstruct
//    @Scheduled(fixedRate = 10000)
    public void normalDistribute() {
        while (true) {
            List<Mission> missions = missionService.getDistributeMission();
            if (missions.size() < 1) {
                System.out.println("等待中.......");
                //未有等待处理的任务，休眠半分钟
                try {
                    Thread.currentThread().sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("开始处理........");
                missionService.updateMissionState(missions);
                LinkedList<TaskDistribution> taskDistributions = new LinkedList<>();
                for (Mission mission : missions) {
                    LinkedList<Worker> neighbor = new LinkedList<>();
                    int code = 6;
                    while (neighbor.size() < 1) {
                        code -= 1;
                        neighbor.addAll(workerService.getWorkerByGeoHash(mission, code));
                    }
                    taskDistributions.add(new TaskDistribution(mission, null, neighbor));
                    taskDistributions.getLast().getVTC_enumeration(0.1, code+1);
//                    System.out.println(mission.getId() + "   " + taskDistributions.getLast().getVTC().size() + "  " + taskDistributions.getLast().isResult());
//            double time=(mission.getDeadline().getTime()-System.currentTimeMillis())/(3600000);
//            System.out.println(time);
                }
                AlgorithmOptimalSet algorithmOptimalSet = new AlgorithmOptimalSet(taskDistributions);
                List<List<TaskDistribution>> res = algorithmOptimalSet.getVTC_extremum(algorithmOptimalSet.getVTC_greedy(taskDistributions));
                for (List<TaskDistribution> t1 : res) {
                    for (TaskDistribution set : t1) {
//                        System.out.println("id:  " + set.getMission().getId());
//                        System.out.println("len: " + set.getLen_VTC() + "   members:" + set.getMission().getMembers());
                        if (set.getWorkers().size() < 1) {
                            System.out.println("任务编号为 "+set.getMission().getId()+"的任务未找到适合工作者，将持续为其查找...");
                            missionService.updateMissionState(0, set.getMission().getId());
                            //降低任务优先级
                            missionService.updatepriority(set.getMission().getId());
                        } else {
                            for (Worker worker : set.getWorkers()) {
//                                System.out.println(worker.getId() + "   " + algorithmOptimalSet.getState().get(worker.getId()));
                                missionDetailsService.addMissionDetails(set.getMission().getId(),worker.getId());
                                missionDetailsService.updateMissionDetailsState(2,set.getMission().getId(),worker.getId());
                                orderService.addOrder(worker.getId(),set.getMission().getUid(),(int)set.getMission().getMoney());
                                worker.setMaxtask(algorithmOptimalSet.getState().get(worker.getId()));

                            }
                            workerService.updateWorkerMaxTaskById(set.getWorkers());
                            missionService.updateMissionMembers(set.getMission().getMembers() - set.getWorkers().size(), set.getMission().getId());
                            missionService.updateMissionState(2, set.getMission().getId());
                            System.out.println("任务编号为 "+set.getMission().getId()+"的任务已找到"+set.getWorkers().size()+"名适合工作者");
                        }
                    }
                }
                System.out.println("本批次处理结束");
                try {
                    Thread.currentThread().sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
