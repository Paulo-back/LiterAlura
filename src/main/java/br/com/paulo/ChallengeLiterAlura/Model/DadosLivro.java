package br.com.paulo.ChallengeLiterAlura.Model;

import br.com.paulo.ChallengeLiterAlura.DTO.AutorDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorDTO> autores,
        @JsonAlias("languages") List<String> linguagens,
        @JsonAlias("download_count") Integer qtdDownloads
) {}
