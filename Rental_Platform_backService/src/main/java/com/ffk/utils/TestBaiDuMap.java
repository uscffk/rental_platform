package com.ffk.utils;


import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
/**
 * @author 房发科
 * @create 2022/3/4
 */

/**
 * 获取经纬度
 *
 */
@Component
public class TestBaiDuMap {

    private static final double EARTH_RADIUS = 6378.137;// 地球半径,单位千米

    /**
     * 根据经纬度获取地名api
     */
    private static final String GET_ADDRESS_URL = "http://api.map.baidu.com/reverse_geocoding/v3/";

    public Map<String,String> getLngAndLat(String address){
        Map<String,String> map = new HashMap<>();
        String url = "http://api.map.baidu.com/geocoder?address="+address+"&output=json&ak=BO1gWgR8LDt1mmYsaTrvhWFszkBxMmYf";
        String json = loadJSON(url);
        JSONObject obj = JSONObject.fromObject(json);
        if(obj.get("status").toString().equals("OK")){
            double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
            double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
            map.put("lng", lng+"");
            map.put("lat", lat+"");

        }else{

        }
        return map;
    }

    /**
     * 根据经纬度解析详细位置
     * @param lng
     * @param lat
     * @return
     */
    public  String getDetailLocByLngAndLat(String lng, String lat){
        String url = "http://api.map.baidu.com/geocoder?location="+lat+","+lng+"&output=json&ak=BO1gWgR8LDt1mmYsaTrvhWFszkBxMmYf";
        String s = loadJSON(url);
        JSONObject jsonObject = JSONObject.fromObject(s);
        String formatted_address = jsonObject.getJSONObject("result").getString("formatted_address");
        return formatted_address;
    }

    public static String loadJSON (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }



    /**
     * 计算A B两地距离
     * @param A
     * @return
     */
    public double distance(Map<String,String> A,Map<String,String> B){
        double lngA = Double.parseDouble(A.get("lng"));
        double latA = Double.parseDouble(A.get("lat"));
        double lngB = Double.parseDouble(B.get("lng"));
        double latB = Double.parseDouble(B.get("lat"));
        //两点纬度之差
        double latSub = rad(latB) - rad(latA);
        //两点经度之差
        double lngSub = rad(lngB) - rad(lngA);
        //计算距离
        double distance = 2*Math.asin(Math.sqrt(
          Math.pow(Math.sin(latSub/2),2)+Math.cos(rad(latA))*Math.cos(rad(latB))*Math.pow(Math.sin(lngSub/2),2)
        ));
        //弧长乘地球半径（半径）
        distance*=EARTH_RADIUS;
        distance = Math.round(distance*10000)/10000;
        return distance;
    }

    //将返回数据改成double类型单位改成千米
    private static double format(String temp) {
        Double one=Double.parseDouble(temp);
        return (one/1000);
    }

    private static double rad(double d) {
        //角度转换成弧度
        return d * Math.PI / 180.0;
    }
}



