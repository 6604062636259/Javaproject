package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lobby extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private game gamePanel;  // ตัวเกม
    private JPanel lobbyPanel;  // ล็อบบี้
    private Image lobbyBackgroundImage;

    public Lobby() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // สร้างหน้า lobby
        lobbyBackgroundImage = new ImageIcon(getClass().getResource("/game/lobbyBackgroundImage.png")).getImage();
        lobbyPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(lobbyBackgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        lobbyPanel.setLayout(new BoxLayout(lobbyPanel, BoxLayout.Y_AXIS));

        JButton easyButton = new JButton("Easy");
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(1);  // ระดับความยาก 1 คือ Easy
            }
        });

        lobbyPanel.setLayout(new BoxLayout(lobbyPanel, BoxLayout.Y_AXIS));

        JButton normalButton = new JButton("Normal");
        normalButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(2);  // ระดับความยาก 2 คือ Normal
            }
        });

        JButton hardButton = new JButton("Hard");
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(3);  // ระดับความยาก 3 คือ Hard
            }
        });

        // เพิ่มปุ่มและข้อความไปที่ lobby
        lobbyPanel.add(Box.createVerticalGlue());
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 20)));  // เว้นระยะห่าง
        lobbyPanel.add(easyButton);
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        lobbyPanel.add(normalButton);
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        lobbyPanel.add(hardButton);
        lobbyPanel.add(Box.createVerticalGlue());

        // สร้างหน้าตัวเกม
        gamePanel = new game();

        // เพิ่มทั้งสองหน้าไปยัง mainPanel
        mainPanel.add(lobbyPanel, "Lobby");
        mainPanel.add(gamePanel, "Game");

        // ตั้งค่าเริ่มต้นหน้า Lobby
        add(mainPanel);
        cardLayout.show(mainPanel, "Lobby");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);  // จัดตำแหน่งให้ตรงกลางหน้าจอ
        setVisible(true);
    }

    // ฟังก์ชันเริ่มเกมตามระดับความยาก
    private void startGame(int difficulty) {
        gamePanel.difficultyLevel = difficulty;  // เก็บค่า difficulty level
        gamePanel.setDifficulty(difficulty);  // ส่งระดับความยากไปยังเกม
        gamePanel.restartGame();  // เริ่มเกมใหม่ที่ระดับความยากที่เลือก
        cardLayout.show(mainPanel, "Game");  // เปลี่ยนไปที่หน้าตัวเกม
        gamePanel.requestFocusInWindow();  // ขอให้หน้าตัวเกมได้รับโฟกัสเพื่อรับคีย์บอร์ด
    }

    // ฟังก์ชันเพื่อแสดงล็อบบี้
    public void showLobby() {
        cardLayout.show(mainPanel, "Lobby");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Lobby();
            }
        });
    }
}
