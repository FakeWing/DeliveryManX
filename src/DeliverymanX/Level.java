package DeliverymanX;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

public class Level {

	private String id;
	private String text1;
	private String text2;
	private List<Option> nextLevel;
	static boolean onoff;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public List<Option> getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(List<Option> nextLevel) {
		this.nextLevel = nextLevel;
	}

	@Override
	public String toString() {
		return text1 + "\n" + text2;
	}

	public static void mainScreen() throws FileNotFoundException {

		Scanner scanner = new Scanner(
				Thread.currentThread().getContextClassLoader().getResourceAsStream("mainscreen.txt"));
		String linea = "";
		while (scanner.hasNext()) {
			linea = scanner.nextLine();
			System.out.println(linea);
		}
	}

	static Level getLine(String idLevel) {
		String tmpdir = System.getProperty("java.io.tmpdir");
		Level level = null;

		try (Scanner scanner = new Scanner(new File(tmpdir + "DefinitivamenteNoEsLaHistoria.txt"))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] values = line.split(";");
				level = new Level();
				level.setId(values[0]);
				level.setText1(values[1]);
				level.setText2(values[2]);
				List nextLevels = new ArrayList();

				for (int i = 3; i < values.length; i++) {
					String[] option = values[i].split("%");
					nextLevels.add(new Option(option[0], option[2], option[1]));
				}
				level.setNextLevel(nextLevels);
				if (level.getId().equals(idLevel))
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return level;
	}

	public static void showEnding() throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException {
		
		BufferedImage img = ImageIO
				.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RASCA.png"));
		ImageIcon icon = new ImageIcon(img);
		JFrame frame = new JFrame();
		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		frame.getContentPane().add(lbl);
		frame.setSize(icon.getIconWidth(), icon.getIconHeight());
		frame.setLocation(300, 120);
		frame.setState(JFrame.NORMAL);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		frame.addComponentListener(Music());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static ComponentListener Music() {

		try {
			BufferedInputStream myStream = new BufferedInputStream(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("defeat.wav"));
			AudioInputStream ais = AudioSystem.getAudioInputStream(myStream);
			Clip c = AudioSystem.getClip();
			c.open(ais);
			c.start();
			Thread.sleep((int) (c.getMicrosecondLength() * 0.001));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static void decifrar() throws IOException {
		String str;
		char c;
		int n;
		String tmpdir = System.getProperty("java.io.tmpdir");
		Scanner e = new Scanner(
				Thread.currentThread().getContextClassLoader().getResourceAsStream("KingdomHearts.txt"));
		PrintWriter fs = new PrintWriter(tmpdir + "DefinitivamenteNoEsLaHistoria.txt", "utf-8");

		while (e.hasNext()) {
			str = e.nextLine();
			StringTokenizer st = new StringTokenizer(str, ";");
			while (st.hasMoreTokens()) {
				n = (Integer.parseInt(st.nextToken()) - 5);
				c = (char) n;
				fs.print(c);
			}
			fs.println("");
		}
		e.close();
		fs.close();
	}

	public static void Button() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		JFrame frame1 = new JFrame();
		frame1.setSize(300, 300);
		JToggleButton button = new JToggleButton("BACKGROUND MUSIC OFF/ON");
		frame1.add(button);
		BufferedInputStream myStream = new BufferedInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("BGM.wav"));
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(myStream);
		Clip clip = AudioSystem.getClip();

		clip.open(audioInputStream);
		clip.start();
		
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {

					clip.close();
					clip.stop();
					System.out.println("MUSIC OFF");

				} else {
					BufferedInputStream myStream = new BufferedInputStream(
							Thread.currentThread().getContextClassLoader().getResourceAsStream("BGM.wav"));
					AudioInputStream audioInputStream = null;
					try {
						audioInputStream = AudioSystem.getAudioInputStream(myStream);
					} catch (UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						clip.open(audioInputStream);
					} catch (LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					clip.start();
					System.out.println("MUSIC ON");
				}
			}
		};
		button.addItemListener(itemListener);
		frame1.setVisible(true);
		
	}

}
