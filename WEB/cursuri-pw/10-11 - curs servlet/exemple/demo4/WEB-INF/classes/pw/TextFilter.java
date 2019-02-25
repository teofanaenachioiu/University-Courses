package pw;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class TextFilter implements Filter {

    String inText = null;
    String outText = null;

    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            PrintWriter out = response.getWriter();
            TextResponseWrapper wrapper = new TextResponseWrapper((HttpServletResponse)response);

            System.err.println("Se va executa filtrul." + ((HttpServletRequest) request).getRequestURI());
            chain.doFilter(request, wrapper);

            String responseText = wrapper.toString();

            out.print(responseText.replaceAll(inText, outText));

            System.err.println("S-a executat filtrul." + ((HttpServletRequest) request).getRequestURI());
         } catch (Exception e) {
           e.printStackTrace();
         }
    }


    public void init(FilterConfig filterConfig) {
      this.inText = filterConfig.getInitParameter("inText");
      this.outText = filterConfig.getInitParameter("outText");
    }

    public void destroy() {
    }

}


class TextResponseWrapper extends HttpServletResponseWrapper {

    private CharArrayWriter output;

    public String toString() {
      return output.toString();
    }

    public TextResponseWrapper(HttpServletResponse response){
        super(response);
        output = new CharArrayWriter();
    }

    public PrintWriter getWriter(){
        return new PrintWriter(output);
    }

}