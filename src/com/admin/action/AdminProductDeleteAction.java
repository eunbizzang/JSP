package com.admin.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.controller.Action;
import com.shop.controller.ActionForward;
import com.shop.model.ProductDAO;

public class AdminProductDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// get 방식으로 넘어온 제품번호에 해당하는 제품을
		// DB에서 삭제하는 비니지스 로직.
		
		int product_num = Integer.parseInt(request.getParameter("pnum").trim());
		
		ProductDAO dao = ProductDAO.getInstance();
		
		int check = dao.deleteProduct(product_num);
		
		dao.updateSequence(product_num);
		
		ActionForward forward = new ActionForward();
		
		PrintWriter out = response.getWriter();
		
		
		if(check > 0) {
			forward.setRedirect(true);
			forward.setPath("admin_prod_list.do");
		}else {
			out.println("<script>");
			out.println("alert('상품 삭제 실패~~~')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		
		return forward;
		
	}

}
