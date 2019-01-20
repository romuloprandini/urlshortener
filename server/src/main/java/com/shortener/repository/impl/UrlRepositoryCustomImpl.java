package com.shortener.repository.impl;

import com.shortener.entity.TopHost;
import com.shortener.repository.UrlRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class UrlRepositoryCustomImpl implements UrlRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<TopHost> findByTopHosts(Pageable pageable) {
        BigInteger total = (BigInteger)entityManager.createNativeQuery("select count(*) from (select count(u.host) from url u group by u.host) a").getSingleResult();


        Query query = entityManager.createQuery("select u.host, count(u.host) as count from Url u group by u.host order by count(u.host) desc");
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult((int)pageable.getOffset());

        List<Object[]> response = query.getResultList();
        List<TopHost> topHosts = new ArrayList<>(response.size());
        for(Object[] row : response) {
            topHosts.add(new TopHost((String)row[0], (Long)row[1]));
        }

        return new PageImpl(topHosts, pageable, total.longValue());
    }
}
