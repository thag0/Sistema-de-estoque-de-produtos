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
 * Classe responsável por administrar os recursos do painel para gravação do
 * arquivo externo do Estoque
 * Implementa a interface ActionListener para execução de função dos componentes
 */
public class PainelSalvarDados extends PainelBase implements ActionListener{
   
   PainelPrincipal pp;

   Graphics2D g2SalvarDados;

   Color corAreaTexto = new Color(10, 10, 10);

   public JButton botao;
   public JLabel label;


   /**
    * Construtor base que inica as configurações padrões do PainelBase, recebe
    * um leitor de teclado para lidar com as entradas do usuário 
    * @param pp PainelPrincipal necessário
    */
   public PainelSalvarDados(PainelPrincipal pp){
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
    * @param painel
    */
   private void configurarBotao(){
      botao = new JButton();
      botao.setBackground(super.corPadraoBotao);
      botao.setForeground(super.corPadraoTextoBotao);
      botao.setText("Salvar");
      super.configurarFonteBotao(botao);
      
      int altura = super.alturaPadraoBotao;
      int largura = super.larguraPadraoBotao;
      botao.setBounds(((super.largura/2) - (largura/2)), ((super.altura)-(altura*4)), largura, altura);

      botao.addActionListener(this);
   }


   /**
    * Faz as configurações do label interativo do painel
    */
   private void configurarLabel(){
      label = new JLabel();
      label.setForeground(Color.white);
      label.setHorizontalAlignment(SwingConstants.CENTER);
      super.configurarFonteLabel(label, super.tamanhoFonteTextoLabelInfo);

      int largura = 250;
      int altura = 20;
      label.setBounds(((super.largura/2) - (largura/2)), ((super.altura) - (altura*3)), largura, altura);
   }


   /**
    * Chama o método privado de desenho da classe
    */
   public void desenharTela(){
      paintComponent(g2SalvarDados);
   }

   
   /**
    * Desenha tudo relacionado ao painel
    * @param g objeto Graphics necessário
    */
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g2SalvarDados = (Graphics2D) g.create();

      desenharFormas(g2SalvarDados);

      configurarFonteDesenho(g2SalvarDados, tamanhoFonteTextoMenu);
      g2SalvarDados.setColor(Color.red);
      texto = "Salvar Dados";
      textoX = textoCentralizado(this, g2SalvarDados, texto);
      textoY = posBaseYMenu;
      desenharTextoComSombra(g2SalvarDados, texto, textoX, textoY, corTextoSelecionado, corSombra);

      configurarFonteDesenho(g2SalvarDados, tamanhoFonteTexto);

      texto = "O programa irá gerar um arquivo nomeado como";
      textoY += espacamentoTexto*2;
      textoX = textoCentralizado(this, g2SalvarDados, texto);
      desenharTextoComSombra(g2SalvarDados, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "\"" + pp.logica.getNomeArquivo() +"\" que poderá ser lido mesmo";
      textoY += espacamentoTexto;
      textoX = textoCentralizado(this, g2SalvarDados, texto);
      desenharTextoComSombra(g2SalvarDados, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "após ser fechado, Você poderá lê-lo novamente";
      textoY += espacamentoTexto;
      textoX = textoCentralizado(this, g2SalvarDados, texto);
      desenharTextoComSombra(g2SalvarDados, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "usando a opção do menu \"Carregar dados\", podendo";
      textoY += espacamentoTexto;
      textoX = textoCentralizado(this, g2SalvarDados, texto);
      desenharTextoComSombra(g2SalvarDados, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "recuperar os dados salvos anteriormente";
      textoY += espacamentoTexto;
      textoX = textoCentralizado(this, g2SalvarDados, texto);
      desenharTextoComSombra(g2SalvarDados, texto, textoX, textoY, corTextoSelecionado, corSombra);

      g2SalvarDados.dispose();
   }


   private void desenharFormas(Graphics2D g2){
      int larguraDesenho = (int) (super.largura * 0.90);
      int alturaDesenho = (int) (super.altura * 0.5);

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
         pp.logica.salvarDados();
      }
   }
}
