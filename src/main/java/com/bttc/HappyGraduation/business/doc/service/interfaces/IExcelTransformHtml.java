//package com.bttc.HappyGraduation.business.doc.service.interfaces;
//
//import jxl.Sheet;
//import jxl.format.Alignment;
//import jxl.format.VerticalAlignment;
//
//import java.util.Map;
//
//public interface IExcelTransformHtml {
//    /**
//     * @param sourcefile
//     * @return
//     * @throws Exception
//     */
////    public String getExcelInfo(File sourcefile) throws Exception;
//
//    /**
//     * @param sheet
//     * @return
//     */
//    public Map<String, String>[] getRowSpanColSpanMap(Sheet sheet);
//
//    /**
//     * @param alignment
//     * @return
//     */
//    public String convertToHtmlGrammer(Alignment alignment);
//
//    /**
//     * @param verticalAlignment
//     * @return
//     */
//    public String convertToHtmlGrammer(VerticalAlignment verticalAlignment);
//
//    /**
//     * @param str
//     * @return
//     */
//    public String fillWithZero(String str);
//
//    /*private static String convertToHtmlGrammer(Colour colour) {
//        StringBuffer sb = new StringBuffer("");
//        if (colour != null && !"default background".equalsIgnoreCase(colour.getDescription())) {
//            sb.append("#");
//            sb.append(fillWithZero(Integer.toHexString(colour.getDefaultRGB().getRed())));
//            sb.append(fillWithZero(Integer.toHexString(colour.getDefaultRGB().getGreen())));
//            sb.append(fillWithZero(Integer.toHexString(colour.getDefaultRGB().getBlue())));
//        }
//        return sb.toString();
//    }*/
//
//}
