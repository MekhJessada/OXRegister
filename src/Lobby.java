import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lobby {
    private JPanel LobbyFrom;
    private JButton changeProfileButton;
    private JButton historyButton;
    private JButton createRoomButton;
    private JButton findRoomButton;
    private JButton roomNameButton;
    private JButton roomNameButton1;
    private JButton roomNameButton2;
    private JButton roomNameButton3;
    private JPanel ProfileFrom;
    private JPanel StatFrom;
    private JPanel CreateFindRoomFrom;
    private JPanel SelectRoomFrom;

    public Lobby() {
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        changeProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        createRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        findRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Lobby form = new Lobby();
        frame.setContentPane(form.LobbyFrom);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600, 250));
        frame.setVisible(true);
    }

}