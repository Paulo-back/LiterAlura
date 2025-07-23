package br.com.paulo.ChallengeLiterAlura.Repository;

import br.com.paulo.ChallengeLiterAlura.Model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long > {

    List<Livro> findByLinguagem(String idioma);


    List<Livro> findTop10ByOrderByQtdDownloadsDesc();
}
