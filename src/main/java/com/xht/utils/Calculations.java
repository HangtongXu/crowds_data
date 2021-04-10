package com.xht.utils;

import com.xht.pojo.Worker;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Calculations {
    //正弦函数
    public double normalDistribution(double x, double mean, double sigma) {
        NormalDistribution normalDistribution=new NormalDistribution(mean,sigma);

        return normalDistribution.cumulativeProbability(x);
    }
    //接受率函数
    public double percent_accept(double direction){
        double mean=0.5;
        double sigma=Math.sqrt(0.17);
        return normalDistribution(direction,mean,sigma);
    }
//根据经纬度计算角度
    public double getAngle(double lat_a, double lng_a, double lat_b, double lng_b) {

        lat_a=lat_a*Math.PI/180;
        lng_a=lng_a*Math.PI/180;
        lat_b=lat_b*Math.PI/180;
        lng_b=lng_b*Math.PI/180;
        double y=Math.sin(lng_b-lng_a)*Math.cos(lat_b);
        double x=Math.cos(lat_a)*Math.sin(lat_b)-Math.sin(lat_a)*Math.cos(lat_b)*Math.cos(lng_b-lng_a);

        double brng=Math.atan2(y,x)*180/Math.PI;
        brng=(brng+360)%360;
        return brng;
    }
//根据经纬度计算距离
    public double getDistance(double lat1, double long1, double lat2, double long2) {
        double a, b, R;
        R = 6378.137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.atan(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }
//期望信息增益函数
    public double information_gain(List<Worker> workers,double work_lat,double work_lng){
        double ig=0;
        List<Double[]> angle=new LinkedList<>();
        for(Worker worker:workers){
            //获取角度
            double degree=getAngle(worker.getLatitude(),worker.getLongtitude(),work_lat,work_lng);
            //获取任务接受率
            double percent=percent_accept(degree);
            //转存
            angle.add(new Double[]{percent,degree});
        }
        //按角度排序
        Collections.sort(angle, new Comparator<Double[]>() {
            @Override
            public int compare(Double[] o1, Double[] o2) {
                return (int)(o1[1]-o2[1]);
            }
        });
        List<Double> angle_worker=new LinkedList<>();
        //计算工作者之间的夹角，计算信息增益
        int length=angle.size();
        for(int i=0;i<length;i++){
            if(i==0){
                angle_worker.add((360-angle.get(length-1)[1]+angle.get(1)[1])/2);
            }
            else if(i==length-1){
                angle_worker.add((360-angle.get(i-1)[1]+angle.get(0)[1])/2);
            }
            else{
                angle_worker.add((angle.get(i+1)[1]-angle.get(i-1)[1])/2);
            }
        }
        //计算信息增益
        for(int i=0;i<length;i++){
            if(angle_worker.get(i)<=0){
                ig+=0;
            }
            else{
                ig+=angle.get(i)[0]*angle_worker.get(i)/360*Math.log(angle_worker.get(i)/360);
            }
        }
        return -ig;
    }

}
