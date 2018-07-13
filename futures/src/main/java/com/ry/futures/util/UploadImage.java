package com.ry.futures.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 图片上传统一接口
 * 
 * @author wangguicheng
 *
 */
@Controller
public class UploadImage {
	
	
	@RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> upload(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
       
		// 这个变量是用作返回值的
		Map<String, String> returnMap = new HashMap<String, String>();

		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
		// 得到上传的文件
		MultipartFile mFile = multipartRequest.getFile("picture");
		
		 //文件夹名
		String imaegnames = multipartRequest.getParameter("imaegnames");	
	
		// 得到上传本机的路径
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String date = (String) sdf.format(new Date());

		String path = "G:" + File.separator + "onebuy" + File.separator
				+ imaegnames + File.separator + date + File.separator;

		// 得到上传的文件的文件名
		String photoName = mFile.getOriginalFilename();

		// 判断后缀
		String fileEnd = photoName.substring(photoName.lastIndexOf(".") + 1).toUpperCase();
		if (!("BMP".equals(fileEnd)) && !("PNG".equals(fileEnd)) && !("GIF".equals(fileEnd)) && !("JPG".equals(fileEnd))
				&& !("JPEG".equals(fileEnd))) {
			returnMap.put("msg", "图片仅限于bmp,png,gif,jpeg,jpg格式");
			returnMap.put("code", "2");
			return null;
		}

		if (null == photoName || "".equals(photoName.trim())) {
			returnMap.put("msg", "请选择上传文件！");
			returnMap.put("code", "2");

			return null;
		}

		if (photoName != null) {

			File file = new File(path);
			File parent = file.getParentFile();

			// 如果父路径不存在就创建
			if (!parent.exists()) {
				boolean flag = FileUtil.makeDirectory(file);

				if (!flag) {
					returnMap.put("msg", "文件地址创建失败，请稍后再试！");
					returnMap.put("code", "2");
					return null;
				}
			}

			// 如果子路径不存在就创建
			if (!file.exists()) {

				file.mkdir();
			}
			InputStream inputStream = mFile.getInputStream();
			byte[] b = new byte[5000000];
			int length = inputStream.read(b);// 文件大小长度
			path += File.separator + UUID.randomUUID().toString().replace("-", "")
					+ photoName.substring(photoName.lastIndexOf(".")); // 上传的服务器路径

			// 文件流写到服务器端
			// 判断当前要上传的文件大小
			if (length > 5000000) {
				returnMap.put("msg", "上传文件过大！");
				returnMap.put("code", "2");

				
				return null;
			}

			FileOutputStream outputStream = new FileOutputStream(path);
			outputStream.write(b, 0, length);
			outputStream.flush();

			inputStream.close();
			outputStream.close();// 关闭

		}

		returnMap.put("path", path);
		returnMap.put("code", "1");

		return returnMap;
	}
}
