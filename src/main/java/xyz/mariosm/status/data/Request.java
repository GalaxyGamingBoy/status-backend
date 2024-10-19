package xyz.mariosm.status.data;

import jakarta.persistence.*;
import lombok.*;
import xyz.mariosm.status.http.RequestPayload;
import xyz.mariosm.status.service.ProjectService;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "requests")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Request {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Getter @Setter
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project")
    @Getter
    @Setter
    private Project project;

    @NonNull
    @Getter
    @Setter
    @Column(nullable = false)
    private String name;

    @NonNull
    @Getter
    @Setter
    @Column(nullable = false)
    private String uri;

    @NonNull
    @Getter
    @Setter
    @Column(nullable = false)
    private String method;

    @NonNull
    @Getter
    @Setter
    @Column(nullable = false)
    private Integer code;

    @NonNull
    @Getter
    @Setter
    @Column(nullable = false)
    private Long interval;

    @Getter
    @Setter
    private String token;

    @Getter
    @Column(nullable = false)
    private Date created;

    @Getter
    @Setter
    private Date modified;

    public Request() {}

    public Request(RequestPayload payload, ProjectService repository) {
        this.project = repository.fetch(payload.project());
        this.name = payload.name();
        this.uri = payload.uri();
        this.method = payload.method();
        this.code = payload.code();
        this.interval = payload.interval();
        this.token = payload.token();
        this.updateCreated();
    }

    public Date updateCreated() {
        this.created = new Date(System.currentTimeMillis());
        this.modified = this.created;
        return this.created;
    }

    public Date updateModified() {
        this.modified = new Date(System.currentTimeMillis());
        return this.modified;
    }

    @Override
    public String toString() {
        return "Request{" +
               "name='" + name + '\'' +
               ", uri='" + uri + '\'' +
               ", method=" + method +
               ", code=" + code +
               ", token='" + token + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) && Objects.equals(project,
                                                                request.project) && Objects.equals(
            name, request.name) && Objects.equals(uri, request.uri) && Objects.equals(method,
                                                                                      request.method) && Objects.equals(
            code, request.code) && Objects.equals(interval,
                                                              request.interval) && Objects.equals(token,
                                                                                                  request.token) && Objects.equals(
            created, request.created) && Objects.equals(modified, request.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, project, name, uri, method, code, interval, token, created, modified);
    }
}
