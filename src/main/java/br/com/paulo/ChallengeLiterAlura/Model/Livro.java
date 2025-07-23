package br.com.paulo.ChallengeLiterAlura.Model;


import jakarta.persistence.*;

@Entity
@Table(name = "Livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private int qtdDownloads;
    private String linguagem;


    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;



    public Livro() {
    }

    public Livro(String titulo, int qtdDownloads, String linguagem) {
        this.titulo = titulo;
        this.qtdDownloads = qtdDownloads;
        this.linguagem = linguagem;
    }

    public Livro(DadosLivro dados) {
        this.titulo = dados.titulo();
        this.qtdDownloads = dados.qtdDownloads() != null ? dados.qtdDownloads() : 0;
        this.linguagem = (dados.linguagens() != null && !dados.linguagens().isEmpty()) ? dados.linguagens().get(0) : "desconhecida";

        if (dados.autores() != null && !dados.autores().isEmpty()) {
            var autorDado = dados.autores().get(0);
            this.autor = new Autor(autorDado.name(), autorDado.birth_year(), autorDado.death_year());
        } else {
            this.autor = new Autor("Desconhecido", 0, 0);
        }
    }

    public Livro(DadosLivro dados, Autor autor) {
        this.titulo = dados.titulo();
        this.autor = autor;
        this.qtdDownloads = dados.qtdDownloads() != null ? dados.qtdDownloads() : 0;
        this.linguagem = dados.linguagens() != null && !dados.linguagens().isEmpty() ? dados.linguagens().get(0) : "desconhecida";

    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getQtdDownloads() {
        return qtdDownloads;
    }

    public void setQtdDownloads(int qtdDownloads) {
        this.qtdDownloads = qtdDownloads;
    }

    public String getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }



    @Override
    public String toString() {
        return "Titulo = " + titulo +
                "\nAutor = " + autor.getAutor() +
                "\nQuantidade de Downloads = " + qtdDownloads +
                "\nLinguagem = " + linguagem;
    }
}
