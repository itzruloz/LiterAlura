package com.challenge.literatura.repository;

import com.challenge.literatura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository; // Importa JpaRepository do Spring Data JPA
import org.springframework.data.jpa.repository.Query; // Importa Query do Spring Data JPA
import org.springframework.stereotype.Repository; // Importa Repository do Spring Framework

import java.awt.print.Book;
import java.time.LocalDate; // Importa LocalDate do Java.time
import java.util.List; // Importa List do Java.util

@Repository // Marca a interface como um componente gerenciado pelo Spring (um repositório de dados)
public interface BookRepository extends JpaRepository<Book, Long> {

    // Query personalizada para buscar autores cujo ano de nascimento seja menor ou igual ao ano fornecido
    @Query("SELECT DISTINCT a FROM Book b JOIN b.authors a WHERE YEAR(a.birthYear) <= :year")
    List<Author> findByAuthorsBirthYearLessThanEqual(int year);

    // Query personalizada para buscar todos os autores de todos os livros
    @Query("SELECT b.authors FROM Book b")
    List<Author> findAllAuthors();

    // Query personalizada para buscar autores cujo nome contenha o nome fornecido (ignorando maiúsculas/minúsculas)
    @Query("SELECT a FROM Book b JOIN b.authors a WHERE lower(a.name) LIKE lower(concat('%', :name, '%'))")
    List<Author> findByAuthorsName(String name);
}
