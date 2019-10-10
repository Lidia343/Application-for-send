
import org.eclipse.swt.SWT;

/**
 * ������� ����� ����������. ��������� ��� �������� ����� ����� � ���������. 
 * ������ ������ ������ WindowGraphics � �������� ��� ����� createWindow(), ��������� ���� ��� �������������� � �������������.
 *
 */

public class Application {   
		
		private Server server; 
		private Client client;
		private ErrorChecker errorChecker;
		private WindowGraphics windowGraphics;
		/**
		 * ��������� ������ WindowGraphics. ������������ ��� ����������� ���������� ��������� ���� (����� readyToSend())
		 *  � ����������� ��������� ������� ��������� ���� �� ������ (����� finished()).
		 */
		private WindowGraphicsListener windowGraphicsListener = new WindowGraphicsListener() {
			@Override
			public void readyToSend() {
				System.out.println ("Ready to send.\n");
				server = new Server();
				server.setPort(windowGraphics.getPort());
				try {
					server.start(); //������ ������� � ��������� ������
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
		 * ������� ����� main() (����� ����� � ���������)
		 */
		public static void main (String[] args) { 
			
			new Application().start(); //�������� ���������� ������ start()
		}
		
		/**
		 * ����� ������������ ��� ������������� �������� ������� �lient, ErrorChecker � WindowGraphics ��������������,
		 * � ����� ��� ���������� ��������� ������ WindowGraphics � ������ ������ ��� �������� ���� ��� �������������� � �������������.
		 */
		public void start() {
			
			client = new Client();
			errorChecker = new ErrorChecker();
			windowGraphics = new WindowGraphics(client, errorChecker); 
			
			windowGraphics.addWindowGraphicsListener(windowGraphicsListener);
			windowGraphics.createWindow();
		}
}

