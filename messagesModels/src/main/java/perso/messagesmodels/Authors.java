/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perso.messagesmodels;

/**
 *
 * @author thomas
 */
public class Authors {
    
    private String pseudo;
    private int age;

    public Authors(String pseudo, int age) {
        this.pseudo = pseudo;
        this.age = age;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    
}
