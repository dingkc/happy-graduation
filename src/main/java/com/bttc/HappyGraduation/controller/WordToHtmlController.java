//package com.bttc.HappyGraduation.controller;
//
//import com.asiainfo.mybatis.service.impl.SaveHtmlImpl;
//import com.asiainfo.service.impl.WordToHtmlImpl;
//import com.bttc.HappyGraduation.business.service.impl.POIReadExcel;
//import com.bttc.HappyGraduation.business.service.impl.PptToHtmlImpl;
//import com.bttc.HappyGraduation.business.service.interfaces.IExcelTransformHtml;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.File;
//import java.util.Date;
//
///**
// * ".business"、".docx"
// *
// * @author DK
// * @create 2018-06-21 17:35
// */
//@RestController
//public class WordToHtmlController {
//
//    @Autowired
//    private WordToHtmlImpl wordToHtml;
//    @Autowired
//    private SaveHtmlImpl saveHtmlMapper;
//
//    @RequestMapping("/word")
//    public String changeFormat() throws Exception{
//        String readWordFile = "";
//        WordToHtmlImpl word = new WordToHtmlImpl();
//        String filePath = "C:\\Users\\DK\\Desktop\\less规则.docx";
//        File file = new File(filePath);
//        String fileName = file.getName();
//        System.out.println("fileName的值是：---" + fileName + ",当前方法=com.asiainfo.WordToHtmlImpl.main()");
//        String[] split = fileName.split("[.]");
//        System.out.println("split[0]的值是：---" + split[0] + ",当前方法=com.asiainfo.WordToHtmlImpl.main()");
//        System.out.println("split[1]的值是：---" + split[1] + ",当前方法=com.asiainfo.WordToHtmlImpl.main()");
//        switch (split[1]) {
//            case "business":
//                readWordFile = word.readWordFile2003(filePath);
//                break;
//            case "docx":
//                readWordFile = word.readWordFile2007(filePath);
//                break;
//        }
//        System.out.println(fileName + "已经转换完成");
//
//        saveHtmlMapper.saveFileInformation(fileName,split[1],split[0],new Date().toString(),filePath,readWordFile);
//
//        return readWordFile;
//    }
//
//
//    @Autowired
//    private IExcelTransformHtml iExcelTransformHtml;
//
//    @Autowired
//    private POIReadExcel poiReadExcel;
//
//    @RequestMapping("/excel")
//    public String change(){
//        String filePath = "C:\\Users\\DK\\Desktop\\JD1722.xlsx";
//        String s = poiReadExcel.readExcelToHtml(filePath, true);
//
//        return s;
//    }
//    /*@RequestMapping("/excel")
//    public String changeFormat() throws Exception {
//        String filepath = "";
//        File sourcefile = new File("C:\\Users\\DK\\Desktop\\JD1722.xlsx");
//        IExcelTransformHtml eth = new ExcelTransformHtmlImpl();
//        StringBuffer sb = new StringBuffer();
//        InputStream is = new FileInputStream(sourcefile);
//        Workbook rwb = Workbook.getWorkbook(is);
//        Sheet sheet = rwb.getSheet(0);
//        int colnum = sheet.getColumns();
//        int rownum = sheet.getRows();
//        Map<String, String> map[] = eth.getRowSpanColSpanMap(sheet);
//        sb.append("<table border='1' cellspacing='0'>");
//        for (int row = 0; row < rownum; row++) {
//            sb.append("<tr>");
//            for (int col = 0; col < colnum; col++) {
//                Cell cell = sheet.getCell(col, row);
//                String content = cell.getContents();
//                CellFormat cellFormat = cell.getCellFormat();
//                if (map[0].containsKey(row + "," + col)) {
//                    String pointString = map[0].get(row + "," + col);
//                    map[0].remove(row + "," + col);
//                    int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
//                    int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
//                    int rowSpan = bottomeRow - row + 1;
//                    int colSpan = bottomeCol - col + 1;
//                    sb.append("<td rowspan= '" + rowSpan + "' colspan= '" + colSpan + "' ");
//                } else if (map[1].containsKey(row + "," + col)) {
//                    map[1].remove(row + "," + col);
//                    continue;
//                } else {
//                    sb.append("<td ");
//                }
//                if (cellFormat != null) {
//                    Alignment alignment = cellFormat.getAlignment();
//                    sb.append("align='" + eth.convertToHtmlGrammer(alignment) + "' ");
//                    VerticalAlignment verticalAlignment = cellFormat.getVerticalAlignment();
//                    sb.append("valign='" + eth.convertToHtmlGrammer(verticalAlignment) + "' ");
//                    Colour bgcolour = cellFormat.getBackgroundColour();
//
//                    sb.append("' ");
//                }
//                sb.append(">");
//                if (content == null || "".equals(content.trim())) {
//                    sb.append("   ");
//                } else {
//                    sb.append(content);
//                }
//                sb.append("</td>");
//            }
//            sb.append("</tr>");
//        }
//        sb.append("</table>");
//        rwb.close();
//        is.close();
//        return sb.toString();
//    }*/
//
//    @Autowired
//    private PptToHtmlImpl pptToHtml;
//
//    @RequestMapping("/ppt")
//    public String change(){
//        File file = new File("C:/Users/DK/Desktop/ppt/test.ppt");
//        boolean doPPTtoImage = pptToHtml.doPPTtoImage(file);
//
//        return String.valueOf(doPPTtoImage);
//    }
//}