package com.favor.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.favor.domain.Favor;
import com.favor.model.dao.FavorDAO;

public class RegistServlet extends HttpServlet {
	ServletFileUpload upload;
	//������ �¾��, �� �ν��Ͻ��� �޸𸮿� �ö�ö�
	//�����̳ʷκ��� ȯ�漳�� �������� ���� �޴´�.
	DiskFileItemFactory factory;
	int tempSize = 1*1024*1024;
	String tempPath; //�뷮�� Ŭ ����� �ӽ� ���丮
	String realPath; //������ ������ ����� ���� ���丮
	File saveFile;//���� ����� ���Ͽ� ���� ��ü
	//jsp�� application ���尴ü�� �ڷ�����  ServletContext�̴�.

	FavorDAO dao;
	@Override
	public void init(ServletConfig config) throws ServletException {
		dao = new FavorDAO();
		ServletContext context = config.getServletContext();
		//������������ ���̱� ���� �ڿ��� ��ΰ� �ٲ�� ��� Ŭ���� �ڵ忡�� �����ϸ� �ȵȴ�.
		//����, dd�� �ڿ��� ����ϴ� ����� ��õ�ȴ�.
		String tempDir = config.getInitParameter("tempDir");
		String realDir = config.getInitParameter("realDir");
		
		System.out.print(realDir);
		
		tempPath = context.getRealPath(tempDir);
		realPath = context.getRealPath(realDir);
		
		System.out.println(realPath);
		
		//���ε� �غ�
		factory = new DiskFileItemFactory(tempSize,new File(tempPath)); 
		upload = new ServletFileUpload(factory);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");//�Ķ���� �ѱ� ��������
		res.setCharacterEncoding("utf-8");//��½� �ѱ� ���� ����
		res.setContentType("text/html");
		//���ε� �ǽ�!!
		try {
			
			List<FileItem> params = upload.parseRequest(req);
			Favor dto = new Favor();
			for(int i=0;i<params.size();i++){
				FileItem item = params.get(i);
				
				if(item.isFormField()){//text �Ķ���Ͷ��.
					if(item.getFieldName().equals("lati")){
						dto.setLati(Double.parseDouble(item.getString()));
					}else if(item.getFieldName().equals("lng")){
						dto.setLng(Double.parseDouble(item.getString()));
					}else if(item.getFieldName().equals("name")){
						dto.setName(item.getString("utf-8"));
					}else if(item.getFieldName().equals("content")){
						dto.setContent(item.getString("utf-8"));
					}else if(item.getFieldName().equals("score")){
						dto.setScore(Double.parseDouble(item.getString()));
					}
				}else{ //���̳ʸ� �����̶��.
					
					try {
						item.write(saveFile = new File(realPath+"/"+item.getName()));
						item.delete(); //�ӽ� ���� ����.
						dto.setImg(saveFile.getName());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			//�����ͺ��̽��� ���ڵ� �Ѱ� ����ֱ�
			int result = dao.insert(dto);
			
			PrintWriter out = res.getWriter();
			
			out.print("<script>");
			if(result!=0){
				out.print("alert('��ϼ���');");
				out.print("location.herf=\"/admin/\";");
			}else{
				out.print("alert('��ϼ���');");
				out.print("history.back();");
			}
			out.print("</script>");
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//�ݳ��� �ڿ����� ������ ���⼭ ó������.(DB, Stream�� �ݴ´ٰų� �̷� �۾����� ����)
	@Override
	public void destroy() {
		
	}
	
}
