package xyz.mariosm.status.service.impl;

import com.influxdb.client.WriteApi;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import xyz.mariosm.status.data.Request;
import xyz.mariosm.status.repository.ProjectRepository;
import xyz.mariosm.status.repository.RequestRepository;
import xyz.mariosm.status.service.InfluxService;
import xyz.mariosm.status.service.RequestService;
import xyz.mariosm.status.task.RequestTask;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Service
public class RequestServiceImpl implements RequestService {
    private final OkHttpClient client;

    private final TaskScheduler scheduler;

    private final RequestRepository requestRepository;

    private final WriteApi influxWriteApi;

    private List<ScheduledFuture<?>> tasks;

    @Autowired
    public RequestServiceImpl(TaskScheduler scheduler, RequestRepository requestRepository,
                               InfluxService influxService) {
        this.client = new OkHttpClient();
        this.scheduler = scheduler;
        this.requestRepository = requestRepository;
        this.influxWriteApi = influxService.getWriteApi();
//        Request request = Request.builder().name("Google")
//                                 .uri("https://google.com")
//                                 .method(HttpMethod.GET.name())
//                                 .interval(60L)
//                                 .statusCode(200)
//                                 .project(projectRepository.findByName("test_project").orElseThrow()).build();
//        request.updateCreated();
//        this.requestRepository.save(request);

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

    void loadRequest(Request request) {
        this.scheduler.scheduleAtFixedRate(new RequestTask(request, client, this.influxWriteApi),
                                           Duration.of(request.getInterval(), ChronoUnit.SECONDS));
    }
}
