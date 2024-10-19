package xyz.mariosm.status.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mariosm.status.data.Project;
import xyz.mariosm.status.data.Request;
import xyz.mariosm.status.repository.RequestRepository;
import xyz.mariosm.status.service.DataService;
import xyz.mariosm.status.service.InfluxService;

import java.util.List;
import java.util.UUID;

@Service
public class DataServiceImpl implements DataService {
    private final QueryApi queryApi;

    private final RequestRepository requestRepository;

    @Autowired
    public DataServiceImpl(InfluxService influxService, ObjectMapper mapper, RequestRepository requestRepository) {
        this.queryApi = influxService.getQueryApi();
        this.requestRepository = requestRepository;
    }

    @Override
    public FluxTable fetchRequest(Request request, String time) {
        return this.query(List.of(request.getId()), time).getFirst();
    }

    @Override
    public List<FluxTable> fetchAll(String time) {
        return this.query(this.requestRepository.findAll().stream().map(Request::getId).toList(), time);
    }

    public List<FluxTable> query(List<UUID> requests, String time) {
        List<String> ids = requests.stream().map((v) -> "\"" + v.toString() + "\"").toList();

        String query = "measurements = [" + String.join(", ", ids) + "]\n" +
                       "from(bucket: \"status\")\n" +
                       "|> range(start: -" + time + ")\n" +
                       "|> filter(fn: (r) => contains(set: measurements, value: r[\"_measurement\"]))";

        return this.queryApi.query(query);
    }
}
