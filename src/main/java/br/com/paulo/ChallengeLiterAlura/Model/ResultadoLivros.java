package br.com.paulo.ChallengeLiterAlura.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadoLivros(List<DadosLivro> results) {
}
