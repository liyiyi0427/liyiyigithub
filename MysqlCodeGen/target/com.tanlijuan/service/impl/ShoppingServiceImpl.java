package com.tanlijuan.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import cn.synny.common.core.page.Page;;


import com.tanlijuan.po.ShoppingPO;
import com.tanlijuan.dao.ShoppingDao;
import com.tanlijuan.service.ShoppingService;

@Service
public class ShoppingServiceImpl implements ShoppingService {

	private static final Logger log =Logger.getLogger(ShoppingServiceImpl.class);
	
	@Resource
	private  ShoppingDao  shoppingDao;
	

	/**
	 * 查找单个 
	 * @param id 
	 * @return Shopping 
	 */
	public ShoppingPO find(Serializable id){
		return shoppingDao.find(id);	
	}

	/**
	 * 查找所有 
	 * @return List<ShoppingPO> 
	 */
	public List<ShoppingPO> findAll(){
		return shoppingDao.findAll();	
	}

	/**
	 * 创建 
	 * @param po 
	 */
	public void create(ShoppingPO po) throws Exception{
		
			if( po != null )
				 shoppingDao.create(po);
	}

	/**
	 * 修改 
	 * @param po
	 */
	public void update(ShoppingPO po) throws Exception{

			if( po != null )
				 shoppingDao.update(po);

	}

	/**
	 * 删除 
	 * @param id 
	 */
	public void delete(Serializable id) throws Exception{
		 shoppingDao.delete(id);
	}

	/**
	 * 删除 
	 * @param id 
	 */
	public void deletes(Serializable[] id) throws Exception{
		shoppingDao.deletes(id);
	}
	
	@Override
	public void listForPage(Map<String, Object> conds, Page<ShoppingPO> page) {
		page.setEntityOrField(true);
		conds.put("page", page);
		List<ShoppingPO> list = shoppingDao.listForPage(conds);
		page.setList(list);
	}


}