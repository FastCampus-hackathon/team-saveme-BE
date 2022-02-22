package com.saveme.comparator.domain;

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
public class WishSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishSetId;
    private String setTitle;
}
