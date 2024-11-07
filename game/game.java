package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class game extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    
    
    public int difficultyLevel;
    private int characterX = 100;
    private int characterY = 400;
    private int velocityY = 0;
    private int velocityX = 0;
    private final int gravity = 1;
    private boolean isJumping = false;
    private boolean isOnPlatform = false;  // ตรวจสอบว่าตัวละครอยู่บนแพลตฟอร์มหรือไม่
    private int lives = 3;
    private boolean gameOver = false;
    private boolean hasWon = false;
    private boolean isAKeyPressed = false;
    private boolean isDKeyPressed = false;
    private Image characterImage;
    private Image enemyImage;
    private Image specialEnemyImage;
    private Image collectibleImage;
    private Image platformImage;
    private Image finishLineImage;
    private Image backgroundImage;
    private Image heartImage;

    private Rectangle[] enemies;  // ศัตรู 4 ตัว
    private Rectangle[] specialEnemy;  // ศัตรูพิเศษ
    private Rectangle[] platforms;  // แพลตฟอร์ม
    private Rectangle finishLine;  // เส้นชัย
    private Rectangle[] collectibles;  // วัตถุที่ต้องเก็บ
    private int collectiblesCount = 0;  // นับจำนวนวัตถุที่เก็บได้

    public game() {
        timer = new Timer(20, this);
        timer.start();
        setFocusable(true);
        setPreferredSize(new Dimension(800, 600));
        addKeyListener(this);
        backgroundImage = new ImageIcon(getClass().getResource("/game/background.png")).getImage();
        characterImage = new ImageIcon(getClass().getResource("/game/character.png")).getImage();
        enemyImage = new ImageIcon(getClass().getResource("/game/enemy.png")).getImage();
        collectibleImage = new ImageIcon(getClass().getResource("/game/collectible.png")).getImage();
        finishLineImage = new ImageIcon(getClass().getResource("/game/finishLine.png")).getImage();
        heartImage = new ImageIcon(getClass().getResource("/game/heart.png")).getImage();
        specialEnemyImage = new ImageIcon(getClass().getResource("/game/specialEnemy.png")).getImage();
        // กำหนดค่าเริ่มต้น (สามารถเลือกระดับความยากเบื้องต้นได้)
        setDifficulty(1); // กำหนดความยากเริ่มต้นเป็น easy (1)
    }

    // ฟังก์ชันสำหรับตั้งค่าระดับความยาก
    public void setDifficulty(int difficulty) {
        switch (difficulty) {
            case 1:  // Easy
                setEasyDifficulty();
                break;
            case 2:  // Normal
                setNormalDifficulty();
                break;
            case 3:  // Hard
                setHardDifficulty();
                break;
        }
    }

    public void setEasyDifficulty() {
        lives = 3;  // หัวใจ 3 ดวงสำหรับระดับ Easy

        // สร้างแพลตฟอร์มสำหรับระดับ Easy
        platforms = new Rectangle[2];
        platforms[0] = new Rectangle(300, 350, 400, 20);  // แพลตฟอร์มชั้นล่าง
        platforms[1] = new Rectangle(100, 250, 300, 20);  // แพลตฟอร์มชั้นบน

        // ศัตรูเฉพาะสำหรับ Easy
        enemies = new Rectangle[3];
        enemies[0] = new Rectangle(300, 290, 50, 75);  // ศัตรูที่ 1
        enemies[1] = new Rectangle(200, 190, 50, 75);  // ศัตรูที่ 2
        enemies[2] = new Rectangle(450, 425, 50, 75);  // ศัตรูที่ 3

        // วัตถุที่เก็บได้สำหรับ Easy
        collectibles = new Rectangle[2];
        collectibles[0] = new Rectangle(500, 300, 30, 50);
        collectibles[1] = new Rectangle(120, 180, 30, 50);
        // เส้นชัย
        finishLine = new Rectangle(700, 400, 50, 100);
        repaint();
    }

    public void setNormalDifficulty() {
        lives = 3;  // หัวใจ 3 ดวงสำหรับระดับ Normal

        // สร้างแพลตฟอร์มใหม่สำหรับระดับ Normal
        platforms = new Rectangle[3];
        platforms[0] = new Rectangle(400, 350, 300, 20);
        platforms[1] = new Rectangle(50, 250, 400, 20);
        platforms[2] = new Rectangle(200, 125, 200, 20);

        // ศัตรูเฉพาะสำหรับ Normal
        enemies = new Rectangle[4];
        enemies[0] = new Rectangle(500, 290, 50, 75);  // ศัตรูที่ 1
        enemies[1] = new Rectangle(250, 190, 50, 75);  // ศัตรูที่ 2
        enemies[2] = new Rectangle(225 , 420, 50, 75);  // ศัตรูที่ 3
        enemies[3] = new Rectangle(240, 60, 50, 75);  // ศัตรูที่ 4

        // วัตถุที่เก็บได้สำหรับ Normal
        collectibles = new Rectangle[3];
        collectibles[0] = new Rectangle(600, 300, 30, 50);
        collectibles[1] = new Rectangle(200, 200, 30, 50);
        collectibles[2] = new Rectangle(500, 400, 30, 50);

        finishLine = new Rectangle(700, 400, 50, 100);
        repaint();
    }

    public void setHardDifficulty() {
        lives = 3;  // หัวใจ 3 ดวงสำหรับระดับ Hard

        // สร้างแพลตฟอร์มใหม่สำหรับระดับ Hard
        platforms = new Rectangle[4];
        platforms[0] = new Rectangle(100, 350, 300, 20);
        platforms[1] = new Rectangle(100, 250, 400, 20);
        platforms[2] = new Rectangle(500, 150, 300, 20);
        platforms[3] = new Rectangle(0, 150, 200, 20);

        // ศัตรูเฉพาะสำหรับ Hard
        enemies = new Rectangle[4];
        enemies[0] = new Rectangle(400, 190, 50, 75);  // ศัตรูที่ 1
        enemies[1] = new Rectangle(125, 190, 50, 75);  // ศัตรูที่ 2
        enemies[2] = new Rectangle(200, 290, 50, 75);  // ศัตรูที่ 3
        enemies[3] = new Rectangle(625, 425, 50, 75);  // ศัตรูที่ 4

        specialEnemy = new Rectangle[3];
        specialEnemy[0] = new Rectangle(600, 100, 50, 60);
        specialEnemy[1] = new Rectangle(150, 100, 50, 60);
        specialEnemy[2] = new Rectangle(525, 440, 50, 60);

        // วัตถุที่เก็บได้สำหรับ Hard
        collectibles = new Rectangle[4];
        collectibles[0] = new Rectangle(150, 300, 30, 50);
        collectibles[1] = new Rectangle(575, 420, 30, 50);
        collectibles[2] = new Rectangle(405, 200, 30, 50);
        collectibles[3] = new Rectangle(50, 100, 30, 50);

        finishLine = new Rectangle(700, 60, 50, 100);
        repaint();
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            velocityY = -15;  // กำหนดความเร็วการกระโดด
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !hasWon) {
            // การกระโดดและตกลงมา
            if (isJumping || !isOnPlatform) {
                characterY += velocityY;
                velocityY += gravity;
            }

            // ตรวจสอบการเหยียบแพลตฟอร์ม
            isOnPlatform = false;
            for (Rectangle platform : platforms) {
                if (new Rectangle(characterX, characterY + 80, 50, 1).intersects(platform)) {
                    characterY = platform.y-80;  // ยืนบนแพลตฟอร์ม
                    isJumping = false;
                    isOnPlatform = true;  // ตัวละครอยู่บนแพลตฟอร์ม
                    velocityY = 0;  // หยุดความเร็วแนวดิ่งเมื่ออยู่บนแพลตฟอร์ม
                }
            }

            if (characterY >= 400) {  // ตกลงมาที่พื้น
                characterY = 400;
                isJumping = false;
                isOnPlatform = true;  // ตัวละครอยู่บนพื้น
            }

            // การเคลื่อนที่ซ้าย-ขวา
            characterX += velocityX;

            // ตรวจสอบขอบหน้าจอ
            if (characterX < 0) {
                characterX = 0;
            } else if (characterX > getWidth() - 50) {
                characterX = getWidth() - 50;
            }

            // ตรวจสอบการชนกับศัตรู
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i] != null && new Rectangle(characterX, characterY, 50, 50).intersects(enemies[i])) {
                    enemies[i] = null;  // ลบศัตรูเมื่อชน
                    lives--;  // ลดหัวใจลง 1 ดวง
                    if (lives <= 0) {
                        gameOver = true;  // ถ้าหัวใจหมด เกมจะจบ
                    }
                }
            }

            // ตรวจสอบการชนกับศัตรูพิเศษ
            if (specialEnemy != null) {
                for (int i = 0; i < specialEnemy.length; i++) {
                    if (specialEnemy[i] != null && new Rectangle(characterX, characterY, 50, 50).intersects(specialEnemy[i])) {
                        specialEnemy[i] = null;  // ลบศัตรูพิเศษเมื่อชน
                        lives -= 3;  // ลดหัวใจลง 3 ดวง
                        if (lives <= 0) {
                            gameOver = true;  // ถ้าหัวใจหมด เกมจะจบ
                        }
                    }
                }
            }
            // ตรวจสอบการเก็บวัตถุ
            for (int i = 0; i < collectibles.length; i++) {
                if (collectibles[i] != null && new Rectangle(characterX, characterY, 50, 50).intersects(collectibles[i])) {
                    collectibles[i] = null;  // ลบวัตถุเมื่อเก็บ
                    collectiblesCount++;  // เพิ่มจำนวนวัตถุที่เก็บได้
                }
            }

            // ตรวจสอบการเข้าเส้นชัย
            if (new Rectangle(characterX, characterY, 50, 50).intersects(finishLine) && collectiblesCount == collectibles.length) {
                hasWon = true;  // ถ้าเก็บวัตถุครบและชนเส้นชัยจะชนะ
            }
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // วาดพื้นหลัง
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);


        // วาดแพลตฟอร์ม
        g.setColor(Color.DARK_GRAY);
        for (Rectangle platform : platforms) {
            g.fillRect(platform.x, platform.y, platform.width, platform.height);
        }

        // วาดตัวละคร
        g.drawImage(characterImage, characterX, characterY, 50, 100, this);

        // วาดศัตรู
        for (Rectangle enemy : enemies) {
            if (enemy != null) {
                g.drawImage(enemyImage, enemy.x, enemy.y, enemy.width, enemy.height, this);
            }
        }

        // วาดศัตรูพิเศษ
        if (specialEnemy != null) {  // ตรวจสอบว่า specialEnemy ไม่เป็น null
            for (Rectangle special : specialEnemy) {
                if (special != null) {
                    g.drawImage(specialEnemyImage, special.x, special.y, special.width, special.height, this);
                }
            }
        }
        // วาดวัตถุที่ต้องเก็บ
        for (Rectangle collectible : collectibles) {
            if (collectible != null) {
                g.drawImage(collectibleImage, collectible.x, collectible.y, collectible.width, collectible.height, this);
            }
        }

        // วาดเส้นชัย
        g.drawImage(finishLineImage, finishLine.x, finishLine.y, finishLine.width, finishLine.height, this);

        // วาดหัวใจ
        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage, 20 + (i * 50), 20, 50, 50, this);
        }

        // แสดงข้อความเมื่อเกมจบ
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", getWidth() / 2 - 150, getHeight() / 2);
        }

        // แสดงข้อความเมื่อชนะ
        if (hasWon) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("You Win!", getWidth() / 2 - 150, getHeight() / 2);
        }

        // แสดงจำนวนวัตถุที่เก็บได้
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Collectibles: " + collectiblesCount + "/" + collectibles.length, 20, 75);

        // แสดงปุ่ม Restart
        if (gameOver || hasWon) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Press R to Restart", getWidth() / 2 - 150, getHeight() / 2 + 100);
            g.drawString("Press Enter to go back to Lobby", getWidth() / 2 - 250, getHeight() / 2 + 50);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // ไม่ใช้งานในกรณีนี้
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // เงื่อนไขสำหรับการกดปุ่มเมื่อเกมยังไม่จบ
        if (!gameOver && !hasWon) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                jump();  // กระโดดเมื่อกด spacebar
            }

            if (e.getKeyCode() == KeyEvent.VK_A) {
                isAKeyPressed = true;
                velocityX = isDKeyPressed ? 0 : -5;  // หยุดหาก D กดอยู่
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                isDKeyPressed = true;
                velocityX = isAKeyPressed ? 0 : 5;  // หยุดหาก A กดอยู่
            }
        }
        // เงื่อนไขสำหรับการกดปุ่ม R เมื่อเกมจบหรือชนะ
        if (gameOver || hasWon) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                restartGame();  // เริ่มเกมใหม่เมื่อกด R
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                // เมื่อเกมจบหรือชนะ ให้กลับไปที่ล็อบบี้
                Lobby mainFrame = (Lobby) SwingUtilities.getWindowAncestor(this);
                mainFrame.showLobby();  // สร้างเมธอด showLobby ใน GameWithLobby เพื่อแสดงล็อบบี้
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver && !hasWon) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                isAKeyPressed = false;
                velocityX = isDKeyPressed ? 5 : 0;  // กลับมาเดินขวาหาก D ยังกดอยู่
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                isDKeyPressed = false;
                velocityX = isAKeyPressed ? -5 : 0; // กลับมาเดินซ้ายหาก A ยังกดอยู่
            }
        }

    }

    public void restartGame() {
        // Reset ค่าเริ่มต้นของศัตรู, แพลตฟอร์ม, และหัวใจตามระดับความยาก
        setDifficulty(difficultyLevel);  // เรียกใช้การตั้งค่าตามระดับความยากที่เลือกไว้
        characterX = 100;
        characterY = 400;
        velocityY = 0;
        velocityX = 0;
        gameOver = false;
        hasWon = false;
        collectiblesCount = 0;
        repaint();  // เรียก repaint เพื่อให้ค่าเหล่านี้ปรากฏใหม่
    }

}
