package cn.club.biz.test;
import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


import cn.club.biz.utils.page.Page;
import com.tanlijuan.po.ShoppingPO;
import com.tanlijuan.service.ShoppingService;

/**
 * @author xuyh
 * @date 2013-10-16上午10:15:50
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-service.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ShoppingServiceTest {
	@Resource
	private ShoppingService shoppingService;
	String id = "642b0f145cb445c28e9cde6625bcd9ad";
	Date now = new Date();

	@Test
	public void testFind() {
		ShoppingPO po = shoppingService.find(id);
		Assert.assertEquals("a", po.getName());
	}

	@Test
	public void testFindAll() {
		List<ShoppingPO> all = shoppingService.findAll();
		Assert.assertEquals(1, all.size());
	}

	@Test
	public void testCreate() {
		ShoppingPO basePO = new ShoppingPO(id, "a", "b", now, now, null, "1");
		try {
			shoppingService.create(basePO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true);
		}
	}

	@Test
	public void testUpdate() {
		ShoppingPO basePO = shoppingService.find(id);
		basePO.setUpdateTime(now);
		try {
			shoppingService.update(basePO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true);
		}
	}

	@Test
	public void testDelete() {
		try {
			shoppingService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true);
		}
	}

	@Test
	public void testDeletes() {
		String[] ids = new String[] { id };
		try {
			shoppingService.deletes(ids);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(true);
		}
	}

	@Test
	public void testListForPage() {
		Map<String, Object> reqs = new HashMap<String, Object>();
		reqs.put("name", "a");
		reqs.put("title", "b");
		Page<ShoppingPO> page = new Page<ShoppingPO>();
		shoppingService.listForPage(reqs, page);
		Assert.assertEquals(1, page.getTotal());
	}
}