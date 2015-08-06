package com.sq.jzq.bean;

import java.util.List;

public class MyAttentionCompanyResult {

	public Integer Stu;
	public AttentionCompanyResult Rst;

	public class AttentionCompanyResult {
		public List<AttentionCompany> Lst;
	}

	public class AttentionCompany {

		public String T; // 公司类别
		public String CE; // 企业名称
		public String A; // 公司所在地区
		public String Id; // 分页id
		public String UID; // 企业id
	}
}
