package com.shortener.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class TopHost {
    private String host;
    private Long count;

    public TopHost() {
    }

    public TopHost(String host, Long count) {
        this.host = host;
        this.count = count;
    }
}
