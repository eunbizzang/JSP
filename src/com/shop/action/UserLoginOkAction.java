package com.shop.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shop.controller.Action;
import com.shop.controller.ActionForward;
import com.shop.model.UserDAO;
import com.shop.model.UserDTO;

public class UserLoginOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 사용자 로그인 폼 페이지에서 넘어온 아이디와 비밀번호를 가지고
		// DB에서 확인하여 회원 여부를 확인하는 비지니스 로직.
		
		String user_id = request.getParameter("user_id").trim();
		
		String user_pwd = request.getParameter("user_pwd").trim();
		
		UserDAO dao = UserDAO.getInstance();
		
		int check = dao.userCheck(user_id, user_pwd);
		
		ActionForward forward = new ActionForward();
		
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		
		
		if(check > 0) {
			
			UserDTO dto = dao.getMember(user_id);
			
			// 세션 정보 저장하는 방법
			session.setAttribute("userId", dto.getMemid());
			session.setAttribute("userName", dto.getMemname());
			
			forward.setRedirect(true);			
			forward.setPath("user_main.do");  // 메인 페이지로 이동
			
		}else if(check == -1) {
			out.println("<script>");
			out.println("alert('비밀번호가 틀립니다. 확인 요망~~~')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('없는 아이디입니다. 다시 한번 아이디를 확인하세요~~~')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
