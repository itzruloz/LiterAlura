package com.challenge.literatura.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

// Definindo a entidade Author para persistência no banco de dados
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único do autor

    @Column(unique = true, nullable = false)
    private String name; // Nome do autor, único e obrigatório

    private LocalDate birthYear; // Ano de nascimento do autor
    private LocalDate deathYear; // Ano de falecimento do autor (pode ser null se ainda estiver vivo)

    // Relacionamento muitos-para-muitos com livros (cada autor pode ter vários livros)
    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<Book> books = new HashSet<>(); // Conjunto de livros escritos pelo autor

    // Construtor padrão (necessário para frameworks de persistência como JPA)
    public Author() {
    }

    // Construtor com parâmetros para inicializar nome, ano de nascimento e ano de falecimento do autor
    public Author(String name, String birthYear, String deathYear) {
        this.name = name;
        try {
            this.birthYear = LocalDate.parse(birthYear); // Parse do ano de nascimento para LocalDate
            this.deathYear = LocalDate.parse(deathYear); // Parse do ano de falecimento para LocalDate
        } catch (DateTimeParseException ex) {
            this.birthYear = null; // Em caso de erro de parsing, define ano de nascimento como null
            this.deathYear = null; // Em caso de erro de parsing, define ano de falecimento como null
        }
    }

    // Método getter para obter o nome do autor
    public String getName() {
        return name;
    }
    // Método toString para representação textual do objeto Author
    @Override
    public String toString() {
        // Mapeia os títulos dos livros escritos pelo autor em uma string separada por vírgula
        String booksStr = books.stream().map(Book::getTitle).collect(Collectors.joining(", "));

        // Retorna uma representação formatada do objeto Author incluindo nome, anos de nascimento e falecimento, e livros escritos
        return "Author{" +
                "name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                ", books=" + booksStr +
                '}';
    }
}