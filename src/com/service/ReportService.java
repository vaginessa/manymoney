package com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.CommonDAO;

public class ReportService {

	public CommonDAO cd = new CommonDAO();

	/**
	 * 支出分类明细
	 * 
	 * @param userID
	 * @return
	 */
	public JSONArray getOutTypeDetail(int userID) {
		String sql = "SELECT TypeName,COUNT(DetailType) AS COUNT,SUM(DetailPrice) AS PRICE "
				+ "FROM money_detail,money_type WHERE DetailDir=-1 AND DetailToWallet=? AND money_type.TypeID=money_detail.DetailType "
				+ "GROUP BY DetailType";
		List<Map<String, Object>> result = cd.executeQuery(sql,
				new Object[] { userID });

		List<Map> list = new ArrayList<Map>();
		for (Map<String, Object> map : result) {
			String name = map.get("TypeName").toString();
			Float price = Float.parseFloat(map.get("PRICE").toString());
			Map<String, Object> NameAndCount = new HashMap<String, Object>();
			NameAndCount.put("label", name);
			NameAndCount.put("value", price);
			NameAndCount.put("highlight", randomColor());
			NameAndCount.put("color", randomColor());
			list.add(NameAndCount);

		}
		JSONArray json = JSONArray.fromObject(list);
		return json;

	}

	public JSONObject getTenDays(int userID) {
		List<Object> list = new ArrayList<>();
		List<Float> list1=new ArrayList<>();
		List<Float> list2=new ArrayList<>();
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		calendar.add(calendar.DATE, -7);
		for (int i = 0; i < 8; i++) {
			calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
			dateString = formatter.format(date);
			list.add(dateString);
			//System.out.println(dateString);

		}
		
		for(int i=0;i<7;i++)
		{
			//System.out.println(list.get(i));
			list1.add(onedayPrice(list.get(i).toString(), list.get(i+1).toString(), userID,-1));
			list2.add(onedayPrice(list.get(i).toString(), list.get(i+1).toString(), userID,1));
		}
		
		Map<String, Object> map = new HashMap<>();
		List<Object> datasets = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		map1.put("fillColor", "rgba(220,220,220,0.5)");
		map1.put("strokeColor", "rgba(220,220,220,0.8");
		map1.put("highlightFill", "rgba(220,220,220,0.75)");
		map1.put("highlightStroke", "rgba(220,220,220,1)");
		map1.put("data", list1);

		map2.put("fillColor", "rgba(151,187,205,0.5)");
		map2.put("strokeColor", "rgba(151,187,205,0.8");
		map2.put("highlightFill", "rgba(151,187,205,0.75)");
		map2.put("highlightStroke", "rgba(151,187,205,1)");
		map2.put("data", list2);

		datasets.add(map1);
		datasets.add(map2);
		map.put("labels", list);
		map.put("datasets", datasets);
		JSONObject json = JSONObject.fromObject(map);
		return json;

	}
	
	public float onedayPrice(String start,String end,int ID,int dir){
		String sql="select SUM(DetailPrice) AS SUM from money_detail where DetailTime/1000 BETWEEN UNIX_TIMESTAMP(?) AND UNIX_TIMESTAMP(?) AND DetailToWallet =? AND DetailDir=?";
		List<Map<String,Object>>  map=cd.executeQuery(sql, new Object[]{start,end,ID,dir});
		Map<String, Object> x=map.get(0);
		if(x.get("SUM")!=null)
		{
			return Float.parseFloat(x.get("SUM").toString());
		}
		else
		{
			return 0;
		}
	}

	public String randomColor() {
		String r, g, b;
		Random random = new Random();
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();

		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;

		return ("#" + r + g + b);
	}

}
