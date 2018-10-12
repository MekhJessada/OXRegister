import javax.swing.*;
import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class register {
    private JTextField name;
    private JTextField userName;
    private JPasswordField password;
    private JButton submitButton;
    private JPanel mainPanel;
    private JRadioButton male;
    private JRadioButton female;
    private JTextField fileAddress;
    private JButton chooseImage;
    private JButton cancelButton;
    private JTextField textField1;
    static MongoClientURI uri ;
    static MongoClient mongo ;
    static DB db;
    static DBCollection user;
    static DBObject checkUsername;
    static DBObject checkName;
    static String gender;
    static  byte[] imageBytes;



    public register() {
        male.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRadio1();
            }
        });
        female.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRadio2();
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectDB();
                submit();
            }
        });
        chooseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                File f=chooser.getSelectedFile();
                String filename = f.getAbsolutePath();
                fileAddress.setText(filename);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        register form = new register();
        frame.setContentPane(form.mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600,250));
        frame.setVisible(true);
    }public void setRadio1(){
        if(male.isSelected()){
            female.setSelected(false);
            female.updateUI();
            gender="male";
        }
    }public void setRadio2(){
        if(female.isSelected()){
            male.setSelected(false);
            male.updateUI();
            gender="female";
        }
    }public void connectDB(){
        try{
            uri = new MongoClientURI("mongodb://admin:88634159sd@ds245532.mlab.com:45532/ox");
            mongo = new MongoClient(uri);
            db = mongo.getDB(uri.getDatabase());
            user = db.getCollection("User");

        }catch (Exception e){

        }
    }public void submit(){
        BasicDBObject searchQuery1  = new BasicDBObject();
        BasicDBObject searchQuery2  = new BasicDBObject();
        searchQuery1.put("username",userName.getText());
        searchQuery2.put("name",name.getText());
        checkUsername = user.findOne(searchQuery1);
        checkName = user.findOne(searchQuery2);
        if (name.getText().isEmpty()||password.getPassword().length==0||userName.getText().isEmpty()||gender==null){
            JOptionPane.showMessageDialog(null, "ใส่ข้อมูลให้ครบ");
        }else if(checkUsername!=null){
            JOptionPane.showMessageDialog(null, "Username ซ้ำ");
        }else if(checkName!=null){
            JOptionPane.showMessageDialog(null, "Name ซ้ำ");
        }else {
            BasicDBObject add = new BasicDBObject();
            add.put("name", name.getText());
            add.put("username", userName.getText());
            add.put("password", new String(password.getPassword()));
            if(male.isSelected()){
                add.put("gender",gender);
            }else if(female.isSelected()){
                add.put("gender",gender);
            }
            try {
                imageBytes = LoadImage(fileAddress.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            GridFS fs = new GridFS( db );
            GridFSInputFile in = fs.createFile( imageBytes );
            if(!fileAddress.getText().isEmpty()){
                add.put("image",in);
            }user.insert(add);
            JOptionPane.showMessageDialog(null, "สมัครสมาชิกสำเร็จ");
            resetText();
        }

    }

    private void resetText() {
        name.setText("");
        userName.setText("");
        password.setText("");
        fileAddress.setText("");
        female.setSelected(false);
        male.setSelected(false);
    }
    public static byte[] LoadImage(String filePath) throws Exception {
        File file = new File(filePath);
        int size = (int)file.length();
        byte[] buffer = new byte[size];
        FileInputStream in = new FileInputStream(file);
        in.read(buffer);
        in.close();
        return buffer;
    }
}
