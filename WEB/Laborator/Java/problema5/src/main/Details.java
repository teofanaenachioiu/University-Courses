package main;
import javax.imageio.ImageIO;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

public class Details extends SimpleTagSupport {

    private String titleX;
    private String titleY;
    private String values;
    private String color;
    private String highest;
    private String lowest;

    public void setTitleX(String titleX) {
        this.titleX = titleX;
    }

    public void setTitleY(String titleY) {
        this.titleY = titleY;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setHighest(String highest) {
        this.highest = highest;
    }

    public void setLowest(String lowest) {
        this.lowest = lowest;
    }

    private int indexMax(String values){
        String[] vals = values.split(",");
        double max = Double.parseDouble(vals[0]);
        int poz = 0;
        for(int i = 0; i < vals.length; i++){
            double doubleVal = Double.parseDouble(vals[i]);
            if(doubleVal > max){
                max = doubleVal;
                poz = i;
            }
        }
        return poz;
    }

    private int indexMin(String values){
        String[] vals = values.split(",");
        double min = Double.parseDouble(vals[0]);
        int poz = 0;
        for(int i = 0; i < vals.length; i++){
            double doubleVal = Double.parseDouble(vals[i]);
            if(doubleVal < min){
                min = doubleVal;
                poz = i;
            }
        }
        return poz;
    }

    public void doTag() throws JspException, IOException {
        int indexMin = indexMin(values);
        int indexMax = indexMax(values);
        String[] v = values.split(",");
        String val = "";
        for(int i = 0; i < v.length; i++){
            if(i == indexMax){
                val += "\t\t\t{y: " + v[i] + ", indexLabel: '" + highest +"'},\n";
            }
            else if(i == indexMin){
                val += "\t\t\t{y: " + v[i] + ", indexLabel: '" + lowest + "'},\n";
            }
            else {
                val += "\t\t\t{y: " + v[i] + "},\n";
            }
        }
        val.substring(0, val.length() - 1);

        JspWriter out = getJspContext().getOut();
        out.println("<!DOCTYPE HTML>\n" +
                "<html>\n" +
                "<head>  \n" +
                "<script>\n" +
                "window.onload = function () {\n" +
                "\n" +
                "var chart = new CanvasJS.Chart(\"chartContainer\", {\n" +
                "\tanimationEnabled: true,\n" +
                "\ttheme: \"light3\",\n" +
                "\ttitle:{\n" +
                "\t\ttext: \"Temperatura pe 4 zile\"\n" +
                "\t},\n" +
                "  \taxisX:{\n" +
                "      title: '" + titleX + "'\n" +
                "    },\n" +
                "\taxisY:{\n" +
                "\t\tincludeZero: false,\n" +
                "      \ttitle: '"+ titleY + "'\n" +
                "\t},\n" +
                "\tdata: [{        \n" +
                "\t\tmarkerColor: '" + color + "',\n" +
                "\t\ttype: \"line\",       \n" +
                "\t\tdataPoints: [\n" +
                val +
                "\t\t]\n" +
                "\t}]\n" +
                "});\n" +
                "chart.render();\n" +
                "\n" +
                "}\n" +
                "</script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"chartContainer\" style=\"height: 370px; width: 100%;\"></div>\n" +
                "<script src=\"https://canvasjs.com/assets/script/canvasjs.min.js\"></script>\n" +
                "</body>\n" +
                "</html>");
    }
}