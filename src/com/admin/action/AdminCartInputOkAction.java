package com.admin.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.controller.Action;
import com.shop.controller.ActionForward;
import com.shop.model.CategoryDAO;

public class AdminCartInputOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 카테고리 폼 페이지에서 넘어온 데이터들을 DB에 
		// 저장하는 비지니스 로직.
		
		String cart_code = request.getParameter("cart_code").trim();
		
		String cart_name = request.getParameter("cart_name").trim();
		
		CategoryDAO dao = CategoryDAO.getInstance();
		
		int res = dao.insertCategory(cart_code, cart_name);
		
		ActionForward forward = new ActionForward();
		
		PrintWriter out = response.getWriter();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("admin_cart_list.do");
		}else {
			out.println("<script>");
			out.println("alert('카테고리 등록 실패~~~')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
