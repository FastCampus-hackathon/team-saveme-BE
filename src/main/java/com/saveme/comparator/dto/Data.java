package com.saveme.comparator.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Data<T> {
    private T data;
}
