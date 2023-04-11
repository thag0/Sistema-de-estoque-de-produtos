package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Classe responsável por administrar os recursos do painel para visualização
 * de informações do estoque
 */
public class PainelInformacoes extends PainelBase{
   
   PainelPrincipal pp;

   Graphics2D g2Informacoes;

   /**
    * Construtor base que inica as configurações padrões do PainelBase, recebe
    * um leitor de teclado para lidar com as entradas do usuário 
    * @param PainelPrincipal painelPrincipal necessário
    */  
   PainelInformacoes(PainelPrincipal pp){
      this.pp = pp;
      configuracaoPadrao();
      addKeyListener(pp.leitorTeclado);

   }


   /**
    * Chama o método privado de desenho da classe
    */
   public void desenharTela(){
      paintComponent(g2Informacoes);
   }


   /**
    * Desenha tudo relacionado ao painel
    * @param g objeto Graphics necessário
    */
   @Override
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g2Informacoes = (Graphics2D) g.create();

      desenharFormas(g2Informacoes);

      configurarFonteDesenho(g2Informacoes, tamanhoFonteTextoMenu);
      texto = "Informações do Estoque";
      textoX = textoCentralizado(this, g2Informacoes, texto);
      textoY = super.posBaseYMenu;
      desenharTextoComSombra(g2Informacoes, texto, textoX, textoY, corTextoSelecionado, corSombra);

      configurarFonteDesenho(g2Informacoes, tamanhoFonteTexto);
      
      texto = "Produtos adicionados: " + pp.logica.getEstoque().getTamanho();
      textoX = super.posBaseXSubMenu;
      textoY = super.posBaseYSubMenu;
      desenharTextoComSombra(g2Informacoes, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "Valor de caixa: R$" + pp.logica.getEstoque().getCaixa();
      textoY += super.espacamentoTexto;
      desenharTextoComSombra(g2Informacoes, texto, textoX, textoY, corTextoSelecionado, corSombra);
      
      g2Informacoes.dispose();
   }


   private void desenharFormas(Graphics2D g2){
      int larguraDesenho = (int) (super.largura * 0.90);
      int alturaDesenho = (int) (super.altura * 0.22);

      g2.setColor(super.corFundoPadraoSubPainel);
      g2.fillRoundRect(((super.largura/2)-(larguraDesenho/2)), (super.posBaseYSubMenu-35), larguraDesenho, alturaDesenho, 20, 20);
      g2.fillRoundRect(((this.largura/2) - (larguraDesenho/2)), (super.altura-20), larguraDesenho, alturaDesenho, 30, 30);
   }
}
