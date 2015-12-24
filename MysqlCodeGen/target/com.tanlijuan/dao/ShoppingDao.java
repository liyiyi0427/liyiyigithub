package com.tanlijuan.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tanlijuan.po.ShoppingPO;

/**
 *  
 * @author codegen 2015-06-11 16:52:35 
 */
public interface ShoppingDao{

	/**
	 * 查找单个 
	 * @param id 
	 * @return Shopping 
	 */
	public ShoppingPO find(Serializable id);

	/**
	 * 查找所有 
	 * @return List<ShoppingPO> 
	 */
	public List<ShoppingPO> findAll();

	/**
	 * 创建 
	 * @param po 
	 */
	public void create(ShoppingPO po);

	/**
	 * 修改 
	 * @param po 
	 */
	public void update(ShoppingPO po);

	/**
	 * 删除 
	 * @param id 
	 */
	public void delete(Serializable id);
	
	/**
	 * 查找所有 
	 * 注此方法后台用
	 */
	public List<ShoppingPO> listForPage(Map<String, Object> conds);
	
	/**
	 * 组删除 
	 * @param id 
	 */
	public void deletes(Serializable[] id);

}