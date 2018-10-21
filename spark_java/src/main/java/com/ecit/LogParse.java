package com.ecit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogParse {
    public static void main(String[] args) {

        String fileName = "D:\\log.txt";
        LogParse.readFileByLines(fileName);

    }

    public static String parseLine(String str) {
        //125.118.123.24 - - [05/Apr/2018:15:20:03 +0800] "GET /css/dlstyle.css HTTP/1.1" 200 5261 "http://111.231.132.168/register.html" "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36"
        StringBuffer buf = new StringBuffer("");
        String pattern = "^(\\d+\\.\\d+\\.\\d+\\.\\d+)\\s\\-\\s-\\s(\\[[^\\[\\]]+\\])\\s(\\\"(?:[^\"]|\\\")+|-\\\")\\s(\\d{3})\\s(\\d+|-)\\s(\\\"(?:[^\"]|\\\")+|-\\\")\\s(\\\"(?:[^\"]|\\\")+|-\\\")$";
        String pattern2 = "^(\\d+\\.\\d+\\.\\d+\\.\\d+)\\s(\\[[^\\[\\]]+\\])\\s(\\\"(?:[^\"]|\\\")+|-\\\")\\s(\\d{3})\\s(\\d+|-)\\s(\\\"(?:[^\"]|\\\")+|-\\\")\\s(\\\"(?:[^\"]|\\\")+|-\\\")\\s(\\\"-\\\")$";
        String[] logs = new String[7];
        if(Pattern.matches(pattern, str)){
            Matcher matcher = Pattern.compile(pattern).matcher(str);
            System.out.println(matcher.matches());
            logs[0] = matcher.group(1);
            logs[1] = matcher.group(2);
            logs[2] = matcher.group(3);
            logs[3] = matcher.group(4);
            logs[4] = matcher.group(5);
            logs[5] = matcher.group(6);
            logs[6] = matcher.group(7);
        } else if(Pattern.matches(pattern2, str)){
            Matcher matcher = Pattern.compile(pattern2).matcher(str);
            logs[0] = matcher.group(1);
            logs[1] = matcher.group(2);
            logs[2] = matcher.group(3);
            logs[3] = matcher.group(4);
            logs[4] = matcher.group(5);
            logs[5] = matcher.group(6);
            logs[6] = matcher.group(7);
        }
        System.out.println(Stream.of(logs).collect(Collectors.joining(" ")));
        System.out.println("================================");
        return buf.toString();
    }

    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            //一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                //显示行号
                parseLine(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}