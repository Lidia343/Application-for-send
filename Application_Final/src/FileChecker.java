
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Класс предназначен для проверки существования выбранного файла.
 *
 */

public class FileChecker {
	
	private String fileName; //Имя файла
	private String message; //Переменная, в которую будет записана информация об ошибке, если она появится

	/**
	 * Конструктор класса.
	 * @param fileName - имя файла
	 */
	public FileChecker(String fileName) { 
		this.fileName = fileName;
		message = "";
	}
	
	/**
	 * Метод проверяет существование файла.
	 * @return true - если файл существует, false - иначе
	 * @throws IOException
	 */
	public boolean testFile() throws IOException { 
		
		try {	
				File f = new File (fileName); 
				FileInputStream in = new FileInputStream(f); 
				in.close();
				
		} catch (IOException e){ 
			message = e.getMessage();
			return false;
		} 
		
		return true;
	}
	
	/**
	 * Метод возвращает сообщение об ошибке.
	 */
	public String getMessage() { 
		return message;
	}
}
