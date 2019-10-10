import java.net.Socket;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Класс предназначен для реализации клиента, который должен подключиться к серверу
 * и передать ему выбранный файл.
 */

public class Client {

	private static Socket clientSocket; //Сокет клиента
	private String iP; //IP-адрес сервера, к которому должен подключиться клиент
	private int port;  //Порт, по которому будет установлено соединение
	private String fileName; //Имя выбранного файла
	private String errorMessage; //Переменная, в которую записывается сообщение об ошибке, если файл не найден
	
	/**
	 * Конструктор класса Client.
	 */
	public Client () { 
	
	}
	
	/**
	 * Метод, после вызова которого клиент будет пытаться установить соединнеие с сервером. 
	 */
	public void run() {  
		try {
		
			clientSocket = new Socket (iP, port);  //Попытка установить соединение по переданным IP-адресу и порту		
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream()); //Создание выходного потока out
			File f = new File (fileName); //Создание объекта класса File 
						
			FileInputStream in = new FileInputStream(f); //Создание входного потока	in для чтения из файла f
			
			out.writeUTF(f.getName()); //Запись в выходной поток имени файла
							
			byte [] buffer = new byte[64*1024]; //Создание массива байтов (буфера)
			int count; //Количество записанных в поток байтов
						
			while((count = in.read(buffer)) != -1){ //Записываем в buffer определённое количество байтов из входного потока in.
				out.write(buffer, 0, count);   //Передаём buffer в метод write() выходного потока, начиная с 0 позиции и до count (количества записанных в поток байтов)
			}
			/*Если во входном потоке для записи осталось меньше байтов, чем вмещает буфер, count 
			 присваивается -1 (это означает конец файла), а в буфер записываются только считанные 
			 из входного потока байты (тогда на позициях в буфере, что не были использованы, остаются прежние байты).
			 Далее лишь эти байты записываются в выходной поток out, после чего запись заканчивается.*/
						
			out.flush(); //"Выталкивание" выходного потока
			in.close();	 //Закрытие входного потока	
					
			clientSocket.close(); //Закрытие сокета клиента
			
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
	}
	
	/**
	 * Метод, возвращающий сообщение об ошибке.
	 */
	public String getErrorMessage() { 
		return errorMessage;
	}
	
	/**
	 * Метод, устанавливающий конфигурацию клиента.
	 * @param iP - IP-адрес
	 * @param port - порт
	 * @param fileName - имя файла для отправки на сервер
	 */
	public void setConfig (String iP, int port, String fileName){
		
		errorMessage = "";
		this.iP = iP;
		this.port = port;
		this.fileName = fileName;
	}
}
	

	

		
	

