package com.book.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.UUID;

public class Book {
    private final UUID id;
    private final String isbn;
    private final String title;
    private final Author author;
    private final Genre genre;
    private final LocalDate datePublished;
    private final Integer pages;
    private final Long price;

    private Book(final Builder builder) {
        id = builder.id;
        isbn = builder.isbn;
        title = builder.title;
        author = builder.author;
        genre = builder.genre;
        datePublished = builder.datePublished;
        pages = builder.pages;
        price = builder.price;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public LocalDate getDatePublished() {
        return datePublished;
    }

    public Integer getPages() {
        return pages;
    }

    public Long getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static final class Builder {
        private UUID id;
        private String isbn;
        private String title;
        private Author author;
        private Genre genre;
        private LocalDate datePublished;
        private Integer pages;
        private Long price;

        private Builder() {
        }

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder isbn(final String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        public Builder author(final Author author) {
            this.author = author;
            return this;
        }

        public Builder genre(final Genre genre) {
            this.genre = genre;
            return this;
        }

        public Builder datePublished(final LocalDate datePublished) {
            this.datePublished = datePublished;
            return this;
        }

        public Builder pages(final Integer pages) {
            this.pages = pages;
            return this;
        }

        public Builder price(final Long price) {
            this.price = price;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
