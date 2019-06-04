package com.bttc.HappyGraduation.scheduler.transfer;

import javax.sql.DataSource;
import java.util.Map;

public interface DataTransferSourceBuilder {
    void addDataSource(Map<String, DataSource> datasource);
}
