import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;


public class login {
    public JPanel mainlogin;
    private JButton login;
    private JButton clear;
    private JButton register;
    private JTextField username;
    private JPasswordField password;
    static MongoClientURI uri ;
    static MongoClient mongo ;
    static DB db;
    static DBCollection user;
    static DBObject checkUsername;
    static DBObject checkPassword;
    String passwordDB;

    public login() {
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetText();
            }
        });
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectDB();
                getUserFromDB();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        login form = new login();
        frame.setContentPane(form.mainlogin);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600,250));
        frame.setVisible(true);

    }
    public void connectDB(){
        try{
            uri = new MongoClientURI("mongodb://admin:88634159sd@ds245532.mlab.com:45532/ox");
            mongo = new MongoClient(uri);
            db = mongo.getDB(uri.getDatabase());
            user = db.getCollection("User");

        }catch (Exception e){

        }
    }private void resetText() {

        username.setText("");
        password.setText("");

    }


    public void  getUserFromDB(){
        BasicDBObject searchQuery1 = new BasicDBObject();
        searchQuery1.put("username", username.getText());
        String passwordTF = new String(password.getPassword());
        checkUsername = user.findOne(searchQuery1);
        if(checkUsername != null) {
            BasicDBObject search = new BasicDBObject();
            DBObject value  = user.findOne(search);
            String name = (String) value.get("name");
            String gender = (String) value.get("gender");
             passwordDB = (String) value.get("password");
            if(CheckPass(passwordDB,passwordTF)){

                //gotolobby
            }else {
                JOptionPane.showMessageDialog(null, "รหัสไม่ถูกต้อง");
                password.setText("");
            }
        }else{
            JOptionPane.showMessageDialog(null, "ไม่มี Username");
            resetText();
        }


    }
    public boolean CheckPass(String passwordDB,String passwordTF){
        if(passwordDB.equals(passwordTF)){
            return true;
        }
        return false;
    }
}
