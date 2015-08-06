package com.sq.jzq.bean;

import java.util.List;

public class MyMsgCircle {

	public Integer Stu;
	public CircleResult Rst; // 获取消息通知(需登录)圈圈信使

	public class CircleResult {
		public List<MyCircleResult> Lst;
	}

	public class MyCircleResult {
		public String SE; // 发送时间
		public String CT; // 内容
		public String ID; // 主键
		public String SR; // 发送人

	}

}
