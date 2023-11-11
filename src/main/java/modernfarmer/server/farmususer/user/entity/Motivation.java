package modernfarmer.server.farmususer.user.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "motivation")
public class Motivation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "motivation_id", nullable = false)
    private Long id;


    @Column(name = "motivation_reason", nullable = false, length = 40)
    private String motivationReason;

    @OneToMany(mappedBy = "motivation", fetch = FetchType.LAZY)
    private Set<UserMotivation> userMotivations = new LinkedHashSet<>();



}