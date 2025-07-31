package com.desafio.backend_pedidos.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Pedido implements Serializable {

    private UUID id;
    private String produto;
    private int quantidade;
    private String dataCriacao;

    public Pedido() {
        this.id = UUID.randomUUID();
        this.dataCriacao = LocalDateTime.now()
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public Pedido(String produto, int quantidade) {
        this.id = UUID.randomUUID();
        this.produto = produto;
        this.quantidade = quantidade;
        this.dataCriacao = LocalDateTime.now()
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
