package com.bttc.HappyGraduation.utils.exception;

import org.apache.commons.lang3.StringUtils;

public enum ErrorCode {
    /**
     * OSRDC+B(业务级异常)
     * +五位异常码 
     */
    UNKNOWN("OSRDC_B_00000", "未知错误!"),
    PARAMETER_NULL("OSRDC_B_00001", "请求参数 [{0}] 不能为空"),
    PARAMETER_INVALID("OSRDC_B_00002", "请求参数 [{0}] 不符合规范"),
    PARAMETER_UNABLE_NULL("OSRDC_B_00003", "请求参数不能为空"),
    SESSION_LOSE("OSRDC_B_00004", "会话失效，请重新登录"),
    JENKINS_JOB_EXSIT("OSRDC_B_00005", "jenkins已存在当前分支流水，请检查"),
    DATA_UNEXSIT("OSRDC_B_00006", "请求数据[{0}] 不存在"),
    FUNCTION_TYPE_INVALID("OSRDC_B_00007", "功能类型不符合规范或不为shell类型"),
    SESSION_SET_EXCEPTION("OSRDC_B_00008", "系统错误：session设置异常"),
    

    //user目录下100-149的错误码
    USER_ALREADY_EXSIT("OSRDC_B_00100", "用户名已存在"),
    USER_VERIFY_CODE_UNEXSIT("OSRDC_B_00101", "验证码已过期或错误"),
    USER_NOT_EXIST("OSRDC_B_00102","用户不存在"),
    USER_EXCEPTION("OSRDC_B_00103", "用户异常，请重新登录"),
    USER_GIT_COMPELETE("OSRDC_B_00104","请输入完整的gitlab用户名密码"),
    USER_OLD_PASSWORD_UNCORRECT("OSRDC_B_00105","输入的旧密码不正确"),

    //email模块150-199的错误码
    EMAIL_NOT_LEGAL("OSRDC_B_00150","发邮件失败, 所有邮箱地址不合法!"),
    EMAIL_FAIL("OSRDC_B_00151","邮件发送失败! "),
    VERIFY_CODE_ERROR("OSRDC_B_00152", "验证码错误"),
    EMAIL_VERIFY_ERROR("OSRDC_B_00153","请确认验证码是否已失效"),
    EMAIL_ALREADY_EXIST("OSRDC_B_00154","邮箱已被注册！"),

    //core目录下250-299的错误码
    CORE_PATH_EXISTED("OSRDC_B_00250", "该路径已存在！"),
    CORE_NETWORK_INSTABILITY("OSRDC_B_00251", "连接到ES搜索平台的网络不稳定，请及时联系管理员！"),
    CORE_DOCUMENT_TRANSFORMATION("OSRDC_B_00252", "文档转化xml结构失败!{0}"),
    CORE_DOCUMENT_NOPARSING("OSRDC_B_00253", "文件大小超过10M，不支持解析! "),
    CORE_PATH_NOTEXIST("OSRDC_B_00254", "该路径不存在！"),
    CORE_INTERFACE_PARAMETER_INVALID("OSRDC_B_00255","查询接口有误，请及时联系管理员！"),
    CORE_SENDREQUEST_EXCEPTION("OSRDC_B_00256","ES接口请求异常，请及时联系管理员！"),
    CORE_RETURNRESULT_HANDLE_EXCEPTION("OSRDC_B_00257","接口返回结果处理异常，请及时联系管理员！"),
    CORE_DOCUMENTSECTION_NULL("OSRDC_B_00258","文档对象中{0}"),
    CORE_FILEINPUTSTREAM_NULL("OSRDC_B_00259","ftp服务器中待解析文件不存在！"),
    CORE_UPLOADSOURCE_FAILED("OSRDC_B_00260","上传文档source至ftp服务器失败！"),
    CORE_BUSINESS_TYPE_ERROR("OSRDC_B_00261","该数据和数据库中不一致，请重新发起检索或联系管理员！"),
    CORE_DOCUMENT_NAME_ERROR("OSRDC_B_00262","该业务下部分数据有误，请及时联系管理员！"),
    CODE_FILE_NOTFOUND("OSRDC_B_00263","该文件找不到！"),
    CODE_THEME_REPEAT("OSRDC_B_00264","主题已存在，操作失败"),
    CODE_FILE_EXISTED("OSRDC_B_00265","该文件已经存在，无法重复上传！"),
    CODE_FILEUPLOAD_SUPPORTED("OSRDC_B_00266","该内容不支持上传，请重新选择文件! "),
    CODE_THEME_DOCUMENT_EXISTED("OSRDC_B_00267","该主题下存在文档，不支持修改! "),
    CORE_NO_FIND_BY_ID("OSRDC_B_00268", "根据此id查询不到数据"),
    CORE_NO_FIND_BY_CONDITION("OSRDC_B_00269", "根据此条件查询不到数据"),
    CORE_PARAM_INCOMPLETE("OSRDC_B_00270", "入参不完整！请检查入参"),
    DELET_CHAPTER_FAIL("OSRDC_B_00271", "删除章节失败！"),
    CORE_UPLOADFILE_FAILED("OSRDC_B_00272", "上传文件失败！"),
    CORE_LEMMA_FILED("OSRDC_B_00273", "词条已存在！"),
    REQ_NO_FIND_BY_ID("OSRDC_B_00274", "需求不存在！"),
    CORE_DELETE_NO_AUTHORITY("OSRDC_B_00275","您无权删除此附件"),
    NO_SELECT_SYSTEM("OSRDC_B_00276","未选择任何相关系统!"),
    NO_CHANGE_SYSTEM("OSRDC_B_00277","未更改任何相关系统!"),
    NO_SELECT_ANALYSIS("OSRDC_B_00278","未选择任何相关分析项!"),
    NO_CHANGE_ANALYSIS("OSRDC_B_00279","未更改任何相关分析项!"),
    ENEITY_NO_CLOSE("OSRDC_B_00280","系统异常"),
    FLOW_PICTURE_UPLOAD_FAIL("OSRDC_B_00275", "流程图图片上传失败！"),
    CODE_FLOW_CHART_REPEAT("OSRDC_B_00281","流程图名称已存在，操作失败"),
    SAVE_BUG_FILE("OSRDC_B_00282","新增BUG失败！"),
    CODE_STORY_REPEAT("OSRDC_B_00283","故事已存在！"),
    CORE_DEV_TASK_NOTEXIST("OSRDC_B_00284", "故事不存在！"),
    CORE_BUG_UPDATE("OSRDC_B_00285", "BUG更新失败！"),
    CORE_BUG_DELETE("OSRDC_B_00286", "BUG删除失败！"),
    CORE_NO_USER("OSRDC_B_00287", "该用户未绑定！"),
    CORE_GET_REQ_FAIL("OSRDC_B_00288", "获取需求失败！"),
    CORE_OBJ_TYPE_IS_EMPTY("OSRDC_B_00289","对象类型不能为空"),
    CORE_SUB_TASK_SAVE("OSRDC_B_00290","子任务编码过长"),
    CORE_STORY_SAVE("OSRDC_B_00291","编码过长"),
    DELETE_SUB_STAK_FILE("OSRDC_B_00292","子任务不存在"),
    AGILE_TASK_NOT("OSRDC_B_00293","任务不存在"),
    CODE_THEME_PARENT("OSRDC_B_00294","父主题不存在，操作失败"),
    CODE_REQ("OSRDC_B_00295","需求编码过长"),
    RELATION_QUERY("OSRDC_B_00296","关联查询失败！"),
    CORE_GET_INFO_FAIL("OSRDC_B_00297", "获取信息失败！"),
    CORE_GET_TASK_FAIL("OSRDC_B_00297", "获取任务失败！"),

    //menu目录下350-399的错误码
    ROUTER_INFO_NOT_FOUNT("OSRDC_B_00350", "路由配置信息未找到，请先配置菜单对应路由！"),

    UTILITY_CLASSES_EXCEPTION("OSRDC_B_00500", "工具类[{0}]异常"),
    FTP_ITERATOR_FAIL("OSRDC_B_00501", "ftp服务器路径解析失败!"),
    FTP_CONNECTION_FAIL("OSRDC_B_00502", "ftp服务器连接失败!"),
    FILEPATH_INVALID("OSRDC_B_00503", "文件路径不符合规范!"),
    BAD_REQUEST("400", "无法找到您要的资源"),
    UNAUTHORIZED("401", "对不起, 您无法访问该资源"),
    INTERNAL_SERVER_ERROR("500", "出现无法预知的错误"),

    NOTEPAD_ID_EMPTY("OSRDC_B_00666", "请选择记事本！"),
    FILE_IS_EMPTY("OSRDC_B_00667", "文件信息为空！"),
    TARGET_FILE_IS_NOT_DIR("OSRDC_B_00668", "不能移动文件到非文件夹下！");

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessageAndCompletion(String... strings) {
        if (strings == null) {
            return this.message;
        } else {
            for (int i = 0; i < strings.length; i++) {
                String one = strings[i];
                this.message = StringUtils.replace(this.message, "{" + i + "}", one);
            }
            return this.message;
        }
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
