package com.saveme.comparator.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Data<T> {

    private T data;

}
