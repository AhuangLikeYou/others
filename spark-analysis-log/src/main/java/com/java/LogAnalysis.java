package com.java;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

/**
 * spark分析本地日志
 */
public class LogAnalysis {

  public static void main(String[] args) throws Exception {

    SparkConf conf = new SparkConf();
    conf.setAppName("interfaceLogs");
    conf.setMaster("local[*]");
    JavaSparkContext jsc = new JavaSparkContext(conf);
    jsc.setLogLevel("ERROR");

    String filePath =
        new File("").getCanonicalPath() + "/spark-analysis-log/src/main/resources/test.log";
    JavaRDD<String> rdd1 = jsc.textFile(filePath);

    String url = "访问接口:/animals/dog";

    JavaPairRDD<String, Integer> jpr1 = rdd1
        //过滤
        .filter(line -> line.contains(url))
        //转换
        .flatMap(line -> Arrays.asList(line.split(" ")).iterator())
        //过滤转换结果
        .filter(line -> line.contains("是否命中：")
        )
        .mapToPair(word -> new Tuple2<>(word, 1));

    List<Tuple2<String, Integer>> jprAll = jpr1.reduceByKey((x, y) -> x + y).collect();
    HashMap<String, Integer> map = new HashMap<>();
    for (Tuple2 t : jprAll) {
      System.out.println(t._1.toString() + " " + t._2.toString());
    }
    jsc.stop();
    jsc.close();
  }
}