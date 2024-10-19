package xyz.mariosm.status.service.impl;

import com.influxdb.client.WriteApi;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import xyz.mariosm.status.data.Request;
import xyz.mariosm.status.exception.NotFoundException;
import xyz.mariosm.status.http.RequestPayload;
import xyz.mariosm.status.repository.RequestRepository;
import xyz.mariosm.status.service.InfluxService;
import xyz.mariosm.status.service.ProjectService;
import xyz.mariosm.status.service.RequestService;
import xyz.mariosm.status.task.RequestTask;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Service
public class RequestServiceImpl implements RequestService {
    private final OkHttpClient client;

    private final TaskScheduler scheduler;

    private final RequestRepository requestRepository;

    private final ProjectService projectService;

    private final WriteApi influxWriteApi;

    private List<ScheduledFuture<?>> tasks = new ArrayList<>();

    @Autowired
    public RequestServiceImpl(TaskScheduler scheduler, RequestRepository requestRepository,
                              ProjectService projectService,
                              InfluxService influxService) {
        this.projectService = projectService;
        this.client = new OkHttpClient();
        this.scheduler = scheduler;
        this.requestRepository = requestRepository;
        this.influxWriteApi = influxService.getWriteApi();

        this.loadRequestsFromDB();
    }

    @Override
    public void cancelAllRequests() {
        tasks.forEach((v) -> v.cancel(true));
    }

    @Override
    public void loadRequests(List<Request> requests) {
        requests.forEach((this::loadRequest));
    }

    @Override
    public void loadRequestsFromDB() {
        this.requestRepository.findAll().forEach(this::loadRequest);
    }

    @Override
    public void refreshFromDB() {
        this.cancelAllRequests();
        this.loadRequestsFromDB();
    }

    @Override
    public Request insert(Request request) {
        return this.requestRepository.save(request);
    }

    @Override
    public Request update(UUID id, Request request) {
        request.setId(id);
        return this.requestRepository.save(request);
    }

    @Override
    public Request fetch(UUID id) {
        return this.requestRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("request", id.toString()));
    }

    @Override
    public Page<Request> fetchAll(int page, int size) {
        return this.requestRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Request> fetchAllFromProject(UUID project, int page, int size) {
        return this.requestRepository.findAllByProject(projectService.fetch(project), PageRequest.of(page, size));
    }

    @Override
    public void delete(UUID id) {
        this.requestRepository.delete(this.fetch(id));
    }

    void loadRequest(Request request) {
        this.scheduler.scheduleAtFixedRate(new RequestTask(request, client, this.influxWriteApi),
                                           Duration.of(request.getInterval(), ChronoUnit.SECONDS));
    }
}
