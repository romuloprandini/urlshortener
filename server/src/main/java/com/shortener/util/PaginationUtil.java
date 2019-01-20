package com.shortener.util;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

public class PaginationUtil {
    private static final String REL_COLLECTION = "collection";
    private static final String REL_NEXT = "next";
    private static final String REL_PREV = "prev";
    private static final String REL_FIRST = "first";
    private static final String REL_LAST = "last";
    private static final String PAGE = "page";
    public static final String X_TOTAL_COUNT="X-Total-Count";


    public static void generatePagination(UriComponentsBuilder uriBuilder, HttpServletResponse response, int page, int totalPages, int pageSize, long totalElements) {
        StringBuilder linkHeader = new StringBuilder();
        if (hasNextPage(page, totalPages)) {
            String uriForNextPage = constructNextPageUri(uriBuilder, page, pageSize);
            linkHeader.append(createLinkHeader(uriForNextPage, REL_NEXT));
        }
        if (hasPreviousPage(page)) {
            String uriForPrevPage = constructPrevPageUri(uriBuilder, page, pageSize);
            appendCommaIfNecessary(linkHeader);
            linkHeader.append(createLinkHeader(uriForPrevPage, REL_PREV));
        }
        if (hasFirstPage(page)) {
            String uriForFirstPage = constructFirstPageUri(uriBuilder, pageSize);
            appendCommaIfNecessary(linkHeader);
            linkHeader.append(createLinkHeader(uriForFirstPage, REL_FIRST));
        }
        if (hasLastPage(page, totalPages)) {
            String uriForLastPage = constructLastPageUri(uriBuilder, totalPages, pageSize);
            appendCommaIfNecessary(linkHeader);
            linkHeader.append(createLinkHeader(uriForLastPage, REL_LAST));
        }

        if (linkHeader.length() > 0) {
            response.addHeader(HttpHeaders.LINK, linkHeader.toString());
            response.addHeader(X_TOTAL_COUNT, String.valueOf(totalElements));
        }
    }

    public static String createLinkHeader(String uri, String rel) {
        return "<" + uri + ">; rel=\"" + rel + "\"";
    }

    private static String constructNextPageUri(UriComponentsBuilder uriBuilder, int page, int size) {
        return uriBuilder.replaceQueryParam(PAGE, page + 1).replaceQueryParam("size", size).build().encode().toUriString();
    }

    private static String constructPrevPageUri(UriComponentsBuilder uriBuilder, int page, int size) {
        return uriBuilder.replaceQueryParam(PAGE, page - 1).replaceQueryParam("size", size).build().encode().toUriString();
    }

    private static String constructFirstPageUri(UriComponentsBuilder uriBuilder, int size) {
        return uriBuilder.replaceQueryParam(PAGE, 0).replaceQueryParam("size", size).build().encode().toUriString();
    }

    private static String constructLastPageUri(UriComponentsBuilder uriBuilder, int totalPages, int size) {
        return uriBuilder.replaceQueryParam(PAGE, totalPages).replaceQueryParam("size", size).build().encode().toUriString();
    }

    private static boolean hasNextPage(int page, int totalPages) {
        return page < (totalPages - 1);
    }

    private static boolean hasPreviousPage(int page) {
        return page > 0;
    }

    private static boolean hasFirstPage(int page) {
        return hasPreviousPage(page);
    }

    private static boolean hasLastPage(int page, int totalPages) {
        return (totalPages > 1) && hasNextPage(page, totalPages);
    }

    private static void appendCommaIfNecessary(StringBuilder linkHeader) {
        if (linkHeader.length() > 0) {
            linkHeader.append(", ");
        }
    }
}
