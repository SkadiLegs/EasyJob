package com.neo.admin.controller;

import com.neo.common.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class TestController {
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    RedisTemplate redisTemplate;
    public static final Integer DEFAULT_ROOT_MENU_ID = 0;
    private static final String ROOT_MENU_NAME = "所有菜单";


    @GetMapping("test")
    public void TestGETContorller(Integer id) {

        redisTemplate.opsForValue().set("testEJKey", "value");
    }

    @PostMapping("/testStr")
    public List<List<String>> groupAnagrams(String string) {
//        String[] strs = new String[]{"", "bat"};
        String[] strs = string.replace("{", "").replace("}", "").split(",");
        //维护是否重复的map
        HashMap<HashSet<Character>, ArrayList<String>> map = new HashMap<>();
        //初始化map
        mapInit(map, strs);
        //将strs转化为ArrayList
        ArrayList<String> strList = new ArrayList<>(Arrays.asList(strs));
        strList.remove(0);
        //遍历strList逐一比较
        for (String str : strList) {
            //标记,如果某个str找到了属于他的分类则target=1
            int target = 0;
            //将String转化为char[]并存入HashSet
            HashSet<Character> set = setAddChar(str);
            //迭代器实现边修改边遍历
            for (Map.Entry<HashSet<Character>, ArrayList<String>> entry : map.entrySet()) {
                //比较转化的set是否与已经在维护的map中的set重复
                if (set.containsAll(entry.getKey())) {
                    //重复则将其存入对应键的值的list中
                    entry.getValue().add(str);
                    target = 1;
                    break;
                }

            }
            if (target != 1) {
                ArrayList<String> tempList = new ArrayList<>();
                tempList.add(str);
                map.put(set, tempList);
            }

        }
        return getMapValue(map);
    }

    //将String转化为char[]并存入HashSet的方法
    public HashSet<Character> setAddChar(String str) {
        char[] strChars = str.toCharArray();
        HashSet<Character> set = new HashSet<>();
        for (char c : strChars) {
            set.add(c);
        }
        return set;
    }

    //遍历map并打印
    public List<List<String>> getMapValue(HashMap<HashSet<Character>, ArrayList<String>> map) {
        List<List<String>> returnList = new ArrayList<>();
        for (Map.Entry<HashSet<Character>, ArrayList<String>> entry : map.entrySet()) {
            entry.getValue().sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.charAt(0) - o2.charAt(0);
                }
            });
            returnList.add(entry.getValue());
        }
        return returnList;
    }

    //给HashMap<HashSet<Character>, ArrayList<String>> 初始化的方法
    public void mapInit(HashMap<HashSet<Character>, ArrayList<String>> map, String[] strs) {
        HashSet<Character> set = setAddChar(strs[0]);
        ArrayList<String> list = new ArrayList<>();
        list.add(strs[0]);
        map.put(set, list);
    }

}
