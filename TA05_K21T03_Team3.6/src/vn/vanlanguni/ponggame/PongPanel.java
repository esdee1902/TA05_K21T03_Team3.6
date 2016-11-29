/*
 * PONG GAME REQUIREMENTS
 * This simple "tennis like" game features two paddles and a ball, 
 * the goal is to defeat your opponent by being the first one to gain 3 point,
 *  a player gets a point once the opponent misses a ball. 
 *  The game can be played with two human players, one on the left and one on 
 *  the right. They use keyboard to start/restart game and control the paddles. 
 *  The ball and two paddles should be red and separating lines should be green. 
 *  Players score should be blue and background should be black.
 *  Keyboard requirements:
 *  + P key: start
 *  + Space key: restart
 *  + W/S key: move paddle up/down
 *  + Up/Down key: move paddle up/down
 *  
 *  Version: 0.5
 */

package vn.vanlanguni.ponggame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.sun.prism.Image;
/**
 * 
 * @author Invisible Man
 *
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = -1097341635155021546L;

	private boolean showTitleScreen = true;
	private boolean playing;
	private boolean gameOver;
	
	/*Button*/
	private JButton btnStart = new JButton("Start");
	/** Background. */
	private Color backgroundColor = Color.BLACK;
	/*Name*/
	private String nameChangeBallSetting  = "Images/earth.jpg";
	private String nameUser1 ="Player 1" , nameUser2 ="Player 2";
	/*Image Icon	 */
	 ImageIcon imgChangeBall ;

	/** State on the control keys. */
	private boolean upPressed;
	private boolean downPressed;
	private boolean wPressed;
	private boolean sPressed;

	/** The ball: position, diameter */
	private int ballX = 225;
	private int ballY = 225;
	private int diameter = 20;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;

	/** Player 1's paddle: position and size */
	private int playerOneX = 0;
	private int playerOneY = 250;
	private int playerOneWidth = 10;
	private int playerOneHeight = 180;

	/** Player 2's paddle: position and size */
	private int playerTwoX = 485;
	private int playerTwoY = 250;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 180;

	/** Speed of the paddle - How fast the paddle move. */
	private int paddleSpeed = 5;

	/** Player score, show on upper left and right. */
	private int playerOneScore;
	private int playerTwoScore;
	
	
	/** Construct a PongPanel. */
	public PongPanel() {
		setBackground(backgroundColor);
		
		
		// listen to key presses
		setFocusable(true);
		addKeyListener(this);

		// call step() 60 fps
		Timer timer = new Timer(1000 / 60, this);
		timer.start();
	}

	/** Implement actionPerformed */
	public void actionPerformed(ActionEvent e) {
		step();
	}
	
	

	/** Repeated task */
	public void step() {

		if (playing) {

			/* Playing mode */



			// move player 1
			// Move up if after moving, paddle is not outside the screen
			if (wPressed && playerOneY - paddleSpeed > 0) {
				playerOneY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (sPressed && playerOneY + playerOneHeight + paddleSpeed < getHeight()) {
				playerOneY += paddleSpeed;
			}

			// move player 2
			// Move up if after moving paddle is not outside the screen
			if (upPressed && playerTwoY - paddleSpeed > 0) {
				playerTwoY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (downPressed && playerTwoY + playerTwoHeight + paddleSpeed < getHeight()) {
				playerTwoY += paddleSpeed;
			}

			/*
			 * where will the ball be after it moves? calculate 4 corners: Left,
			 * Right, Top, Bottom of the ball used to determine whether the ball
			 * was out yet
			 */
			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diameter + ballDeltaX;
			// FIXME Something not quite right here
			int nextBallTop = ballY + ballDeltaY;
			int nextBallBottom = ballY + diameter + ballDeltaX;

			// Player 1's paddle position
			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight;
			int playerOneMiddleTop = (int)( playerOneHeight * 1/3 + playerOneTop);
			int playerOneMiddleBot = (int)( playerOneHeight * 2/3 + playerOneTop);

			// Player 2's paddle position
			int playerTwoLeft = playerTwoX;
			int playerTwoTop = playerTwoY;
			int playerTwoBottom = playerTwoY + playerTwoHeight;
			int playerTwoMiddleTop = (int)( playerTwoHeight * 1/3 + playerTwoTop);
			int playerTwoMiddleBot = (int)( playerTwoHeight * 2/3 + playerTwoTop);

			// ball bounces off top and bottom of screen
			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				ballDeltaY *= -1;
				Sound.play("Sound/boing.wav");
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

					playerTwoScore++;

					// Player 2 Win, restart the game
					if (playerTwoScore == 3) {
						playing = false;
						gameOver = true;
						Sound.play("Sound/laser_x.wav");
					}
					ballX = 225;
					ballY = 225;
					ballDeltaX = -1;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					//ballDeltaX = -1;
					if(nextBallTop >= playerOneTop && nextBallBottom < playerOneMiddleTop)
					{
						ballDeltaX = -5;
					}
					else if(nextBallTop >= playerOneMiddleTop && nextBallBottom <=playerOneMiddleBot)
					{
						ballDeltaX = -10;
					}
					else if(nextBallTop >= playerOneMiddleBot && nextBallBottom <= playerOneBottom)
					{
						ballDeltaX = -15;
					}
					
					ballDeltaX *= -1;
					Sound.play("Sound/boing.wav");
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {
					

					playerOneScore++;

					// Player 1 Win, restart the game
					if (playerOneScore == 3) {
						playing = false;
						gameOver = true;
						Sound.play("Sound/laser_x.wav");
					}
					ballX = 225;
					ballY = 225;
					ballDeltaX = -1;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					//ballDeltaX = 1;
					if(nextBallTop >= playerTwoTop && nextBallBottom < playerTwoMiddleTop)
					{
						ballDeltaX = 5;
					}
					else if(nextBallTop >= playerTwoMiddleTop && nextBallBottom <=playerTwoMiddleBot)
					{
						ballDeltaX = 10;
					}
					else if(nextBallTop >= playerTwoMiddleBot && nextBallBottom <= playerTwoBottom)
					{
						ballDeltaX = 15;
					}
					ballDeltaX *= -1;
					Sound.play("Sound/boing.wav");
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;
		}

		// stuff has moved, tell this JPanel to repaint itself
		repaint();
	}

	/** Paint the game screen. */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (showTitleScreen) {

			/* Show welcome screen */

			// Draw game title and start message
			
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 36));
			ImageIcon imgWellcome = new ImageIcon("Images/wellcome.gif");
			g.drawImage(imgWellcome.getImage(), 0, 0, 500,500, null);
			g.setColor(Color.BLUE);
			g.drawString("Pong Game", 150, 100);
			add(btnStart);
			btnStart.setVisible(true);
			btnStart.setBounds(180, 160, 120, 25);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
			g.setColor(Color.white);
			g.drawString("Press C to Change Ball", 150, 400);
			g.drawString("Press N to Change Name", 150, 440);
			
			
			
			btnStart.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					showTitleScreen = false;
					playing = true;
					Sound.play("Sound/boxing_bell.wav");
				}
			});
			
			
			
			
		} else if (playing) {

			/* Game is playing*/
			ImageIcon imgPlayBackground = new ImageIcon("Images/BG.jpg");
			ImageIcon imgChangeBall =  new ImageIcon(nameChangeBallSetting); 
			g.drawImage(imgPlayBackground.getImage(), 0, 0, 500,500, null);

			btnStart.setVisible(false);
			// set the coordinate limit
			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			// draw dashed line down center
			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.drawLine(250, lineY, 250, lineY + 25);
			}

			// draw "goal lines" on each side
			g.setColor(Color.green);
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());

			// draw the scores
			g.setColor(Color.white);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 36));
			g.drawString(nameUser1 , 50, 50);
			g.drawString(nameUser2, 280, 50);
			g.drawString(String.valueOf(playerOneScore), 100, 100); // Player 1
																	// score
			g.drawString(String.valueOf(playerTwoScore), 350, 100); // Player 2
																	// score

			// draw the ball
			g.setColor(Color.RED);
			
			//g.fillOval(ballX, ballY, diameter, diameter);
			g.drawImage(imgChangeBall.getImage(), ballX, ballY, diameter, diameter, null);
			// draw the paddles
			g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
			g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
		} else if (gameOver) {

			/* Show End game screen with winner name and score */

			// Draw scores
			// TODO Set Blue color
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 36));
			
			g.setColor(Color.white);
			g.drawString(nameUser1 , 50, 50);
			g.drawString(nameUser2, 280, 50);
			g.drawString(String.valueOf(playerOneScore), 100, 100);
			g.drawString(String.valueOf(playerTwoScore), 350, 100);

			// Draw the winner name
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 36));
			if (playerOneScore > playerTwoScore) {
				g.drawString(nameUser1 +" Wins!", 115, 200);
				g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
				g.drawString("Press Space to restart", 135, 250);
			} else {
				g.drawString(nameUser2 +" Wins!", 115, 200);
				g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
				g.drawString("Press Space to restart", 120, 250);
			}

			// Draw Restart message
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
			// TODO Draw a restart message
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (showTitleScreen) {
			/* su kien cho chanage ball setting*/
			if (e.getKeyCode() == KeyEvent.VK_C){
				ChangeBallSetting w = new ChangeBallSetting();
				w.setLocationRelativeTo(PongPanel.this);
				w.setVisible(true);
				Settings s = w.getSettings();
				System.out.println("After open window");
				if (w.dialogResult == MyDialogResult.YES
						&& s.getBall1() != null) {

					System.out.println("Ball Setting: Neptune");
					nameChangeBallSetting = s.getBall1();

				} else if (w.dialogResult == MyDialogResult.YES
						&& s.getBall2() != null) {

					System.out.println("Ball Setting: Football");
					nameChangeBallSetting = s.getBall2();

				} else if (w.dialogResult == MyDialogResult.YES
						&& s.getBall3() != null) {

					System.out.println("Ball Setting: Tenis Ball");
					nameChangeBallSetting = s.getBall3();

				} else {
					System.out.println("User chose to cancel");
				}
				/*
				else {
					System.out.println("User chose to cancel");
				}
				*/
			}
			/* su kien cho chanage user name setting*/
			else if (e.getKeyCode() == KeyEvent.VK_N){
				ChangeNameSetting w = new ChangeNameSetting();
				w.setLocationRelativeTo(PongPanel.this);
				w.setVisible(true);
				Settings s = w.getSetings();
				System.out.println("After open window");

				// Stop and wait for user input

				if (w.dialogResult == MyDialogResult.YES) {
					System.out.printf(
							"User settings: \n Username1: %s \n Username2: %s",
							s.getUserName1(), s.getUserName2());
					nameUser1 = s.getUserName1();
					nameUser2 = s.getUserName2();
				} else {
					System.out.println("User chose to cancel");
				}
			}
				
		} else if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			}
		} else if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			gameOver = false;
			showTitleScreen = true;
			playerOneScore = 0;
			playerTwoScore = 0;
			playerOneY = 250;
			playerTwoY = 250;
			ballX = 225;
			ballY = 225;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			wPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			sPressed = false;
		}
	}
}

		


