package com.zybzyb.liangyuoj.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    /**
     * 缩放图像
     * @param srcImageFile 源图像文件地址
     * @param result       缩放后的图像地址
     * @param scale        缩放比例
     * @param flag         缩放选择:true 放大; false 缩小;
     * @throws IOException IO异常
     */
    public static void scale(String srcImageFile, String result, int scale, boolean flag) throws IOException{
        BufferedImage src = ImageIO.read(new File(srcImageFile));
        int width = src.getWidth();
        int height = src.getHeight();
        if (flag) {
            width = width * scale;
            height = height * scale;
        } else {
            width = width / scale;
            height = height / scale;
        }
        Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        ImageIO.write(tag, "JPEG", new File(result));
    }

}
