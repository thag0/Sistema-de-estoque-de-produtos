package core;

import java.io.Serializable;

/**
 * Classe responsável por cada produto individual armazenado no Estoque
 */
public class Produto implements Serializable{
   private String codigo;
   private String nome;
   private double valor;
   private int unidades;

   /**
    * Inicialização padrão de um Produto
    * @param codigo código único do produto
    * @param nome nome do produto
    * @param valor valor de cada unidade do produto
    * @param unidades unidades disponíveis para venda do produto
    */
   Produto(String codigo, String nome, double valor, int unidades){
      this.codigo = codigo;
      this.nome = nome;
      this.valor = valor;
      this.unidades = unidades;
   }

   /**
    * @return retorna o código do produto
    */
   public String getCodigo(){
      return this.codigo;
   }


   /**
    * Altera o código de um produto
    * @param codigo código para alteração
    */
   public void setCodigo(String codigo){
      this.codigo = codigo;
   }


   /**
    * @return retorna o nome do produto
    */
   public String getNome(){
      return this.nome;
   }


   /**
    * Altera o nome de um produto
    * @param nome nome para alteração
    */
   public void setNome(String nome){
      this.nome = nome;
   }


   /**
    * @return retorna o valor do produto
    */
   public double getValor(){
      return this.valor;
   }


   /**
    * Altera o valor do produto
    * @param valor valor para alteração
    */
   public void setValor(double valor){
      this.valor = valor;
   }


   /**
    * @return retorna as unidades do produto
    */
   public int getUnidades(){
      return this.unidades;
   }


   /**
    * Altera as unidades do produto
    * @param unidades valor de unidades para alteraçãos
    */
   public void setUnidades(int unidades){
      this.unidades = unidades;
   }


   /**
    * Reduz um valor de unidades específicas de um produto
    * @param valor valor para redução
    */
   public void reduzirUnidades(int valor){
      this.unidades -= valor;
   }

 
   /**
    * Adiciona um valor de unidades específicas de um produto
    * @param valor valor para adição
    */
   public void adicionarUnidades(int valor){
      this.unidades += valor;
   }
}
