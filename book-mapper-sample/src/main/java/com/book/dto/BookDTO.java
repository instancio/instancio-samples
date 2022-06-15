package com.book.dto;

import com.book.entity.Genre;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;

public class BookDTO {
    private String bookId;
    private String isbn;
    private String bookTitle;
    private AuthorDTO author;
    private Genre genre;
    private LocalDate publishedOn;
    private int numberOfPages;
    private long priceCents;

    public String getBookId() {
        return bookId;
    }

    public String getISBN() {
        return isbn;
    }

    public void setBookId(final String bookId) {
        this.bookId = bookId;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(final String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(final AuthorDTO author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(final Genre genre) {
        this.genre = genre;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(final LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(final int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public long getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(final long priceCents) {
        this.priceCents = priceCents;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
