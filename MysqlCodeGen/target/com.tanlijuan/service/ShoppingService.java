package com.tanlijuan.service;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.Map;

import cn.synny.common.core.page.Page;
import com.tanlijuan.po.ShoppingPO;

public interface ShoppingService {

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
	public void create(ShoppingPO po) throws Exception;

	/**
	 * 修改 
	 * @param po 
	 */
	public void update(ShoppingPO po) throws Exception;

	/**
	 * 删除 
	 * @param id 
	 */
	public void delete(Serializable id) throws Exception;

	/**
	 * 删除 
	 * @param ids 
	 */
	public void deletes(Serializable[] ids) throws Exception;
	
	/**
	 * 分页查询模板
	 * 
	 * @param conds
	 * @return
	 */
	public void listForPage(Map<String, Object> conds, Page<ShoppingPO> page);

}