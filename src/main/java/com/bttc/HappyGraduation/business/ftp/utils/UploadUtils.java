package com.bttc.HappyGraduation.business.ftp.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * @author mz
 * @Description：解决文件重名问题
 * @date 2018/6/27
 * @time 20:35
 */
public class UploadUtils {

    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

    private UploadUtils() {
    }

    static {
        getAllFileType(); //初始化文件类型信息
    }

    /**
     * getAllFileType:存储常见文件头信息. 注意: 以下这些信息可能有所变化, 因为我自己都不知道这些都是什么年头的标签头信息了.
     */
    private static void getAllFileType() {
        FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg"); //JPEG (jpg)
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png"); //PNG (png)
        FILE_TYPE_MAP.put("47494638396126026f01", "gif"); //GIF (gif)
        FILE_TYPE_MAP.put("49492a00227105008037", "tif"); //TIFF (tif)
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp"); //16色位图(bmp)
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp"); //24位位图(bmp)
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp"); //256色位图(bmp)
        FILE_TYPE_MAP.put("41433130313500000000", "dwg"); //CAD (dwg)
        FILE_TYPE_MAP.put("3c21444f435459504520", "html"); //HTML (html)
        FILE_TYPE_MAP.put("3c21646f637479706520", "htm"); //HTM (htm)
        FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css"); //css
        FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js"); //js
        FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf"); //Rich Text Format (rtf)
        FILE_TYPE_MAP.put("38425053000100000000", "psd"); //Photoshop (psd)
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml"); //Email [Outlook Express 6] (eml)
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc"); //MS Excel 注意：word、msi 和 excel的文件头一样
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "vsd"); //Visio 绘图
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb"); //MS Access (mdb)
        FILE_TYPE_MAP.put("252150532D41646F6265", "ps");
        FILE_TYPE_MAP.put("255044462d312e350d0a", "pdf"); //Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb"); //rmvb/rm相同
        FILE_TYPE_MAP.put("464c5601050000000900", "flv"); //flv与f4v相同
        FILE_TYPE_MAP.put("00000020667479706d70", "mp4");
        FILE_TYPE_MAP.put("49443303000000002176", "mp3");
        FILE_TYPE_MAP.put("000001ba210001000180", "mpg"); //
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv"); //wmv与asf相同
        FILE_TYPE_MAP.put("52494646e27807005741", "wav"); //Wave (wav)
        FILE_TYPE_MAP.put("52494646d07d60074156", "avi");
        FILE_TYPE_MAP.put("4d546864000000060001", "mid"); //MIDI (mid)
        FILE_TYPE_MAP.put("504b0304140000000800", "zip");
        FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");
        FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
        FILE_TYPE_MAP.put("504b03040a0000000000", "jar");
        FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");//可执行文件
        FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");//jsp文件
        FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");//MF文件
        FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");//xml文件
        FILE_TYPE_MAP.put("494e5345525420494e54", "sql");//xml文件
        FILE_TYPE_MAP.put("7061636b616765207765", "java");//java文件
        FILE_TYPE_MAP.put("406563686f206f66660d", "bat");//bat文件
        FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");//gz文件
        FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "properties");//bat文件
        FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");//bat文件
        FILE_TYPE_MAP.put("49545346030000006000", "chm");//bat文件
        FILE_TYPE_MAP.put("04000000010000001300", "mxp");//bat文件
        FILE_TYPE_MAP.put("504b0304140006000800", "docx");//docx文件
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "wps");//WPS文字wps、表格et、演示dps都是一样的
        FILE_TYPE_MAP.put("6431303a637265617465", "torrent");
        FILE_TYPE_MAP.put("6D6F6F76", "mov"); //Quicktime (mov)
        FILE_TYPE_MAP.put("FF575043", "wpd"); //WordPerfect (wpd)
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx"); //Outlook Express (dbx)
        FILE_TYPE_MAP.put("2142444E", "pst"); //Outlook (pst)
        FILE_TYPE_MAP.put("AC9EBD8F", "qdf"); //Quicken (qdf)
        FILE_TYPE_MAP.put("E3828596", "pwl"); //Windows Password (pwl)
        FILE_TYPE_MAP.put("2E7261FD", "ram"); //Real Audio (ram)
    }


    /**
     * 获取文件真实名称
     * 由于浏览器的不同获取的名称可能为:c:/upload/1.jpg或者1.jpg
     * 最终获取的为  1.jpg
     *
     * @param name 上传上来的文件名称
     * @return 真实名称
     */
    public static String getRealName(String name) {
        //获取最后一个"/"
        int index = name.lastIndexOf("\\");
        return name.substring(index + 1);
    }


    /**
     * 获取随机名称
     *
     * @param realName 真实名称
     * @return uuid 随机名称
     */
    public static String getUUIDName(String realName) {
        //realname  可能是  1sfasdf.jpg   也可能是 1sfasdf 1
        //获取后缀名
        int index = realName.lastIndexOf(".");
        if (index == -1) {
            return UUID.randomUUID().toString().replace("-", "").toUpperCase();
        } else {
            return UUID.randomUUID().toString().replace("-", "").toUpperCase() + realName.substring(index);
        }
    }


    /**
     * 获取文件目录,可以获取256个随机目录
     *
     * @return 随机目录
     */
    public static String getDir() {
        String s = "0123456789ABCDEF";
        Random r = new Random();
        // /A/A
        return "/" + s.charAt(r.nextInt(16)) + "/" + s.charAt(r.nextInt(16));
    }

    public static void main(String[] args) {
        //String s="G:\\day17-基础加强\\resource\\1.jpg";
        String s = "1.jgp";
        String realName = getRealName(s);
        System.out.println(realName);

        String uuidName = getUUIDName(realName);
        System.out.println(uuidName);

        String dir = getDir();
        System.out.println(dir);
    }


    /**
     * 得到上传文件的文件头.
     *
     * @param src
     * @return
     */
    //传入进来的只需要文件输入流的前十个字节!!!
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        //遍历字节数组
        for (int i = 0; i < src.length; i++) {
            //进行位与运算,如果想知道为什么就自己百度,这里就简单的拿来用.
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        //将转换后的十个数字串联在一起就成了一个文件的格式信息(我们看到的是数字,但是这些数字都是有规律的, 你可以每个类型的文件都上传一次,就能看到同类型的文件得到的数字其头部或者尾部是一致的).
        return stringBuilder.toString();
    }

    /**
     * 使用这个工具类时候, 实际上就是调用这个方法即可.
     * 根据指定文件的文件头判断其文件类型---> 上面两个方法都是准备工作,这个才是最好得到文件类型的主方法.
     *
     * @param :MultipartFile multipartFile: 我这里用的是SSM自带的接收上传文件的类. 你可以根据不同的接收进行转变.
     * @return
     */
    public static String getFileType(MultipartFile multipartFile) throws IOException {
        String res = null;
        try {
            //将接收的上传资源转换成文件输入流.
            FileInputStream is = (FileInputStream) multipartFile.getInputStream();
            //准备数组,固定长度为10,我们只需要前十个.
            byte[] b = new byte[10];
            //读取前十个到数组中去.
            is.read(b, 0, b.length);
            //调用方法,得到该文件的格式数字,说白了, 前面十个字节里存储的就是文件的格式信息,有的在尾部,这个此处不细分,感兴趣的可以百度ID3自己学习.
            String fileCode = bytesToHexString(b);


            //遍历一开始就准备好的格式信息
            Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();

            while (keyIter.hasNext()) {
                String key = keyIter.next();
                //当串联组成的字符串的头或者尾部有等于这个格式信息Map中的数据时候该文件格式就出来了.
                if (key.toLowerCase().startsWith(fileCode.toLowerCase()) || fileCode.toLowerCase().startsWith(key.toLowerCase())) {
                    res = FILE_TYPE_MAP.get(key);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
