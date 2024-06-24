package com.neo.common.uilts;


import com.neo.common.entity.enums.DateTimePatternEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtil {

    private static final Object lockObj = new Object();
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
        if (tl == null) {
            synchronized (lockObj) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }

        return tl.get();
    }

    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern) {
        try {
            return getSdf(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static LocalDate fromLocateDate2String(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zone).toLocalDate();
        return localDate;
    }

    //获取n天以前的日期
    public static Date getDayAgo(Integer day) {
        // 获取当前的本地时间，并减去指定的天数(minusDays(day);)
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(day);
        // 获取系统默认的时区
        ZoneId zone = ZoneId.systemDefault();
        // 将本地时间转换为该时区的即时时间
        Instant instant = localDateTime.atZone(zone).toInstant();
        // 将即时时间转换为 Date 类型并返回
        return Date.from(instant);
    }

    public static List<String> getBetweenDate(Date startDate, Date endDate) {
        // 将输入的日期类型转换为LocalDate类型
        LocalDate startLocalDate = fromLocateDate2String(startDate);
        LocalDate endLocalDate = fromLocateDate2String(endDate);
        long numOfDays = ChronoUnit.DAYS.between(startLocalDate, endLocalDate) + 1;
        // 创建一个从起始日期开始，每次递增一天的流，并限制流的长度为天数差
        List<LocalDate> localDateList = Stream.iterate(startLocalDate, date -> date.plusDays(1)).limit(numOfDays).collect(Collectors.toList());
        // 将LocalDate列表转换为日期字符串列表，格式为"yyyy_MM_dd"
        List<String> dateList = localDateList.stream().map(date -> date.format(DateTimeFormatter.ofPattern(DateTimePatternEnum.YYYY_MM_DD.getPattern()))).collect(Collectors.toList());
        return dateList;
    }


}
