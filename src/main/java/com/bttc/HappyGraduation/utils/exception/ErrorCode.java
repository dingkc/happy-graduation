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
    GITLAB_BRANCH_NOT_AUTHORITY_SEARCH("OSRDC_B_00059", "您没有权限查询分支!"),
    GITLAB_GROUP_INVALID("OSRDC_B_00060", "git组名称中不能含有中文"),
    GITLAB_PROJECT_UNEXIT("OSRDC_B_00061", "当前gitlab项目不存在！"),
    GITLAB_MERGE_REQUEST_UNEXIT("OSRDC_B_00062", "当前merge请求不存在！"),
    GITLAB_MERGE_CONFLICT("OSRDC_B_0063","当前合并请求与源分支有冲突，分支不能合并"),
    GITLAB_MERGE_Method_NOT_ALLOWED("OSRDC_B_0064","当前合并请求代码无差异或已被合并、关闭、有冲突,请确认后重试！"),
    GITLAB_MERGE_SHA_NOT_MATCH_HEAD("OSRDC_B_0065","SHA与源分支的HEAD不匹配"),
    GITLAB_MERGE_ERROR("OSRDC_B_0066","合并失败，请刷新后重试！"),
    GITLAB_MERGE_ALREADY_EXITS("OSRDC_B_0067","该请求已存在,请勿重复提交"),
    GITLAB_GROUP_UNEXSIT("OSRDC_B_0068","[{0}] 应用gitlab组不存在！"),
    GITLAB_PROJECT_UNEXSIT("OSRDC_B_0069","[{0}] 应用gitlab库不存在！"),
    GITLAB_BRANCH_EXIST("OSRDC_B_00070", "新建分支名已存在"),
    GITLAB_BRANCH_NOT_EXIST("OSRDC_B_00071","GitFlow模式必须包含master和develop分支，请先新建{0}分支！"),
    GITLAB_USER_WRONG("OSRDC_B_00072","gitlab用户信息错误！"),
    GITLAB_BRANCH_NOT_AUTHORITY_CREATE("OSRDC_B_00073", "您没有权限创建分支!"),
    GITLAB_INVALID_REFERENCE_NAME("OSRDC_B_00074", "无效的引用名称!"),
    GLOBAL_INTEGRATION_WORK("OSRDC_B_00075", "队列已满，请稍后再试！"),
    GLOBAL_INTEGRATION_IS_NOT_EXIT("OSRDC_B_00076", "队列不存在！"),


    //user目录下100-149的错误码
    USER_ALREADY_EXSIT("OSRDC_B_00100", "用户名已存在"),
    USER_VERIFY_CODE_UNEXSIT("OSRDC_B_00101", "验证码已过期或错误"),
    USER_NOT_EXIST("OSRDC_B_00102","用户不存在"),
    USER_EXCEPTION("OSRDC_B_00103", "用户异常，请重新登录"),
    USER_GIT_COMPELETE("OSRDC_B_00104","请输入完整的gitlab用户名密码"),
    USER_OLD_PASSWORD_UNCORRECT("OSRDC_B_00105","输入的旧密码不正确"),
    USER_NOT_AGILE_USERTYPE("OSRDC_B106","不是敏捷平台用户类型"),
    USER_NOT_AGILE("OSRDC_B_107","请输入正确的敏捷用户账号！"),
    USER_AGILE_USERNAME_EXSIT("OSRDC_B108","敏捷用户账号已存在，请重新输入！"),
    
    
    //email模块150-199的错误码
    EMAIL_NOT_LEGAL("OSRDC_B_00150","发邮件失败, 所有邮箱地址不合法!"),
    EMAIL_FAIL("OSRDC_B_00151","邮件发送失败! "),
    VERIFY_CODE_ERROR("OSRDC_B_00152", "验证码错误"),
    EMAIL_VERIFY_ERROR("OSRDC_B_00153","请确认验证码是否已失效"),
    
    //project模块200-249的错误码
    PROJECT_NAME_ALREADY_EXSIT("OSRDC_B_00200", "该项目名已存在"),
    PROJECT_NO_AUTHORITY("OSRDC_B_00201", "您无权操作此项目"),
    PROJECT_IS_MEMBER("OSRDC_B_00202", "该用户已是项目成员"),
    PROJECT_NOT_CHANGE("OSRDC_B_00203", "不能修改自己权限"),
    PROJECT_NOT_ALLOW("OSRDC_B_00204", "不能修改项目创建者权限"),
    PROJECT_REL_GITLAB_REPOSITORY_ALREADY_EXSIT("OSRDC_B_00205", "项目关联gitlab配置库已存在，请勿重复添加!"),
    PROJECT_ROLE_OR_USER_ALREADY_EXSIT("OSRDC_B_00206", "当前所有者已存在对该资源的权限，请勿重复添加!"),
    PROJECT_ROLE_OR_USER_UNEXSIT_AUTHORITY("OSRDC_B_00207", "当前所有者不存在对该资源的权限，请勿重复删除！"),
    PROJECT_INFO_ERROR("OSRDC_B_00208", "请完善项目信息"),
    USER_IS_APPLYING("OSRDC_B_00209", "已存在审批工单，请勿重复提交"),
    PROJECT_NOT_FLUSH_DATA("OSRDC_B_00208", "数据异常，请刷新后重试"),


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


    //pipeline模块下300-349的错误码
    PIPELINE_PACKAGE_UNEXSIT("OSRDC_B_00300", "构建包不存在！"),
    PIPELINE_FTP_SERVER_UNEXSIT("OSRDC_B_00301", "ftp主机不存在！"),
    PIPELINE_PACKAGE_DOWNLOAD_FAIL("OSRDC_B_00302", "构建包下载失败！"),
    PIPELINE_PACKAGE_PATH_ERROR("OSRDC_B_00303", "构建包路径错误或者构建包不存在，下载失败！"),
    PIPIELINE_STEP_EMPTY("OSRDC_B_00304","流水线没有配置步骤，无法执行！"),
    PIPELINE_MUST_EDIT("OSRDC_B_00305","当前用户必须保存对流水线的编辑权限！"),
    PIPELINE_CREATOR_UNDELETE_AUTHORITY("OSRDC_B_00306","流水创建者不允许删除对流水的权限！"),
    PIPELINE_NOT_AUTHORITY("OSRDC_B_00307","当前项目流水都不具备权限！"),
    PIPELINE_CAN_RUNNING("OSRDC_B_00308","当前流水线可能正在运行，请稍后操作！"),
    PIPELINE_JENKINS_MEMORY_FULL("OSRDC_B_00309","服务器内存已满，请重试或联系管理员！"),
    PIPELINE_LOG_FORMAT_ERROR("OSRDC_B_00310","日志格式错误,请联系管理员！"),
    PIPELINE_NAME_ALREADY_EXISTED("OSRDC_B_00311","流水线名称已存在，请更换后重试！"),
    PIPELINE_STEP_UNEXSIT("OSRDC_B_00312","流水线步骤不存在，请添加流水线步骤！"),
    PIPELINE_FUNCTION_UNEXSIT("OSRDC_B_00313","第[{0}]个步骤下任务不存在，请添加流水线任务！"),
    FEATURE_APP_NOT_IN_ENV("OSRDC_B_00314","该环境实例下不包含特性分支改变的应用"),
    FEATURE_BRANCH_IS_EMPTY("OSRDC_B_00315","特性分支信息不能为空！"),
    PIPELINES_IS_EMPTY("OSRDC_B_00316","流水线信息为空！"),
    MACHINES_IP_IS_EMPTY("OSRDC_B_00317","主机ip信息不能为空！"),
    MACHINES_IP_IS_EXIST("OSRDC_B_00318","主机ip信息不能重复！"),
    PIPELINE_ID_IS_EMPTY("OSRDC_B_00319","流水线信息不能为空！"),
    PIPELINE_RUN_PARAM_IS_EMPTY("OSRDC_B_00320","流水线运行参数信息不能为空！"),
    PIPELINE_BUILD_JOB_ERROR("OSRDC_B_00321","流水线功能job构建失败！"),
    PIPELINE_FUNCTION_FLOW_NOT_START("OSRDC_B_00322","流水线功能尚未开始!"),


    //menu目录下350-399的错误码
    ROUTER_INFO_NOT_FOUNT("OSRDC_B_00350", "路由配置信息未找到，请先配置菜单对应路由！"),

    UTILITY_CLASSES_EXCEPTION("OSRDC_B_00500", "工具类[{0}]异常"),
    FTP_ITERATOR_FAIL("OSRDC_B_00501", "ftp服务器路径解析失败!"),
    FTP_CONNECTION_FAIL("OSRDC_B_00502", "ftp服务器连接失败!"),
    FILEPATH_INVALID("OSRDC_B_00503", "文件路径不符合规范!"),
    BAD_REQUEST("400", "无法找到您要的资源"),
    UNAUTHORIZED("401", "对不起, 您无法访问该资源"),
    INTERNAL_SERVER_ERROR("500", "出现无法预知的错误"),

    //develop目录下400-449的错误码
    DEVELOP_TASK_NOT_EXIST("OSRDC_B_00400", "开发任务不存在！"),
    SOFT_REQ_NOT_EXIST("OSRDC_B_00401", "软件需求不存在！"),

    //application目录下450-499的错误码
    APPLICATION_IS_EXIST("OSRDC_B_00450", "应用已存在，请更换名字后重试！"),
    APPLICATION_NAME_IS_NOT_EMPTY("OSRDC_B_00451", "应用名称不能为空！"),
    APPLICATION_TYPE_IS_NOT_EMPTY("OSRDC_B_00452", "应用类型不能为空！"),
    APPLICATION_STATUS_IS_NOT_EMPTY("OSRDC_B_00453", "应用状态不能为空！"),
    APPLICATION_LANGUAGE_IS_NOT_EMPTY("OSRDC_B_00454", "应用语言不能为空！"),
    APPLICATION_VERSION_IS_NOT_EMPTY("OSRDC_B_00455", "应用开发版本不能为空！"),
    PACKAGE_TAG_IS_NOT_EMPTY("OSRDC_B_00457", "包标签不能为空！"),
    DEPLOY_METHOD_IS_NOT_EMPTY("OSRDC_B_00458", "部署方式不能为空！"),
    IMAGE_IS_NOT_EMPTY("OSRDC_B_00459", "镜像不能为空！"),
    COLONY_NAME_IS_NOT_EMPTY("OSRDC_B_00460", "集群名称不能为空！"),
    SCRIPT_IS_NOT_EMPTY("OSRDC_B_00461", "启动脚本不能为空！"),
    HOST_ADDRESS_IS_NOT_EMPTY("OSRDC_B_00462", "主机地址不能为空！"),
    VIRTUAL_MACHINE_SCRIPT_IS_NOT_EMPTY("OSRDC_B_00463", "虚机自定义启动脚本不能为空！"),
    CONFIGURATION_CENTER_IS_NOT_EMPTY("OSRDC_B_00464", "配置中心选项不能为空！"),
    CUSTOME_SCRIPT_IS_NOT_EMPTY("OSRDC_B_00465", "自定义脚本选项不能为空！"),
    GITLAB_GROUP_ID_IS_NOT_EMPTY("OSRDC_B_00466", "gitlab组编号不能为空！"),
    GITLAB_PROJECT_ID_IS_NOT_EMPTY("OSRDC_B_00467", "gitlab库编号不能为空！"),
    GITLAB_BRANCH_IS_NOT_EMPTY("OSRDC_B_00468", "gitlab分支不能为空！"),
    PROJECT_ID_IS_NOT_EMPTY("OSRDC_B_00469", "项目信息不能为空！"),
    APPLICATION_IS_NOT_EXIST("OSRDC_B_00470", "应用不能为空！"),
    APPLICATION_DEVELOPMENT_MODE_IS_NOT_EMPTY("OSRDC_B_00471", "应用开发模式不能为空！"),
    APPLICATION_BE_ASSOOCIATED("OSRDC_B_00472", "有模板关联该应用，请先解除关系！"),
    APPLICATION_TYPE_NOT_ALLOW_UPDATE("OSRDC_B_00473","应用类型不允许修改！"),

    //featureBranch目录下500-549的错误码
    ONLINE_TIME_IS_EMPTY("OSRDC_B_00500", "上线时间为空！"),
    SOURCE_CONFIG_VERSION_IS_NOT_EXIST("OSRDC_B_00501", "源版本号 [{0}]不存在，请先确认amber上是否存在该配置版本号！"),
    CONFIGURATIONS_IS_EMPTY("OSRDC_B_00502", "配置信息不能为空！"),
    CHANGE_TYPE_IS_EMPTY("OSRDC_B_00503", "变更类型不能为空！"),
    WORK_ITEM_IS_EMPTY("OSRDC_B_00504", "关联工作项不能为空！"),
    WORK_ITEM_ID_IS_EMPTY("OSRDC_B_00505", "关联工作项id不能为空！"),
    WORK_ITEM_CODE_IS_EMPTY("OSRDC_B_00506", "关联工作项编码不能为空！"),
    FEATURE_BRANCH_IS_NOT_EXIST("OSRDC_B_00507","特性分支不存在"),
    FEATURE_BRANCH_STATUS_IS_NOT_BEFORE_RELEASE("OSRDC_B_00508","该特性分支不处于待发布状态"),
    WORK_CODE_IS_NOT_EXIT("OSRDC_B_00509","该工单不存在"),


    //component目录下550-599的错误码
    COMPONENT_NOT_FINF_BY_FUNCTIONFLOWID("OSRDC_B_00550","当前功能不包含构件信息"),

    //machine目录下600-649的错误码
    USER_AUTHENTICATION_FAILED("OSRDC_B_00600","用户认证失败，请重试！"),
    HOST_IP_IS_NOT_EMPTY("OSRDC_B_00601","主机ip不能为空！"),
    HOST_USERNAME_IS_NOT_EMPTY("OSRDC_B_00602","主机用户名不能为空！"),
    HOST_PASSWORD_IS_NOT_EMPTY("OSRDC_B_00603","主机用户密码不能为空！"),
    MACHINE_RESOURCES_ALREADY_EXIST("OSRDC_B_00604","机器资源已存在，请勿重复添加"),
    MACHINE_HOST_IP_NOT_ALLOW_UPDATE("OSRDC_B_00605","主机IP不允许修改"),
    MACHINE_RESOURCES_DO_NOT_EXIST("OSRDC_B_00606","机器资源不存在"),
    MACHINE_RESOURCES_IS_USE("OSRDC_B_00607","机器资源正在被使用，无法{0}！"),
    PLEASE_EXAMINE_MACHINE_STATUS("OSRDC_B_00608","请检查机器状态！"),

    //environment目录下的650-699的错误码
    TMPLATE_IS_EXIST("OSRDC_B_00650","模板名称已存在，请更换后重试！"),
    TMPLATE_NAME_IS_EMPTY("OSRDC_B_00651","模板名称不能为空！"),
    TMPLATE_LEVEL_IS_EMPTY("OSRDC_B_00652","模板等级不能为空！"),
    PROJECT_ID_IS_EMPTY("OSRDC_B_00653","项目信息不能为空！"),
    APPLICATIONS_REL_IS_EMPTY("OSRDC_B_00654","关联应用不能为空！"),
    ENVTEMPLATES_HAS_FLOW("OSRDC_B_00655","无法删除，请先删除模板下的实例"),
    ENVIRONMENT_NAME_ALREADY_EXSIT("OSRDC_B_00656","环境实例名称已存在"),
    VIRTUAL_MACHINE_SCRIPT_IS_EMPTY("OSRDC_B_00657","虚机启动脚本不能为空！"),
    COLONY_NAME_IS_EMPTY("OSRDC_B_00658","集群名称不能为空！"),
    IMAGE_ID_IS_EMPTY("OSRDC_B_00659","镜像信息不能为空！"),
    SCRIPT_ID_IS_EMPTY("OSRDC_B_00660","启动脚本不能为空！"),
    REQ_INFO_ID_IS_EMPTY("OSRDC_B_00661","需求编号不能为空！"),
    ENVIRONMENT_NAME_IS_EMPTY("OSRDC_B_00662","环境实例名称不能为空！"),
    FEATURE_APPLICATIONS_IS_EMPTY("OSRDC_B_00663","特性应用列表不能为空！"),
    COMMON_APPLICATIONS_IS_EMPTY("OSRDC_B_00664","公共应用列表不能为空！"),
    CHANGE_APPLICATIONS_IS_EMPTY("OSRDC_B_00665","变更应用列表不能为空！"),
    FEATURE_APPLICATIONS_MUST_CONTAINS_ALL_CHANGE_APPLICATIONS("OSRDC_B_00666","特性应用必须包含所有变更应用！"),
    TEMPLATE_APPLICATIONS_IS_CHANGE("OSRDC_B_00667","模板的关联应用信息不能更改！"),
    ENVIRONMENT_TYPE_IS_EMPTY("OSRDC_B_00668","环境实例类型不能为空！"),
    MARATHON_ADDRESS_IS_EMPTY("OSRDC_B_00669","马拉松地址不能为空！"),
    MACHINE_ID_IS_EMPTY("OSRDC_B_00670","主机资源信息不能为空！"),
    ENVIRONMENT_AT_LEAST_ONE_APPLICATION_EXIST("OSRDC_B_00671","环境实例每个应用至少存在一个关联资源！"),
    ENVIRONMENT_AT_LEAST_ONE_WEB_APPLICATION_EXIST("OSRDC_B_00672","环境实例至少存在一个WEB应用！"),
    MACHINE_IP_IS_EXIST("OSRDC_B_00673","添加的主机资源已存在，不可重复添加！"),
    ENVIRONMENT_IS_EMPTY("OSRDC_B_00674","环境实例信息不能为空！"),
    ENVIRONMENT_TEMPLATE_IS_EMPTY("OSRDC_B_00675","环境模板信息不能为空！"),
    ENVIRONMENT_TEMPLATE_NAME_IS_EMPTY("OSRDC_B_00676","环境模板名称不能为空！"),
    ENVIRONMENT_REL_RESOURCE_IS_EMPTY("OSRDC_B_00677","该环境实例下没有对应的关联资源！"),
    ENVIRONMENT_REL_APPLICATIONS_MUST_CONTAINS_ALL_CHANGE_APPLICATIONS("OSRDC_B_00678","变更应用必须是模板关联应用！"),
    ENVIRONMENT_APPLICATION_REL_IS_EXIST("OSRDC_B_00679","当前环境模板应用[{0}]已存在！"),
    TEMPLATE_AT_LEAST_ONE_APPLICATION_EXIST("OSRDC_B_00680","环境模板至少存在一个应用!"),

    //image目录下的700-749的错误码
    IMAGE_SCRIPT_NOT_UNIQUE("OSRDC_B_00700","启动脚本列表中有相同的脚本"),
    IMAGE_NAME_IS_ALREADY_EXSIT("OSRDC_B_00701","镜像名称已存在"),
    IMAGE_SOURCE_URL_IS_ALREADY_EXSIT("OSRDC_B_00702","镜像源地址已存在"),
    IMAGE_SHELL_PARAMETER_INCOMPLETE("OSRDC_B_00703","找不到对应的shell脚本参数"),
    IMAGE_DOCKER_BUILD_PULL_FAIL("OSRDC_B_00704","镜像地址错误，镜像下载失败"),
    IMAGE_DOCKER_PUSH_FAIL("OSRDC_B_00705","目标镜像仓库地址错误，镜像上传失败"),
    IMAGE_NOT_EXSIT("OSRDC_B_00706","基础镜像不存在！"),

    //system目录下的750-799的错误码
    CORRELATION_SYSTEM_ALREADY_EXSIT("OSRDC_B_00750","关联系统已存在，请刷新后重试！");


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
