package com.example.filter;

import com.example.entity.Book;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BookFilter {
    private String title;
    private String authorName;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String sortBy;
    private String sortDirection;
    private Integer page;
    private Integer pageSize;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PanacheQuery<Book> createQuery() {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<>();
        List<String> conditions = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            conditions.add("title LIKE ?" + (params.size() + 1));
            params.add("%" + title + "%");
        }

        if (authorName != null && !authorName.isEmpty()) {
            conditions.add("author.name LIKE ?" + (params.size() + 1));
            params.add("%" + authorName + "%");
        }

        if (minPrice != null) {
            conditions.add("price >= ?" + (params.size() + 1));
            params.add(minPrice);
        }

        if (maxPrice != null) {
            conditions.add("price <= ?" + (params.size() + 1));
            params.add(maxPrice);
        }

        if (!conditions.isEmpty()) {
            query.append("WHERE ").append(String.join(" AND ", conditions));
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            query.append(" ORDER BY ").append(sortBy);
            if (sortDirection != null && !sortDirection.isEmpty()) {
                query.append(" ").append(sortDirection.toUpperCase());
            }
        }

        PanacheQuery<Book> panacheQuery = Book.find(query.toString(), params.toArray());

        if (page != null && pageSize != null) {
            panacheQuery.page(page, pageSize);
        }

        return panacheQuery;
    }
} 