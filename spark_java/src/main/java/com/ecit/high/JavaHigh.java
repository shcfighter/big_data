package com.ecit.high;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

public class JavaHigh {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().appName("JavaHigh").getOrCreate();
        JavaRDD<String> lines = spark.read().textFile("hdfs://localhost:9000/data/input/people_info.txt").javaRDD();
        JavaRDD<People> peoplesF = lines.filter(line -> {
            String[] l = line.split(" ");
            return l[1].equals("F");
        }).flatMap(line -> {
            List<People> list = new ArrayList<>();
            String[] l = line.split(" ");
            list.add(new People(l[1], Integer.parseInt(l[2])));
            return list.iterator();
        });
        long fCount = peoplesF.count();
        JavaRDD<People> peoplesF2 = peoplesF.sortBy(p -> p.getHigh(), false, 3);
        People people1 = peoplesF2.first();
        JavaRDD<People> peoplesF3 = peoplesF.sortBy(p -> p.getHigh(), true, 3);
        People people2 = peoplesF3.first();

        JavaRDD<People> peoplesM = lines.filter(line -> {
            String[] l = line.split(" ");
            return l[1].equals("M");
        }).flatMap(line -> {
            List<People> list = new ArrayList<>();
            String[] l = line.split(" ");
            list.add(new People(l[1], Integer.parseInt(l[2])));
            return list.iterator();
        });
        long mCount = peoplesM.count();
        JavaRDD<People> peoplesM2 = peoplesM.sortBy(p -> p.getHigh(), false, 3);
        People people3 = peoplesM2.first();
        JavaRDD<People> peoplesM3 = peoplesM.sortBy(p -> p.getHigh(), true, 3);
        People people4 = peoplesM3.first();


        System.out.println("====================================================");
        System.out.println(fCount);
        System.out.println(people1);
        System.out.println(people2);

        System.out.println(mCount);
        System.out.println(people3);
        System.out.println(people4);
        System.out.println("====================================================");


    }

}
