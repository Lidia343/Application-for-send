import java.net.Socket;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * ����� ������������ ��� ���������� �������, ������� ������ ������������ � �������
 * � �������� ��� ��������� ����.
 */

public class Client {

	private static Socket clientSocket; //����� �������
	private String iP; //IP-����� �������, � �������� ������ ������������ ������
	private int port;  //����, �� �������� ����� ����������� ����������
	private String fileName; //��� ���������� �����
	private String errorMessage; //����������, � ������� ������������ ��������� �� ������, ���� ���� �� ������
	
	/**
	 * ����������� ������ Client.
	 */
	public Client () { 
	
	}
	
	/**
	 * �����, ����� ������ �������� ������ ����� �������� ���������� ���������� � ��������. 
	 */
	public void run() {  
		try {
		
			clientSocket = new Socket (iP, port);  //������� ���������� ���������� �� ���������� IP-������ � �����		
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream()); //�������� ��������� ������ out
			File f = new File (fileName); //�������� ������� ������ File 
						
			FileInputStream in = new FileInputStream(f); //�������� �������� ������	in ��� ������ �� ����� f
			
			out.writeUTF(f.getName()); //������ � �������� ����� ����� �����
							
			byte [] buffer = new byte[64*1024]; //�������� ������� ������ (������)
			int count; //���������� ���������� � ����� ������
						
			while((count = in.read(buffer)) != -1){ //���������� � buffer ����������� ���������� ������ �� �������� ������ in.
				out.write(buffer, 0, count);   //������� buffer � ����� write() ��������� ������, ������� � 0 ������� � �� count (���������� ���������� � ����� ������)
			}
			/*���� �� ������� ������ ��� ������ �������� ������ ������, ��� ������� �����, count 
			 ������������� -1 (��� �������� ����� �����), � � ����� ������������ ������ ��������� 
			 �� �������� ������ ����� (����� �� �������� � ������, ��� �� ���� ������������, �������� ������� �����).
			 ����� ���� ��� ����� ������������ � �������� ����� out, ����� ���� ������ �������������.*/
						
			out.flush(); //"������������" ��������� ������
			in.close();	 //�������� �������� ������	
					
			clientSocket.close(); //�������� ������ �������
			
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
	}
	
	/**
	 * �����, ������������ ��������� �� ������.
	 */
	public String getErrorMessage() { 
		return errorMessage;
	}
	
	/**
	 * �����, ��������������� ������������ �������.
	 * @param iP - IP-�����
	 * @param port - ����
	 * @param fileName - ��� ����� ��� �������� �� ������
	 */
	public void setConfig (String iP, int port, String fileName){
		
		errorMessage = "";
		this.iP = iP;
		this.port = port;
		this.fileName = fileName;
	}
}
	

	

		
	

