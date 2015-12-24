package com.tanlijuan.controller;


import javax.annotation.Resource;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tanlijuan.po.ShoppingPO;
import com.tanlijuan.vo.ShoppingVO;
import com.tanlijuan.service.ShoppingService;
import cn.synny.common.core.page.Model;
import cn.synny.common.core.page.Page;

@Controller
@RequestMapping("/com/tanlijuan/shopping")
public class ShoppingController {

	private static final Logger log =Logger.getLogger(ShoppingController.class);
	@Resource
	private  ShoppingService  shoppingService;

    /**
	 * shopping 列表视图
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listShoppingView() {
		log.debug("request page: /sysset/shopping/shopping_list");
		return "sysset/shopping/shopping_list";
	}
	/**
	 * shopping 列表数据
	 * @param reqs
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Model listShoppingActoin(@RequestParam Map<String, Object> reqs, Page<ShoppingPO> page) {
		log.debug("request data: listShopping");
		Model model = new Model();
		try {
			shoppingService.listForPage(reqs, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.setPage(page);
		return model;
	}

	/**
	 * shopping 添加
	 * 
	 * @param shoppingVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Model addShoppingActoin(ShoppingVO shoppingVO) {
		Model model = new Model();
		model.set("msg", "true");
		try {
			ShoppingPO shoppingPO = new ShoppingPO();
			BeanUtils.copyProperties(shoppingPO, shoppingVO);
			shoppingService.create(shoppingPO);
		} catch (Exception e) {
			e.printStackTrace();
			model.set("msg", "false");
		}
		return model;
	}

	/**
	 * shopping 修改
	 * 
	 * @param shoppingVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Model updateShoppingActoin(ShoppingVO shoppingVO) {
		Model model = new Model();
		model.set("msg", "true");
		try {
			ShoppingPO shoppingPO = new ShoppingPO();
			BeanUtils.copyProperties(shoppingPO, shoppingVO);
			shoppingService.update(shoppingPO);
		} catch (Exception e) {
			e.printStackTrace();
			model.set("msg", "false");
		}
		return model;
	}

	/**
	 * shopping 删除
	 * 
	 * @param shoppingIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json")
	public Model delShoppingAction(@RequestParam("shoppingIds") String shoppingIds) {
		Model model = new Model();
		model.set("msg", "true");
		try {
			shoppingService.deletes(shoppingIds.split(","));
		} catch (Exception e) {
			e.printStackTrace();
			model.set("msg", "false");
		}
		return model;
	}


}