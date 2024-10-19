package xyz.mariosm.status.service;

import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;

public interface InfluxService {
    WriteApi getWriteApi();

    QueryApi getQueryApi();
}
