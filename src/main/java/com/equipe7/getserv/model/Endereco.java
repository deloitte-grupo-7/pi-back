package com.equipe7.getserv.model;

public class Endereco {

    private Long id;
    private Long id_usuario; //fk
    private String rua;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco (){
    }

}
