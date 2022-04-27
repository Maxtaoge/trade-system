package ai.techfin.tradesystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "placement_list")
public class PlacementList {

    private static final Logger log = LoggerFactory.getLogger(PlacementList.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @ElementCollection(targetClass = Placement.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "placement_list_data", joinColumns = @JoinColumn(name = "list_id", referencedColumnName = "id"))
    private Set<Placement> placements = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "placementList", cascade = CascadeType.ALL)
    @JsonIgnore
    private ModelOrderList modelOrderList = null;

    public PlacementList(Set<Placement> placements) {
        this.placements = placements;
    }

    public PlacementList() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModelOrderList getModelOrderList() {
        return modelOrderList;
    }

    /**
     * Add a relationship between this entity with the given MOL Will remove the existing(previous) relationship within
     * (this -- this's origin, the given object -- it's origin)
     *
     * @param modelOrderList set to null to just remove the existing relationship
     */
    public void setModelOrderList(ModelOrderList modelOrderList) {
        // base line -- when it is the same, no state need to be changed
        if (modelOrderList == this.modelOrderList) {
            return;
        }
        ModelOrderList origin = this.modelOrderList;
        if (modelOrderList == null) {
            // do this side
            this.modelOrderList = null;
            // do the other side
            if (origin.getPlacementList() == this) {
                origin.setPlacementList(null);
            }
        } else {
            // do this side
            this.modelOrderList = modelOrderList;
            // do that side
            modelOrderList.setPlacementList(this);
            // do origin side
            if (origin != null) {
                origin.setPlacementList(null);
            }
        }
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Placement> getPlacements() {
        return placements;
    }

    public void setPlacements(Set<Placement> placements) {
        this.placements = placements;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlacementList)) {
            return false;
        }
        return id != null && id.equals(((PlacementList) o).id);
    }
}
