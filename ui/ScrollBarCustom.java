package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollBarCustom extends BasicScrollBarUI{

   final int larguraScrollBar = 9;
   final int alturaScrollBar = 10;

   // Configura a cor do gradiente da scrollbar
   final Color corScrollBar ;//new Color(80, 80, 105);

   public ScrollBarCustom(Color corPainelBase){
      int vermelho = (int) (corPainelBase.getRed() * 1.7);
      int verde = (int) (corPainelBase.getGreen() * 1.7);
      int azul = (int) (corPainelBase.getBlue() * 1.7);
      
      if(vermelho > 255) vermelho = 255;
      if(verde > 255) verde = 255;
      if(azul > 255) azul = 255;

      corScrollBar = new Color(vermelho, verde, azul);
   }

   @Override
   protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds){
      // Desenha a parte deslizante da scrollbar
      Graphics2D g2 = (Graphics2D)g;
      g2.setPaint(corScrollBar);
      g2.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
      g2.dispose();
   }


   public Dimension getPreferredSize(JComponent c){
      // Configura a largura e altura da scrollbar
      return new Dimension(larguraScrollBar, alturaScrollBar);
   }
}
