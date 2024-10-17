package xyz.mariosm.status.service;

import com.influxdb.client.WriteApi;

public interface InfluxService {
    WriteApi getWriteApi();
}
