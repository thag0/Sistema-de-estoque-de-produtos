import java.awt.CardLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import ui.PainelPrincipal;

public class Main{
   public static void main(String[] args){

      JFrame janela = new JFrame();

      try{
         BufferedImage icone = ImageIO.read(new File("./resource/image/caixa.png"));
         janela.setIconImage(icone);
      }catch(Exception e){}

      PainelPrincipal pp = new PainelPrincipal();

      janela.setLayout(new CardLayout());

      janela.add(pp);
      janela.add(pp.pc);
      janela.add(pp.pr);
      janela.add(pp.pl);
      janela.add(pp.psd);
      janela.add(pp.pld);
      janela.add(pp.prv);
      janela.add(pp.pi);
      janela.add(pp.pep);

      janela.setTitle("Sistema de estoque");
      janela.setVisible(true);
      janela.setResizable(false);
      janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      janela.pack();
      janela.setLocationRelativeTo(null);

      pp.iniciarThread();
   }
}
