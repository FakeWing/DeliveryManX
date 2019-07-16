package DeliverymanX;

import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class FileManager {

	public static void Game() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
		
		final String FINAL = "99";
		Level.Button();
		Level.mainScreen();
		Player.PlayerName(Player.getName());
		Level.decifrar();
		Player.loadSave(Player.getName());
		String firstLevel = Player.getSaveId();
		Level level = null;
		String choiceId = Player.getSaveId();
		String choiceKeyboard;
		String currentId = null;

		do {
			boolean validOption = false;
			if (choiceId == null) {
				choiceId = "1";
			}
			level = Level.getLine(null == level ? firstLevel : choiceId);
			System.out.println((Photo.readAscii(level.getId())).getTextPhoto());
			System.out.println(" ");
			System.out.println(level);
			System.out.println(" ");
			
			currentId = choiceId;

			for (Option nextLevel : level.getNextLevel()) {
				System.out.println(nextLevel.getChoice() + nextLevel.getText());
			}

			Scanner scannerChoice = new Scanner(System.in);
			choiceKeyboard = scannerChoice.nextLine();
			
			for (Option option : level.getNextLevel()) {
				if (option.getChoice().equals(choiceKeyboard)) {
					validOption = true;
					choiceId = option.getId();
					if (currentId == null){
						System.out.println("AUTOGUARDADO... en la etapa " + Player.getSaveId());
					} else
						System.out.println("AUTOGUARDADO... en la etapa " + currentId);
					Player.setSaveId(currentId);
					Player.savePlayer(currentId);
				}
			}
			if (!validOption) {
				System.out.println("_______________________");
				System.out.println("INVALID OPTION");
				System.out.println("PLEASE PICK A NEW ONE");
				System.out.println("_______________________");
				choiceId = currentId;
				continue;
			}
		} while (!FINAL.equals(choiceId));
		System.out.println("COMPRA EL DLC!!!!");
		Level.showEnding();
	}

}
