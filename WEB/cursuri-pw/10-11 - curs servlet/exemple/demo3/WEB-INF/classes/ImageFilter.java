import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.imageio.ImageIO;

public class ImageFilter implements Filter {

  String text = null;

  public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) {
    try {
      System.err.println("Se executa filtrul.");
      System.err.println("Textul care trebuie afisat pe imagine este: " + text);
      // chain.doFilter (request, response);

      HttpServletRequest httpRequest = (HttpServletRequest)request;
      ServletContext application = httpRequest.getSession().getServletContext();
      String file = application.getRealPath(httpRequest.getServletPath());
      System.err.println("S-a cerut fisierul cu numele: " + file);
      InputStream in = new FileInputStream(file);
      OutputStream out = response.getOutputStream();

      BufferedImage oldImg = ImageIO.read(in);
      BufferedImage newImg = null;
      newImg = writeTextOnImage(oldImg, text);
      ImageIO.write(newImg, "gif", out);   

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void init(FilterConfig filterConfig) {
    this.text = filterConfig.getInitParameter("text");
  } 

  public void destroy() {
  }


  protected BufferedImage writeTextOnImage(BufferedImage oldImg, String text){

    int w = oldImg.getWidth();
    int h = oldImg.getHeight();
    BufferedImage newImg = new BufferedImage(w, h, oldImg.getColorModel().getTransparency());   

    Graphics2D g = newImg.createGraphics();

    // display old image
    float transperancy = (float) 0.75;
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transperancy));   
    g.drawImage(oldImg, null, 0, 0);   

    // display an transparent oval
    g.setColor(new Color(152, 152, 152));		
    int minHeight = (h/4 < 50) ? 50: h/4;
    int startY = (h - minHeight) / 2;
    g.fillOval(0, startY, w, minHeight);

    // display the text inside the oval
    g.setColor(Color.black);
    FontRenderContext frc = g.getFontRenderContext();
    int fontSize = (w / 12 > minHeight) ? minHeight : w / 12;
    Font font = new Font ("Tahoma", Font.BOLD, fontSize);
    TextLayout layout = new TextLayout(text, font, frc);

    layout.draw(g, (w-layout.getAdvance())/2, h/2 + (layout.getAscent() - layout.getDescent()) / 2);

    g.dispose();   
    return newImg;
  }

}
