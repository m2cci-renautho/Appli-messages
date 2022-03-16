/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package perso.messagesdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import perso.messagesmodels.Authors;
import perso.messagesmodels.Messages;

/**
 *
 * @author thomas
 */
public class MessagesDAO {
    
    
    private static final String QS = "SELECT content, sent_date, pseudo, age FROM messages JOIN authors ON (messages.author_id = authors.id)";
    public static List<Messages> getAllMessages(DataSource ds) throws SQLException {

        try ( Connection conn = ds.getConnection();  PreparedStatement stmt = conn.prepareStatement(QS)) {

            try ( ResultSet rs = stmt.executeQuery()) {
                List<Messages> messages = new ArrayList();
                while (rs.next()) {
                    
                    String author_pseudo = rs.getString("pseudo");
                    int author_age = rs.getInt("age");
                    String sent_date = rs.getString("sent_date");
                    String content = rs.getString("content");
                    
                    Authors auth = new Authors(author_pseudo, author_age);
                    Messages current = new Messages(auth, content, sent_date);
                    messages.add(current);
                }
                return messages;
            }
        }
    }
}
