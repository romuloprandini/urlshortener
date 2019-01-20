package com.shortener.service;

import com.shortener.entity.TopHost;
import com.shortener.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UrlService {

    Url getById(Long id);

    Boolean exists(String identifier);

    Url getByIdentifier(String identifier);

    String getUrlByIdentifier(String identifier);

    Url create(String url);

    Page<Url> findAll(Pageable pageable);

    void incrementAccess(String identifier);

    Url findLatestUrlCreated();

    Page<Url> findTopUrl(Pageable pageable);

    Page<TopHost> findTopHost(Pageable pageable);
}
