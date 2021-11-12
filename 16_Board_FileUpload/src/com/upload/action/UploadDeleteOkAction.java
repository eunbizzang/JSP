package com.upload.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.upload.model.UploadDAO;

public class UploadDeleteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 삭제 폼 페이지에서 넘어온 글번호와 비밀번호를 가지고
		// DB에서 게시글을 삭제하는 비지니스 로직.
		
		String upload_pwd = request.getParameter("upload_pwd").trim();
		
		int upload_no = Integer.parseInt(request.getParameter("upload_no").trim());
		
		UploadDAO dao = UploadDAO.getInstance();
		
		int res = dao.deleteUpload(upload_no, upload_pwd);
		
		dao.sequenceUpdate(upload_no);
		
		ActionForward forward = new ActionForward();
		
		PrintWriter out = response.getWriter();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("upload_list.do");
		}else if(res == -1) {
			out.println("<script>");
			out.println("alert('비밀번호가 틀립니다. 학인해 주삼.~~~')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('자료실 게시물 삭제 실패~~~')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
