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

public class PainelEdicaoProduto extends PainelBase implements ActionListener{

   Graphics2D g2Edicao;
   PainelPrincipal pp;

   JButton botao;

   public JTextField caixaTextoCodigo, caixaTextoNome, caixaTextoValor, caixaTextoUnidades;
   public boolean focarCaixaCodigo, focarCaixaNome, focarCaixaValor, focarCaixaUnidades, codigoEncontrado;
   public JLabel label;
   public String textoPadraoLabel = "Insira um código de produto";

   public Color corCaixaTextoSelecionado = super.corCaixaTextoSelecionado;
   public Color corCaixaTextoNaoSelecionado = super.corCaixaTextoNaoSelecionado;

   public PainelEdicaoProduto(PainelPrincipal pp){
      this.pp = pp;
      configuracaoPadrao();
      addKeyListener(pp.getLeitorTeclado());

      configurarCaixaTexto();
      configurarFoco();
      configurarBotao();
      configurarLabel();

      add(caixaTextoCodigo);
      add(caixaTextoNome);
      add(caixaTextoValor);
      add(caixaTextoUnidades);
      add(botao);
      add(label);
   }

   public void desenharTela(){
      paintComponent(g2Edicao);
   }


   private void configurarCaixaTexto(){
      caixaTextoCodigo = new JTextField();
      int xCodigo = super.posXBaseCaixaTexto;
      int yCodigo = (int)(super.posBaseYSubMenu - (super.alturaPadraoCaixaTexto/1.3));

      caixaTextoCodigo.setBackground(super.corCaixaTextoNaoSelecionado);
      caixaTextoCodigo.setBounds(xCodigo, yCodigo, super.larguraPadraoCaixaTexto, super.alturaPadraoCaixaTexto);

      caixaTextoNome = new JTextField();
      yCodigo += (super.espacamentoTexto*2);
      caixaTextoNome.setBackground(super.corCaixaTextoNaoSelecionado);
      caixaTextoNome.setBounds(xCodigo, yCodigo, super.larguraPadraoCaixaTexto, super.alturaPadraoCaixaTexto);

      caixaTextoValor = new JTextField();
      yCodigo += super.espacamentoTexto;
      caixaTextoValor.setBackground(super.corCaixaTextoNaoSelecionado);
      caixaTextoValor.setBounds(xCodigo, yCodigo, super.larguraPadraoCaixaTexto, super.alturaPadraoCaixaTexto);

      caixaTextoUnidades = new JTextField();
      yCodigo += super.espacamentoTexto;
      caixaTextoUnidades.setBackground(super.corCaixaTextoNaoSelecionado);
      caixaTextoUnidades.setBounds(xCodigo, yCodigo, super.larguraPadraoCaixaTexto, super.alturaPadraoCaixaTexto);
   }


   private void configurarFoco(){
      caixaTextoCodigo.addFocusListener(new FocusListener(){
         @Override
         public void focusGained(FocusEvent e){
            focarCaixaCodigo = true;
         }

         @Override
         public void focusLost(FocusEvent e){
            focarCaixaCodigo = false;
         }
      });

      caixaTextoNome.addFocusListener(new FocusListener(){
         @Override
         public void focusGained(FocusEvent e){
            focarCaixaNome = true;
         }

         @Override
         public void focusLost(FocusEvent e){
            focarCaixaNome = false;
         }
      });

      caixaTextoValor.addFocusListener(new FocusListener(){
         @Override
         public void focusGained(FocusEvent e){
            focarCaixaValor = true;
         }

         @Override
         public void focusLost(FocusEvent e){
            focarCaixaValor = false;
         }
      });

      caixaTextoUnidades.addFocusListener(new FocusListener(){
         @Override
         public void focusGained(FocusEvent e){
            focarCaixaUnidades = true;
         }

         @Override
         public void focusLost(FocusEvent e){
            focarCaixaUnidades = false;
         }
      });
   }


   private void configurarBotao(){
      botao = new JButton();
      botao.setForeground(super.corPadraoTextoBotao);
      botao.setBackground(super.corPadraoBotao);
      botao.setText("Alterar");
      botao.addActionListener(this);
      super.configurarFonteBotao(botao);

      int larguraPadrao = super.larguraPadraoBotao;
      int alturaBotao = super.alturaPadraoBotao;
      int xBotao = ((super.largura/2)-(super.larguraPadraoBotao/2));
      int yBotao = (super.altura-(super.alturaPadraoBotao*3));
      botao.setBounds(xBotao, yBotao, larguraPadrao, alturaBotao);
   }


   private void configurarLabel(){
      label = new JLabel();
      label.setText(textoPadraoLabel);
      configurarFonteLabel(label, super.tamanhoPadraoTextoLabel+3);
      label.setForeground(Color.WHITE);
      label.setHorizontalAlignment(SwingConstants.CENTER);
      label.setBackground(super.corFundoPadraoPainel);

      int larguraLabel = (int) (super.largura * 0.6);
      int alturaLabel = 30;
      int xLabel = (super.largura/2) - (larguraLabel/2);
      int yLabel = super.altura - (alturaLabel*5);
      label.setBounds(xLabel, yLabel, larguraLabel, alturaLabel);
   }


   @Override
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g2Edicao = (Graphics2D) g.create();

      desenharFormas(g2Edicao);

      configurarFonteDesenho(g2Edicao, tamanhoFonteTextoMenu);
      texto = "Editar produto";
      textoX = textoCentralizado(this, g2Edicao, texto);
      textoY = super.posBaseYMenu;
      desenharTextoComSombra(g2Edicao, texto, textoX, textoY, corTextoSelecionado, corSombra);

      configurarFonteDesenho(g2Edicao, tamanhoFonteTexto);

      texto = "Código do produto";
      textoX = super.posBaseXSubMenu;
      textoY = super.posBaseYSubMenu;
      desenharTextoComSombra(g2Edicao, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "Novo nome do produto";
      textoY += super.espacamentoTexto*2;
      desenharTextoComSombra(g2Edicao, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "Novo valor do produto";
      textoY += super.espacamentoTexto;
      desenharTextoComSombra(g2Edicao, texto, textoX, textoY, corTextoSelecionado, corSombra);

      texto = "Novas unidades do produto";
      textoY += super.espacamentoTexto;
      desenharTextoComSombra(g2Edicao, texto, textoX, textoY, corTextoSelecionado, corSombra);

      g2Edicao.dispose();
   }


   private void desenharFormas(Graphics2D g2){
      int larguraDesenho = (int) (super.largura * 0.90);
      int alturaDesenho = (int) (super.altura * 0.13);

      g2.setColor(super.corFundoPadraoSubPainel);
      g2.fillRoundRect(((super.largura/2)-(larguraDesenho/2)), (super.posBaseYSubMenu-35), larguraDesenho, alturaDesenho, 20, 20);

      //retangulo central
      alturaDesenho = (int) (super.altura * 0.32);
      g2.fillRoundRect(((super.largura/2)-(larguraDesenho/2)), (super.posBaseYSubMenu+40), larguraDesenho, alturaDesenho, 20, 20);

      //retangulo inferior
      alturaDesenho = (int) (super.altura * 0.22);
      g2.fillRoundRect(((this.largura/2) - (larguraDesenho/2)), (super.altura-20), larguraDesenho, alturaDesenho, 30, 30);
   }


   public void limparCamposTexto(){
      caixaTextoCodigo.setText("");
      caixaTextoNome.setText("");
      caixaTextoValor.setText("");
      caixaTextoUnidades.setText("");
      
      label.setText(textoPadraoLabel);
   }


   @Override
   public void actionPerformed(ActionEvent e){
      if(e.getSource() == botao){
         pp.logica.editarProduto(this);
      }
   }
}
