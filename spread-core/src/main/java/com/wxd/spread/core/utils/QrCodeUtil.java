package com.wxd.spread.core.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * 二维码工具类
 * 
 * @author wangxiaodan
 *
 */
public class QrCodeUtil {

	/**
	 * 将图片内容写入到文件中
	 * @param matrix
	 * @param format
	 * @param file
	 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	/**
	 * 将图片内容写入到流中
	 * @param matrix
	 * @param format	 二维码的图片格式 例如jpg
	 * @param stream
	 * @throws IOException
	 */
	public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}

	
	/**
	 * 生成二维码
	 * @param text	二维码内容
	 * @return
	 * @throws WriterException 
	 */
	public static BitMatrix genQR(String text) throws WriterException {
		int width = 300; // 二维码图片宽度
		int height = 300; // 二维码图片高度

		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码

		return new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
	}
	

	/**
	 * 将google对象转换为java的图片对象
	 * @param matrix
	 * @return
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
}
