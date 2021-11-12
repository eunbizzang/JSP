package com.admin.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.controller.Action;
import com.shop.controller.ActionForward;
import com.shop.model.CategoryDAO;

public class AdminCartDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// get 방식으로 넘어온 카테고리 번호에 해당하는
		// 카테고리를 Db에서 삭제하는 비지니스 로직.
		
		int cart_num = Integer.parseInt(request.getParameter("cnum").trim());
		
		CategoryDAO dao = CategoryDAO.getInstance();
		
		int check = dao.deleteCategory(cart_num);
		
		dao.updateCategoryNo(cart_num);
		
		ActionForward forward = new ActionForward();
		
		PrintWriter out = response.getWriter();
		
		
		if(check > 0) {
			forward.setRedirect(true);
			forward.setPath("admin_cart_list.do");
		}else {
			out.println("<script>");
			out.println("alert('카테고리 삭제 실패~~~')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
