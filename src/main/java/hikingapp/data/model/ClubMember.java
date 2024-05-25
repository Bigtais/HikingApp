package hikingapp.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NamedEntityGraph(
        name = "member-with-hikes",
        attributeNodes = {
                @NamedAttributeNode("id"),
                @NamedAttributeNode("firstName"),
                @NamedAttributeNode("lastName"),
                @NamedAttributeNode("email"),
                @NamedAttributeNode("hikes")
        }
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClubMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{member.name.not_empty}")
    private String firstName;

    @NotEmpty(message = "{member.name.not_empty}")
    private String lastName;

    @NotEmpty(message = "{member.email.not_empty}")
    private String email;

    @NotEmpty(message = "{member.password.not_empty}")
    @Size(min = 8, message = "{member.password.too_short}")
    private String password;

    @Version
    private long version = 0;

    @Transient
    public static long updateCounter = 0;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Hike> hikes;

    @ElementCollection(fetch = FetchType.EAGER)
    Collection<String> authorities;

    public void addHike(Hike hike) {
        if (hikes == null)
            hikes = new ArrayList<>();
        hikes.add(hike);
        hike.setCreator(this);
    }

    public void removeHike(Hike hike) {
        if (hikes != null)
            hikes.removeIf(hike::equals);
    }

    @PreUpdate
    public void beforeUpdate() {
        System.err.println("PreUpdate of " + this);
    }

    @PostUpdate
    public void afterUpdate() {
        System.err.println("PostUpdate of " + this);
        updateCounter++;
    }
}