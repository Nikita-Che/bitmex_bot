package finalVersionBitmexBot.servlet;

import finalVersionBitmexBot.ThreadManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        performAction(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        performAction(request, response);
    }

    private void performAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (request.getParameter("startButton") != null) {
            ThreadManager threadManager = new ThreadManager();
            threadManager.startSubscriptions();

            response.getWriter().println("<h1>Потоки запущены</h1>");
          // повторно отправить на старт
        } else if (request.getParameter("stopButton") != null) {
            ThreadManager.running = false;

            response.getWriter().println("<h1>Потоки остановлены</h1>");
        } else {
            response.getWriter().println("<h1>Неверный запрос</h1>");
        }
    }
}
