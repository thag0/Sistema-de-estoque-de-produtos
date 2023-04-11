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
 * Classe responsável por administrar os recursos do painel para cadastro de produto
 * Implementa a interface ActionListener para execução de função dos componentes
 */
public class PainelCadastro extends PainelBase implements ActionListener{

   PainelPrincipal pp;

   //teclas
   public boolean esc = false;

   public boolean focarCodigoProduto = false, focarNomeProduto = false, focarValorProduto = false, focarUnidadesProduto = false;

   public JTextField caixaTextoCodigoProduto = new JTextField();
   public JTextField caixaTextoNomeProduto = new JTextField();
   public JTextField caixaTextoValorProduto = new JTextField();
   public JTextField caixaTextoUnidadesProduto = new JTextField();
   
   public JLabel label = new JLabel();
   public String textoLabel = "";

   public JButton botao = new JButton();
   public String textoPadraoBotao = "Confirmar";

   Graphics2D g2Cadastro;

   public Color corCaixaTextoSelecionado = super.corCaixaTextoSelecionado;
   public Color corCaixaTextoNaoSelecionado = super.corCaixaTextoNaoSelecionado; 

   //posicoes dos textos
   int yTextoCodigo = super.posBaseYSubMenu - (super.alturaPadraoCaixaTexto/3);//alinhar a caixa com o texto
   int yTextoNome = yTextoCodigo + super.espacamentoTexto;
   int yTextoValor = yTextoNome + super.espacamentoTexto;
   int yTextoUnidades = yTextoValor + super.espacamentoTexto;

   /**
    * Construtor base que inica as configurações padrões do PainelBase, recebe
    * um leitor de teclado para lidar com as entradas do usuário 
    * @param pp PainelPrincipal necessário
    */
   public PainelCadastro(PainelPrincipal pp){
      this.pp = pp;

      configuracaoPadrao();

      addKeyListener(pp.leitorTeclado);

      configurarCaixaTexto();
      configurarFoco();
      configurarBotao();
      configurarLabel();

      add(caixaTextoCodigoProduto);
      add(caixaTextoNomeProduto);
      add(caixaTextoValorProduto);
      add(caixaTextoUnidadesProduto);
      add(botao);
      add(label);
   }

   
   /**
    * Faz as configurações das TextFeild usadas
    */
   private void configurarCaixaTexto(){
      int alturaCaixaTexto = super.alturaPadraoCaixaTexto;
      int larguraCaixaTexto = super.larguraPadraoCaixaTexto;
      int xCaixaTexto = super.posXBaseCaixaTexto;

      caixaTextoCodigoProduto.setEditable(true);
      caixaTextoCodigoProduto.setBackground(super.corCaixaTextoNaoSelecionado);
      caixaTextoCodigoProduto.setBounds(xCaixaTexto, yTextoCodigo, larguraCaixaTexto, alturaCaixaTexto);
      
      caixaTextoNomeProduto.setEditable(true);
      caixaTextoNomeProduto.setBackground(super.corCaixaTextoNaoSelecionado);
      caixaTextoNomeProduto.setBounds(xCaixaTexto, yTextoNome, larguraCaixaTexto, alturaCaixaTexto);

      caixaTextoValorProduto.setEditable(true);
      caixaTextoValorProduto.setBackground(super.corCaixaTextoNaoSelecionado);
      caixaTextoValorProduto.setBounds(xCaixaTexto, yTextoValor, larguraCaixaTexto, alturaCaixaTexto);
      
      caixaTextoUnidadesProduto.setEditable(true);
      caixaTextoUnidadesProduto.setBackground(super.corCaixaTextoNaoSelecionado);
      caixaTextoUnidadesProduto.setBounds(xCaixaTexto, yTextoUnidades, larguraCaixaTexto, alturaCaixaTexto);
   }


   /**
    * Configura o foco das caixas de texto para mantê-las ativas
    * enquanto o usuário estiver com algumas delas selecionada
    */
   private void configurarFoco(){
      caixaTextoCodigoProduto.addFocusListener(new FocusListener(){
         @Override
         public void focusGained(FocusEvent e) {
            focarCodigoProduto = true;
         }

         @Override
         public void focusLost(FocusEvent e) {
            focarCodigoProduto = false;
         }
      });

      caixaTextoNomeProduto.addFocusListener(new FocusListener(){
         @Override
         public void focusGained(FocusEvent e) {
            focarNomeProduto = true;
         }

         @Override
         public void focusLost(FocusEvent e) {
            focarNomeProduto = false;
         }
      });

      caixaTextoValorProduto.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            focarValorProduto = true;
         }

         @Override
         public void focusLost(FocusEvent e) {
            focarValorProduto = false;
         }      
      });

      caixaTextoUnidadesProduto.addFocusListener(new FocusListener(){
         @Override
         public void focusGained(FocusEvent e) {
            focarUnidadesProduto = true;
         }

         @Override
         public void focusLost(FocusEvent e) {
            focarUnidadesProduto = false;
         }
      });
   }


   /**
    * Configura o label interativo do painel
    */
   private void configurarLabel(){
      int comprimentoLabel = 280;
      int alturaLabel = 30;
      
      label.setForeground(Color.white);
      label.setBounds((largura/2)-(comprimentoLabel/2), (altura - (alturaLabel*2)), comprimentoLabel, alturaLabel);
      label.setVisible(true);
      super.configurarFonteLabel(label);
      label.setText("Preencha os campos");
      label.setHorizontalAlignment(JLabel.CENTER);
   }



   /**
    * Configura o botão do painel
    */
   private void configurarBotao(){
      botao.setForeground(super.corPadraoTextoBotao);
      botao.setBackground(super.corPadraoBotao);
      botao.setText(textoPadraoBotao);
      botao.setFocusable(true);

      super.configurarFonteBotao(botao);

      int comprimentoBotao = super.larguraPadraoBotao;
      int alturaBotao = super.alturaPadraoBotao;
      botao.setBounds(((largura/2)-(comprimentoBotao/2)), (altura - (alturaBotao*4)), comprimentoBotao, alturaBotao);

      botao.addActionListener(this);
   }

   /**
    * Chama o método privado de desenho da classe
    */
   public void desenharTela(){
      paintComponent(g2Cadastro);
   }


   /**
    * Desenha tudo relacionado ao painel
    * @param g objeto Graphics necessário
    */
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g2Cadastro = (Graphics2D) g.create();

      desenharFormas(g2Cadastro);

      super.configurarFonteDesenho(g2Cadastro, super.tamanhoFonteTextoMenu);

      texto = "Cadastrar produto";
      textoX = textoCentralizado(this, g2Cadastro, texto);
      textoY = posBaseYMenu;
      desenharTextoComSombra(g2Cadastro, texto, textoX, textoY, corTextoSelecionado, corSombra);

      super.configurarFonteDesenho(g2Cadastro, super.tamanhoFonteTexto);

      texto = "Código do produto: ";
      textoY = posBaseYSubMenu + 10;
      textoX = posBaseXSubMenu;
      desenharTextoComSombra(g2Cadastro, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "Nome do produto: ";
      textoY += espacamentoTexto;
      desenharTextoComSombra(g2Cadastro, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "Valor do produto: ";
      textoY += espacamentoTexto;
      desenharTextoComSombra(g2Cadastro, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "Unidades do produto: ";
      textoY += espacamentoTexto;
      desenharTextoComSombra(g2Cadastro, texto, textoX, textoY, corTextoSelecionado, corSombra);

      g2Cadastro.dispose();
   }


   private void desenharFormas(Graphics2D g2){
      int larguraDesenho = (int) (super.largura * 0.9);
      int alturaDesenho = 200;

      g2.setColor(super.corFundoPadraoSubPainel);
      g2.fillRoundRect(((this.largura/2) - (larguraDesenho/2)), (super.posBaseYSubMenu - 35), larguraDesenho, alturaDesenho, 20, 20);
      g2.fillRoundRect(((this.largura/2) - (larguraDesenho/2)), (super.altura-20), larguraDesenho, alturaDesenho, 30, 30);
   }


   /**
    * Limpa os campos de texto das TextField do painel para
    * evitar poluição visual em usos futuros
    */
   public void limparCamposTexto(){
      caixaTextoCodigoProduto.setText("");
      caixaTextoNomeProduto.setText("");
      caixaTextoValorProduto.setText("");
      caixaTextoUnidadesProduto.setText("");

      label.setText("Preencha os campos");
   }


   /**
    * Recupera os eventos do painel 
    * @param e evento
    */
   @Override
   public void actionPerformed(ActionEvent e){
      if(e.getSource() == botao){
         pp.logica.adicionarProduto(this);
      }
   }
}
