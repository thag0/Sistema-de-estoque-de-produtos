package structure;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ui.PainelPrincipal;

/**
 * Classe responsável por administrar os eventos do teclado do programa
 * 
 * Ainda necessário implementar uma forma de concentrar as teclas nessa nessa classe
 * e remover as variáveis de teclas de cada painel individual
 */
public class LeitorTeclado implements KeyListener{

   PainelPrincipal pp;

   private boolean esc = false, setaCima = false, setaBaixo = false, enter = false;

   /**
    * Ainda necessário remover a responsabilidade de cada painel precisar
    * ter uma tecla 
    * @param pp painel principal que possui as teclas e os subpaineis
    */
   public LeitorTeclado(PainelPrincipal pp){
      this.pp = pp;
   }


   @Override
   public void keyTyped(KeyEvent e){
   }

   /**
    * Altera o estado de tecla de cada painel
    */
   @Override
   public void keyPressed(KeyEvent e){
      switch(e.getKeyCode()){
         case KeyEvent.VK_UP:
            setaCima = true;
         break;

         case KeyEvent.VK_DOWN:
            setaBaixo = true;
         break;

         case KeyEvent.VK_ENTER:
            enter = true;
         break;

         case KeyEvent.VK_ESCAPE:
            esc = true;
         break;
      }
      /*
      if(pp.estadoAtual == pp.estadoMenu){
         switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
               pp.setaCima = true;
            break;
   
            case KeyEvent.VK_DOWN:
               pp.setaBaixo = true;
            break;
   
            case KeyEvent.VK_ENTER:
               pp.enter = true;
            break;
         }
      }

      else if(pp.estadoAtual == pp.estadoCadastroProduto){
         switch(e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
               pp.pc.esc = true;
            break;
         }
      }

      else if(pp.estadoAtual == pp.estadoRemocaoProduto){
         switch(e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
               pp.pr.esc = true;
            break;
         }
      }

      else if(pp.estadoAtual == pp.estadoListaProduto){
         switch(e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
               pp.pl.esc = true;
            break;
         }         
      }

      else if(pp.estadoAtual == pp.estadoSalvarDados){
         switch(e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
               pp.psd.esc = true;
            break;

            case KeyEvent.VK_ENTER:
               pp.psd.enter = true;
            break;
         }         
      }

      else if(pp.estadoAtual == pp.estadoLerDados){
         switch(e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
               pp.pld.esc = true;
            break;

            case KeyEvent.VK_ENTER:
               pp.pld.enter = true;
            break;
         }         
      }

      else if(pp.estadoAtual == pp.estadoRegistrarVenda){
         switch(e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
               pp.prv.esc = true;
            break;
         }
      }

      else if(pp.estadoAtual == pp.estadoInformacoes){
         switch(e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
               pp.pi.esc = true;
            break;
         }
      }
      */
   }


   @Override
   public void keyReleased(KeyEvent e){
   }

   //recuperação e configuração de teclas
   public boolean getEnter(){
      return this.enter;
   }
   
   public void setEnter(boolean valor){
      this.enter = valor;
   }

   public boolean getEsc(){
      return this.esc;
   }

   public void setEsc(boolean valor){
      this.esc = valor;
   }

   public boolean getSetaCima(){
      return this.setaCima;
   }

   public void setSetaCima(boolean valor){
      this.setaCima = valor;
   }

   public boolean getSetaBaixo(){
      return this.setaBaixo;
   }

   public void setSetaBaixo(boolean valor){
      this.setaBaixo = valor;
   }
}
