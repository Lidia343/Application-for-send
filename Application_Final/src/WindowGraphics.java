
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import java.io.InputStream;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionAdapter;



/**
 * ����� ������������ ��� �������� ���������� ��������� � ���������� �������������� � ������������� ����� ������ ���������.
 */

public class WindowGraphics {
	
	private Display display; //������ ������ Display
	private Shell shell;  //���� shell
	private Color colorFore; //���� ������
	private Color colorBack; //���� ����
	private Font font; //�����
	private Text textFile; //��������� ���� ��� ����� �����
	private Text textIP; //��������� ���� ��� IP-������
	private Text textPort; //��������� ���� ��� �����
	private Client client; //������ ������ Client
	private ErrorChecker errorChecker; //������ ������ ErrorChecker
	private int port; //����
	private WindowGraphicsListener windowGraphicsListener; //��������� ������ WindowGraphics
	
	/**
	 * ����������� ������ WindowGraphics.
	 */
	public WindowGraphics() {
		display = new Display(); 
		shell = new Shell (display); 
		port = 8000; //�������� ����� �� ���������
	}
	
	
	/**
	 * ����������� ������ WindowGraphics.
	 * @param client - ������
	 * @param errorChecker - ������ ������ errorChecker
	 */
	public WindowGraphics (Client client, ErrorChecker errorChecker) {
		
		display = new Display(); 
		shell = new Shell (display); 
		this.client = client;
		this.errorChecker = errorChecker;
		port = 8000; //�������� ����� �� ���������
	}
		
	// ----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * ����� ��� �������� � ������������� ����������� �����������.
	 * 
	 */
	public void createWindow() {
		
		colorFore = new Color (display, 50, 50, 10);
		colorBack = new Color (display, 245, 245, 240);
		font = new Font(display, "Courier New", 13, SWT.NORMAL);
		
		setImage();
		
		createGridLayout(shell); 
		
		Label labelFile = new Label(shell, SWT.NONE);
		GridData gridData = createGridData (3, 2, false, 0, 0, 0, 0); 
		labelFile.setLayoutData(gridData); 
		setLabel (labelFile, "����");
		
		//�������� ��� ������������ ���� ��� ����� �������� ����� � ������ "�����...":
		Composite composite1 = new Composite (shell, SWT.BORDER);
		createGridLayout (composite1, 2, 10);
		gridData = createGridData (4, 0, true, 0, 0, 0, 0);
		composite1.setLayoutData(gridData);
		
		textFile = new Text (composite1, SWT.MULTI | SWT.BORDER);
		gridData = createGridData (4, 2, true, 0, 0, 0, 0);
		textFile.setLayoutData(gridData);
		setText (textFile, false);
		
		Button buttonView = new Button (composite1, SWT.PUSH);
		gridData = createGridData (2, 2, false, 100, 0, 0, 0);
		buttonView.setLayoutData(gridData);
		setButton(buttonView, "�����...");
		
		Label labelIP = new Label(shell, SWT.NONE);
		gridData = createGridData (3, 2, false, 0, 0, 0, 0);
		labelIP.setLayoutData(gridData);
		setLabel(labelIP, "IP:");
		
		//�������� ��� ������������ ����� ��� ����� IP-������, ����� � ������������ ����� � ��������� "����":
		Composite composite2 = new Composite (shell, SWT.BORDER);
		createGridLayout (composite2, 3, 10);
		gridData = createGridData (4, 0, true, 0, 0, 0, 0);
		composite2.setLayoutData(gridData);
		
		textIP = new Text (composite2, SWT.MULTI | SWT.BORDER);
		gridData = createGridData (4, 2, true, 200, 22, 0, 0);
		textIP.setLayoutData(gridData);
		setText (textIP, true);
	
		Label labelPort = new Label (composite2, SWT.NONE);
		gridData = createGridData (1, 2, false, 0, 0, 135, 0);
		labelPort.setLayoutData(gridData);
		setLabel (labelPort, "����:");
		
		textPort = new Text (composite2, SWT.MULTI | SWT.BORDER);
		gridData = createGridData (4, 2, true, 94, 22, 0, 0);
		textPort.setLayoutData(gridData);
		setText (textPort, true);
		
		Button buttonSend = new Button (shell, SWT.PUSH);
		gridData = createGridData (3, 2, false, 130, 30, 0, 2);
		buttonSend.setLayoutData(gridData);
		setButton (buttonSend, "���������");
		
		buttonView.addSelectionListener(selectionView); 
		
		buttonSend.addSelectionListener(selectionSend); 
		
		shell.pack(); //��������� ������������ ������� ���� ��� ��������� ����������
		shell.open(); //�������� ����
		
		/* ���� �������, ��������������� � shell, �� �����������,
		 * ���� display �� ������������, ���������� �������� ������ ������������������:*/
		while (!shell.isDisposed()) { 
			if (!display.readAndDispatch()) display.sleep(); 
		}
		
		//������������ ��������:
		colorBack.dispose();
		colorFore.dispose();
		font.dispose();
		display.dispose(); 	
	}
	
	//  -----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * ����� ��� ��������� ������ ���� ����������.
	 */
	private void setImage() {
		try {
			InputStream stream = WindowGraphics.class.getResourceAsStream("image.png");
			Image image = new Image (display, stream);
			setShell (shell, "�������� �����", image);
			stream.close();
		} catch (Exception e) {
			createMessageBox (SWT.ERROR, e.getMessage());
			setShell (shell, "�������� �����", null);
		}
	}
	
	/**
	 * ����� ��������� ������� ��� ���������� Shell.
	 * @param s - ���� shell
	 * @param text - ��������� ���� shell
	 * @param image - ������ ���� shell
	 */
	
	private void setShell (Shell s, String text, Image image) { 
		s.setMinimumSize(615, 215); 
		s.setText (text);          
		s.setImage(image);			
		s.setBackground (colorBack);    
	}
	
	/**
	 * �������� ������� �� ����� �� shell ��� ������������ � ��� ����������� �����������.
	 * @param s - ������ ������ Shell
	 */
	private void createGridLayout(Shell s) { 
		
		GridLayout g = new GridLayout();
		g.numColumns = 2; //���������� ��������
		g.marginWidth = 10; //���������� �� ������ � ������� ����� ����
		g.marginHeight = 10; //���������� �� �������� � �������� ����� ����
		g.horizontalSpacing = 5; //���������� ����� ��������� �������� �� �����������
		g.verticalSpacing = 20; //���������� ����� ��������� �������� �� ���������
		s.setLayout(g); //��������� ���� �� ���� shell
	}
	
	/**
	 * ���������� ������ createGridLayout() (��� ���������).
	 * @param c - ��������
	 * @param numColumns - ���������� ��������
	 * @param horizontalSpacing - ���������� ����� ��������� �������� �� �����������
	 */
	private void createGridLayout(Composite c, int numColumns, int horizontalSpacing) {
		
		GridLayout g = new GridLayout();
		g.numColumns = numColumns;
		g.horizontalSpacing = horizontalSpacing;
		c.setLayout(g);
	}
	
	/**
	 * ����� ��� �������� ������� ������ GridData (��� ���������� �������, ���������� � ���������� ������������).
	 * ������������ �������� �������� �������� horAl � grab, ��������� ��������� ����� �������� ��� 0 (����� ����������� �������� �� ���������).
	 * @param horAl - ������������ �� �����������
	 * @param vertAl - ������������ �� ��������� 
	 * @param grab - ����������/������ ������� ���������� ������������ �� ����������� ��� ��������� �������� ����
	 * @param width - ������ ����������
	 * @param height - ������ ����������
	 * @param horInd - ������ �� ����������� �� ���������� ��������� (��� ������� �������� ������������) ���������� � ������
	 * @param horSpan - ���������� �����, ���������� ����������� �� �����������
	 * @return gridData - ������ ������ GridData
	 */
	private GridData createGridData(int horAl, int vertAl, boolean grab, int width, int height, int horInd, int horSpan) {
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = horAl; 
		if (vertAl != 0) gridData.verticalAlignment = vertAl; 
		gridData.grabExcessHorizontalSpace = grab; 
		if (width != 0) gridData.widthHint = width; 
		if (height != 0) gridData.heightHint = height; 
		if (horInd != 0) gridData.horizontalIndent = horInd; 
		if (horSpan != 0) gridData.horizontalSpan = horSpan; 
		return gridData;
	}
	
	/**
	 * ����� ��������� ������� ��� ���������� Label
	 * @param label - ��������� Label
	 * @param text - �����
	 */
	private void setLabel(Label label, String text) {
		
		label.setText(text); 
		label.setBackground(colorBack);
		label.setForeground(colorFore);
		label.setFont(font); 
	}
	
	/**
	 * ����� ��������� ������� ��� ���������� Text.
	 * @param text - ��������� Text
	 * @param editable - ���������������
	 */
	private void setText (Text text, boolean editable) { 
		
		text.setEditable(editable); 
		text.setForeground(colorFore);
		text.setFont(font);
	}
		
	/**
	 * ����� ��������� ������� ��� ���������� Button.
	 * @param button - ��������� Button
	 * @param text - �������� ������
	 */
	private void setButton(Button button, String text) { 
		
		button.setText(text);
		button.setBackground(colorBack);
		button.setForeground(colorFore);
		button.setFont(font);
	}		
	
	// --------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * ��������� ������� ������ "�����..."
	 */
	SelectionAdapter selectionView = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent event) { 
			
			FileDialog fileDialog = new FileDialog (shell, SWT.OPEN); 
			fileDialog.setText("����� �����");
	    	Dialogue dialog = new Dialogue (fileDialog); 	
	    	String fileName = dialog.play();	//�������� ���� ��� ������ ����� � ������������ ����� ���������� ����� ���������� fileName
	    	if (!fileName.contentEquals(""))
	    		textFile.setText(fileName); //������������ ����� �������� ����� ���������� �����, ���� ���� ���� ������ ������ "������"
		}
	 };
	
	 // -------------------------------------------------------------------------------------------------------------------------------------
	 
	 /**
	 * ��������� ������� ������ "���������"
	 */
	SelectionAdapter selectionSend = new SelectionAdapter() {
		 @Override
		 public void widgetSelected(SelectionEvent event) { 
						
			 errorChecker.setConfig(textFile.getText(), textIP.getText(), textPort.getText()); 
			 errorChecker.checkUserInput(); 
			 int messageCode = errorChecker.getMessageCode();
			 String message;
			 			
			 if (messageCode  == SWT.OK) { //���� ���� ��������� ���������:
				 
				 message = "���� ���������";
				 String fileName = textFile.getText();
				 String iP = textIP.getText();
				 port = Integer.parseInt(textPort.getText());
				 
				 if (windowGraphicsListener != null)
					 windowGraphicsListener.readyToSend(); //����� ������ ��������� ������ WindowGraphics
		
			 	 client.setConfig(iP, port, fileName);
			 	 client.run();  //������ �������
			 		
			 	 errorChecker.setConfig(client.getErrorMessage());
			 	 errorChecker.checkSendError();
			 	 messageCode = errorChecker.getMessageCode();
			 	 if (messageCode == SWT.ERROR) {
			 		 message = client.getErrorMessage();
			 	 }
			 	 //�������� ��������� ���� � ���������� ������������ (��� ��������� ������� �� ���������� messageCode � message):
			 	 createMessageBox (messageCode , message);
			 	 
			 	 if (windowGraphicsListener != null)
			 		 windowGraphicsListener.finished(); //����� ������ ��������� ������ WindowGraphics
			 	 
			 } else { //���� ���� �� ���� ���� ��������� �����������:
				 	
				 	message = errorChecker.getMessage();
			 		createMessageBox (messageCode, message);
			 }	
		 }
	};
		
	/**
	* ����� ��� �������� ��������� ���� � ���������� ������������ (��� ��������� ������� �� ���������� ����������).
	* @param messageCode - ��� ������ (SWT.OK, SWT.ERROR ��� SWT.ICON_INFORMATION)
	* @param message - ��������� ��� ������������
	*/
	public void createMessageBox (int messageCode, String message) {
			
		MessageBox messageBox = new MessageBox (shell, messageCode);
		messageBox.setText("���������");
		messageBox.setMessage(message);
		messageBox.open();
	}
	
	/**
	 * ����� ��������� ���������� ��������� ������ WindowGraphics.
	 * @param windowGraphicsListener - ��������� ������ WindowGraphicsListener
	 */
	public void addWindowGraphicsListener (WindowGraphicsListener windowGraphicsListener) {
		this.windowGraphicsListener = windowGraphicsListener;
	}
	
	/**
	 * ����� ���������� ����.
	 */
	public int getPort() {
		return port;
	}
	// ----------------------------------------------------------------------------------------------------------------------------------
}
	  
