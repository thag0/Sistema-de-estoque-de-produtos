package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * Classe responsável por administrar os recursos do painel para listagem de produto
 */
public class PainelLista extends PainelBase{
   
   PainelPrincipal pp;

   Graphics2D g2Lista;

   Color corAreaTexto = new Color(10, 10, 10);

   public JTextArea areaTexto;
   public JScrollPane telaRolagem;
   public JLabel label;

   /**
    * Construtor base que inica as configurações padrões do PainelBase, recebe
    * um leitor de teclado para lidar com as entradas do usuário 
    * @param pp PainelPrincipal necessário
    */   
   public PainelLista(PainelPrincipal pp){
      this.pp = pp;
      configuracaoPadrao();
      addKeyListener(pp.leitorTeclado);

      configurarAreaTexto();
      configurarLabel();

      add(telaRolagem);
      add(label);
   }


   /**
    * Configura a área de texto e a caixa de rolagem dinâmica
    */
   private void configurarAreaTexto(){
      areaTexto = new JTextArea();
      telaRolagem = new JScrollPane(areaTexto);
      telaRolagem.setVisible(false);

      telaRolagem.getVerticalScrollBar().setUI(new ScrollBarCustom(super.corFundoPadraoPainel));
      telaRolagem.getHorizontalScrollBar().setUI(new ScrollBarCustom(super.corFundoPadraoPainel));

      int comprimento = (int) (super.largura * 0.96);
      int altura = (int) (super.altura * 0.84);//330;
  
      telaRolagem.setBorder(null);
      telaRolagem.setBounds((super.largura/2)-(comprimento/2), 60, comprimento, altura);
  
      super.configurarFonteAreaTexto(areaTexto);
      areaTexto.setBackground(super.corFundoPadraoPainel);
      areaTexto.setForeground(Color.white);
      areaTexto.setAutoscrolls(true);
      areaTexto.setBorder(null);
   }


   /**
    * Faz as configurações do label interativo do painel
    */   
   private void configurarLabel(){
      label = new JLabel();

      int altura = 40;
      int largura = 300;

      label.setHorizontalAlignment(SwingConstants.CENTER);
      super.configurarFonteLabel(label, super.tamanhoFonteTextoLabelInfo+3);
      label.setText("Estoque vazio ou não carregado");
      label.setVisible(false);
      label.setForeground(Color.white);
      label.setBounds(((super.largura/2)-(largura/2)), ((super.altura/2)-(altura/2)), largura, altura);
   }


   /**
    * Chama o método privado de desenho de tela da classe
    */
   public void desenharTela(){
      paintComponent(g2Lista);
   }


   /**
    * Desenha tudo relacionado ao painel
    * @param g objeto Graphics necessário
    */   
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g2Lista = (Graphics2D) g.create();

      super.configurarFonteDesenho(g2Lista, tamanhoFonteTextoMenu);
      g2Lista.setColor(Color.red);
      texto = "Lista de Produtos";
      textoX = textoCentralizado(this, g2Lista, texto);
      textoY = posBaseYMenu;
      desenharTextoComSombra(g2Lista, texto, textoX, textoY, corTextoSelecionado, corSombra);

      g2Lista.dispose();
   }
}
