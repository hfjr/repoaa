package com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.utils.yingzt;

import com.fab.core.logger.BaseLogger;
import com.fab.core.util.EncodeSHAUtils;
import com.swetake.util.Qrcode;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by csy on 2016/7/6.
 */
public class QRCodeUtil {
    public static final int SIZE_8 = 8;
    public static final int SIZE_12 = 12;
    public static final int SIZE_15 = 15;
    public static final int SIZE_30 = 30;
    public static final int SIZE_50 = 50;
    public static final int SIZE_100 = 100;

//    private static Image bytesToImage(byte[] bytes) {
//        try {
//            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
////            Iterator<?> readers = ImageIO.getImageReadersByFormatName("png");
////
////            ImageReader reader = (ImageReader) readers.next();
////            Object source = bis;
////
////            ImageInputStream iis = ImageIO.createImageInputStream(source);
////
////            reader.setInput(iis, true);
////            ImageReadParam param = reader.getDefaultReadParam();
////
////            return reader.read(0, param);
//            Image image = ImageIO.read(bis);
//            if (bis != null) {
//                bis.close();
//            }
//            return image;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            BaseLogger.error(ex.getMessage(), ex);
//        }
//        return null;
//    }

    public static byte[] generateQRCode(String content, int size) {
        //生成实际的长度和宽度像素点数, 长宽默认相同
        int width = 145;
        int height = width;
        // 设置偏移量 不设置可能导致解析出错
        int pixoff = 0;
        //单元格大小,默认3
        int unitSize = 3;
        //根据不同的规格确定图片长宽，单元格大小
        switch (size) {
            case SIZE_8:
                width = 100;
                height = width;
                unitSize = 2;
                break;
            case SIZE_12:
                width = 142;
                height = width;
                unitSize = 3;
                break;
            case SIZE_15:
                width = 185;
                height = width;
                unitSize = 4;
                break;
            case SIZE_30:
                width = 300;
                height = width;
                unitSize = 6;
                break;
            case SIZE_50:
                width = 500;
                height = width;
                unitSize = 10;
                break;
            case SIZE_100:
                width = 1000;
                height = width;
                unitSize = 20;
                break;
            default:
                width = 300;
                height = width;
                unitSize = 6;
                break;
        }

        //生成二维码
        try {
            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            qrcodeHandler.setQrcodeVersion(7);
            byte[] contentBytes = content.getBytes("utf-8");
            BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D gs = bufImg.createGraphics();
            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, width, height);
            // 设定图像颜色> BLACK
            gs.setColor(Color.BLACK);

            // 输出内容> 二维码
            if (contentBytes.length > 0 && contentBytes.length < width - 2 * pixoff) {
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
                pixoff = (width - unitSize * codeOut.length) / 2;
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            gs.fillRect(j * unitSize + pixoff, i * unitSize + pixoff, unitSize, unitSize);
                        }
                    }
                }
            } else {
                BaseLogger.error(String.format("QRCode content bytes length = %d,not in [ 0,%d ].", contentBytes.length, (width - 2 * pixoff)));
            }


            //是否生成logo
            InputStream logoIs = QRCodeUtil.class.getClassLoader().getResourceAsStream("logo/logo.png");
            if (logoIs != null) {
                Image logoImg = ImageIO.read(logoIs);
                int widthLogo = logoImg.getWidth(null) > bufImg.getWidth() * 2 / 10 ? (bufImg.getWidth() * 2 / 10) : logoImg.getWidth(null);
                int heightLogo = logoImg.getHeight(null) > bufImg.getHeight() * 2 / 10 ? (bufImg.getHeight() * 2 / 10) : logoImg.getWidth(null);
                //logo放在中心
                int x = (bufImg.getWidth() - widthLogo) / 2;
                int y = (bufImg.getHeight() - heightLogo) / 2;
                gs.drawImage(logoImg, x, y, widthLogo, heightLogo, null);
            }
            gs.dispose();
            bufImg.flush();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufImg, "png", bos);
            byte[] bytes = bos.toByteArray();
            if (bos != null) {
                bos.close();
            }
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            BaseLogger.error(e.getMessage(), e);
        }

        return null;
    }

    public static void main(String[] args) {
        //generateQRCode("http://192.168.13.22:9000/vj/weixin/user/registerwebpage/78egy9m", 30);
    }
}
