package demolib;

import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class IterateTag extends TagSupport {

    private Iterator iterator;
    private Collection _collection;
    private String crtElem;


    public String getCrtElem() {
        return crtElem;
    }

    public void setCrtElem(String crtElem) {
        this.crtElem = crtElem;
    }

    public void setCollection(Collection collection) {
        this._collection = collection;
    }

    public int doStartTag() throws JspTagException {
        if (_collection == null) {
            String errorMessage = "No collection with name " + _collection + " found";
            throw new JspTagException(errorMessage);
        }
        iterator = _collection.iterator();
        if (iterator.hasNext()) {
            pageContext.setAttribute(crtElem, iterator.next());
            return EVAL_BODY_INCLUDE;
        } else
            return SKIP_BODY;
    }

    public int doAfterBody() {
        if (iterator.hasNext()) {
            pageContext.setAttribute(crtElem, iterator.next());
            return EVAL_BODY_AGAIN;
        } else
            return SKIP_BODY;
    }

}
