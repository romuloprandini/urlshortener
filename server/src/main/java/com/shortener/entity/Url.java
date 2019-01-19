package com.shortener.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter @Setter
public class Url {

    public Url() {

    }

    @Builder
    public Url(Long id, @NotNull String identifier, @NotNull String url, @NotNull String protocol, @NotNull String host, Integer port, @NotNull Timestamp createdAt, Timestamp lastAccess, Long totalAccess) {
        this.id = id;
        this.identifier = identifier;
        this.url = url;
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.createdAt = createdAt;
        this.lastAccess = lastAccess;
        this.totalAccess = totalAccess;
    }

    @Id
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String identifier;

    @NotNull
    @Column(nullable = false)
    private String url;

    @NotNull
    @Column(nullable = false)
    private String protocol;

    @NotNull
    @Column(nullable = false)
    private String host;

    private Integer port;

    @NotNull
    @Column(nullable = false)
    private Timestamp createdAt;

    private Timestamp lastAccess;

    private Long totalAccess;
}
