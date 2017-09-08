package com.fz.cdh.pcdd.network.bean;

import com.lidroid.xutils.db.annotation.Id;

import java.io.Serializable;

public class UserInfo implements Serializable {
	
	@Id
	public int _id;
	
	public int id;
	public String account;
	public String user_photo;
	public String nick_name;
	public String personal_sign;
	public int sex;		// 1男 2女
	public String mobile;
	public double point;
	public long create_time;
	public String band_id;		// 绑定识别码
	public int band_type;		// 绑定类型 2微信  3qq
	public String registration_id;	// 极光设备id;
	public String code;			// 邀请码
	public String im_account;	//环信帐号
	public int level;
	public String level_name;	//	会员名称
	public int user_type;
	public long login_time;
	public String withdrawals_password;	//提现密码
	public String real_name;			//开户姓名
	public String bank_name;			//银行名称
	public String bank_no;				//银行卡号
	public String open_card_address;	//开户地址

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
