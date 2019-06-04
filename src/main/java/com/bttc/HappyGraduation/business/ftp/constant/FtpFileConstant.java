package com.bttc.HappyGraduation.business.ftp.constant;

/**
 * @author Dk
 * @date 17:34 2019/4/16.
 */
public class FtpFileConstant {
    private FtpFileConstant(){}


    public enum FileType {
        /*
         * <p>Title: FileType</p>
         * <p>Description: 文件类型</p>
         * @Author: Dk
         * @Date: 2019/4/16 17:40
         **/
        DIR(1,"dir"),
        TXT(2,"txt"),
        DOC(3,"doc"),
        DOCX(4,"docx"),
        PPT(5,"ppt"),
        PPTX(6,"pptx"),
        XLSX(7,"xlsx"),
        XLS(8,"xls");

        private Integer value;
        private String name;

        FileType(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
}
