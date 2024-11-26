package com.apiapp.services;

import com.apiapp.dtos.article.ArticleCreateDto;
import com.apiapp.dtos.article.ArticleDto;
import com.apiapp.dtos.article.ArticleUpdateDto;
import com.apiapp.payloads.ApiPayload;
import com.apiapp.payloads.ApiResponse;
import com.apiapp.payloads.PaginationPayload;
import com.apiapp.payloads.results.DataResult;

import java.util.List;

public interface ArticleService {
    ApiPayload<List<ArticleDto>> getAllArticles();

    PaginationPayload<ArticleDto> getPaginationArticles(Integer pageNumber, Integer pageSize);

    ApiResponse createArticle(ArticleCreateDto articleCreateDto);

    ArticleUpdateDto findUpdateArticle(Long id);

    ApiResponse updateArticle(Long id, ArticleUpdateDto articleUpdateDto);

    ApiResponse deleteArticle(Long id);
}
