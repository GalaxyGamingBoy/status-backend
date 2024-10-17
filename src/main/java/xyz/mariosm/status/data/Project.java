package xyz.mariosm.status.data;

import jakarta.persistence.*;
import lombok.*;
import xyz.mariosm.status.http.ProjectPayload;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity @Table(name = "projects")
@Builder @AllArgsConstructor @RequiredArgsConstructor
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;

    @NonNull @Getter @Setter
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator")
    @NonNull @Getter @Setter
    private User creator;

    @Getter
    @Column(nullable = false)
    private Date created;

    @Getter @Setter
    private Date modified;

    public Project() {}

    public Project(ProjectPayload payload, User user) {
        this.name = payload.name();
        this.creator = user;

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
        return "Project{" +
               "name='" + name + '\'' +
               ", created=" + created +
               ", modified=" + modified +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) && Objects.equals(name,
                                                                project.name) && Objects.equals(
            creator, project.creator) && Objects.equals(created, project.created) && Objects.equals(
            modified, project.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creator, created, modified);
    }
}
