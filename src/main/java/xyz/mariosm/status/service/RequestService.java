package xyz.mariosm.status.service;

import org.springframework.data.domain.Page;
import xyz.mariosm.status.data.Request;
import xyz.mariosm.status.http.RequestPayload;

import java.util.List;
import java.util.UUID;

public interface RequestService {
    void cancelAllRequests();
    void loadRequests(List<Request> requests);
    void loadRequestsFromDB();
    void refreshFromDB();

    Request insert(Request request);
    Request update(UUID id, Request request);
    Request fetch(UUID id);
    Page<Request> fetchAll(int page, int size);
    Page<Request> fetchAllFromProject(UUID project, int page, int size);
    void delete(UUID id);
}
