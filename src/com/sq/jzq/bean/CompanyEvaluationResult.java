package com.sq.jzq.bean;

import java.util.List;

public class CompanyEvaluationResult {

	public Integer Stu;
	public VipResult Rst; // 企业会员

	public class VipResult {
		public List<CompanyPEvaluation> Lst;
	}

	public class CompanyPEvaluation {

		public String T; // 职位名称
		public String PENAME; // 评价人姓名
		public String CS; // 评价内容
		public String ID; // ID
		public String TX; // 头像地址
		public String PEID; // 评价人ID
		public String TS; // 评价时间
		public String JOBINFOID; // 职位id
	}
}
