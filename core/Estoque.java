package core;

import java.io.Serializable;
import java.util.ArrayList;

/***
 * Base do funcionamento do programa, armazena valor de caixa obtido pelas vendas
 * além dos produtos disponíveis
 */
public class Estoque implements Serializable{
   private double caixa;
   private ArrayList <Produto> produtos;

   /**
    * Cria um objeto padrão Estoque que é inicializado com uma lista vazia de Produto
    * e com o valor de caixa zerado
    */
   public Estoque(){
      produtos = new ArrayList <Produto>();
      caixa = 0;
   }


   /**
    * @return Retorna o tamanho dos produtos cadastrados no estoque
    */
   public int getTamanho(){
      return this.produtos.size();
   }


   /**
    * @return Retorna a lista de produtos armazenados
    */
   public ArrayList <Produto> getProdutos(){
      return this.produtos;
   }


   /**
    * @return Retorna o valor de caixa armazenado
    */
   public double getCaixa(){
      return this.caixa;
   }


   /**
    * Adiciona ao valor armazenado de caixa do estoque
    * @param valor valor para a adição
    */
   public void adicionarCaixa(double valor){
      this.caixa += valor;
   }


   /**
    * Subtrai ao valor armazenado de caixa do estoque
    * @param valor valor para a subtração
    */
   public void reduzirCaixa(double valor){
      this.caixa -= valor;
   }


   /**
    * Adiciona na lista de produtos do Estoque um objeto do tipo Produto
    * @param codigo código único do produto
    * @param nome nome produto
    * @param valor valor de cada unidade do produto
    * @param unidades unidades disponíveis para venda
    */
   public void adicionarProduto(String codigo, String nome, double valor, int unidades){
      Produto produto = new Produto(codigo, nome, valor, unidades);
      produtos.add(produto);
   }


   /**
    * Sobrepões os dados de informação do produto caso seja adicionado um novo 
    * produto com um código já registrado anteriormente
    * @param indice índice na lista de produtos
    * @param codigo  código do produto
    * @param nome nome do produto novo
    * @param valor valor do produto novo
    * @param unidades unidades do produto novo
    */
   public void sobrescreverProduto(int indice, String codigo, String nome, double valor, int unidades){
      for(int i = 0; i < this.getTamanho(); i++){
         if(i == indice){
            this.getProdutos().get(i).setCodigo(codigo);
            this.getProdutos().get(i).setNome(nome);
            this.getProdutos().get(i).setUnidades(unidades);
            this.getProdutos().get(i).setValor(valor);
         }
      }
   }
   
}
