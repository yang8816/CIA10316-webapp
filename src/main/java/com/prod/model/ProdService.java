package com.prod.model;

import java.util.List;

public class ProdService {

	private ProdDAO_interface dao;

	public ProdService() {
		dao = new ProdJDBCDAO();
	}

	public ProdVO addProd(String prodName, String prodBrand, Double prodPrice, java.sql.Date prodRegTime) 
	{

		ProdVO prodVO = new ProdVO();

		prodVO.setProdName(prodName);
		prodVO.setProdBrand(prodBrand);
		prodVO.setProdPrice(prodPrice);
		prodVO.setProdRegTime(prodRegTime);
	
		dao.insert(prodVO);

		return prodVO;
	}

	public ProdVO updateProd(Integer prodId, String prodName, String prodBrand, Double prodPrice, java.sql.Date prodRegTime) {

		ProdVO prodVO = new ProdVO();

		prodVO.setProdId(prodId);
		prodVO.setProdName(prodName);
		prodVO.setProdBrand(prodBrand);
		prodVO.setProdPrice(prodPrice);
		prodVO.setProdRegTime(prodRegTime);
	
		dao.insert(prodVO);

		return prodVO;

	}

	public void deleteProd(Integer prodId) {
		dao.delete(prodId);
	}

	public ProdVO getOneProd(Integer prodId) {
		return dao.findByPrimaryKey(prodId);
	}

	public List<ProdVO> getAll() {
		return dao.getAll();
	}
}
