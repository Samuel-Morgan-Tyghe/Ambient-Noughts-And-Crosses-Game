package TicTacToe;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateGame implements ActionListener {

	Icon hoverIcon;
	Icon hoverIconwinner;
	Icon WinnerIcon;
	String seticon;
	int RedH = 0; // High score red
	int YellowH = 0; // High score yellow
	int turn = 10;

	JFrame f = new JFrame("Ambient Tic Tac Toe");

	JButton restartButton = new JButton("Restart");
	JButton viewerButton = new JButton("Viewer");
	JLabel Highscore = new JLabel("Red " + RedH + " Yellow " + YellowH);
	JButton[] tileButton = new JButton[10];

//	Get Image to Image Icon (gif's can be difficult to resize without breaking image sequence)
//	Solution: Resize Gif's externally ( java isn't an image editor Sam 🤦 )

//	Example of old code:
//	Icon redIcon = new ImageIcon("/Users/administrator/Downloads/giphy (5).gif");

//	Images are now Embedded into source file, use getResource to ascertain them.

	Icon yellowIcon = new ImageIcon(getClass().getResource("/yellow.gif"));
	Icon winnerRed = new ImageIcon(getClass().getResource("/RedWinner.gif"));
	Icon winnerYellow = new ImageIcon(getClass().getResource("/YellowWinner.gif"));
	Icon plain = new ImageIcon(getClass().getResource("/plain.gif"));
	Icon redIcon = new ImageIcon(getClass().getResource("/red.gif"));

//	 Above icons would be compressed to example below except tiles need to alternate between icons
	JLabel winnerConfetti = new JLabel(new ImageIcon(getClass().getResource("/Confetti.gif")));
//........................................................................................
//	Get Sound Resources in a String array to cycle through
//	I tried embedding sounds with .getresource and others (input stream, buffer),
//	I got to to the stage of URL.toString() but the address doesn't point to the file. ༼ ಠل͟ಠ༽
//	Response: this gives me a chance to try embedding the sounds later in executable .jar using eclipse export function

//	Update: Tried exporting (extract/package resources into .jar ) and it failed to work,
//	To distribute I will have to try Embedding it again

//	EXAMPLE OF OLD CODE: InputStream A3 = getClass().getResourceAsStream("/A3_04.wav"); ( Needed to cast to AudioInputStream,and bypass String (file url))
//	Main problem was IO exception error when handling AudioSystem.getAudioInputStream one solution
//	would be to throw catch and try

//	Alternatively; place in playSound() Three benefits to This choice:
//	➊ Makes Code more organised - All playsound code is self contained
//	➋ Reduces the Amount of lines written
//	➌ Enables me to alter the order of sounds with less faffing around
	// ........................................................................................

	public CreateGame() {

//		Reset icon ( Red always starts)
		hoverIcon = redIcon;

//		Create and set 9 buttons (It's more efficient to create a Button Array and iterate) 
		for (int i = 0; i < 9; i++) {

			tileButton[i] = new JButton(Integer.toString(i));
			tileButton[i].addActionListener(this);
			tileButton[i].setVisible(true);
			tileButton[i].setIcon(plain);
			f.getContentPane().add(tileButton[i]);
			tileButton[i].setRolloverIcon(hoverIcon);
			tileButton[i].setPressedIcon(hoverIcon);
			tileButton[i].setRolloverEnabled(true);

		}

		restartButton.addActionListener(new Restart());
		viewerButton.addActionListener(new Repaint());

//		tileButton Layout 

		tileButton[0].setBounds(50, 100, 100, 100);
		tileButton[1].setBounds(150, 100, 100, 100);
		tileButton[2].setBounds(250, 100, 100, 100);
		tileButton[3].setBounds(50, 200, 100, 100);
		tileButton[4].setBounds(150, 200, 100, 100);
		tileButton[5].setBounds(250, 200, 100, 100);
		tileButton[6].setBounds(50, 300, 100, 100);
		tileButton[7].setBounds(150, 300, 100, 100);
		tileButton[8].setBounds(250, 300, 100, 100);

		Highscore.setBounds(50, 399, 100, 50);
		restartButton.setBounds(250, 413, 88, 25);
		viewerButton.setBounds(162, 413, 80, 25);
		winnerConfetti.setBounds(0, 0, 401, 500);

		f.getContentPane().add(Highscore);
		f.getContentPane().add(restartButton);
		f.getContentPane().add(viewerButton);
		f.getContentPane().add(winnerConfetti);
// if i use .add winnerConfetti on action listener , it places it behind everything. 
//		Better to toggle visibility + easier to edit.
		winnerConfetti.setVisible(false);

		f.getContentPane().setFont(new Font("AppleGothic", Font.PLAIN, 13));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(401, 500);
		f.getContentPane().setLayout(null);
		f.setVisible(true);
		f.setResizable(false);

//		call MyDrawPanel to create a gradient background
		MyDrawPanel gradientBackground = new MyDrawPanel();
		gradientBackground.setBounds(0, 0, 401, 500);
		f.getContentPane().add(gradientBackground);

	}

//	Call this later when A tile button is pressed to play ambient sounds
	public void playSound() {
//		 Load sounds
		try {
			AudioInputStream A = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/A3_04.wav"));
			AudioInputStream Bb = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Bb4_01.wav"));
			AudioInputStream C = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/C5_01.wav"));
			AudioInputStream D = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/D4_01.wav"));
			AudioInputStream E = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/E4_01.wav"));
			AudioInputStream F = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/F4_01.wav"));
			AudioInputStream G = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/G4_01.wav"));

//		Randomise which pair of sounds play , and a rule to increase chances of (IR IR++)  melodically it pairs well
			int IR = (int) (Math.random() * 6);
			int IR2 = (int) (Math.random() * 6);
			if (IR == IR2) {
				IR2 = +2;
				if (IR2 > 6) {
					IR2 = 0;
				}
			}
//          log in array
			AudioInputStream[] Sounds = { C, D, E, F, G, A, Bb };

			Clip clip = AudioSystem.getClip();
			Clip clip2 = AudioSystem.getClip();

			clip.open(Sounds[IR]);
			clip2.open(Sounds[IR2]);

			clip.start();
			clip2.start();

		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

//	I need to research if this is the cleanest way to code 
//	( In essence the main calls the Create Game method, which includes the whole game (including other methods))

	public static void main(String[] args) {

		new CreateGame();

	}

//	when button TileButton is pressed 
//  it listens and defines which button is being pressed by using .getsource() / "execute"
//  I could have made separate action listener functions but that would not have been efficient
//	remove all interactive functions
//	play sound

	@Override
	public void actionPerformed(ActionEvent e) {
		f.repaint();

		Object execute = e.getSource();
		AbstractButton executeButton = (AbstractButton) execute;

//      execute (object) into executeButton (AbstractButton);
//		prevents having to cast "(AbstractButton)" every time you call the object
//		AbstractButton IS A Object , Polymorphism? its definitely Class Hierarchy 
//		JButton IS A AbstractButton , lets leave it AbstractButton in case we add a JToggleButton
//		(although we could implement an additional actionlistner to deal with that).

		executeButton.removeActionListener(this); // Can't press tile twice

		playSound();

//		Decide whose turn, and define which icon and token(text) to use

		if (turn % 2 == 0) {

			executeButton.setIcon(redIcon);
			executeButton.setText("Red");

			hoverIcon = yellowIcon; // prepare what hover icon to use for next player
		} else {

			executeButton.setIcon(yellowIcon);
			executeButton.setText("Yellow");

			hoverIcon = redIcon;

		}
		turn++;

		tileButtonCheck();

		checkGame();
		

	}

	private void tileButtonCheck() {
		// iterate through what has and hasn't been pressed
		for (int i = 0; i < 9; i++) {

			if (tileButton[i].getText() == "Red") {
				tileButton[i].setRolloverIcon(redIcon);
				tileButton[i].setPressedIcon(redIcon);

			} else if (tileButton[i].getText() == "Yellow") {

				tileButton[i].setRolloverIcon(yellowIcon);
				tileButton[i].setPressedIcon(yellowIcon);

			} else {

				tileButton[i].setRolloverIcon(hoverIcon);
				tileButton[i].setPressedIcon(hoverIcon);
			}
		}
		// TODO Auto-generated method stub

	}

	private void checkGame() {

//		I am sure there's a more efficient way to do this. If anyone knows please let me know.
//		3 iterations ; check horizontal ( first row,second row, third row),
//		check Vertical and check diagonal(+reverse)
//		If any three in a row occur call newHighscore()

		int j = 0;
		int l = 1;
		int k = 2;
		for (int no = 0; no < 3; no++) {

			if ((tileButton[j].getText() == tileButton[k].getText())
					&& tileButton[k].getText() == tileButton[l].getText()) {
				newHighscore();
				return; // use return to prevent multiple scoring.

			}

			j = j + 3;
			k = k + 3;
			l = l + 3;
		}

		j = 0;
		l = 3;
		k = 6;

		for (int no = 0; no < 3; no++) {

			if ((tileButton[j].getText() == tileButton[k].getText())
					&& tileButton[k].getText() == tileButton[l].getText()) {
				newHighscore();
				return;

			}
			j++;
			k++;
			l++;

		}

		j = 0;
		l = 4;
		k = 8;

		for (int no = 0; no < 2; no++) {

			if ((tileButton[j].getText() == tileButton[k].getText())
					&& tileButton[k].getText() == tileButton[l].getText()) {
				newHighscore();
				return;

			}
			j = j + 2;
			k = k - 2;

		}

	}

	public void newHighscore() {

//		Decide which winner Aesthetics to activate
	
		winnerConfetti.setVisible(true);

		if (hoverIcon == yellowIcon) {
			RedH++;
			hoverIconwinner = redIcon;
			WinnerIcon = winnerRed;

		} else {
			YellowH++;
			hoverIconwinner = yellowIcon;
			WinnerIcon = winnerYellow;
		}
		Highscore.setText("Red " + RedH + " Yellow " + YellowH);
		try {
			Thread.sleep(1000); // gives player chance to view how they won
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 9; i++) { // iterate to display winners icon on every tile

			tileButton[i].setRolloverIcon(WinnerIcon);
			tileButton[i].setIcon(WinnerIcon);
			tileButton[i].setPressedIcon(WinnerIcon);
			tileButton[i].removeActionListener(this); // without this it would continue running the game

		}

	}

//	we now have a finished game , Restart() restarts the game ( this function can be called at any time)
//	resets all tile functions and reference Variables ( except High score Total )

//	Question:will dispose() then call CreateGame() work? 
//	Response: save high score to Text file and it will work (resets text file(overwrite function) class Instance Variable)

	public void Restart() {

		winnerConfetti.setVisible(false);

//		clear all recorded buttons that have been pressed

		hoverIcon = redIcon;

		for (int i = 0; i < 9; i++) {
			tileButton[i].setText(Integer.toString(i));
			tileButton[i].removeActionListener(this);
			tileButton[i].setIcon(plain);
			tileButton[i].setRolloverIcon(plain);
			tileButton[i].addActionListener(this);
			tileButton[i].setRolloverIcon(hoverIcon);
			tileButton[i].setPressedIcon(hoverIcon);

		}
		turn = 10;

	}

//	Separate action listener for Restart button

	class Restart implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			f.repaint();
			Restart();
		}

	}

//	Separate action listener for Background colour button

	class Repaint implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			f.repaint();
			winnerConfetti.setVisible(false);

		}

	}

}

// 390 lines of code is a-lot, Next project, extract portions of this codes functionality into other small classes.
