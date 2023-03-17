package kr.or.ddit.prod.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import kr.or.ddit.vo.BuyerVO;

public class OthersDAOImplTest {

	private OthersDAO dao = new OthersDAOImpl();
	@Test
	public void testSelectLprodList() {
		List<Map<String, Object>> LprodList = dao.selectLprodList();
		LprodList.stream().forEach(System.out::println);
	}

	//@Test
	public void testSelectBuyerList() {
		List<BuyerVO> BuyerList = dao.selectBuyerList(null);
		BuyerList.stream().forEach(System.out::println);
		BuyerList = dao.selectBuyerList("P101");
		BuyerList.stream().forEach(System.out::println);
		
	}

}
