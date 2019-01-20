package com.shortener.repository;

import com.shortener.entity.TopHost;
import com.shortener.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UrlRepositoryCustom {
    Page<TopHost> findByTopHosts(Pageable pageable);
}
