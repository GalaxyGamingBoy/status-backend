package xyz.mariosm.status.service;

import xyz.mariosm.status.data.Request;

import java.util.List;

public interface RequestService {
    void cancelAllRequests();
    void loadRequests(List<Request> requests);
    void loadRequestsFromDB();
}
