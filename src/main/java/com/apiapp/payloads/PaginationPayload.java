package com.apiapp.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationPayload<TEntity> {
    private Integer totalPage;
    private Integer pageSize;
    private Integer currentPage;
    private List<TEntity> data;
}
