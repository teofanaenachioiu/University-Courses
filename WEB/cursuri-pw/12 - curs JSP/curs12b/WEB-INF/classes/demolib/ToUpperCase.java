package demolib;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class ToUpperCase extends BodyTagSupport {

    public int doAfterBody() throws JspException {
        try {
            BodyContent content = getBodyContent();
            String body = content.getString();
            JspWriter out = content.getEnclosingWriter();
            if (body != null)
                out.print(body.toUpperCase());
        } catch (IOException e) {
            System.err.println(e);
        }
        return SKIP_BODY;
    }

}
