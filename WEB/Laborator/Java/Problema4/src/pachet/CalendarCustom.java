package pachet;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CalendarCustom extends SimpleTagSupport
{
    public int an,luna,zi;
    String culoare;
    String style;
    public int getAn() {
        return an;
    }
    public void setAn(int an) {
        this.an = an;
    }
    public int getLuna() {
        return luna;
    }
    public void setLuna(int luna) {
        this.luna = luna;
    }
    public int getZi() {
        return zi;
    }
    public void setZi(int zi) {
        this.zi = zi;
    }
    public String getCuloare() {
        return culoare;
    }
    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }
    public String getStyle() {
        return style;
    }
    public void setStyle(String style) {
        this.style = style;
    }
    @Override
    public String toString() {
        return "pachet.CalendarDragut [an=" + an + ", luna=" + luna + ", zi=" + zi + ", culoare=" + culoare + ", style="
                + style + "]";
    }
    public void doTag() throws JspException, IOException
    {
        JspWriter out = getJspContext().getOut();
        if (culoare==null)
        {
            culoare = "#1abc9c";
        }
        if (style==null)
        {
            style = "";
        }
        out.println(calendarHTML());
        System.out.println(toString());
    }


    private String zileGoale()
    {
        String total="";
        for (int i=1;i<zileleSaptamanii();i++)
        {
            total+="<li></li>";
        }
        return total;
    }

    private int numarZilele()
    {
        Calendar mycal = new GregorianCalendar(an, luna-1, zi);
        return mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private String zilele()
    {
        int daysCount = numarZilele();

        String total = "";
        for (int i=1;i<=daysCount;i++)
        {
            if (i!=zi)
            {
                total+="<li>"+i+"</li>";
            }
            else
            {
                total+="<li><span class=\"active\" style=\"background: "+culoare+";"+style+"\">"+i+"</span></li>";
            }
        }
        return total;
    }

    private int zileleSaptamanii()
    {
        Calendar c = new GregorianCalendar(an, luna-1, 0);
        return c.get(Calendar.DAY_OF_WEEK);
    }


    private String calendarHTML()
    {
        String sb = "<div class=\"month\" style=\"background: "+culoare+";"+style+"\">      " +
                "  <ul>" +
                "    <li>" +
                "      "+new DateFormatSymbols().getMonths()[luna-1]+"<br>" +
                "      <span style=\"font-size:18px\">"+an+"</span>" +
                "    </li>" +
                "  </ul>" +
                "</div>" +
                "<ul class=\"weekdays\">" +
                "  <li>Mo</li>" +
                "  <li>Tu</li>" +
                "  <li>We</li>" +
                "  <li>Th</li>" +
                "  <li>Fr</li>" +
                "  <li>Sa</li>" +
                "  <li>Su</li>" +
                "</ul>" +
                "<ul class=\"days\"> " +
                zileGoale() +
                zilele()+
                "</ul>";

        return sb;
    }

}