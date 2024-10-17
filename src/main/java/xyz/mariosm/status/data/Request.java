package xyz.mariosm.status.data;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpMethod;

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
    @Getter
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project")
    @NonNull
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
    private Integer statusCode;

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
               ", statusCode=" + statusCode +
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
            statusCode, request.statusCode) && Objects.equals(interval,
                                                              request.interval) && Objects.equals(token,
                                                                                                  request.token) && Objects.equals(
            created, request.created) && Objects.equals(modified, request.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, project, name, uri, method, statusCode, interval, token, created, modified);
    }
}
