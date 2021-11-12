package com.upload.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.upload.model.UploadDAO;
import com.upload.model.UploadDTO;

public class UploadContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// get 방식으로 넘어온 글번호에 해당하는 게시글의 상세내역을 조회하여
		// 해당 게시글을 View Page로 이동시키는 비지니스 로직.
		
		int upload_no = Integer.parseInt(request.getParameter("no"));
		
		UploadDAO dao = UploadDAO.getInstance();
		
		// 조회수를 증가시켜 주는 메서드 호출
		dao.uploadHit(upload_no);
		
		// 글번호에 해당하는 상세내역을 조회하는 메서드 호출.
		UploadDTO dto = dao.uploadContent(upload_no);
		
		request.setAttribute("upCont", dto);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		
		forward.setPath("view/upload_content.jsp");
		
		
		return forward;
	}

}
