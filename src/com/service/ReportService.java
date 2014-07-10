package com.service;

import java.util.List;
import java.util.Map;

import com.dao.CommonDAO;

public class ReportService {
	
	public CommonDAO cd = new CommonDAO();
	
	public List<Map<String,Object>> getTypeDetail(int userID)
	{
		String sql="SELECT TypeName,COUNT(DetailType) AS COUNT,SUM(DetailPrice*DetailDir) AS PRICE"+
"FROM money_detail,money_type WHERE DetailToWallet=? AND money_type.TypeID=money_detail.DetailType"+
"GROUP BY DetailType";
		List<Map<String,Object>> result = cd.executeQuery(sql, new Object[]{userID});
		return result;
		
	}

}
