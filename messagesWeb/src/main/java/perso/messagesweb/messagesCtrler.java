/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package perso.messagesweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import perso.messagesdao.MessagesDAO;
import perso.messagesmodels.Messages;

/**
 *
 * @author thomas
 */
@WebServlet(name = "messagesCtrler", urlPatterns = {"/messagesCtrler"})
public class messagesCtrler extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     @Resource(name = "jdbc/messages")
    private DataSource dataSource;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
         
        List <Messages> messages;
        try{
            messages = MessagesDAO.getAllMessages(dataSource);
        }
        catch(SQLException ex){
            throw new ServletException(ex.getMessage(), ex);
        }
        //Création du JSON de réponse
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for( Messages current : messages){
             JsonObject author = Json.createObjectBuilder()   
                                        .add("pseudo", current.getAuthor().getPseudo())
                                        .add("age", current.getAuthor().getAge())
                                        .build();
             JsonObject message = Json.createObjectBuilder()
                                        .add("author", author)
                                        .add("content", current.getContent())
                                        .add("sent_date", current.getSent_date())
                                        .build();
            builder.add(message);
            
        }
        
        JsonArray messagesArray = builder.build();
        
        //Pour écrire dans le stream de sortie : 
        StringWriter stWriter = new StringWriter(); //On définit un objet StringWriter
        JsonWriter jsonWriter = Json.createWriter(stWriter); //On fait un lien entre le StringWriter et un objet JsonWriter
        jsonWriter.writeArray(messagesArray); //On écrit l'array contenant les messages en Json
        jsonWriter.close(); //On ferme le jsonWriter
        String jsonData = stWriter.toString(); //On convertit en String le JsonWriter
     
        try ( PrintWriter out = response.getWriter()) {
           
            out.println(jsonData); //On écrit la chaîne obtenue sur la réponse
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
