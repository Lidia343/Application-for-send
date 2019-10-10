import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ����� ������������ ��� ���������� �������, ������� �������, ���� ������ �������� � ���.
 * �� ������ ��������� ����, ��������� �������������.
 */

public class Server extends Thread{  //����� ����������� �� Thread ��� ����������� �������� ���������� ������ ��� ������ �������
	
	private static Socket clientSocket; //����� �������
	private static ServerSocket server; //����� �������
	private int port; //����, �� �������� ����� ����������� ����������
	
	/**
	 * ����������� ������ Server.
	 */
	public Server () { 
	}
	
	/**
	 *����� ��� ������� �������
	 */
	@Override
	public void run () {
		try { //���� try, ����������� ��� ������ � ���� ����, ������� ���������� ��������� �� ������� ����������
			
			server = new ServerSocket(port); //��������� ����� ��� ����������
			clientSocket = server.accept(); //������ �������, ���� � ��� �������� ������
			try {
				InputStream in = clientSocket.getInputStream(); 
				DataInputStream din = new DataInputStream(in); //�������� �������� ������ �� �������
				//����� ���������� ��������� � ��������:
				String fileName = din.readUTF();
				byte[] buffer = new byte[64*1024];
				FileOutputStream outF = new FileOutputStream(fileName);
				int count;

				while ((count = din.read(buffer)) != -1){              
				outF.write(buffer, 0, count); 
				}
						
				outF.flush();
				outF.close();
			} finally {
				clientSocket.close(); //�������� ������ �������
			}
			
		} catch (IOException e){
			System.err.println(e);
		}
		finally {
			try {
				server.close(); //�������� ������ �������
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ����� ������������� ���� ��� �������.
	 * @param port - ����
	 */
	public void setPort (int port) {
		this.port = port;
	}
}