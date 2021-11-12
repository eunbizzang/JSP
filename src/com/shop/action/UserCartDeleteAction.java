package com.shop.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.controller.Action;
import com.shop.controller.ActionForward;
import com.shop.model.CartDAO;

public class UserCartDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// get 방식으로 넘어온 카트번호에 해당하는 장바구니 내역을
		// DB에서 삭제하는 비지니스 로직.
		
		int cart_num = Integer.parseInt(request.getParameter("num").trim());
		
		CartDAO dao = CartDAO.getInstance();
		
		int check = dao.deleteCart(cart_num);
		
		dao.updateCartNo(cart_num);
		
		ActionForward forward = new ActionForward();
		
		PrintWriter out = response.getWriter();
		
		
		if(check > 0) {
			forward.setRedirect(true);
			forward.setPath("user_cart_list.do");
		}else {
			out.println("<script>");
			out.println("alert('장바구니 삭제 실패~~~')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		
		return forward;
		
	}

}
