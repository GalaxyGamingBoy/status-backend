package xyz.mariosm.status.service;

import com.influxdb.query.FluxTable;
import xyz.mariosm.status.data.Project;
import xyz.mariosm.status.data.Request;

import java.util.List;

public interface DataService {
    FluxTable fetchRequest(Request request, String time);
    List<FluxTable> fetchAll(String time);
}
