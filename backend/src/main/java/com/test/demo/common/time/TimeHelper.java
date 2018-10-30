package com.test.demo.common.time;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeHelper {
    public JSONObject dealSecondToTimeLook(String diff) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        long diffTime = Long.parseLong(diff);
        int days = (int) (diffTime / (24 * 60 * 60));
        if (days > 0) {
            jsonObject.put("timeFlag", 3);
            jsonObject.put("time", days);
            jsonObject.put("lastSeen", "always " + days + " d");
        } else {
            diffTime = (diffTime % (24 * 60 * 60));
            int hours = (int) (diffTime / (60 * 60));
            if (hours > 0) {
                jsonObject.put("timeFlag", 2);
                jsonObject.put("time", hours);
                jsonObject.put("lastSeen", "always " + hours + " h");
            } else {
                diffTime = (int) (diffTime % (60 * 60));
                int min = (int) (diffTime / (60));
                if (min > 0) {
                    jsonObject.put("timeFlag", 1);
                    jsonObject.put("time", min);
                    jsonObject.put("lastSeen", "Appeared " + min + " minutes");

                } else {
                    jsonObject.put("timeFlag", 0);
                    jsonObject.put("time", "within one minutes");
                    jsonObject.put("lastSeen", "within one minutes");
                }
            }


        }
        return jsonObject;

    }
    public JSONArray sortDate(JSONArray array) throws JSONException {
        for (int i = 1; i < array.size(); i++) {
            for (int j = i; j > 0; j--) {
                JSONObject front = array.getJSONObject(j - 1);
                JSONObject behind = array.getJSONObject(j);
                if (front.getInt("dateLastActivity") > behind.getInt("dateLastActivity")) {
                    JSONObject temp = behind;
                    array.element(j, front);
                    array.element(j - 1, temp);
                }
            }
        }
        return array;
    }
    public String calcDiff(long now,long before){
        return String.valueOf((now-before)/1000);
    }
    public int calcMinDiff(long now,long before){
        int seconds = 60;
//        int seconds = 1;
        return Integer.parseInt(calcDiff(now, before))/seconds;
    }
    public String dealWithTimeAndDiff(String before,String timeFormat) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(timeFormat);
        Date beforeTime = df.parse(before);
        return calcDiff(systemTime2UTC(),beforeTime.getTime());
    }
    public String UTC2GMT8(SimpleDateFormat df,String before) throws ParseException {
        Date beforeTime = df.parse(before);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT-8")) ;
        return format.format(new Date(beforeTime.getTime()));
    }
    public long systemTime2UTC(){
        Calendar cal = Calendar.getInstance() ;
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTimeInMillis();
    }
    public String time2GMT8(String before,String timeFormat) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(timeFormat);
        return UTC2GMT8(df,before);
    }
    public static String timeStamp2Date(String millSeconde,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(millSeconde)));
    }
    public static String month2AbbreviateEnglish(String month){
        int a = Integer.parseInt(month);
        String[] abbreviateMonth =new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        return abbreviateMonth[a];
    }
}
