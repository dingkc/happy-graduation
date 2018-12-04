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

    //code目录下50-99的错误码
    GITLAB_BRANCH_INVALID("OSRDC_B_00050", "gitlab分支名称中不能含有中文"),
    GITLAB_USER_PROJECT_UNEXSIT("OSRDC_B_00051", "该用户没有创建项目"),
    GITLAB_PATH_ERROR("OSRDC_B_00052", "路径有误!"),
    GITLAB_USER_NAME_ALREADY_EXSIT("OSRDC_B_00053", "gitlab用户名已存在"),
    GITLAB_USER_ERROR("OSRDC_B_00054", "gitlab用户名或密码错误"),
    GITLAB_PROJECT_EXIT("OSRDC_B_00055", "gitlab库名已存在"),
    GITLAB_GROUP_EXIT("OSRDC_B_00056", "gitlab组名已存在"),
    GITLAB_USER_ACCESS_ERROR("OSRDC_B_00057", "gitlab用户权限认证失败"),
    GITLAB_USER_UNCONFIGURE("OSRDC_B_00058", "请在个人中心配置gitlab用户名和密码!"),
    GITLAB_BRANCH_NOT_AUTHORITY("OSRDC_B_00059", "您没有权限查询分支"),

    //user目录下100-149的错误码
    USER_ALREADY_EXSIT("OSRDC_B_00100", "用户名已存在"),
    USER_VERIFY_CODE_UNEXSIT("OSRDC_B_00101", "验证码已过期或错误"),
    USER_NOT_EXIST("OSRDC_B_00102", "用户不存在"),
    USER_EXCEPTION("OSRDC_B_00103", "用户异常，请重新登录"),
    USER_GIT_COMPELETE("OSRDC_B_00104", "请输入完整的gitlab用户名密码"),
    USER_OLD_PASSWORD_UNCORRECT("OSRDC_B_00105", "输入的老密码不正确"),


    //email模块150-199的错误码
    EMAIL_NOT_LEGAL("OSRDC_B_00150", "发邮件失败, 所有邮箱地址不合法!"),
    EMAIL_FAIL("OSRDC_B_00151", "邮件发送失败! "),
    VERIFY_CODE_ERROR("OSRDC_B_00152", "验证码错误"),
    EMAIL_VERIFY_ERROR("OSRDC_B_00153", "请确认验证码是否已失效"),

    //project模块200-249的错误码
    PROJECT_NAME_ALREADY_EXSIT("OSRDC_B_00200", "该项目名已存在"),
    PROJECT_NO_AUTHORITY("OSRDC_B_00201", "您无权操作此项目"),
    PROJECT_IS_MEMBER("OSRDC_B_00202", "该用户已是项目成员"),
    PROJECT_NOT_CHANGE("OSRDC_B_00203", "不能修改自己权限"),
    PROJECT_NOT_ALLOW("OSRDC_B_00204", "不能修改项目创建者权限"),
    PROJECT_REL_GITLAB_REPOSITORY_ALREADY_EXSIT("OSRDC_B_00205", "项目关联gitlab配置库已存在，请勿重复添加!"),


    //core目录下250-299的错误码
    CORE_PATH_EXISTED("OSRDC_B_00250", "该路径已存在！"),
    CORE_NETWORK_INSTABILITY("OSRDC_B_00251", "连接到ES搜索平台的网络不稳定，请及时联系管理员！"),
    CORE_DOCUMENT_TRANSFORMATION("OSRDC_B_00252", "文档转化xml结构失败!{0}"),
    CORE_DOCUMENT_NOPARSING("OSRDC_B_00253", "文件大小超过10M，不支持解析! "),
    CORE_PATH_NOTEXIST("OSRDC_B_00254", "该路径不存在！"),
    CORE_INTERFACE_PARAMETER_INVALID("OSRDC_B_00255", "查询接口有误，请及时联系管理员！"),
    CORE_SENDREQUEST_EXCEPTION("OSRDC_B_00256", "ES接口请求异常，请及时联系管理员！"),
    CORE_RETURNRESULT_HANDLE_EXCEPTION("OSRDC_B_00257", "接口返回结果处理异常，请及时联系管理员！"),
    CORE_DOCUMENTSECTION_NULL("OSRDC_B_00258", "文档对象中{0}"),
    CORE_FILEINPUTSTREAM_NULL("OSRDC_B_00259", "ftp服务器中待解析文件不存在！"),
    CORE_UPLOADSOURCE_FAILED("OSRDC_B_00260", "上传文档source至ftp服务器失败！"),
    CORE_BUSINESS_TYPE_ERROR("OSRDC_B_00261", "该数据和数据库中不一致，请重新发起检索或联系管理员！"),
    CORE_DOCUMENT_NAME_ERROR("OSRDC_B_00262", "该业务下部分数据有误，请及时联系管理员！"),
    CODE_FILE_NOTFOUND("OSRDC_B_00263", "该文件找不到！"),

    //pipeline模块下300-349的错误码
    PIPELINE_PACKAGE_UNEXSIT("OSRDC_B_00300", "构建包不存在！"),
    PIPELINE_FTP_SERVER_UNEXSIT("OSRDC_B_00301", "ftp主机不存在！"),
    PIPELINE_PACKAGE_DOWNLOAD_FAIL("OSRDC_B_00302", "构建包下载失败！"),
    PIPELINE_PACKAGE_PATH_ERROR("OSRDC_B_00303", "构建包路径错误或者构建包不存在，下载失败！"),
    PIPIELINE_STEP_EMPTY("OSRDC_B_00304", "流水线没有配置步骤，无法执行！"),

    //menu目录下350-399的错误码
    ROUTER_INFO_NOT_FOUNT("OSRDC_B_00350", "路由配置信息未找到，请先配置菜单对应路由！"),


    UTILITY_CLASSES_EXCEPTION("OSRDC_B_00500", "工具类[{0}]异常"),
    FTP_ITERATOR_FAIL("OSRDC_B_00501", "ftp服务器路径解析失败!"),
    FTP_CONNECTION_FAIL("OSRDC_B_00502", "ftp服务器连接失败!"),
    FILEPATH_INVALID("OSRDC_B_00503", "文件路径不符合规范!"),
    BAD_REQUEST("400", "无法找到您要的资源"),
    UNAUTHORIZED("401", "对不起, 您无法访问该资源"),
    INTERNAL_SERVER_ERROR("500", "出现无法预知的错误");


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
