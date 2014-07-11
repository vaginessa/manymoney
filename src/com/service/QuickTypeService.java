package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.dao.CommonDAO;

public class QuickTypeService {
	public CommonDAO cd=new CommonDAO();
	
	/**
	 * get sys types
	 * @return
	 */
	public String getSysTypehtml()
	{
		String sql="SELECT * FROM MONEY_SYSTYPE WHERE 1";
		List<Map<String,Object>> pList = cd.executeQuery(sql, null);
		StringBuffer buffer = new StringBuffer();
		for(Map<String,Object> map:pList){
			buffer.append(" <li><a href='#nogo'  cid='"+map.get("ID")+"' class='s"+(int)(Math.random()*3+1)+"'>"+map.get("TypeName")+"</a></li>");
		}
		return buffer.toString();
	}
	
	public List<String> getSysTypeList() {
		String sql="SELECT * FROM MONEY_SYSTYPE WHERE 1";
		List<Map<String,Object>> pList = cd.executeQuery(sql, null);
		List<String> list=new ArrayList<String>();
		for(Map<String,Object> map:pList){
			list.add(map.get("TypeName").toString());
		}
		return list;
	}
	
	public String getUserTypeHtml(int id)
	{
		String sql="SELECT * FROM MONEY_TYPE WHERE typetouser=?";
		
		List<Map<String,Object>> pList = cd.executeQuery(sql,new Object[]{id} );
		StringBuffer buffer = new StringBuffer();
		for(Map<String,Object> map:pList){
			if ((int) map.get("TypeDir") == -1)
				buffer.append(" <li><a uid='"+map.get("TypeID")+"' class='xx'>x</a><a href='#nogo' dir='"+map.get("TypeDir")+"' cid='"+map.get("TypeID")+"' class='tt s"+(int)(Math.random()*3+1)+"'>"+map.get("TypeName")+"</a></li>");
			else if ((int) map.get("TypeDir") == 1)
			buffer.append(" <li><a uid='"+map.get("TypeID")+"' class='xx'>x</a><a href='#nogo' dir='"+map.get("TypeDir")+"' cid='"+map.get("TypeID")+"' class='income tt'>"+map.get("TypeName")+"</a></li>");
			
		}
		return buffer.toString();
	}
	/**
	 * insert a type
	 * @param name
	 * @param ID
	 * @return
	 */
	public Boolean addType(String name,String ID,int dir)
	{
		String sql="INSERT INTO MONEY_TYPE (TypeName,TypeToUser,TypeDir) VALUES ('"+name+"',"+ID+","+dir+")";
		int res=cd.executeUpdate(sql, null);
		return res>0;
	}
	
	public Boolean deleteType(int id) {
		String sql="delete FROM  MONEY_TYPE where TypeID=?";
		int res=cd.executeUpdate(sql, new Object[]{id});
		return res>0;
		
	}
	
}
