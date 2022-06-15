package com.book.mapper;

import com.book.dto.AuthorDTO;
import com.book.dto.BookDTO;
import com.book.entity.Book;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.instancio.Select.field;

@ExtendWith(InstancioExtension.class)
class BookMapperTest {

    private final BookMapper mapper = new BookMapper();

    @Test
    void map() {
        final BookDTO bookDto = createBookDTO();
        final Book book = mapper.toEntity(bookDto);

        assertThat(book.getId().toString()).isEqualTo(bookDto.getBookId());
        assertThat(book.getIsbn()).isEqualTo(bookDto.getISBN());
        assertThat(book.getTitle()).isEqualTo(bookDto.getBookTitle());
        assertThat(book.getGenre()).isEqualTo(bookDto.getGenre());
        assertThat(book.getDatePublished()).isEqualTo(bookDto.getPublishedOn());
        assertThat(book.getPages()).isEqualTo(bookDto.getNumberOfPages());
        assertThat(book.getPrice()).isEqualTo(bookDto.getPriceCents());

        final AuthorDTO authorDto = bookDto.getAuthor();
        assertThat(book.getAuthor().getId().toString()).isEqualTo(authorDto.getAuthorId());
        assertThat(book.getAuthor().getName()).isEqualTo(authorDto.getFirstName() + " " + authorDto.getLastName());

    }

    private BookDTO createBookDTO() {
        return Instancio.of(BookDTO.class)
                .supply(all(field(BookDTO.class, "bookId"), field(AuthorDTO.class, "authorId")),
                        () -> UUID.randomUUID().toString())
                .create();

        // Manual data setup
        //
        // final AuthorDTO authorDto = new AuthorDTO();
        // authorDto.setAuthorId(UUID.randomUUID().toString());
        // authorDto.setFirstName("Jane");
        // authorDto.setLastName("Doe");
        //
        // final BookDTO bookDto = new BookDTO();
        // bookDto.setBookId(UUID.randomUUID().toString());
        // bookDto.setIsbn("test-isbn");
        // bookDto.setBookTitle("test-title");
        // bookDto.setAuthor(authorDto);
        // bookDto.setGenre(Genre.FICTION);
        // bookDto.setPublishedOn(LocalDate.of(1865, 3, 17));
        // bookDto.setNumberOfPages(123);
        // bookDto.setPriceCents(3099);
        // return bookDto;
    }
}
