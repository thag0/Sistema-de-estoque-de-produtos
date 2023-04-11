package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Classe base para a criação de painéis de interface gráfica para o programa. Contém valores padrão para as cores,
 * fontes, tamanhos e posições de texto, além de métodos para configurar esses valores e desenhar textos com sombra.
 * Também contém métodos para ativar e desativar o painel.
 * @author Thiago Barroso
*/
public class PainelBase extends JPanel{
   protected final int largura = 700;
   protected final int altura = 450;
   protected Font minhaFonte;
   protected String caminho = "/font/";
   protected String nomeFonte = "Abalone Smile.ttf";

   //cores 
   protected Color corFundoPadraoPainel = new Color(30, 30, 55);
   protected Color corFundoPadraoSubPainel;

   protected Color corPadraoBotao = new Color(70, 70, 80);
   protected Color corPadraoTextoBotao = Color.WHITE;

   protected Color corTextoSelecionado = Color.white;
   protected Color corTexto = new Color(160, 160, 160);
   protected Color corSombra = Color.DARK_GRAY;

   protected Color corCaixaTextoSelecionado = new Color(255, 255, 255);
   protected Color corCaixaTextoNaoSelecionado = new Color(150, 150, 150);

   //valores de texto
   protected int tamanhoFonteTextoMenu = 30;
   protected int tamanhoFonteTexto = 23;
   protected float tamanhoFonteTextoBotao = 15;
   protected float tamanhoFonteTextoLabelInfo = 16;
   protected int textoX, textoY, relevoSombra = 2;

   protected int espacamentoTexto = (altura/11);
   protected String texto;
   protected float tamanhoPadraoTextoLabel = 16;

   //valores padroes de posicionamento de texto para menus e sub menus
   protected int posBaseYMenu = 40;
   protected int posBaseYSubMenu = 100;
   protected int posBaseXSubMenu = 55;

   //valores padroes botoes
   protected int alturaPadraoBotao = 30;
   protected int larguraPadraoBotao = 110;

   //valores padroes caixas de texto;
   protected int alturaPadraoCaixaTexto = 24;
   protected int larguraPadraoCaixaTexto = 100;
   protected int posXBaseCaixaTexto = largura - (larguraPadraoCaixaTexto * 2);

   /**
    * Construtor basico que recupera o arquivo de fonte e aplica ao painel além 
    * de adicionar um mouselistener para recuperar o foco para o painel
    */
   public PainelBase(){
      getFonte(caminho + nomeFonte);

      float intensidade = 0.7f;
      int vermelho = (int) (corFundoPadraoPainel.getRed() * intensidade);
      int verde = (int) (corFundoPadraoPainel.getGreen() * intensidade);
      int azul = (int) (corFundoPadraoPainel.getBlue() * intensidade);

      corFundoPadraoSubPainel = new Color(vermelho, verde, azul);

      //conseguir recuperar o foco para o painel
      addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            requestFocusInWindow();
         }
      });
   }

   /**
    * Recupera o arquivo de fonte na pasta "font" do projeto
    * @param caminho caminho relativo do arquivo (caminho + nomeFonte)
    */
   private void getFonte(String caminho){
      try {
         InputStream inputStream = getClass().getResourceAsStream(caminho);
         minhaFonte = Font.createFont(Font.TRUETYPE_FONT, inputStream);
      }catch(Exception e){
         try{
            //usar fonte padrão como arial
            InputStream inputStream = getClass().getResourceAsStream("/font/arial.ttf");
            minhaFonte = Font.createFont(Font.TRUETYPE_FONT, inputStream);
         }catch(Exception e2){}
      }
   }

   
   /**
    * Faz uma configuração básica no painel, como configurar doublebuffered, background, layout, tamanho,
    * focosuble e visible
    */
   protected void configuracaoPadrao(){
      setDoubleBuffered(true);
      setOpaque(true);
      setBackground(corFundoPadraoPainel);
      
      setLayout(null);
      setPreferredSize(new Dimension(largura, altura));

      setFocusable(true);
      setVisible(false);
   }


   /**
    * Configura a fonte para desenho de textos com objeto Graphics2D de tamanho especificado.
    * @param g2 o objeto Graphics2D utilizado para desenhar o texto.
    * @param tamanho o tamanho da fonte a ser utilizada.
   */
   protected void configurarFonteDesenho(Graphics2D g2, float tamanho){
      g2.setFont(minhaFonte);
      g2.setFont(g2.getFont().deriveFont(Font.BOLD, tamanho));
   }

   
   /**
    * Configura a fonte do botão do painel
    * @param botao botão necessário para configuração
    */
   protected void configurarFonteBotao(JButton botao){
      botao.setFont(minhaFonte);
      botao.setFont(botao.getFont().deriveFont(Float.valueOf(tamanhoFonteTextoBotao)));
   }


   /**
    * Configura a fonte do label do painel
    * @param label label necessário para configuração
    */
   protected void configurarFonteLabel(JLabel label){
      label.setFont(minhaFonte);
      label.setFont(label.getFont().deriveFont(tamanhoFonteTextoBotao));
   }


   /**
    * Configura a fonte do label do painel a partir de um tamanho especificado
    * @param label label necessário para configuração
    */
   protected void configurarFonteLabel(JLabel label, float tamanho){
      label.setFont(minhaFonte);
      label.setFont(label.getFont().deriveFont(tamanho));      
   }

   
   /**
    * Configura a fonte do TextArea 
    * @param areaTexto areaTexto necessária para configuração
    */
   protected void configurarFonteAreaTexto(JTextArea areaTexto){
      areaTexto.setFont(minhaFonte);
      areaTexto.setFont(areaTexto.getFont().deriveFont(Float.valueOf(tamanhoFonteTexto)));
   }


   /**
    * Altera a visibilidade e foco do painel para verdadeiro
    */
   public void ativarPainel(){
      this.setVisible(true);
      this.setFocusable(true);
   }


   /**
    * Altera a visibilidade e foco do painel para falso
    */
   public void desativarPainel(){
      this.setVisible(false);
      this.setFocusable(false);
   }


   /**
    * Desenha o texto informado com uma efeito de sombra personalizado
    * @param g2d objeto Graphics2D necessário para desenho
    * @param texto texto que será desenhado
    * @param textoX posição X de desenho do texto
    * @param textoY posição Y de desenho do texto
    * @param corTexto cor de desenho do texto
    * @param corSombra cor da sombra de desenho relativa ao texto
    */
   protected void desenharTextoComSombra(Graphics2D g2d, String texto, int textoX, int textoY, Color corTexto, Color corSombra){
      g2d.setColor(corSombra);
      int sombaX = textoX + relevoSombra;
      int sombraY = textoY + relevoSombra;
      g2d.drawString(texto, sombaX, sombraY);
      
      g2d.setColor(corTexto);
      g2d.drawString(texto, textoX, textoY);
   }


   /**
    * Centraliza a posição X do texto relativa à largura do painel informado
    * @param painel painel necessário para calculo
    * @param g2d  objeto Graphics2D necessário para desenho
    * @param texto texto que será desenhado
    * @return posição X do centralizado
    */
   protected int textoCentralizado(PainelBase painel, Graphics2D g2d, String texto){
      int comprimentoTextoCentralizado = (int) g2d.getFontMetrics().getStringBounds(texto, g2d).getWidth();
      int x = (painel.largura/2) - (comprimentoTextoCentralizado/2);
      return x;
   }
}
