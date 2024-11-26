package com.apiapp.services.impls;

import com.apiapp.dtos.article.ArticleCreateDto;
import com.apiapp.dtos.article.ArticleDto;
import com.apiapp.dtos.article.ArticleUpdateDto;
import com.apiapp.exceptions.ResourceNotFoundException;
import com.apiapp.models.Article;
import com.apiapp.models.Category;
import com.apiapp.payloads.ApiPayload;
import com.apiapp.payloads.ApiResponse;
import com.apiapp.payloads.PaginationPayload;
import com.apiapp.repositories.ArticleRepository;
import com.apiapp.services.ArticleService;
import com.apiapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiPayload<List<ArticleDto>> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleDto> articleList = articles.stream().map(article -> modelMapper.map(article, ArticleDto.class)).toList();
        return new ApiPayload<>(true, articleList);
    }

    @Override
    public PaginationPayload<ArticleDto> getPaginationArticles(Integer pageNumber, Integer pageSize) {
        pageNumber = (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
        pageSize = (pageSize == null || pageSize < 1) ? 10 : pageSize;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("id"));
        Page<Article> articles = articleRepository.findAll(pageable);
        List<ArticleDto> result = articles.stream().map(article -> modelMapper.map(article, ArticleDto.class)).toList();
        PaginationPayload<ArticleDto> payload = new PaginationPayload<>();
        payload.setData(result);
        payload.setTotalPage(articles.getTotalPages());
        payload.setCurrentPage(pageNumber);
        payload.setPageSize(pageSize);
        return payload;
    }

    @Transactional
    @Override
    public ApiResponse createArticle(ArticleCreateDto articleCreateDto) {
        try {
            Category category = categoryService.getById(articleCreateDto.getCategoryId());
            Article article = modelMapper.map(articleCreateDto, Article.class);
            article.setCreatedDate(LocalDate.now());
            article.setUpdatedDate(LocalDate.now());
            article.setCategory(category);
            article.setId(null);
            articleRepository.save(article);

            return new ApiResponse("Article added successfully", true);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ApiResponse(e.getMessage(), false);
        }
    }

    @Override
    public ArticleUpdateDto findUpdateArticle(Long id) {
        Article findArticle = articleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Article", "id", id));
        ArticleUpdateDto article = modelMapper.map(findArticle, ArticleUpdateDto.class);
        return article;
    }

    @Override
    public ApiResponse updateArticle(Long id, ArticleUpdateDto articleUpdateDto) {
        try {
            Article findArticle = articleRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Article", "id", id));
            Category category = categoryService.getById(articleUpdateDto.getCategoryId());
            findArticle.setTitle(articleUpdateDto.getTitle());
            findArticle.setDescription(articleUpdateDto.getDescription());
            findArticle.setUpdatedDate(LocalDate.now());
            findArticle.setCategory(category);
            articleRepository.save(findArticle);
            return new ApiResponse("Article updated successfully", true);
        } catch (Exception e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }

    @Override
    public ApiResponse deleteArticle(Long id) {
        try {
            Article findArticle = articleRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Article", "id", id));
            articleRepository.delete(findArticle);
            return new ApiResponse("Article deleted successfully", true);
        } catch (Exception e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }
}
