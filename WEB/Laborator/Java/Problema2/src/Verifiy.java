


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Verifiy", urlPatterns = {"/verifiy"})
public class Verifiy extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = -1998450952225068314L;
    private ImageGenerator cig;
    private String captchaString;

    public void init()
    {
        try
        {
            cig = new ImageGenerator(300, 200);
            captchaString = cig.generateCaptchaString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String captchaInput = request.getParameter("captchaInput");
        System.out.println("I have: " + captchaString + " You Have: " + captchaInput);
        String message = "Wrong input";
        if (captchaInput.equals(captchaString)) {
            message = "Success";
        }
        captchaString = cig.generateCaptchaString();
        request.setAttribute("captcha", cig.getCaptcha(captchaString));
        request.setAttribute("message", message);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        System.out.println("test");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("test");
        request.setAttribute("captcha", cig.getCaptcha(captchaString));
        request.getRequestDispatcher("/index.jsp").forward(request, response);

    }
}