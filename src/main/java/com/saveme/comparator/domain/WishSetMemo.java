package com.saveme.comparator.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@ToString
@DynamicInsert
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WishSetMemo {

    @EmbeddedId
    private WishSetMemoPK wishSetMemoPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @MapsId("wishId")
    private Wish wish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_set_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @MapsId("wishSetId")
    private WishSet wishSet;

    private String memo;
}
