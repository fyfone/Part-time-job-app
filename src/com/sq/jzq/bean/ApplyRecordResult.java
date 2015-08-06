package com.sq.jzq.bean;

import java.util.List;

public class ApplyRecordResult {

	public Integer Stu;
	public ApplyResult Rst;

	public class ApplyResult {
		public List<ApplyRecord> Lst;
	}

	public class ApplyRecord {

		public String U; // 单位
		public String Rt; // 职位发布时间
		public String ST; // 申请时间
		public String T; // 职位名称
		public String Cn; // 企业名称
		public String FT; // 职位分类
		public String A; // 职位所在地
		public String S; // 薪水
		public String Id; // 申请信息编号
		public String Jid; // 职位信息编号
	}
}
