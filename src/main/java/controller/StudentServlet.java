package controller;

import dao.ClassStudentDao;
import dao.StudentDao;
import model.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(urlPatterns = "/student")
public class StudentServlet extends HttpServlet {
    StudentDao studentDao = new StudentDao();
    ClassStudentDao classStudentDao = new ClassStudentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        RequestDispatcher dispatcher = null;
        switch (action) {
            case "create":
                req.setAttribute("classStudent", classStudentDao.getAll());
                dispatcher = req.getRequestDispatcher("/create.jsp");
                dispatcher.forward(req, resp);
                break;
            case "search":
                String search = req.getParameter("search");
                req.setAttribute("students", studentDao.getAllByName(search));
                dispatcher = req.getRequestDispatcher("/home.jsp");
                dispatcher.forward(req, resp);
                break;
            default:
                req.setAttribute("students", studentDao.getAll());
                dispatcher = req.getRequestDispatcher("/home.jsp");
                dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        RequestDispatcher dispatcher = null;
        switch (action) {
            case "create":
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                LocalDate birth = LocalDate.parse(request.getParameter("birth"));
                String address = request.getParameter("address");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                int idClass = Integer.parseInt(request.getParameter("class"));

                Student st = new Student(id, name, birth, address, phone, email, classStudentDao.findById(idClass));
                studentDao.create(st);
                resp.sendRedirect("/student");
                break;
        }
    }
}
