package demolib;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class Salut extends TagSupport {

    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print("Hellow World!");
        } catch (IOException e) {
            System.err.println(e);
        }
        return SKIP_BODY;
    }    
}