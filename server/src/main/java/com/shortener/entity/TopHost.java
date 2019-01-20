package com.shortener.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class TopHost {
    private String host;
    private Integer count;

    public TopHost() {
    }

    public TopHost(String host, Integer count) {
        this.host = host;
        this.count = count;
    }
}
