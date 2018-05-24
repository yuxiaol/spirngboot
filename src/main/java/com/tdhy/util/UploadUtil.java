package com.tdhy.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.springframework.web.multipart.MultipartFile;

import com.tdhy.exception.TdhyRuntimeException;

/** 
 * 上传file工具类
 * @author yuxiaolong@tiandihengye.com
 * @date 2017年10月19日
 */
public class UploadUtil {
	/** 
	 * uploadImg  上传图片
	 * @param MultipartFile filedata
	 * @return String 返回图片名称
	 * @throws TdhyRuntimeException
	 */
	public static String uploadImg(MultipartFile filedata) throws TdhyRuntimeException {
		FileOutputStream outputStream = null;
		OutputStream out = null;
		InputStream inputStream = null;
		try {
			String localPath = BaseSystemConstants.IMG_PATH;
			String localMinPath = BaseSystemConstants.IMG_PATH_MIN;
			File file1 = new File(localPath);
			if(!file1.exists()){
				file1.mkdirs();
			}
			File file2 = new File(localMinPath);
			if (!file2.exists()) {
				file2.mkdirs(); // 如没有则创建一个文件夹
			}
			String contentType = filedata.getContentType();
			String imageType = filedata.getContentType().substring(contentType.indexOf("/") + 1);
			String imageName = UuIdUtil.getUuid();
			String fileName = localPath + imageName + "." + imageType;
			String fileMinName = localMinPath + imageName + "." + imageType;
			inputStream = filedata.getInputStream();
			byte[] buts = input2byte(inputStream);
			outputStream = new FileOutputStream(fileName);
			outputStream.write(buts);
			outputStream.flush();
			Image img = ImageIO.read(new File(fileName));
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			byte[] buffImg = compressPic(buts, width, height);
			out = new FileOutputStream(fileMinName);
			out.write(buffImg);
			out.flush();
			return imageName+"."+imageType;
		} catch (Exception e) {
			e.printStackTrace();
			throw new TdhyRuntimeException("上传图片异常");
		} finally {
			try {
				out.close();
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		int num = 100;
		while ((rc = inStream.read(buff, 0, num)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		swapStream.close();
		return in2b;
	}

	public static byte[] compressPic(byte[] imageByte, int width, int height) throws IOException{
		byte[] inByte = null;
		ByteArrayOutputStream out = null;
		ByteArrayInputStream byteInput = null;
		try {
			byteInput = new ByteArrayInputStream(imageByte);
			Image img = ImageIO.read(byteInput);
			/*
			 *  判断图片格式是否正确
			 */
			if (img.getWidth(null) == -1) {
				return inByte;
			} else {
				int newWidth;
				int newHeight;
				double xishu = 0.0;
				int imgWidth = img.getWidth(null);
				int num1 = 3000,num2 = 2000,num3 = 1000,num4 = 500;
				if (imgWidth > num1) {
					xishu = 7.7;
				} else if (imgWidth > num2 && imgWidth < num1) {
					xishu = 5.5;
				} else if (imgWidth > num3) {
					xishu = 3.3;
				} else if (imgWidth > num4 && imgWidth < num3) {
					xishu = 0.8;
				}
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = ((double) img.getWidth(null)) / (double) width + xishu;
				double rate2 = ((double) img.getHeight(null)) / (double) height + xishu;
				// 根据缩放比率大的进行缩放控制
				double rate = rate1 > rate2 ? rate1 : rate2;
				newWidth = (int) (((double) img.getWidth(null)) / rate);
				newHeight = (int) (((double) img.getHeight(null)) / rate);

				BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
				img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				/*
				 * Image.SCALE_SMOOTH 的缩略算法
				 */
				tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

				ImageWriter imgWrier;
				ImageWriteParam imgWriteParams;
				// 指定写图片的方式为 jpg
				imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
				imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
				// 将压缩后的图片返回字节流
				out = new ByteArrayOutputStream(imageByte.length);
				imgWrier.reset();
				imgWrier.setOutput(ImageIO.createImageOutputStream(out));
				imgWrier.write(null, new IIOImage(tag, null, null), imgWriteParams);
				out.flush();
				inByte = out.toByteArray();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally{
			try {
				out.close();
				byteInput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inByte;
	}
	
	/**
	 * 图片压缩   传入byte[]  返回BufferedImage
	 * @param imageByte
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage compressPicTwo(byte[] imageByte, int width, int height) {
		BufferedImage tag = null;
		try {
			ByteArrayInputStream byteInput = new ByteArrayInputStream(imageByte);
			Image img = ImageIO.read(byteInput);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				return tag;
			} else {
				int newWidth;
				int newHeight;
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = ((double) img.getWidth(null)) / (double) width + 0.2;
				double rate2 = ((double) img.getHeight(null)) / (double) height + 0.2;
				// 根据缩放比率大的进行缩放控制
				double rate = rate1 > rate2 ? rate1 : rate2;
				newWidth = (int) (((double) img.getWidth(null)) / rate);
				newHeight = (int) (((double) img.getHeight(null)) / rate);

				tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_ARGB);

				img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				/*
				 * Image.SCALE_SMOOTH 的缩略算法
				 */
				tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return tag;
	}
	
	/**
	 * String base64 转 byte[]
	 * @param str
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bt;
	}
}
