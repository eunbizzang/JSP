package com.admin.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shop.controller.Action;
import com.shop.controller.ActionForward;
import com.shop.model.AdminDAO;
import com.shop.model.AdminDTO;

public class AdminLoginOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 관리자 입력폼 창에서 입력한 관리자 아이디가
		// 실제 DB상의 관리자 아이디와 일치 여부를 확인하는 비지니스 로직.
		
		String admin_id = request.getParameter("admin_id").trim();
		
		String admin_pwd = request.getParameter("admin_pwd").trim();
		
		
		AdminDAO dao = AdminDAO.getInstance();
		
		int res = dao.adminCheck(admin_id, admin_pwd);
		
		ActionForward forward = new ActionForward();
		
		PrintWriter out = response.getWriter();
		
		// 세션을 생성하는 방법
		HttpSession session = request.getSession();
		
		if(res > 0) {
			// 관리자인 경우 관리자의 정보를 받아 오자.
			AdminDTO dto = dao.getAdmin(admin_id);
			
			session.setAttribute("adminId", dto.getAdmin_id());
			session.setAttribute("adminName", dto.getAdmin_name());
			
			// 세션에 저장된 정보를 가지고 View Page로 이동하자.
			forward.setRedirect(false);
			
			forward.setPath("admin/admin_main.jsp");
			
		}else if(res == -1) {
			// 아이디는 맞으나 비밀번호가 틀린 경우
			out.println("<script>");
			out.println("alert('관리자 비밀번호가 틀립니다. 확인해 주세요~~~')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			// 존재하지 않는 아이디인 경우
			out.println("<script>");
			out.println("alert('존재하지 않는 관리자입니다. 확인 요망!!!')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
