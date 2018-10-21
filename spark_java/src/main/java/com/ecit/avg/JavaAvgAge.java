package com.ecit.avg;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaAvgAge {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().appName("JavaAvgAge").getOrCreate();
        JavaRDD<String> lines = spark.read().textFile("hdfs://localhost:9000/data/input/sample_age_data.txt").javaRDD();
        JavaRDD<String> ages = lines.flatMap(l -> Arrays.asList(l.split(" ")[1]).iterator());
        JavaPairRDD<String, Long> agePair = ages.mapToPair(a -> new Tuple2<String, Long>("age", Long.parseLong(a)));
        JavaPairRDD<String, Long> agePair2 = agePair.reduceByKey((t1, t2) -> t1 + t2);
        long count = lines.count();
        JavaPairRDD<String, Long> agePair3 = agePair2.flatMapToPair(s -> {
            ArrayList<Tuple2<String,Long>> list=new ArrayList<>();
            list.add(new Tuple2<String,Long>(s._1, s._2 / count));
            return list.iterator();
        });
        agePair3.saveAsTextFile("hdfs://localhost:9000/data/out2");

        List<Tuple2<String, Long>> output = agePair2.collect();
        output.forEach(t -> {
            System.out.println("============================================================================");
            System.out.println(t._2 / count);
            System.out.println("============================================================================");
        });
    }
}
