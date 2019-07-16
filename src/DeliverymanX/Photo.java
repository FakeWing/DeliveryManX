package DeliverymanX;
import java.util.Scanner;

public class Photo {

	private String id;
	private String textPhoto;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTextPhoto() {
		return textPhoto;
	}

	public void setTextPhoto(String textPhoto) {
		this.textPhoto = textPhoto;
	}

	@Override
	public String toString() {
		return textPhoto;
	}

	static Photo readAscii(String idLevel) {

		try (Scanner scan = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("Photo.txt")).useDelimiter("###")) {

			while (scan.hasNext()) {
				String values[] = scan.next().split("k");
				Photo photo = new Photo();
				photo.setId(values[0].trim());
				photo.setTextPhoto(values[1]);
				if (photo.getId().equals(idLevel)) {
					return photo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
