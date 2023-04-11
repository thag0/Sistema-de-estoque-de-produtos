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
import javax.swing.SwingConstants;


/**
 * Classe responsável por administrar os recursos do painel para registro de venda
 * Implementa a interface ActionListener para execução de função dos componentes
 */
public class PainelRegistrarVenda extends PainelBase implements ActionListener{
   
   PainelPrincipal pp;

   Graphics2D g2RegistrarVenda;

   public boolean focarCaixaTextoCodigo = false, focarCaixaTextoUnidades = false; 
   public JTextField caixaTextoCodigo, caixaTextoUnidades;
   public JLabel label, labelFeedback;
   JButton botao;

   int posYTextoCodigo = super.posBaseYSubMenu;
   int posYTextoUnidades = super.posBaseYSubMenu + super.espacamentoTexto;

   public Color corCaixaTextoSelecionado = super.corCaixaTextoSelecionado;
   public Color corCaixaTextoNaoSelecionado = super.corCaixaTextoNaoSelecionado;    


   /**
    * Construtor base que inica as configurações padrões do PainelBase, recebe
    * um leitor de teclado para lidar com as entradas do usuário 
    * @param pp PainelPrincipal necessário
    */   
   public PainelRegistrarVenda(PainelPrincipal pp){
      this.pp = pp;
      configuracaoPadrao();
      addKeyListener(pp.leitorTeclado);

      configurarLabel();

      configurarBotao();
      configurarCaixaTexto();
      configurarFoco();

      add(label);
      add(labelFeedback);
      add(caixaTextoCodigo);
      add(caixaTextoUnidades);
      add(botao);
   }

   /**
    * Faz as configurações do botão do painel
    */
   public void configurarBotao(){
      botao = new JButton();
      botao.setForeground(super.corPadraoTextoBotao);
      botao.setBackground(super.corPadraoBotao);
      botao.setText("Confirmar");
      super.configurarFonteBotao(botao);
      
      int xBotao = ((super.largura/2) - (super.larguraPadraoBotao/2));
      int yBotao = (super.altura - (super.alturaPadraoBotao*4));
      botao.setBounds(xBotao, yBotao, super.larguraPadraoBotao, super.alturaPadraoBotao);

      botao.addActionListener(this);
   }


   /**
    * Faz as configurações do label interativo do painel
    */
   public void configurarLabel(){
      label = new JLabel();
      label.setText("Digite um código para busca");
      label.setForeground(Color.white);
      super.configurarFonteLabel(label, super.tamanhoFonteTextoLabelInfo+4);
      label.setHorizontalAlignment(SwingConstants.CENTER);

      int largura = 380;
      int altura = 30;
      int xLabel = ((super.largura/2) - (largura/2));
      int yLabel = (super.altura - (altura*7));
      label.setBounds(xLabel, yLabel, largura, altura);

      //label de feedback
      labelFeedback = new JLabel();
      labelFeedback.setText("Preencha os campos");
      labelFeedback.setForeground(Color.white);
      super.configurarFonteLabel(labelFeedback);
      labelFeedback.setHorizontalAlignment(SwingConstants.CENTER);

      xLabel = ((super.largura/2) - (largura/2));
      yLabel = (super.altura - (altura*2));
      labelFeedback.setBounds(xLabel, yLabel, largura, altura);
   }


   /**
    * Faz as configurações das TextFeild usadas
    */
   public void configurarCaixaTexto(){
      caixaTextoCodigo = new JTextField();
      caixaTextoUnidades = new JTextField();

      //código do produto
      int xCodigo = super.posXBaseCaixaTexto;
      int yCodigo = (int)(posYTextoCodigo - (super.alturaPadraoCaixaTexto/1.3));
      caixaTextoCodigo.setBounds(xCodigo, yCodigo, super.larguraPadraoCaixaTexto, super.alturaPadraoCaixaTexto);
      caixaTextoCodigo.setEditable(true);
      caixaTextoCodigo.setBackground(corCaixaTextoNaoSelecionado);

      //unidades do produto
      int xUnidades = super.posXBaseCaixaTexto;
      int yUnidades = (int)(posYTextoUnidades - (super.alturaPadraoCaixaTexto/1.3));;
      caixaTextoUnidades.setBounds(xUnidades, yUnidades, super.larguraPadraoCaixaTexto, super.alturaPadraoCaixaTexto);
      caixaTextoUnidades.setEditable(true);
      caixaTextoUnidades.setBackground(corCaixaTextoNaoSelecionado);

   }


   /**
    * Configura o foco das caixas de texto para mantê-las ativas
    * enquanto o usuário estiver com algumas delas selecionada
    */
   private void configurarFoco(){
      caixaTextoCodigo.addFocusListener(new FocusListener(){
         @Override
         public void focusGained(FocusEvent e){
            focarCaixaTextoCodigo = true;
         }

         @Override
         public void focusLost(FocusEvent e){
            focarCaixaTextoCodigo = false;
         }
      });

      caixaTextoUnidades.addFocusListener(new FocusListener() {
         public void focusGained(FocusEvent e){
            focarCaixaTextoUnidades = true;
         }

         public void focusLost(FocusEvent e){
            focarCaixaTextoUnidades = false;
         }
      });
   }


   /**
    * Chama o método privado de desenho da classe
    */
   public void desenharTela(){
      paintComponent(g2RegistrarVenda);
   }


   /**
    * Desenha tudo relacionado ao painel
    * @param g objeto Graphics necessário
    */
   @Override
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g2RegistrarVenda = (Graphics2D) g.create();

      desenharFormas(g2RegistrarVenda);

      configurarFonteDesenho(g2RegistrarVenda, tamanhoFonteTextoMenu);
      
      texto = "Registrar venda";
      textoX = textoCentralizado(this, g2RegistrarVenda, texto);
      textoY = super.posBaseYMenu;
      desenharTextoComSombra(g2RegistrarVenda, texto, textoX, textoY, corTextoSelecionado, corSombra);

      configurarFonteDesenho(g2RegistrarVenda, tamanhoFonteTexto);

      texto = "Código do produto";
      textoX = super.posBaseXSubMenu;
      textoY = posYTextoCodigo;
      desenharTextoComSombra(g2RegistrarVenda, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "Unidades vendidas";
      textoX = super.posBaseXSubMenu;
      textoY = posYTextoUnidades;
      desenharTextoComSombra(g2RegistrarVenda, texto, textoX, textoY, corTextoSelecionado, corSombra);

      g2RegistrarVenda.dispose();
   }


   private void desenharFormas(Graphics2D g2){
      int larguraDesenho = (int) (super.largura * 0.90);
      int alturaDesenho = (int) (super.altura * 0.22);

      g2.setColor(super.corFundoPadraoSubPainel);
      g2.fillRoundRect(((super.largura/2)-(larguraDesenho/2)), (super.posBaseYSubMenu-35), larguraDesenho, alturaDesenho, 20, 20);
      g2.fillRoundRect(((this.largura/2) - (larguraDesenho/2)), (super.altura-20), larguraDesenho, alturaDesenho, 30, 30);
   }


   /**
    * Limpa os campos de texto das TextField do painel para
    * evitar poluição visual em usos futuros
    */
   public void limparCamposTexto(){
      caixaTextoCodigo.setText("");
      caixaTextoUnidades.setText("");
   }

   /**
    * Recupera os eventos do painel 
    * @param e evento
    */
   @Override
   public void actionPerformed(ActionEvent e){
      pp.logica.registrarVenda(this);
   }
}
