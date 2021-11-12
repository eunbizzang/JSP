package com.shop.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.controller.Action;
import com.shop.controller.ActionForward;
import com.shop.model.CartDAO;
import com.shop.model.CartDTO;

public class UserCartAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 상품 상세 내역에서 장바구니 버튼을 클릭하면
		// 해당 데이터들을 장바구니 테이블에 저장하는 비지니스 로직.
		
		String cart_name = request.getParameter("p_name").trim();
		
		int cart_price = Integer.parseInt(request.getParameter("p_price").trim());
		
		int cart_pqty = Integer.parseInt(request.getParameter("p_qty").trim());
		
		// type="hidden"으로 넘어온 데이터들도 받아 주어야 한다.
		int cart_num = Integer.parseInt(request.getParameter("p_num").trim());
		
		String cart_pspec = request.getParameter("p_spec").trim();
		
		String cart_pimage = request.getParameter("p_image").trim();
		
		String userId = request.getParameter("userId").trim();
		
		
		CartDTO dto = new CartDTO();
		dto.setCart_pnum(cart_num);
		dto.setCart_userId(userId);
		dto.setCart_pname(cart_name);
		dto.setCart_pqty(cart_pqty);
		dto.setCart_price(cart_price);
		dto.setCart_pspec(cart_pspec);
		dto.setCart_pimage(cart_pimage);
		
		CartDAO dao = CartDAO.getInstance();
		
		int check = dao.insertCart(dto);
		
		ActionForward forward = new ActionForward();
		
		PrintWriter out = response.getWriter();
		
		
		if(check > 0) {
			forward.setRedirect(true);
			forward.setPath("user_cart_list.do");
		}else {
			out.println("<script>");
			out.println("alert('장바구니 저장 실패~~~')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		
		return forward;
	}

}
