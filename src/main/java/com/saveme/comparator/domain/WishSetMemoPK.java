package com.saveme.comparator.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class WishSetMemoPK implements Serializable {
    private Long wishSetId;
    private Long wishId;
}
