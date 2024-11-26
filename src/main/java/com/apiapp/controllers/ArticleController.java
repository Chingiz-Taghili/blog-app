package com.apiapp.controllers;

import com.apiapp.dtos.article.ArticleCreateDto;
import com.apiapp.dtos.article.ArticleDto;
import com.apiapp.dtos.article.ArticleUpdateDto;
import com.apiapp.payloads.ApiResponse;
import com.apiapp.payloads.PaginationPayload;
import com.apiapp.services.ArticleService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/article")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("get-all")
    public ResponseEntity<PaginationPayload<ArticleDto>> getAll(Integer pageNumber, Integer pageSize) {
        PaginationPayload<ArticleDto> articles = articleService.getPaginationArticles(pageNumber, pageSize);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<ArticleUpdateDto> getUpdateArticle(@PathVariable Long id) {
        ArticleUpdateDto articleUpdateDto = articleService.findUpdateArticle(id);
        return new ResponseEntity<>(articleUpdateDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody ArticleCreateDto articleCreateDto) {
        ApiResponse response = articleService.createArticle(articleCreateDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody ArticleUpdateDto articleUpdateDto) {
        ApiResponse response = articleService.updateArticle(id, articleUpdateDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        ApiResponse response = articleService.deleteArticle(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
