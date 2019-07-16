package DeliverymanX;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Player {

	static String name;
	static String saveId;

	public static String getName() {
		return name;
	}

	public void setName(String name) {
		Player.name = name;
	}

	public static String getSaveId() {
		return saveId;
	}

	public static void setSaveId(String saveId) {
		Player.saveId = saveId;
	}

	public Player(String name, String saveId) {
		this.name = name;
		this.saveId = saveId;
	}

	@Override
	public String toString() {
		return name + ";" + saveId;
	}

	public static void PlayerName(String name) {
		Scanner nombre = new Scanner(System.in);
		Player.name = nombre.nextLine();

		try {
			validar(Player.name);
			System.out.println("Validando nombre...");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			System.out.println("");
			System.out.println("Nombre Validado satisfactoriamente.");
		}
	}

	public static void validar(String name) throws GameException {
		
		if (name.equalsIgnoreCase("Jaime")) {
			Player.name = "Juan";
			throw new GameException("El Profesor Jaime esta durmiendo, en su lugar Juan esta dispuesto a jugar.");
		}
		if (name.equalsIgnoreCase("Pablo")) {
			Player.name = "Juanito";
			throw new GameException(
					"El Profesor Pablo encuentra el juego muy rasca, pero Juanito esta dispuesto a sacrificarse.");
		} 
		if (name.equalsIgnoreCase("Challa")||name.equalsIgnoreCase("Rasca")) {
			Player.name="Cheater";
			throw new GameException("CHEATCODE ACCEPTED!");
			
			
		}
	}

	public static void savePlayer(String idLevel) {

		String tmpdir = System.getProperty("java.io.tmpdir");
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(tmpdir + "\\" + name + ".txt"))) {
			bw.write(name + ";" + saveId);
			bw.newLine();
		} catch (IOException e) {
			System.out.println("ERROR AL GRABAR");
			e.printStackTrace();
		}
	}

	public static void loadSave(String Player) throws FileNotFoundException {
		
		

		String tmpdir = System.getProperty("java.io.tmpdir");
		if (name.equalsIgnoreCase("Cheater")) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(tmpdir + "\\Cheater.txt"))) {
			bw.write("Cheater" + ";" + "65");
			bw.newLine();
		} catch (IOException e) {
			System.out.println("ERROR AL GRABAR");
			e.printStackTrace();
		}}
		File ArchivoLeer = new File(tmpdir + "\\" + name + ".txt");
		
		if (ArchivoLeer.exists()) {
			Scanner EntrarArchivo = new Scanner(ArchivoLeer);
			Player player = null;

			while (EntrarArchivo.hasNext()) {
				String line = EntrarArchivo.nextLine();
				String[] values = line.split(";");
				player = new Player(name, saveId);
				player.setName(values[0]);
				player.setSaveId(values[1]);
				if (player.getName().equalsIgnoreCase(name)) {
					saveId = player.getSaveId();
				}
			}

			EntrarArchivo.close();
		
			System.out.println("Tu partida se encuentra guardada en la etapa " + saveId);
			

		} else {
			System.out.println(name + "...el juego comienza...");

			saveId = "1";
		}
	}

}
