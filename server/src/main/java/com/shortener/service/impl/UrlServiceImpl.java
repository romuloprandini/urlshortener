package com.shortener.service.impl;

import com.shortener.entity.TopHost;
import com.shortener.entity.Url;
import com.shortener.exception.BusinessException;
import com.shortener.repository.UrlRepository;
import com.shortener.service.UrlService;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@Transactional
public class UrlServiceImpl implements UrlService {

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = ALPHABET.length();


    private final UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public Url getById(@NonNull Long id) {
        return urlRepository.findById(id).orElse(null);
    }

    @Override
    public Url getByIdentifier(@NonNull String identifier) {
        Long id = decodeIdentifier(identifier);
        return urlRepository.findById(id).orElse(null);
    }

    @Override
    public String getUrlByIdentifier(@NonNull String identifier) {
        Long id = decodeIdentifier(identifier);
        return urlRepository.findUrlById(id);
    }

    @Override
    public Url create(@NonNull String url) {
        Timestamp createdAt = Timestamp.from(Instant.now());
        URL urltoShort;
        try {
            urltoShort = new URL(url);
        } catch (MalformedURLException e) {
            throw new BusinessException("Invalid URL");
        }

        Url newUrl = new Url();
        newUrl.setId(createdAt.getTime());
        newUrl.setUrl(urltoShort.toString());
        newUrl.setProtocol(urltoShort.getProtocol());
        newUrl.setHost(urltoShort.getHost());
        newUrl.setPort(urltoShort.getPort());
        newUrl.setCreatedAt(createdAt);
        newUrl.setTotalAccess(0L);
        newUrl.setIdentifier(encodeIdentifier(newUrl.getId()));
        return urlRepository.save(newUrl);
    }

    @Override
    public Page<Url> findAll(Pageable pageable) {
        return urlRepository.findAll(pageable);
    }

    @Override
    public void incrementAccess(@NonNull String identifier) {
        Long id = decodeIdentifier(identifier);
        urlRepository.updateTotalAccessAndLastAccess(id);
    }

    @Override
    public Url findLatestUrlCreated() {
        return urlRepository.findByLatesteCreated();
    }

    @Override
    public Page<Url> findTopUrl(Pageable pageable) {
        return urlRepository.findByTopUrl(pageable);
    }

    @Override
    public Page<TopHost> findTopHost(Pageable pageable) {
        return urlRepository.findByTopHosts(pageable);
    }

    private String encodeIdentifier(long num) {
        StringBuilder sb = new StringBuilder();
        while ( num > 0 ) {
            sb.append( ALPHABET.charAt( (int)(num % BASE) ) );
            num /= BASE;
        }
        return sb.reverse().toString();
    }

    public Long decodeIdentifier(String identifier) {
        Long num = 0L;
        for ( int i = 0; i < identifier.length(); i++ )
            num = num * BASE + ALPHABET.indexOf(identifier.charAt(i));
        return num;
    }
}
