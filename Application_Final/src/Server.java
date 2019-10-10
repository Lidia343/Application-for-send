import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Класс предназначен для реализации сервера, который ожидает, пока клиент свяжется с ним.
 * На сервер передаётся файл, выбранный пользователем.
 */

public class Server extends Thread{  //Класс наследуется от Thread для возможности создания отдельного потока для работы сервера
	
	private static Socket clientSocket; //Сокет клиента
	private static ServerSocket server; //Сокет сервера
	private int port; //Порт, по которому будет установлено соединение
	
	/**
	 * Конструктор класса Server.
	 */
	public Server () { 
	}
	
	/**
	 *Метод для запуска сервера
	 */
	@Override
	public void run () {
		try { //Блок try, необходимый для записи в него кода, который необходимо проверить на наличие исключений
			
			server = new ServerSocket(port); //Установка порта для соединения
			clientSocket = server.accept(); //Сервер ожидает, пока с ним свяжется клиент
			try {
				InputStream in = clientSocket.getInputStream(); 
				DataInputStream din = new DataInputStream(in); //Создание входного потока от клиента
				//Далее аналогично действиям с клиентом:
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
				clientSocket.close(); //Закрытие сокета клиента
			}
			
		} catch (IOException e){
			System.err.println(e);
		}
		finally {
			try {
				server.close(); //Закрытие сокета сервера
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Метод устанавливает порт для сервера.
	 * @param port - порт
	 */
	public void setPort (int port) {
		this.port = port;
	}
}