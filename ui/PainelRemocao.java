package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Classe responsável por administrar os recursos do painel para remoção de produto
 * Implementa a interface ActionListener para execução de função dos componentes
 */
public class PainelRemocao extends PainelBase implements ActionListener{

   PainelPrincipal pp;

   Graphics2D g2Remocao;

   public boolean focarCodigoProduto = false;

   public JTextField caixaTextoCodigoProduto;
   public JLabel label;
   public JButton botao;

   String mensagemPadraoLabel = "Digite um código de produto";

   public Color corCaixaTextoSelecionado = super.corCaixaTextoSelecionado;
   public Color corCaixaTextoNaoSelecionado = super.corCaixaTextoNaoSelecionado;   

   /**
    * Construtor base que inica as configurações padrões do PainelBase, recebe
    * um leitor de teclado para lidar com as entradas do usuário 
    * @param pp PainelPrincipal necessário
    */
   public PainelRemocao(PainelPrincipal pp){
      this.pp = pp;
      configuracaoPadrao();

      addKeyListener(pp.leitorTeclado);

      configurarCaixaTexto();
      configurarFoco();
      configurarLabel();
      configurarBotao();

      add(caixaTextoCodigoProduto);
      add(label);
      add(botao);
   }


   /**
    * Faz as configurações do botão do painel
    */
   private void configurarBotao(){
      botao = new JButton();
      botao.setText("Confirmar");
      botao.setOpaque(true);
      botao.setBackground(super.corPadraoBotao);
      botao.setForeground(super.corPadraoTextoBotao);
      botao.setVisible(true);
      super.configurarFonteBotao(botao);

      int comprimentoBotao = super.larguraPadraoBotao;
      int alturaBotao = super.alturaPadraoBotao;
      botao.setBounds((largura/2-(comprimentoBotao/2)), ((altura/2)+70), comprimentoBotao, alturaBotao);

      botao.addActionListener(this);
   }


   /**
    * Faz as configurações do label interativo do painel
    */
   private void configurarLabel(){
      int comprimentoLabel = 240;
      int alturaLabel = 30;
      label = new JLabel();
      label.setForeground(Color.white);
      super.configurarFonteLabel(label);
      label.setText(mensagemPadraoLabel);
      label.setBounds((super.largura/2 - (comprimentoLabel/2)), (super.altura/2), comprimentoLabel, alturaLabel);
      label.setHorizontalAlignment(JLabel.CENTER);
   }


   private void configurarCaixaTexto(){
      caixaTextoCodigoProduto = new JTextField();

      int alturaCaixaTexto = super.alturaPadraoCaixaTexto;
      int larguraCaixaTexto = super.larguraPadraoCaixaTexto;

      caixaTextoCodigoProduto.setEditable(true);
      caixaTextoCodigoProduto.setBounds(super.posXBaseCaixaTexto, (posBaseYSubMenu-17), larguraCaixaTexto, alturaCaixaTexto);
   }


   /**
    * Faz a configuração relacionada ao foco da TextField
    */
   private void configurarFoco(){
      caixaTextoCodigoProduto.addFocusListener(new FocusListener(){
         @Override
         public void focusGained(FocusEvent e){
            focarCodigoProduto = true;
         }

         @Override
         public void focusLost(FocusEvent e){
            focarCodigoProduto = false;
         }
      });
   }


   /**
    * Chama o método privado de desenho da classe
    */
   public void desenharTela(){
      paintComponent(g2Remocao);
   }


   /**
    * Desenha tudo relacionado ao painel
    * @param g objeto Graphics necessário
    */
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g2Remocao = (Graphics2D) g.create();

      desenharFormas(g2Remocao);

      super.configurarFonteDesenho(g2Remocao, tamanhoFonteTextoMenu);
      
      texto = "Remoção de Produto";
      textoY = posBaseYMenu;
      textoX = textoCentralizado(this, g2Remocao, texto);
      desenharTextoComSombra(g2Remocao, texto, textoX, textoY, corTextoSelecionado, corSombra);

      super.configurarFonteDesenho(g2Remocao, tamanhoFonteTexto);

      texto = "Código do produto";
      textoX = posBaseXSubMenu;
      textoY = posBaseYSubMenu;
      desenharTextoComSombra(g2Remocao, texto, textoX, textoY, corTextoSelecionado, corSombra);

      g2Remocao.dispose();
   }


   private void desenharFormas(Graphics2D g2){
      int larguraDesenho = (int) (super.largura * 0.9);
      int alturaDesenho = 66;

      g2.setColor(super.corFundoPadraoSubPainel);
      g2.fillRoundRect(((super.largura/2)-(larguraDesenho/2)), (super.posBaseYSubMenu-40), larguraDesenho, alturaDesenho, 20, 20);
      g2.fillRoundRect(((this.largura/2) - (larguraDesenho/2)), (super.altura-20), larguraDesenho, alturaDesenho, 30, 30);
   }


   /**
    * Limpa os campos de texto das TextField do painel para
    * evitar poluição visual em usos futuros
    */   
   public void limparCamposTexto(){
      caixaTextoCodigoProduto.setText("");

      label.setText(mensagemPadraoLabel);
   }


   /**
    * Recupera os eventos do painel 
    * @param e evento
    */   
   @Override
   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == botao){
         pp.logica.excluirProduto(this);
      }
   }
}
