package hikingapp.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NamedEntityGraph(
        name = "hikes-with-creator-and-category",
        attributeNodes = {
                @NamedAttributeNode("id"),
                @NamedAttributeNode("name"),
                @NamedAttributeNode("website"),
                @NamedAttributeNode("date"),
                @NamedAttributeNode("creator"),
                @NamedAttributeNode("category")
        }
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{hike.name.not_empty}")
    @Size(min = 3, message = "{hike.name.too_short}")
    private String name;

    @NotEmpty(message = "{hike.desc.not_empty}")
    @Size(min = 8, message = "{hike.desc.too_short}")
    private String description;

    @NotEmpty(message = "{hike.website.not_empty}")
    private String website;

    @NotNull(message = "{hike.date.not_empty}")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClubMember creator;

    @NotNull(message = "{hike.category.not_empty}")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @Version
    private long version = 0;

    @Transient
    public static long updateCounter = 0;

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
