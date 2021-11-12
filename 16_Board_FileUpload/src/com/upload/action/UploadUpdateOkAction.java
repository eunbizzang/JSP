package com.upload.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.upload.model.UploadDAO;
import com.upload.model.UploadDTO;

public class UploadUpdateOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 자료실 수정 폼 페이지에서 넘어온 데이터들을
		// DB에 저장하는 비지니스 로직.
		
		UploadDTO dto = new UploadDTO();
		
		// 첨부파일이 저장될 경로(위치)
		String saveFolder = 
				"C:\\NCS\\workspace(jsp)\\16_Board_FileUpload\\WebContent\\upload";
		
		// 첨부파일 최대 크기
		int fileSize = 10 * 1024 * 1024;   // 10MB
		
		// 파일 업로드 진행 시 이진파일 업로드를 위한 객체 생성.
		MultipartRequest multi = new MultipartRequest(
					request, 
					saveFolder, 
					fileSize,
					"UTF-8",
					new DefaultFileRenamePolicy()
		);
		
		// 자료실 수정 폼 페이지에서 넘어온 데이터들을 받아 주자.
		String upload_writer = multi.getParameter("upload_writer").trim();
		
		String upload_title = multi.getParameter("upload_title").trim();
		
		String upload_content = multi.getParameter("upload_content").trim();
		
		String upload_pwd = multi.getParameter("upload_pwd").trim();
		
		// type="file"로 넘어온 데이터는 getFile() 메서드로 받아주어야 한다.
		File upload_file = multi.getFile("upload_file");
		
		// 히든으로 넘어온 데이터들도 받아주어야 한다.
		int upload_no = 
			Integer.parseInt(multi.getParameter("upload_no").trim());
		
		if(upload_file != null) {
			
			String fileName = upload_file.getName();
			
			Calendar cal = Calendar.getInstance();
			
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			
			//......./upload/2021-11-05
			String homedir = saveFolder+"/"+year+"-"+month+"-"+day;
			
			// 날짜 폴더를 만들어야 한다.
			File path1 = new File(homedir);
			if(!path1.exists()) {
				path1.mkdir();
			}
			
			// 파일을 만들어 보자.
			// 파일은 "작성자_파일명"
			//............/upload/2021-11-05/작성자_파일명
			String reFileName = upload_writer+"_"+fileName;
			
			upload_file.renameTo(new File(homedir+"/"+reFileName));
			
			String fileDBName = 
					"/"+year+"-"+month+"-"+day+"/"+reFileName;
			
			dto.setUpload_file(fileDBName);
		}
		
		dto.setUpload_no(upload_no);
		dto.setUpload_writer(upload_writer);
		dto.setUpload_title(upload_title);
		dto.setUpload_cont(upload_content);
		dto.setUpload_pwd(upload_pwd);
		
		UploadDAO dao = UploadDAO.getInstance();
		
		int res = dao.updateUpload(dto);
		
		ActionForward forward = new ActionForward();
		
		PrintWriter out = response.getWriter();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("upload_content.do?no="+upload_no);
		}else if(res == -1) {
			out.println("<script>");
			out.println("alert('비밀번호가 틀립니다. 확인해 주세요.~~~')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('자료실 수정 실패~~~')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
