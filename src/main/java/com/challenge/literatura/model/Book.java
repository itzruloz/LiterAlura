package com.challenge.literatura.model;

import com.challenge.literatura.dto.BookDTO;
import com.challenge.literatura.model.Author;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;



@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private int downloadCount;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    // Construtor padrão
    public Book() {
    }

    // Construtor que aceita um BookDTO
    public Book(BookDTO bookDTO) {
        this.title = bookDTO.title();
        this.downloadCount = bookDTO.downloadCount();
        this.authors = bookDTO.authors().stream()
                .map(authorDTO -> new Author(authorDTO.name(), authorDTO.birthYear(), authorDTO.deathYear()))
                .collect(Collectors.toSet());
    }

    // Getter para title
    public String getTitle() {
        return title;
    }

    // Setter para title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter para downloadCount
    public int getDownloadCount() {
        return downloadCount;
    }

    // Setter para downloadCount
    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    // Getter para authors
    public Set<Author> getAuthors() {
        return authors;
    }

    // Setter para authors
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        String authorsStr = authors.stream()
                .map(Author::getName)
                .collect(Collectors.joining(", "));

        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", downloadCount=" + downloadCount +
                ", authors=[" + authorsStr + "]" +
                '}';
    }
}
