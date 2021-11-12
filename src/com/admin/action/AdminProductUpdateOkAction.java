package com.admin.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.shop.controller.Action;
import com.shop.controller.ActionForward;
import com.shop.model.ProductDAO;
import com.shop.model.ProductDTO;

public class AdminProductUpdateOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 상품 수정 폼 페이지에서 넘어온 데이터들을 
		// DB에 저장하는 비지니스 로직.
		
		// 첨부파일 저장될 위치(경로) 설정.
		String saveFolder = "C:\\NCS\\workspace(jsp)\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\18_ShoppingMall\\upload";
		
		// 첨부파일 용량(크기) 제한 - 파일 업로드 최대 크기
		int fileSize = 5 * 1024 * 1024;        // 5MB
		
		// 이진 파일 업로드를 위한 객체 생성
		MultipartRequest multi = new MultipartRequest(
					request, 
					saveFolder, 
					fileSize,
					"UTF-8",
					new DefaultFileRenamePolicy()
		);
	
		
		// 수정 폼 페이지에서 넘어온 데이터들을 받아주어야 한다.
		String p_category = multi.getParameter("p_category").trim();
		
		String p_name = multi.getParameter("p_name").trim();
		
		String p_company = multi.getParameter("p_company").trim();
		
		int p_qty = Integer.parseInt(multi.getParameter("p_qty").trim());
		
		int p_price = Integer.parseInt(multi.getParameter("p_price").trim());
		
		String p_spec = multi.getParameter("p_spec").trim();
		
		String p_content = multi.getParameter("p_content").trim();
		
		int p_point = Integer.parseInt(multi.getParameter("p_point").trim());
		
		int p_num = Integer.parseInt(multi.getParameter("p_num").trim());
		
		String p_image_New = multi.getFilesystemName("p_image_New");
		
		if(p_image_New == null) {  // 새로운 첨부파일 이미지가 없는 경우
			p_image_New = multi.getParameter("p_image_Old");
		}
		
		
		ProductDTO dto = new ProductDTO();
		dto.setPnum(p_num);
		dto.setPname(p_name);
		dto.setPcategory_fk(p_category);
		dto.setPcompany(p_company);
		dto.setPimage(p_image_New);
		dto.setPqty(p_qty);
		dto.setPrice(p_price);
		dto.setPspec(p_spec);
		dto.setPcontent(p_content);
		dto.setPoint(p_point);
		
		
		ProductDAO dao = ProductDAO.getInstance();
		
		int check = dao.updateProduct(dto);
		
		ActionForward forward = new ActionForward();
		
		PrintWriter out = response.getWriter();
		
		
		if(check > 0) {
			forward.setRedirect(true);
			forward.setPath("admin_prod_list.do");
		}else {
			out.println("<script>");
			out.println("alert('상품 정보 수정 실패~~~')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
