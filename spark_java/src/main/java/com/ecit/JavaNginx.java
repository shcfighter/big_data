package com.ecit;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.rdd.RDD;
import scala.Tuple2;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaNginx {
    public static String pattern = "^(\\d+\\.\\d+\\.\\d+\\.\\d+)\\s\\-\\s-\\s(\\[[^\\[\\]]+\\])\\s(\\\"(?:[^\"]|\\\")+|-\\\")\\s(\\d{3})\\s(\\d+|-)\\s(\\\"(?:[^\"]|\\\")+|-\\\")\\s(\\\"(?:[^\"]|\\\")+|-\\\")$";
    public static String pattern2 = "^(\\d+\\.\\d+\\.\\d+\\.\\d+)\\s(\\[[^\\[\\]]+\\])\\s(\\\"(?:[^\"]|\\\")+|-\\\")\\s(\\d{3})\\s(\\d+|-)\\s(\\\"(?:[^\"]|\\\")+|-\\\")\\s(\\\"(?:[^\"]|\\\")+|-\\\")\\s(\\\"-\\\")$";
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("nginx");
        SparkContext sc = new SparkContext(conf);
        JavaRDD<String> accessRdd = sc.textFile("hdfs://localhost:9000/data/input/access.log", 1).toJavaRDD();
        JavaPairRDD<String, Integer> accessPairRDD = accessRdd.filter(line -> {
            String[] logs = parseLog(line);
            if(Objects.isNull(logs) || logs.length <= 0){
                return false;
            }
            if(Objects.isNull(logs[2])){
                return false;
            }
            if(logs[2].contains(".html")){
                return true;
            }
            return false;
        }).map(line -> Stream.of(parseLog(line)).collect(Collectors.joining(" ")))
                .mapToPair(line -> new Tuple2(line, 1));
        accessPairRDD.saveAsTextFile("hdfs://localhost:9000/data/nginx");
    }

    public static String[] parseLog(String log){
        String[] logs = new String[7];
        if(Pattern.matches(pattern, log)){
            Matcher matcher = Pattern.compile(pattern).matcher(log);
            matcher.matches();
            logs[0] = matcher.group(1);
            logs[1] = matcher.group(2);
            logs[2] = matcher.group(3);
            logs[3] = matcher.group(4);
            logs[4] = matcher.group(5);
            logs[5] = matcher.group(6);
            logs[6] = matcher.group(7);
        } else if(Pattern.matches(pattern2, log)){
            Matcher matcher = Pattern.compile(pattern2).matcher(log);
            matcher.matches();
            logs[0] = matcher.group(1);
            logs[1] = matcher.group(2);
            logs[2] = matcher.group(3);
            logs[3] = matcher.group(4);
            logs[4] = matcher.group(5);
            logs[5] = matcher.group(6);
            logs[6] = matcher.group(7);
        }
        return logs;
    }
}