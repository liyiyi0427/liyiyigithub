package com.tanlijuan.vo;

import java.io.Serializable;
import java.util.Date;


/**
 *  
 * @author codegen 2015-06-11 16:52:35 
 */
public class ShoppingVO {

	/**  **/ 
	private int id;
	
    /**  **/ 
	private int num;
	
    /**  **/ 
	private int goodsid;
	
    /**  **/ 
	private int userid;
	

	/**
	 * 构造 
	 */
	public ShoppingVO() {
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	public int getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}