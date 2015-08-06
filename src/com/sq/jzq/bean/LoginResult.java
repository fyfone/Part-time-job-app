package com.sq.jzq.bean;

public class LoginResult {

	public Integer Stu;
	public LongResult Rst; // 企业会员

	public class LongResult {
		public Integer Scd;// 返回状态值
		public String Msg; // 返回字符串
		public String Sid; // sid手机号
		public String Etype; // 用户类型
		public String PH; // 用户头像
	}

}
