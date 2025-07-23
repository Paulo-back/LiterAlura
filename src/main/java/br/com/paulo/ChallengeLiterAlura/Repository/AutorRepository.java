package br.com.paulo.ChallengeLiterAlura.Repository;

import br.com.paulo.ChallengeLiterAlura.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByAutor(String nome);
    Optional<Autor>findByAutorContainingIgnoreCase(String nome);
    @Query("SELECT a FROM Autor a WHERE a.anoFalec > :ano AND a.anoNasc < :ano")
    List<Autor> entreAnos(int ano);

}
