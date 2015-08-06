package com.sq.jzq.bean;

import java.util.List;

public class EnterpriseResult {

	public Integer Stu;
	public VipResult Rst; // 企业会员

	public class VipResult {
		public List<EnterpriseDetails> Lst;
	}

	public class EnterpriseDetails {
		
		public String CNATURE;// 公司性质
		public String SE; // 公司规模
		public String RE; // 注册时间
		public String CT; // 企业所在地区
		public String CTYPE; // 行业类型
		public String ME; // 联系电话
		public String INN; // 公司介绍
		public String CE; // 名称
		public String PE; // 联系人
		public String CNET; // 网址
		public String LO; // 头像
	}
}
