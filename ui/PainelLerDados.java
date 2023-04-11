package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Classe responsável por administrar os recursos do painel para leitura do
 * arquivo externo do Estoque
 * Implementa a interface ActionListener para execução de função dos componentes
 */
public class PainelLerDados extends PainelBase implements ActionListener{

   PainelPrincipal pp;

   Graphics2D g2LerDados;

   JButton botao;
   public JLabel label;


   /**
    * Construtor base que inica as configurações padrões do PainelBase
    * @param pp PainelPrincipal necessário
    */
   public PainelLerDados(PainelPrincipal pp){
      this.pp = pp;
      configuracaoPadrao();
      addKeyListener(pp.leitorTeclado);

      configurarBotao();
      configurarLabel();

      add(botao);
      add(label);
   }

   
   /**
    * Faz as configurações do botão do painel
    */
   public void configurarBotao(){
      botao = new JButton();
      botao.setBackground(super.corPadraoBotao);
      botao.setForeground(super.corPadraoTextoBotao);
      botao.setText("Ler arquivo");
      super.configurarFonteBotao(botao);

      int largura = 110;
      int altura = 30;
      botao.setBounds(((super.largura/2)-(largura/2)), (super.altura - (altura*4)), largura, altura);

      botao.addActionListener(this);
   }


   /**
    * Faz as configurações do label interativo do painel
    */
   public void configurarLabel(){
      label = new JLabel();
      label.setHorizontalAlignment(SwingConstants.CENTER);
      super.configurarFonteLabel(label, super.tamanhoFonteTextoLabelInfo);
      label.setForeground(Color.white);
      label.setBackground(Color.black);

      int largura = 280;
      int altura = 30;
      label.setBounds(((super.largura/2)-(largura/2)), (super.altura-(altura*2)), largura, altura);
   }


   /**
    * Chama o método privado de desenho da classe
    */
   public void desenharTela(){
      paintComponent(g2LerDados);
   }


   /**
    * Desenha tudo relacionado ao painel
    * @param g objeto Graphics necessário
    */
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g2LerDados = (Graphics2D) g.create();

      desenharFormas(g2LerDados);

      configurarFonteDesenho(g2LerDados, tamanhoFonteTextoMenu);
      texto = "Ler Arquivo";
      textoX = textoCentralizado(this, g2LerDados, texto);
      textoY = posBaseYMenu;
      desenharTextoComSombra(g2LerDados, texto, textoX, textoY, corTextoSelecionado, corSombra);

      configurarFonteDesenho(g2LerDados, tamanhoFonteTexto);

      texto = "O programa irá procurar pelo arquivo";
      textoX = textoCentralizado(this, g2LerDados, texto);
      textoY += espacamentoTexto*2;
      desenharTextoComSombra(g2LerDados, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "chamado \"" + pp.logica.getNomeArquivo() + "\" para fazer a leitura, que";
      textoX = textoCentralizado(this, g2LerDados, texto);
      textoY += super.espacamentoTexto;
      desenharTextoComSombra(g2LerDados, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "deverá estar salvo na pasta \"resource\" do projeto";
      textoX = textoCentralizado(this, g2LerDados, texto);
      textoY += super.espacamentoTexto;
      desenharTextoComSombra(g2LerDados, texto, textoX, textoY, corTextoSelecionado, corSombra);    

      g2LerDados.dispose();
   }


   private void desenharFormas(Graphics2D g2){
      int larguraDesenho = (int) (super.largura * 0.90);
      int alturaDesenho = (int) (super.altura * 0.35);

      g2.setColor(super.corFundoPadraoSubPainel);
      g2.fillRoundRect(((super.largura/2)-(larguraDesenho/2)), (super.posBaseYSubMenu-20), larguraDesenho, alturaDesenho, 20, 20);
      g2.fillRoundRect(((this.largura/2) - (larguraDesenho/2)), (super.altura-20), larguraDesenho, alturaDesenho, 30, 30);
   }


   /**
    * Recupera os eventos do painel 
    * @param e evento
    */   
   @Override
   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == botao){
         pp.logica.lerDados();
      }
   }
}
