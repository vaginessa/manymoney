package com.service;

import java.util.List;
import java.util.Map;

import com.dao.CommonDAO;

public class PrintToolService {
	
	public CommonDAO cd=new CommonDAO();
	public String PrintDir(int dir)
	{
		if(dir==1)
			return "<span color='green'>收入</span>";
		else if(dir==-1)
		{
			return "<span style='color:green'>支出</span>";
		}
		return "";
	}
	
	public String printType(int typeID)
	{
		String sql="SELECT TypeName FROM MONEY_TYPE WHERE TypeID=?";
		
		List<Map<String,Object>> pList = cd.executeQuery(sql,new Object[]{typeID} );
		String name=null;
		if(pList.size()>0)
		{
			name=pList.get(0).get("TypeName").toString();
		}
		return name;
	}

}
