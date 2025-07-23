package br.com.paulo.ChallengeLiterAlura.Service;

public interface IConsumo {

    <T> T obterDados(String json,Class<T> classe);
}
