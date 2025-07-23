package br.com.paulo.ChallengeLiterAlura.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String autor;
    private Integer anoNasc;
    private Integer anoFalec;

    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {
    }

    public Autor(String nome, Integer birth_year, Integer death_year) {
        this.autor = nome;
        this.anoNasc = birth_year;
        this.anoFalec = death_year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getAnoNasc() {
        return anoNasc;
    }

    public void setAnoNasc(Integer anoNasc) {
        this.anoNasc = anoNasc;
    }

    public Integer getAnoFalec() {
        return anoFalec;
    }

    public void setAnoFalec(Integer anoFalec) {
        this.anoFalec = anoFalec;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "Autor = " + autor +
                "\nAno Nascimento = " + anoNasc +
                "\nAno Falecimento = " + anoFalec ;
    }
}
