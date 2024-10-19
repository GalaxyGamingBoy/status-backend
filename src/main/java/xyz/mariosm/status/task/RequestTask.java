package xyz.mariosm.status.task;

import com.influxdb.client.WriteApi;
import com.influxdb.client.write.Point;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import xyz.mariosm.status.data.Request;

import java.io.IOException;

@Log4j2
public class RequestTask implements Runnable {
    private final Request source;
    private final OkHttpClient client;
    private final okhttp3.Request request;
    private final WriteApi influx;

    public RequestTask(Request request, OkHttpClient client, WriteApi influx) {
        this.source = request;
        this.client = client;
        this.request = new okhttp3.Request.Builder()
                                          .url(request.getUri())
                                          .method(request.getMethod(), null)
                                          .build();
        this.influx = influx;

        log.info("Registering Request Task, {}", request.getName());
    }

    @Override
    public void run() {
        log.info("Sending scheduled request, {}", this.source.getName());

        try (Response response = client.newCall(request).execute()) {
            Long responseTime = response.receivedResponseAtMillis() - response.sentRequestAtMillis();
            Integer status = response.code();

            if (status.equals(this.source.getCode())) {
                log.info("{}, Status: {} (Time: {})", this.source.getName(), status, responseTime);
            } else {
                log.info("{}, Status: {}", this.source.getName(), status);
            }

            this.influx.writePoint(Point.measurement(this.source.getId().toString())
                                        .addTag("code", String.valueOf(status))
                                        .addTag("ok", String.valueOf(status.equals(this.source.getCode())))
                                        .addField("response", responseTime));
        } catch (IOException e) {
            log.error(e);
        }
    }
}
