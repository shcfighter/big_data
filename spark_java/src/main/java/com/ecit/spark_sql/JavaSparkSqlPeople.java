package com.ecit.spark_sql;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;

public class JavaSparkSqlPeople {

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().getOrCreate();

        // 创建一个RDD
        JavaRDD<String> peopleRDD = spark.sparkContext()
                .textFile("hdfs://localhost:9000/data/input/people.txt", 1)
                .toJavaRDD();

        // 使用string定义schema
        String schemaString = "name age";

        // 基于用字符串定义的schema生成StructType
        List<StructField> fields = new ArrayList<>();
        for (String fieldName : schemaString.split(" "))
        {
            StructField field = DataTypes.createStructField(fieldName, DataTypes.StringType, true);
            fields.add(field);
        }

        StructType schema = DataTypes.createStructType(fields);

        // 把RDD (people)转换为Rows
        JavaRDD<Row> rowRDD = peopleRDD.map(record -> {
            String[] attributes = record.split(",");
            return RowFactory.create(attributes[0], attributes[1].trim());
        });

        // 对RDD应用schema
        Dataset<Row> peopleDataFrame = spark.createDataFrame(rowRDD, schema);

        // 使用DataFrame创建临时视图
        peopleDataFrame.createOrReplaceTempView("people");

        // 运行SQL查询
        Dataset<Row> results = spark.sql("SELECT name FROM people");

        // SQL查询的结果是DataFrames类型，支持所有一般的RDD操作
        // 结果的列可以通过属性的下标或者名字获取
        Dataset<String> namesDS = results.map((MapFunction<Row, String>) row -> "Name: " + row.getString(0), Encoders.STRING());

        namesDS.show();
        // +-------------+
        // |        value|
        // +-------------+
        // |Name: Michael|
        // |   Name: Andy|
        // | Name: Justin|
        // +-------------+
    }


}
