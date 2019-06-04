package com.bttc.HappyGraduation.scheduler;

/**
 * Created by jiajt on 2018/10/27.
 */
public interface JobConstants {
    String JOB_UNDERLINE = "_";
    String JOB_TYPE_BASE = "base";
    Integer JOB_STATE_EXPIRE = 0;
    Integer JOB_STATE_EFFECT = 1;

    Integer JOB_EXCUTE_STATE_UNSTART = 1;
    Integer JOB_EXCUTE_STATE_RUNNING = 2;
    Integer JOB_EXCUTE_STATE_FAILURE = 3;
    Integer JOB_EXCUTE_STATE_SUCCESS = 4;

    String JOB_TYPE_SINGLE = "I";
    String JOB_TYPE_CIRCLE = "C";

    String CIRCLE_SCAN_JOB = "CircleScanJob";
    String SINGLE_SCAN_JOB = "SingleScanJob";

    String JOB_TRIGGER = "_trigger";

    String COL_JOB_DEFINE_ID = "SCHEDULER_ID";
    String COL_JOB_DEFINE_TYPE = "SCHEDULER_TYPE";

    String CIRCLE_SCAN_JOB_PROPERTY = "osrdc.scheduler.circle.cron";
    String SINGLE_SCAN_JOB_PROPERTY = "osrdc.scheduler.single.cron";
    String DATA_TRANSFER = "bc.scheduler.datatransfer";
    String DATA_TRANSFER_OPEN_FLAG = "true";

    String SCAN_JOB_GROUP_PROPERTY = "osrdc.scheduler.group";
    String SCAN_JOB_NAME_PROPERTY = "osrdc.scheduler.name";

    String JOB_START_MESSAGE = "job started...";

    String JOB_TYPE_DATA_TRANSFER = "DataTransfer";

    String JOB_PARAM_DATA_TRANSFER_KEY = "DataTransferDefine";

}
