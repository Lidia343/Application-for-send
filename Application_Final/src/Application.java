
import org.eclipse.swt.SWT;

/**
 * Главный класс приложения. Необходим для создания точки входа в программу. 
 * Создаёт объект класса WindowGraphics и вызывает его метод createWindow(), создающий окно для взаимодействия с пользователем.
 *
 */

public class Application {   
		
		private Server server; 
		private Client client;
		private ErrorChecker errorChecker;
		private WindowGraphics windowGraphics;
		/**
		 * Слушатель класса WindowGraphics. Предназначен для определения готовности отправить файл (метод readyToSend())
		 *  и определения окончания попытки отправить файл на сервер (метод finished()).
		 */
		private WindowGraphicsListener windowGraphicsListener = new WindowGraphicsListener() {
			@Override
			public void readyToSend() {
				System.out.println ("Ready to send.\n");
				server = new Server();
				server.setPort(windowGraphics.getPort());
				try {
					server.start(); //Запуск сервера в отдельном потоке
				} catch (Exception e) {
					windowGraphics.createMessageBox(SWT.ERROR, e.getMessage());
				}
			}
			
			@Override
			public void finished() {
				System.out.println ("Finished.\n");
			}
		};
		
		/**
		 * Главный метод main() (точка входа в программу)
		 */
		public static void main (String[] args) { 
			
			new Application().start(); //Передача управления методу start()
		}
		
		/**
		 * Метод предназначен для инициализации объектов классов Сlient, ErrorChecker и WindowGraphics соответственно,
		 * а также для добавления слушателя класса WindowGraphics и вызова метода для создания окна для взаимодействия с пользователем.
		 */
		public void start() {
			
			client = new Client();
			errorChecker = new ErrorChecker();
			windowGraphics = new WindowGraphics(client, errorChecker); 
			
			windowGraphics.addWindowGraphicsListener(windowGraphicsListener);
			windowGraphics.createWindow();
		}
}

