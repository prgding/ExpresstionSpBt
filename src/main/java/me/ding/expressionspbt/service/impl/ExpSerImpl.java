package me.ding.expressionspbt.service.impl;

import me.ding.expressionspbt.service.ExpSer;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

@Service
public class ExpSerImpl implements ExpSer {
    public static Map<String, String> qaMap = new HashMap<>();
    private static Map<String, Boolean> askedMap = new HashMap<>();
    private int count = 1;
    private static String strange = "";


    static {
        // 读取txt文件内容、分割qa并存入Map
        try {
            File file1 = ResourceUtils.getFile("classpath:input-front50.txt");
            Scanner scanner = new Scanner(file1);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] kv = line.split(":");
                if (kv.length == 2) {
                    qaMap.put(kv[0], kv[1]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 初始化askedMap, 全部设置为未提问(false)
        for (String key : qaMap.keySet()) {
            askedMap.put(key, false);
        }
    }

    public String getQuestion() {
        // 创建一个随机数生成器
        Random random = new Random();
        // 生成一个随机数，获取对应的key和value
        String key = getRandomUnaskedKey(qaMap, askedMap, random, strange);
        askedMap.put(key, true);
        // 输出题目
        return key + " [" + count + "]";
    }

    @Override
    public String checkAnswer(String question, String input) {
        String key = question.split(" \\[")[0];
        if (input.equals(qaMap.get(key))) {
            count++;
            return "正确";
        } else return "错误";
    }

    @Override
    public String pass(String question) {
        String key = question.split(" \\[")[0];
        String value = qaMap.get(key);
        count++;
        return key + ":" + value;
    }

    @Override
    public String strange(String question) {
        String key = question.split(" \\[")[0];
        String value = qaMap.get(key);
        count++;
        return key + ":" + value;
    }

    @Override
    public String getAskedMap() {
        // 返回value为true的key和value
        String result = "";
        for (String key : askedMap.keySet()) {
            if (askedMap.get(key)) {
                result += key + ":" + qaMap.get(key) + "<br>";
            }
        }
        return result;
    }

    @Override
    public String reset() {
        count = 1;
        // 初始化askedMap, 全部设置为未提问(false)
        for (String key : qaMap.keySet()) {
            askedMap.put(key, false);
        }
        return "重置提问成功";
    }

    // 获取一个尚未被提问过的key
    private static String getRandomUnaskedKey(Map<String, String> qaMap, Map<String, Boolean> askedMap, Random random
            , String strange) {
        String[] unaskedKeys = askedMap.keySet().stream().filter(key -> !askedMap.get(key)).toArray(String[]::new);
        if (unaskedKeys.length > 0) {
            return unaskedKeys[random.nextInt(unaskedKeys.length)];
        } else {
            // 如果所有的key都已经被提问过，则重新设置所有key的标记，并返回一个随机的key
            return "全部提问过了，重新开始吧";
        }
    }
}