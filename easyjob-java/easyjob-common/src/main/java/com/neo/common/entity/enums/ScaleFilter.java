package com.neo.common.entity.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ScaleFilter {
    private static final Logger logger = LoggerFactory.getLogger(ScaleFilter.class);

    public static Boolean createThumbnail(File file, int thumbnailWidth, int thumbnailHeight, File targetFile) {
        try {
            BufferedImage src = ImageIO.read(file);
            //thumbnailWidth 缩略图的宽度   thumbnailHeight 缩略图的高度
            int sorceW = src.getWidth();
            int sorceH = src.getHeight();
            //小于 指定高宽不压缩
            if (sorceW <= thumbnailWidth) {
                return false;
            }
            int height; // 目标文件的高度
            if (sorceW > thumbnailWidth) { // 目标文件宽度大于指定宽度
                height = thumbnailWidth * sorceH / sorceW;
            } else {// 目标文件宽度小于指定宽度 那么缩略图大小就跟原图一样大
                thumbnailWidth = sorceW;
                height = sorceH;
            }
            BufferedImage dst = new BufferedImage(thumbnailWidth, height, BufferedImage.TYPE_INT_RGB);
            Image scaleImage = src.getScaledInstance(thumbnailWidth, height, Image.SCALE_SMOOTH);
            Graphics2D g = dst.createGraphics();
            g.drawImage(scaleImage, 0, 0, thumbnailWidth, height, null);
            g.dispose();
            int resultH = dst.getHeight();
            // 高度过大的，裁剪图片
            if (resultH > thumbnailHeight) {
                resultH = thumbnailHeight;
                dst = dst.getSubimage(0, 0, thumbnailWidth, resultH);
            }
            ImageIO.write(dst, "JPEG", targetFile);
            return true;
        } catch (Exception e) {
            logger.error("生成缩略图失败");
        }
        return false;
    }


    public static void main(String[] args) {
        createThumbnail(new File("C:\\Users\\Administrator\\Pictures\\20180316193912_VJWJG11.jpeg"), 400, 200,
                new File("C:\\Users\\Administrator\\Pictures\\20180316193912_VJWJG12.jpeg"));
    }
}