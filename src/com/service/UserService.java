package com.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.dao.CommonDAO;
import com.entity.AccountEntity;

public class UserService  {

	public CommonDAO cd = new CommonDAO();
	
	/**
	 * 登陆方法
	 * @param account 帐号
	 * @param password 密码
	 * @return
	 */
	public List<Object> Login(String account,String password){
		String sql = "select * from money_user where UserEmail=? and UserPassword=?";
		List<Map<String,Object>> lm = cd.executeQuery(sql, new Object[]{account,password});
		
		List<Object> rl = new ArrayList<Object>();
		
		//登录成功
		if(lm.size()>0){
			Map<String,Object> accountMap = lm.get(0);
			AccountEntity acc = new AccountEntity();
			acc.setID((int)accountMap.get("ID"));
			acc.setEmail(accountMap.get("UserEmail").toString());
			acc.setPassword(accountMap.get("UserPassword").toString());
			if(accountMap.get("NickName")!=null)
			acc.setNickName(accountMap.get("NickName").toString());
			if(accountMap.get("UserBirthday")!=null)
			acc.setBirthday(accountMap.get("UserBirthday").toString());
			acc.setSex((accountMap.get("UserSex").toString()));
			if(accountMap.get("UserImg")!=null)
			acc.setImg(accountMap.get("UserImg").toString());
			rl.add(0,"ok");
			rl.add(1,acc);
			
		}
		//登录不成功 可有有两种愿意，1.帐号不存在 2.密码错误
		else{
			sql=  "select * from money_user where UserEmail=?";
			lm = cd.executeQuery(sql, new Object[]{account});
			if(lm.size()==0){//用户不不存在错误
				System.out.println("this");
				rl.add(0,"notexist");
			}else{//密码错误
				rl.add(0,"Error");
			}
			rl.add(null);
		}
		
		return rl;
	}
	
	/**
	 * 注册方法,
	 * @param account 用户实体
	 * @return
	 */
	public Boolean Register(AccountEntity account)
	{
		
		String sql = "INSERT INTO money_user (UserEmail,UserPassword,NickName,UserBirthday,UserSex,UserImg) VALUEs (?,?,?,?,?,?)";
		Object pattams[]={account.getEmail(),account.getPassword() ,account.getNickName(),account.getBirthday(),account.getSex(),account.getImg()};
		int result=cd.executeUpdate(sql, pattams);
		ininTheUser(account.getEmail());//注册完毕初始化用户
		return result>0;
	}
	
	/**
	 * 初始化用户，添加钱包和基本系统默认类别
	 * @param email
	 * @return
	 */
	private Boolean ininTheUser(String email) {
		 addWallet(email, "默认钱包");
		int ID=getIDbyEmail(email);
		QuickTypeService qts=new QuickTypeService();
		List<String> list=qts.getSysTypeList();
		
		for(int i=0;i<list.size();i++)
		{
			qts.addType(list.get(i),String.valueOf(ID),-1);
			
		}
		
		return true;
		
	}
	
	/**
	 * 给用户添加钱包
	 * @param email
	 * @param wallet
	 * @return
	 */
	public Boolean addWallet(String email,String wallet){
		int ID=getIDbyEmail(email);
		String sql = "INSERT INTO money_wallet (WalletName,WalletToUser) VALUEs (?,?)";
		Object pattams[]={wallet,ID};
		int result=cd.executeUpdate(sql, pattams);
	
		return result>0;
		
	}
	
	/***
	 * 通过邮箱查找用户id
	 * @param email
	 * @return
	 */
	public int getIDbyEmail(String email)
	{
		String sql = "select * from money_user where UserEmail=?";
		List<Map<String,Object>> lm = cd.executeQuery(sql, new Object[]{email});
		
		//登录成功
		if(lm.size()>0){
			
			Map<String,Object> accountMap = lm.get(0);
			return (int)accountMap.get("ID");
			
		}
		return -1;
	}
	
	/**
	 * 检查邮箱是否被占用
	 * @param Email
	 * @return
	 */
	public Boolean CheckEmail(String Email)
	{
		String sql = "select * from money_user where UserEmail=?";
		List<Map<String,Object>> lm = cd.executeQuery(sql, new Object[]{Email});
		if(lm.size()>0)
			System.out.println("用户存在");
		return lm.size()>0;
	}
	
	
	/**
	 * 修改个人信息
	 * @param account
	 * @return
	 */
	public Boolean changeInfoByEmail(AccountEntity account){
		
		String sql ="UPDATE money_user set NickName=?,UserBirthday=?,UserSex=? where UserEmail=?";
		int result=cd.executeUpdate(sql, new Object[]{account.getNickName(),account.getBirthday(),account.getSex(),account.getEmail()});
		return result>0;
		
	}
}
