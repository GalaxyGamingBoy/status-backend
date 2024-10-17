package xyz.mariosm.status.service.impl;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.mariosm.status.service.InfluxService;

@Service
public class InfluxServiceImpl implements InfluxService {
    private final InfluxDBClient client;

    @Autowired
    public InfluxServiceImpl(@Value("${influx.host}") String host, @Value("${influx.apikey}") String apikey,
                             @Value("${influx.org}") String org, @Value("${influx.bucket}") String bucket) {
        this.client = InfluxDBClientFactory.create(host, apikey.toCharArray(), org, bucket);
    }

    @Override
    public WriteApi getWriteApi() {
        return this.client.makeWriteApi();
    }
}
