package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dao.CommonDAO;
import com.entity.WalletEntity;

public class WalletService {
	
	public CommonDAO cd = new CommonDAO();
	
	
	/**
	 * get wallet list by ID
	 * @param ID
	 * @return
	 */
	public List<WalletEntity> GetWalletByid(int ID)
	{
		String sql="SELECT * FROM MONEY_WALLET WHERE WalletToUser=?";
		List<Map<String,Object>> lm=cd.executeQuery(sql, new Object[]{ID});
		
		List<WalletEntity> li=new ArrayList<>();
		if(lm.size()>0)
		{
			for(int i=0;i<lm.size();i++)
			{
				Map<String,Object> walletMap=lm.get(i);
				WalletEntity we=new WalletEntity();
				we.setWalletID((int)walletMap.get("WalletID"));
				we.setWalletName(walletMap.get("WalletName").toString());
				we.setWalletToUser((int)walletMap.get("WalletToUser"));
				li.add(we);
			}
		}
		else
		{
			
		}
		return li;
	}

}
