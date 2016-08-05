package cn.edu.hfut.dmic.webcollector.util;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GeoCodingUtils 
{
	public static String ak = "jHNGIKURNveqOeyKlvccI8ts";
	private static Logger log = LoggerFactory.getLogger(GeoCodingUtils.class);
	
	public static void main(String[] args)
	{
		String address="大兴区北京亦庄经济开发区荣华中路8号华联力宝购物中心一层45号（华联购物中心南门外右侧）";
		Map<String,Object> result =geoDecodingFromBaidu(address);
		if(!result.containsKey("status"))
		{
			int end = address.indexOf("（");
			if(end>0)
				result =geoDecodingFromBaidu(address.split("（")[0]);
		}
		log.info(result.toString());
	}
	
	public static Map<String,Object> geoDecodingFromBaidu(String location)
	{
		log.info(location);
		if(location.contains("（"))
			location = location.substring(0,location.indexOf("（"));
		Map<String,Object> resultMap = new HashMap<String,Object>();
	    String url = "http://api.map.baidu.com/geocoder/v2/?address="+location+"&output=json&ak=" + ak;
	     //1.地理编码服务，即根据地址来查经度纬度
	    try
	    {
	    	String return_value = HttpUtils.do_get(url);
	    	JSONObject map = (JSONObject) JSONValue.parse(return_value);
			Object status = map.get("status");
			String statusStr = status.toString();

			if(statusStr.trim().equals("0"))
			{
				Object result = map.get("result");
				JSONObject map2 = (JSONObject) JSONValue.parse(result.toString());
				Object confidenceObj = map2.get("confidence");
				if(Integer.valueOf(confidenceObj.toString().trim())>=0)
				{
					Object locationObj = map2.get("location");
					JSONObject map3 = (JSONObject) JSONValue.parse(locationObj.toString());
					Object lng = map3.get("lng");
					Object lat = map3.get("lat");
					if(lat!=null && lng != null)
					{
						resultMap.put("status", 1);
						resultMap.put("lat", lat);
						resultMap.put("lng", lng);
					}
				}
			}
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	resultMap.put("status", -1);
	    	return resultMap;
	    }
	    return resultMap;
	}
	
//	public static String getPostUrl(String key,String address)
//	{
//		StringBuilder sb = new StringBuilder();
//		sb.append("http://restapi.amap.com/v3/geocode/geo?address=");
//		sb.append(address);
//		sb.append("&output=json&key=");
//		sb.append(key);
//		
//		String url = sb.toString();
//		return url;
//	}
	
//	private static final double EARTH_RADIUS = 6378.137;  	//单位km
//    //两点计算距离，传入两点的经纬度
//    public static double getPointDistance(double lat1,double lng1,double lat2,double lng2)
//    {  
//        double result = 0 ;  
//          
//        double radLat1 = radian(lat1);  
//          
//        double ratlat2 = radian(lat2);  
//        double a = radian(lat1) - radian(lat2);  
//        double b = radian(lng1) - radian(lng2);  
//          
//        result = 2*Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(ratlat2)*Math.pow(Math.sin(b/2), 2)));  
//        result = result*EARTH_RADIUS;     
//      
//        result = Math.round(result*1000)/1000.0;   //返回的单位是米，四舍五入  
//          
//        return result;  
//    }  
//      
//    //由角度转换为弧度 
//    private static double radian(double d)
//    {  
//        return (d*Math.PI)/180.00;  
//    } 
}
