package br.com.paulo.ChallengeLiterAlura.Principal;

import br.com.paulo.ChallengeLiterAlura.DTO.AutorDTO;
import br.com.paulo.ChallengeLiterAlura.Model.*;
import br.com.paulo.ChallengeLiterAlura.Repository.AutorRepository;
import br.com.paulo.ChallengeLiterAlura.Repository.LivroRepository;
import br.com.paulo.ChallengeLiterAlura.Service.ConsumoApi;
import br.com.paulo.ChallengeLiterAlura.Service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private List<Livro> livros = new ArrayList<>();
    private List<Autor> AutorR = new ArrayList<>();
    private LivroRepository repository;
    private AutorRepository autorRepository;
    private Scanner sc = new Scanner(System.in);
    private String ulr = "https://gutendex.com/books?search=";
    private String urlSufixo = "&languages=";
    private String lingua = "pt";

    public Principal(LivroRepository repositorio, AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
        this.repository = repositorio;
    }

    public void menu(){
        var i = 77;
        while(i!=0) {
            System.out.println(
                    """     
                            \n1 - Buscar livro pelo título 🔍
                            2 - Listar livros registrados 📚
                            3 - Listar autores registrados ✍️
                            4 - Listar autores vivos em um determinado ano ⏳🌳
                            5 - Listar livros em determinado idioma 🗣️
                            6 - Pesquisa autores por nome 🧑‍💻
                            7 - Top 10 Livros 🏆
                            8 - Busca autor ℹ️
                            0 - Sair 🚪
                            
                            """
            );
            try {
                System.out.print("Digite: ");
                i = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
                sc.nextLine();
            }

            switch (i) {
                case 1:
                    buscaLivro();
                    break;
                case 2:
                    listaLivros();
                    break;
                case 3:
                    listaAutores();
                    break;
                case 4:
                    listaAutoresVivosEmDeterminadoAno();
                    break;
                case 5:
                    listaLivrosPorIdioma();
                    break;
                case 6:
                    pesquisaAutores();
                    break;
                case 7:
                    topDezLivros();
                    break;
                case 8:
                    buscaAutor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção Inválida!!!");
                    break;

            }
        }
    }




    private void buscaLivro() {

        DadosLivro dados = getDadosLivro(1);
        if (dados != null){
            System.out.println("Dados:");
//        System.out.println(dados);
            Autor autor = null;
            if(dados.autores() != null && !dados.autores().isEmpty()){
                AutorDTO autorDTO = dados.autores().get(0);

                autor = autorRepository.findByAutor(autorDTO.name())
                        .orElseGet(() -> {
                            Autor novoAutor = new Autor(
                                    autorDTO.name(),
                                    autorDTO.birth_year(),
                                    autorDTO.death_year()
                            );
                            return autorRepository.save(novoAutor);
                        });
            }


            Livro livro = new Livro(dados, autor);
            System.out.println("----- LIVRO -----");
            System.out.println(livro.toString());
            System.out.println("--------------");

            System.out.println("O livro está correto? Sim [1] | Não[2]");
            var resposta = sc.nextInt();
            sc.nextLine();
            if (resposta == 1){
                repository.save(livro);
            }else {
                System.out.println("Tente novamente: ");
                sc.nextLine();
                buscaLivro();
            }


        }else {

            System.out.println("Nenhum livro foi encontrado para essa busca.");
        }
    }
    private void buscaAutor(){
        DadosLivro dados = getDadosLivro(2);
        if (dados != null) {
            System.out.println("Dados:");
//        System.out.println(dados);
            Autor autor = null;
            if (dados.autores() != null && !dados.autores().isEmpty()) {
                AutorDTO autorDTO = dados.autores().get(0);

                autor = autorRepository.findByAutor(autorDTO.name())
                        .orElseGet(() -> {
                            Autor novoAutor = new Autor(
                                    autorDTO.name(),
                                    autorDTO.birth_year(),
                                    autorDTO.death_year()
                            );

                            return autorRepository.save(novoAutor);
                        });
                System.out.println(autor.toString()+"\n");


            }
        }
    }
    private DadosLivro getDadosLivro(int n){
        if (n ==1){
            System.out.print("Digite o nome do livro: ");
        }else {
            System.out.print("Digite o nome do autor: ");
        }

        String nome = sc.nextLine();
         String nomeFormatado = nome.replace(" ","%20");

        var json = consumo.obterDados("https://gutendex.com/books?search="+nomeFormatado);
        ResultadoLivros resultado = conversor.obterDados(json,ResultadoLivros.class);

        if (resultado.results() != null && !resultado.results().isEmpty()) {
            return resultado.results().get(0); // Pega o primeiro livro da lista
        } else {
            return null;
        }

    }
    private void listaLivros(){
        livros = repository.findAll();
        livros.forEach(l -> System.out.println("----- LIVRO -----" + "\nTitulo: "+l.getTitulo() +
                "\nAutor: "+ l.getAutor().getAutor()+ "\nIdioma: " + l.getLinguagem() +
                "\nQuantidade de Downloads: " + l.getQtdDownloads() + "\n--------------"));

    }
    private void pesquisaAutores() {
        System.out.println("Digite o nome do autor para pesquisa");
        var autor = sc.nextLine();

        Optional<Autor>buscaAutor = autorRepository.findByAutorContainingIgnoreCase(autor);
        if (buscaAutor.isPresent()){
            System.out.println("Nome: " + buscaAutor.get().getAutor() +
                    "Ano nascimento: " + buscaAutor.get().getAnoNasc()+
                    "Ano falecimento: " + buscaAutor.get().getAnoFalec()+
                    "\nLivros: "+ buscaAutor.get().getLivros()
                    .stream().map(Livro::getTitulo).collect(Collectors.toList()));

        }


        }
    private void listaAutores() {
        AutorR = autorRepository.findAll();

        AutorR.forEach(a -> System.out.println("----- AUTOR -----" + "\nAutor: "+a.getAutor() +
                "\nAno nascimento: "+ a.getAnoNasc() + "\nAno Falecimento: " + a.getAnoFalec() +
                "\nLivros: " + a.getLivros().stream().map(Livro::getTitulo).collect(Collectors.toList()) + "\n--------------"));

    }

    private void listaAutoresVivosEmDeterminadoAno() {
        System.out.println("Digite um ano para pesquisa: ");
        var ano = sc.nextInt();
        AutorR = autorRepository.entreAnos(ano);
        AutorR.forEach(a -> System.out.println("----- AUTOR -----" + "\nAutor: "+a.getAutor() +
                "\nAno nascimento: "+ a.getAnoNasc() + "\nAno Falecimento: " + a.getAnoFalec() +
                "\nLivros: " + a.getLivros().stream().map(Livro::getTitulo).collect(Collectors.toList()) + "\n--------------"));
    }
    private void listaLivrosPorIdioma() {
        System.out.println("Digite a abreviação do idioma para pesquisa");
        System.out.println("Se precisar use o gabarito");
        System.out.println("""
                Português -> pt
                Inglês -> en
                Espanhol -> es
                Francês -> fr
                Alemão -> de""");

        System.out.print("Digite: ");
        String idioma = sc.nextLine();
        livros = repository.findByLinguagem(idioma);
        livros.forEach(l -> System.out.println("\nTitulo: "+l.getTitulo() +
                "\nAutor: "+ l.getAutor().getAutor()+ "\nLinguagem: " + l.getLinguagem() +
                "\nQuantidade de Downloads: " + l.getQtdDownloads()+"\nAutor ano de nascimento: "
                +  l.getAutor().getAnoNasc() + "\nAutor ano de falecimento: " + l.getAutor().getAnoFalec() + "\n"));
    }
    public void topDezLivros(){
        livros = repository.findTop10ByOrderByQtdDownloadsDesc();
        livros.forEach(l -> System.out.println("----- LIVRO -----" + "\nTitulo: "+l.getTitulo() +
                "\nAutor: "+ l.getAutor().getAutor()+ "\nIdioma: " + l.getLinguagem() +
                "\nQuantidade de Downloads: " + l.getQtdDownloads() + "\n--------------"));
    }

}
