package com.sq.jzq.bean;

import java.util.List;

public class CompanyResumeCollectionResult {

	public Integer Stu;
	public ResumeCollectionResult Rst;

	public class ResumeCollectionResult {
		public List<ResumeCollection> Lst;
	}

	public class ResumeCollection {

		public String IN; // 求职意向
		public String UID; // 求职者用户ID
		public String TI; // 申请时间
		public String ID; // 条目id
		public String IT; // 兼职时间
		public String NE; // 人员名字

	}
}
