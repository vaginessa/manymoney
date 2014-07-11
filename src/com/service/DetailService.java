	package com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dao.CommonDAO;
import com.entity.DetailEntity;
import com.entity.WalletEntity;

public class DetailService {
	
	public CommonDAO cd = new CommonDAO();
	
	//insert a detail
	public Boolean AddDetail(DetailEntity detail)
	{
		String sql = "INSERT INTO money_detail (DetailType,DetailDir,DetailPrice,DetailTime,DetailToWallet,DetailCommont) VALUES (?,?,?,?,?,?)";
		Object pattams[]={detail.getDetailType(),detail.getDetailDir(),detail.getDetailPrice(),detail.getDetailDate(),detail.getDetailTowallet(),detail.getDetailCommont()};
		int result=cd.executeUpdate(sql, pattams);
		return result>0;
	}
	
	public List<DetailEntity> getListbyWalletID(int ID)
	{
		String sql="SELECT * FROM MONEY_DETAIL WHERE DETAILTOWALLET=? order By DetailTime desc";
		List<Map<String,Object>> lm=cd.executeQuery(sql, new Object[]{ID});
		
		List<DetailEntity> li=new ArrayList<>();
		
		if(lm.size()>0)
		{
			for(int i=0;i<lm.size();i++)
			{
				Map<String,Object> walletMap=lm.get(i);
				DetailEntity de=new DetailEntity();
				
				de.setDetailID(Integer.parseInt(walletMap.get("DetailID").toString()));
				de.setDetailCommont(walletMap.get("DetailCommont").toString());
				de.setDetailDate(Long.parseLong(walletMap.get("DetailTime").toString()));
				de.setDetailDir(Integer.parseInt(walletMap.get("DetailDir").toString()));
				de.setDetailPrice(Float.parseFloat(walletMap.get("DetailPrice").toString()));
				de.setDetailType(Integer.parseInt(walletMap.get("DetailType").toString()));
				li.add(de);
			}
		}
		else
		{
			
		}
		return li;
	}

}
