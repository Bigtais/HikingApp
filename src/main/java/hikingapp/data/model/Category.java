package hikingapp.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "categories-with-hikes",
        attributeNodes = {
                @NamedAttributeNode("id"),
                @NamedAttributeNode("name"),
                @NamedAttributeNode("hikes")
        }
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{category.name.not_empty}")
    @Size(min = 3, message = "{category.name.too_short}")
    private String name;

    @Version
    private long version = 0;

    @Transient
    public static long updateCounter = 0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Hike> hikes;

    public void addHike(Hike hike) {
        if (hikes == null)
            hikes = new ArrayList<>();
        hikes.add(hike);
        hike.setCategory(this);
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
