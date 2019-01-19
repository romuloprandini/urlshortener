package com.shortener.service;

import com.shortener.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UrlService {

    Url getById(Long id);

    Url getByIdentifier(String identifier);

    String getUrlByIdentifier(String identifier);

    Url create(String url);

    Page<Url> findAll(Pageable pageable);

    void incrementAccess(String identifier);

    Url findLatestUrlCreated();

    Page<Url> findTopUrl(Pageable pageable);

    Page<Url> findTopHost(Pageable pageable);
}
