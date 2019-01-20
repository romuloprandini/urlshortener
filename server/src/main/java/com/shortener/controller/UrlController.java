package com.shortener.controller;

import com.shortener.entity.Url;
import com.shortener.service.UrlService;
import com.shortener.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, exposedHeaders = {"Location", "Link", "X-Total-Count"})
@RestController
@RequestMapping(value = "${shortener.server.path:/}")
public class UrlController {

    private final UrlService urlService;

    @Value("${shortener.server.path:''}")
    private String path;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
        this.path = "";
    }

    @GetMapping(value = "{identifier}")
    public ResponseEntity<?> getFullUrl(@PathVariable("identifier") String identifier,
                                             @RequestParam(value = "noRedirect", required = false, defaultValue = "false") Boolean noRedirect) throws URISyntaxException {

        if(!urlService.exists(identifier)) {
            return ResponseEntity.notFound().build();
        }

        urlService.incrementAccess(identifier);

        if(noRedirect) {
            Url url = urlService.getByIdentifier(identifier);
            return ResponseEntity.ok(url);
        }
        String url = urlService.getUrlByIdentifier(identifier);
        return ResponseEntity.status(HttpServletResponse.SC_MOVED_PERMANENTLY).location(new URI(url)).build();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> saveUrl(@RequestBody String stringUrl) throws URISyntaxException {
        Url urlBean = urlService.create(stringUrl);
          URI uri = new URI(path.concat("/"+urlBean.getIdentifier()));
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTop(@RequestParam(value = "type", defaultValue = "url") String type, Pageable pageable, UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        Page<?> pageUrl;
        switch (type) {
            case "url":
                pageUrl = urlService.findTopUrl(pageable);
             break;
            case "host":
                pageUrl = urlService.findTopHost(pageable);
                break;
            default:
                pageUrl = urlService.findAll(pageable);
                break;
        }
        PaginationUtil.generatePagination(uriBuilder, response, pageable.getPageNumber(), pageUrl.getTotalPages(), pageable.getPageSize(), pageUrl.getTotalElements());
        return ResponseEntity.ok(pageUrl.getContent());
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestUrlCreated() {
        Url latestUrl = urlService.findLatestUrlCreated();
        return ResponseEntity.ok(latestUrl);
    }
}
