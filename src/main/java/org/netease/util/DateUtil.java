package org.netease.util;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {


    public static String getNextSaturdayAfter1930() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());

        // 计算本周五的日期（如果今天是周五且时间在19:30之前，则为今天，否则为下周五）
        LocalDateTime thisFriday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).withHour(19).withMinute(30);

        // 如果当前时间已经超过周五19:30，则获取下周五的日期
        if (now.isAfter(thisFriday)) {
            thisFriday = thisFriday.plusWeeks(1);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 格式化并返回字符串
        return thisFriday.format(formatter);
    }
}
