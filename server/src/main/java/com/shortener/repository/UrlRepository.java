package com.shortener.repository;

import com.shortener.entity.TopHost;
import com.shortener.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findByIdentifier(String identifier);

    @Query("select u.url from Url u where u.id = ?1")
    String findUrlById(Long id);

    @Query("select u.url from Url u where u.identifier = ?1")
    String findUrlByIdentifier(String identifier);

    @Query("select u.host, count(u.host) as count from Url u group by u.host order by count(u.host) desc")
    Page<TopHost> findByTopHosts(Pageable pageable);

    @Query("select u from Url u order by u.totalAccess")
    Page<Url> findByTopUrl(Pageable pageable);

    @Query("select u from Url u where u.id =  max(u.id)")
    Url findByLatesteCreated();

    @Modifying
    @Query("update Url u set u.totalAccess = u.totalAccess + 1, u.lastAccess = current_timestamp() where u.id = ?1")
    void updateTotalAccessAndLastAccess(Long id);

    Boolean existsByIdentifier(String identifier);
}
