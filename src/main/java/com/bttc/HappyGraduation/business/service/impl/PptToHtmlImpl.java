package com.bttc.HappyGraduation.business.service.impl;

import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author DK
 * @create 2018-06-22 11:15
 */
@Service
public class PptToHtmlImpl {
    /*public static void main(String[] args) {
        // 读入PPT文件
        File file = new File("C:/Users/DK/Desktop/test.pptx");
        doPPTtoImage(file);
    }*/

    public boolean doPPTtoImage(File file) {
        boolean isppt = checkFile(file);
        if (!isppt) {
            System.out.println("This file is not in the specified format!");
            return false;
        }
        try {

            FileInputStream is = new FileInputStream(file);
            SlideShow ppt = new SlideShow(is);
            is.close();
            Dimension pgsize = ppt.getPageSize();
            org.apache.poi.hslf.model.Slide[] slide = ppt.getSlides();
            for (int i = 0; i < slide.length; i++) {
                System.out.print("第" + i + "页。");

                TextRun[] truns = slide[i].getTextRuns();
                for ( int k=0;k<truns.length;k++){
                    RichTextRun[] rtruns = truns[k].getRichTextRuns();
                    for(int l=0;l<rtruns.length;l++){
                        int index = rtruns[l].getFontIndex();
                        String name = rtruns[l].getFontName();
                        rtruns[l].setFontIndex(1);
                        rtruns[l].setFontName("宋体");
//                        System.out.println(rtruns[l].getText());
                    }
                }
                BufferedImage img = new BufferedImage(pgsize.width,pgsize.height, BufferedImage.TYPE_INT_RGB);

                Graphics2D graphics = img.createGraphics();
                graphics.setPaint(Color.BLUE);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                slide[i].draw(graphics);

                // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                FileOutputStream out = new FileOutputStream("C:/Users/DK/Desktop/ppt/image/pict_"+ (i + 1) + ".jpeg");
                javax.imageio.ImageIO.write(img, "jpeg", out);
                out.close();

            }
            System.out.println("success!!");
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(e);
            // System.out.println("Can't find the image!");
        } catch (IOException e) {
        }
        return false;
    }

    // function 检查文件是否为PPT
    public static boolean checkFile(File file) {

        boolean isppt = false;
        String filename = file.getName();
        String suffixname = null;
        if (filename != null && filename.indexOf(".") != -1) {
            suffixname = filename.substring(filename.indexOf("."));
            if (suffixname.equals(".ppt")) {
                isppt = true;
            }
            return isppt;
        } else {
            return isppt;
        }
    }
}