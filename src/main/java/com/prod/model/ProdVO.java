package com.prod.model;
import java.sql.Date;

public class ProdVO implements java.io.Serializable
{
	private Integer prodId;
	private String prodName;
	private String prodBrand;
	private Double prodPrice;
	private Date prodRegTime;
	
	public Integer getProdId() {
		return prodId;
	}
	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdBrand() {
		return prodBrand;
	}
	public void setProdBrand(String prodBrand) {
		this.prodBrand = prodBrand;
	}
	public Double getProdPrice() {
		return prodPrice;
	}
	public void setProdPrice(Double prodPrice) {
		this.prodPrice = prodPrice;
	}
	public Date getProdRegTime() {
		return prodRegTime;
	}
	public void setProdRegTime(Date prodRegTime) {
		this.prodRegTime = prodRegTime;
	}
	
	

}
