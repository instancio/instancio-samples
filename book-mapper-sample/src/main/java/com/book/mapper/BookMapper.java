package com.book.mapper;

import com.book.dto.BookDTO;
import com.book.entity.Author;
import com.book.entity.Book;

import java.util.UUID;

public class BookMapper {

    public Book toEntity(BookDTO dto) {
        final Author author = Author.newBuilder()
                .id(UUID.fromString(dto.getAuthor().getAuthorId()))
                .name(String.format("%s %s", dto.getAuthor().getFirstName(), dto.getAuthor().getLastName()))
                .build();

        return Book.newBuilder()
                .id(UUID.fromString(dto.getBookId()))
                .isbn(dto.getISBN())
                .title(dto.getBookTitle())
                .author(author)
                .genre(dto.getGenre())
                .datePublished(dto.getPublishedOn())
                .pages(dto.getNumberOfPages())
                .price(dto.getPriceCents())
                .build();
    }

}
