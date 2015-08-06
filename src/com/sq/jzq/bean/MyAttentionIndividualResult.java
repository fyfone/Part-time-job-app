package com.sq.jzq.bean;

import java.util.List;

public class MyAttentionIndividualResult {

	public Integer Stu;
	public IndividualCompanyResult Rst;

	public class IndividualCompanyResult {
		public List<IndividualCompany> Lst;
	}

	public class IndividualCompany {

		public String U; // 单位(小时、天)
		public String Rt; // 发布时间
		public String A; // 职位所在地区
		public String S; // 薪水(人名币)
		public String N; // 企业名称
		public String Id; // 企业id
	}
}
