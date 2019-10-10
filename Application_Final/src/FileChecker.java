
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * ����� ������������ ��� �������� ������������� ���������� �����.
 *
 */

public class FileChecker {
	
	private String fileName; //��� �����
	private String message; //����������, � ������� ����� �������� ���������� �� ������, ���� ��� ��������

	/**
	 * ����������� ������.
	 * @param fileName - ��� �����
	 */
	public FileChecker(String fileName) { 
		this.fileName = fileName;
		message = "";
	}
	
	/**
	 * ����� ��������� ������������� �����.
	 * @return true - ���� ���� ����������, false - �����
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
	 * ����� ���������� ��������� �� ������.
	 */
	public String getMessage() { 
		return message;
	}
}
