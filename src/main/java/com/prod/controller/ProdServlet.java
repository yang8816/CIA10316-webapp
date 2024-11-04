package com.prod.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.prod.model.*;

public class ProdServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("prodId");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入商品編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer prodId = null;
				try {
					prodId = Integer.valueOf(str);
				} catch (Exception e) {
					errorMsgs.add("商品編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ProdService prodSvc = new ProdService();
				ProdVO prodVO = prodSvc.getOneProd(prodId);
				if (prodVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("prodVO", prodVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/emp/listOneProd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
				/***************************1.接收請求參數****************************************/
				Integer prodId = Integer.valueOf(req.getParameter("prodId"));
				
				/***************************2.開始查詢資料****************************************/
				ProdService prodSvc = new ProdService();
				ProdVO prodVO = prodSvc.getOneProd(prodId);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("prodVO", prodVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/emp/update_prod_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
Integer prodId = Integer.valueOf(req.getParameter("prodId").trim());
				
String prodName = req.getParameter("prodName");
				String prodNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (prodName == null || prodName.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白");
				} else if(!prodName.trim().matches(prodNameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
String prodBrand = req.getParameter("prodBrand").trim();
				if (prodBrand == null || prodBrand.trim().length() == 0) {
					errorMsgs.add("品牌請勿空白");
				}	
				
				Double prodPrice = null;
				try {
prodPrice = Double.valueOf(req.getParameter("prodPrice").trim());
				} catch (NumberFormatException e) {
					prodPrice = 0.0;
					errorMsgs.add("價格請填數字.");
				}
							
				java.sql.Date prodRegTime = null;
				try {
prodRegTime = java.sql.Date.valueOf(req.getParameter("prodRegTime").trim());
				} catch (IllegalArgumentException e) {
					prodRegTime=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
	

//Integer deptno = Integer.valueOf(req.getParameter("deptno").trim());

				ProdVO prodVO = new ProdVO();
				prodVO.setProdId(prodId);
				prodVO.setProdName(prodName);
				prodVO.setProdBrand(prodBrand);
				prodVO.setProdPrice(prodPrice);
				prodVO.setProdRegTime(prodRegTime);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
req.setAttribute("prodVO", prodVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/update_prod_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProdService prodSvc = new ProdService();
				prodVO = prodSvc.updateProd(prodId, prodName, prodBrand, prodPrice, prodRegTime);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("prodVO", prodVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/emp/listOneProd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
String prodName = req.getParameter("prodName");
				String prodNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (prodName == null || prodName.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白");
				} else if(!prodName.trim().matches(prodNameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
String prodBrand = req.getParameter("prodBrand").trim();
				if (prodBrand == null || prodBrand.trim().length() == 0) {
					errorMsgs.add("品牌請勿空白");
				}
								
				Double prodPrice = null;
				try {
prodPrice = Double.valueOf(req.getParameter("prodPrice").trim());
				} catch (NumberFormatException e) {
					prodPrice = 0.0;
					errorMsgs.add("價格請填數字.");
				}
								
				java.sql.Date prodRegTime = null;
				try {
prodRegTime = java.sql.Date.valueOf(req.getParameter("prodRegTime").trim());
				} catch (IllegalArgumentException e) {
					prodRegTime=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				

				
//Integer deptno = Integer.valueOf(req.getParameter("deptno").trim());

				ProdVO prodVO = new ProdVO();
				prodVO.setProdName(prodName);
				prodVO.setProdBrand(prodBrand);
				prodVO.setProdPrice(prodPrice);
				prodVO.setProdRegTime(prodRegTime);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
req.setAttribute("prodVO", prodVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/addEmp.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ProdService prodSvc = new ProdService();
				prodVO = prodSvc.addProd(prodName, prodBrand, prodPrice, prodRegTime);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
				/***************************1.接收請求參數***************************************/
				Integer prodId = Integer.valueOf(req.getParameter("prodId"));
				
				/***************************2.開始刪除資料***************************************/
				ProdService prodSvc = new ProdService();
				prodSvc.deleteProd(prodId);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/emp/listAllProd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
		}
	}
}
