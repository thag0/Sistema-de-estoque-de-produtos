package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import structure.LeitorTeclado;
import structure.Logica;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


/**
 * Admnistra os menus e alterna entre os paineis de cada opção
 * Implementa a interface runnable para criar uma thread separada
 * para o programa
 */
public class PainelPrincipal extends PainelBase implements Runnable{
   final int fps = 60;

   //paineis
   public PainelCadastro pc;
   public PainelRemocao pr;
   public PainelLista pl;
   public PainelSalvarDados psd;
   public PainelLerDados pld;
   public PainelRegistrarVenda prv;
   public PainelInformacoes pi;
   public PainelEdicaoProduto pep;

   //estados do programa
   public final int maxOpcoesMenuPrincipal = 8;
   public int indiceMenu = 1;
   
   public int estadoAtual;
   public final int estadoMenu = 1;
   public final int estadoCadastroProduto = 2;
   public final int estadoRemocaoProduto = 3;
   public final int estadoListaProduto = 4;
   public final int estadoSalvarDados = 5;
   public final int estadoLerDados = 6;
   public final int estadoRegistrarVenda = 7;
   public final int estadoInformacoes = 8;
   public final int estadoEditarProduto = 9;

   LeitorTeclado leitorTeclado = new LeitorTeclado(this);
   Logica logica = new Logica(this);
   Thread threadPrograma;

   Graphics2D g2;

   int contadorLixo;

   BufferedImage icone;

   /**
    * Comstrutor básico que inicaliza valores padrão da classe mãe 
    * e adiciona os diferentes paineis
    */
   public PainelPrincipal(){

      configuracaoPadrao();
      addKeyListener(leitorTeclado);
      setVisible(true);

      pc = new PainelCadastro(this);
      pr = new PainelRemocao(this);
      pl = new PainelLista(this);
      psd = new PainelSalvarDados(this);
      pld = new PainelLerDados(this);
      prv = new PainelRegistrarVenda(this);
      pi = new PainelInformacoes(this);
      pep = new PainelEdicaoProduto(this);

      add(pc);
      add(pr);
      add(pl);
      add(psd);
      add(pld);
      add(prv);
      add(pi);
      add(pep);

      try{
         icone = ImageIO.read(new File("./resource/image/pacote.png"));
      }catch(Exception e){
         icone = null;
      }

      estadoAtual = estadoMenu;
   }


   /**
    * Inicia uma trhead exclusiva para a execução do programa
    */
   public void iniciarThread(){
      threadPrograma = new Thread(this);
      threadPrograma.start(); 
   }


   /**
    * Base do funcionamento em loop do programa
    * Com base no estado atual do programa, atualiza as funções lógicas e admnistra
    * qual painel será desenhado, além de gerenciar o lixo de memória do programa
    * com o método limparLixo()
    */
   @Override
   public void run(){

      //método mais eficiente pra calcular fps em termo de uso de cpu
      double intervaloDesenho = 1000000000/fps;
      double proximoTempoDesenho = System.nanoTime() + intervaloDesenho;
      double tempoRestante;

      while(threadPrograma != null){
         atualizar();
         repaint();
         limparLixo();

         try{
            tempoRestante = proximoTempoDesenho - System.nanoTime();
            tempoRestante /= 1000000;
            if(tempoRestante < 0) tempoRestante = 0;

            Thread.sleep((long)tempoRestante);
            proximoTempoDesenho += intervaloDesenho;

         }catch(Exception e){
            threadPrograma.interrupt();
         }
      }
   }
 

   /**
    * Atualiza as funções lógicas do programa com base no estado atual
    * de execução
    */
   public void atualizar(){
      if(estadoAtual == estadoMenu) logica.telaMenu();
      else if(estadoAtual == estadoCadastroProduto) logica.telaCadastroProduto(pc);
      else if(estadoAtual == estadoRemocaoProduto) logica.telaRemocaoProduto(pr);
      else if(estadoAtual == estadoListaProduto) logica.telaListaProduto(pl);
      else if(estadoAtual == estadoSalvarDados) logica.telaSalvarDados(psd);
      else if(estadoAtual == estadoLerDados) logica.telaLerDados(pld);
      else if(estadoAtual == estadoRegistrarVenda) logica.telaRegistrarVenda(prv);
      else if(estadoAtual == estadoInformacoes) logica.telaInformacoes(pi);
      else if(estadoAtual == estadoEditarProduto) logica.telaEditarProduto(pep);
   }


   /**
    * Atualiza as funções gráficas do programa com base no estado atual
    * de execução
    */
   @Override
   protected void paintComponent(Graphics g){
      if(estadoAtual == estadoMenu) this.desenharTela(g);
      else if(estadoAtual == estadoCadastroProduto)  pc.desenharTela();
      else if(estadoAtual == estadoRemocaoProduto) pr.desenharTela();
      else if(estadoAtual == estadoListaProduto) pl.desenharTela();
      else if(estadoAtual == estadoSalvarDados) psd.desenharTela();
      else if(estadoAtual == estadoInformacoes) pi.desenharTela();
      else if(estadoAtual == estadoEditarProduto) ;//pep.desenharTela();
   }


   /**
    * Desenha tudo necessário e relacionado ao painel de menus do programa
    * @param g objeto Graphics necessário
    */
   private void desenharTela(Graphics g){
      super.paintComponent(g);
      g2 = (Graphics2D) g;

      desenharFormas(g2);

      if(icone != null) g2.drawImage(icone, (super.largura-60), (super.altura-60), 30, 30, null);

      configurarFonteDesenho(g2, tamanhoFonteTextoMenu);

      g2.setColor(Color.white);
      texto = "Menu";
      textoX = textoCentralizado(this, g2, texto);
      textoY = posBaseYMenu;
      desenharTextoComSombra(g2, texto, textoX, textoY, corTextoSelecionado, corSombra);

      //desenhar submenus
      configurarFonteDesenho(g2, tamanhoFonteTexto);

      textoX = posBaseXSubMenu;
      textoY = posBaseYSubMenu;
      texto = "Cadastrar produto";
      if(this.indiceMenu != 1) desenharTextoComSombra(g2, texto, textoX, textoY, corTexto, corSombra); 
      else desenharTextoSelecionado();


      textoY += espacamentoTexto;
      texto = "Remover produto";
      if(this.indiceMenu != 2) desenharTextoComSombra(g2, texto, textoX, textoY, corTexto, corSombra); 
      else desenharTextoSelecionado();

      textoY += espacamentoTexto;
      texto = "Listar produtos";
      if(this.indiceMenu != 3) desenharTextoComSombra(g2, texto, textoX, textoY, corTexto, corSombra); 
      else desenharTextoSelecionado();

      textoY += espacamentoTexto;
      texto = "Salvar dados";
      if(this.indiceMenu != 4) desenharTextoComSombra(g2, texto, textoX, textoY, corTexto, corSombra); 
      else  desenharTextoSelecionado();

      textoY += espacamentoTexto;
      texto = "Carregar dados";
      if(this.indiceMenu != 5) desenharTextoComSombra(g2, texto, textoX, textoY, corTexto, corSombra); 
      else  desenharTextoSelecionado();

      textoY += espacamentoTexto;
      texto = "Registrar venda";
      if(this.indiceMenu != 6) desenharTextoComSombra(g2, texto, textoX, textoY, corTexto, corSombra); 
      else  desenharTextoSelecionado();

      textoY += espacamentoTexto;
      texto = "Dados do estoque";
      if(this.indiceMenu != 7) desenharTextoComSombra(g2, texto, textoX, textoY, corTexto, corSombra); 
      else  desenharTextoSelecionado();

      textoY += espacamentoTexto;
      texto = "Editar produto";
      if(this.indiceMenu != 8) desenharTextoComSombra(g2, texto, textoX, textoY, corTexto, corSombra); 
      else  desenharTextoSelecionado();

      g2.dispose();
   }


   private void desenharFormas(Graphics2D g2){
      g2.setColor(super.corFundoPadraoSubPainel);

      //faixa superior
      int larguraDesenho = (int) (super.largura * 0.2);
      int alturaDesenho = 20;
      int larguraArco = 20;
      int alturaArco = 20;
      g2.fillRoundRect(0, 0, larguraDesenho, alturaDesenho, larguraArco, alturaArco);

      //faixa canto esquerdo
      larguraDesenho = 20;
      alturaDesenho = (int) (super.altura * 0.2);
      g2.fillRoundRect(0, 0, larguraDesenho, alturaDesenho, larguraArco, alturaArco);   
      
      //faixa canto direito
      larguraDesenho = 20;
      alturaDesenho = (int) (super.altura * 0.7);
      g2.fillRoundRect((this.largura-larguraDesenho), (this.altura-alturaDesenho), larguraDesenho, alturaDesenho, larguraArco, alturaArco);

      //faixa inferior
      larguraDesenho = (int) (super.largura * 0.7);
      alturaDesenho = 20;
      g2.fillRoundRect((this.largura-larguraDesenho), (this.altura-alturaDesenho), larguraDesenho, alturaDesenho, larguraArco, alturaArco);


      larguraDesenho = 80;
      alturaDesenho = 80;
      g2.fillRoundRect((super.largura-larguraDesenho), (super.altura-alturaDesenho), alturaDesenho, alturaDesenho, larguraArco, alturaArco);
   }


   public void desenharTextoSelecionado(){
      configurarFonteDesenho(g2, tamanhoFonteTexto+4);
      desenharTextoComSombra(g2, texto, textoX+11, textoY, corTextoSelecionado, corSombra);
      configurarFonteDesenho(g2, tamanhoFonteTexto);
   }


   public LeitorTeclado getLeitorTeclado(){
      return this.leitorTeclado;
   }


   /**
    * Chama o garbage collect do java baseado no tempo
    * passado da execução do programa
    */
   public void limparLixo(){
      contadorLixo++;
      if(contadorLixo == (fps * 30)){
         System.gc();
         contadorLixo = 0;
      }
   }
}
