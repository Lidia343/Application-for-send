
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
 * Класс предназначен для создания интерфейса программы и реализации взаимодействия с пользователем через данный интерфейс.
 */

public class WindowGraphics {
	
	private Display display; //Объект класса Display
	private Shell shell;  //Окно shell
	private Color colorFore; //Цвет шрифта
	private Color colorBack; //Цвет фона
	private Font font; //Шрифт
	private Text textFile; //текстовое поле для имени файла
	private Text textIP; //текстовое поле для IP-адреса
	private Text textPort; //текстовое поле для порта
	private Client client; //Объект класса Client
	private ErrorChecker errorChecker; //Объект класса ErrorChecker
	private int port; //Порт
	private WindowGraphicsListener windowGraphicsListener; //Слушатель класса WindowGraphics
	
	/**
	 * Конструктор класса WindowGraphics.
	 */
	public WindowGraphics() {
		display = new Display(); 
		shell = new Shell (display); 
		port = 8000; //Значение порта по умолчанию
	}
	
	
	/**
	 * Конструктор класса WindowGraphics.
	 * @param client - клиент
	 * @param errorChecker - объект класса errorChecker
	 */
	public WindowGraphics (Client client, ErrorChecker errorChecker) {
		
		display = new Display(); 
		shell = new Shell (display); 
		this.client = client;
		this.errorChecker = errorChecker;
		port = 8000; //Значение порта по умолчанию
	}
		
	// ----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Метод для создания и инициализации графических компонентов.
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
		setLabel (labelFile, "Файл");
		
		//Композит для расположения поля для ввода названия файла и кнопки "Обзор...":
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
		setButton(buttonView, "Обзор...");
		
		Label labelIP = new Label(shell, SWT.NONE);
		gridData = createGridData (3, 2, false, 0, 0, 0, 0);
		labelIP.setLayoutData(gridData);
		setLabel(labelIP, "IP:");
		
		//Композит для расположения полей для ввода IP-адреса, порта и расположнеия метки с названием "Порт":
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
		setLabel (labelPort, "Порт:");
		
		textPort = new Text (composite2, SWT.MULTI | SWT.BORDER);
		gridData = createGridData (4, 2, true, 94, 22, 0, 0);
		textPort.setLayoutData(gridData);
		setText (textPort, true);
		
		Button buttonSend = new Button (shell, SWT.PUSH);
		gridData = createGridData (3, 2, false, 130, 30, 0, 2);
		buttonSend.setLayoutData(gridData);
		setButton (buttonSend, "Отправить");
		
		buttonView.addSelectionListener(selectionView); 
		
		buttonSend.addSelectionListener(selectionSend); 
		
		shell.pack(); //Установка оптимального размера окна под имеющиеся компоненты
		shell.open(); //Открытие окна
		
		/* Пока ресурсы, ассоциированные с shell, не освобождены,
		 * если display не используется, выполнение текущего потока приостанавливается:*/
		while (!shell.isDisposed()) { 
			if (!display.readAndDispatch()) display.sleep(); 
		}
		
		//Освобождение ресурсов:
		colorBack.dispose();
		colorFore.dispose();
		font.dispose();
		display.dispose(); 	
	}
	
	//  -----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Метод для установки иконки окна приложения.
	 */
	private void setImage() {
		try {
			InputStream stream = WindowGraphics.class.getResourceAsStream("image.png");
			Image image = new Image (display, stream);
			setShell (shell, "Отправка файла", image);
			stream.close();
		} catch (Exception e) {
			createMessageBox (SWT.ERROR, e.getMessage());
			setShell (shell, "Отправка файла", null);
		}
	}
	
	/**
	 * Метод установки свойств для компонента Shell.
	 * @param s - окно shell
	 * @param text - заголовок окна shell
	 * @param image - иконка окна shell
	 */
	
	private void setShell (Shell s, String text, Image image) { 
		s.setMinimumSize(615, 215); 
		s.setText (text);          
		s.setImage(image);			
		s.setBackground (colorBack);    
	}
	
	/**
	 * Создание таблицы из ячеек на shell для расположения в них графических компонентов.
	 * @param s - объект класса Shell
	 */
	private void createGridLayout(Shell s) { 
		
		GridLayout g = new GridLayout();
		g.numColumns = 2; //Количество столбцов
		g.marginWidth = 10; //Расстояние от левого и правого краев окна
		g.marginHeight = 10; //Расстояние от верхнего и нижненго краев окна
		g.horizontalSpacing = 5; //Расстояние между соседними ячейками по горизонтали
		g.verticalSpacing = 20; //Расстояние между соседними ячейками по вертикали
		s.setLayout(g); //Установка слоя на окно shell
	}
	
	/**
	 * Перегрузка метода createGridLayout() (для композита).
	 * @param c - композит
	 * @param numColumns - количество столбцов
	 * @param horizontalSpacing - расстояние между соседними ячейками по горизонтали
	 */
	private void createGridLayout(Composite c, int numColumns, int horizontalSpacing) {
		
		GridLayout g = new GridLayout();
		g.numColumns = numColumns;
		g.horizontalSpacing = horizontalSpacing;
		c.setLayout(g);
	}
	
	/**
	 * Метод для создания объекта класса GridData (для управления данными, связанными с различными компонентами).
	 * Обязательным является указание значений horAl и grab, остальные параметры можно передать как 0 (будут установлены значения по умолчанию).
	 * @param horAl - выравнивание по горизонтали
	 * @param vertAl - выравнивание по вертикали 
	 * @param grab - разрешение/запрет захвата свободного пространства по горизонтали при изменении размеров окна
	 * @param width - ширина компонента
	 * @param height - высота компонента
	 * @param horInd - отступ по горизонтали от начального положения (что задаётся способом выравнивания) компонента в ячейке
	 * @param horSpan - количество ячеек, занимаемое компонентом по горизонтали
	 * @return gridData - объект класса GridData
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
	 * Метод установки свойств для компонента Label
	 * @param label - компонент Label
	 * @param text - текст
	 */
	private void setLabel(Label label, String text) {
		
		label.setText(text); 
		label.setBackground(colorBack);
		label.setForeground(colorFore);
		label.setFont(font); 
	}
	
	/**
	 * Метод установки свойств для компонента Text.
	 * @param text - компонент Text
	 * @param editable - редактируемость
	 */
	private void setText (Text text, boolean editable) { 
		
		text.setEditable(editable); 
		text.setForeground(colorFore);
		text.setFont(font);
	}
		
	/**
	 * Метод установки свойств для компонента Button.
	 * @param button - компонент Button
	 * @param text - название кнопки
	 */
	private void setButton(Button button, String text) { 
		
		button.setText(text);
		button.setBackground(colorBack);
		button.setForeground(colorFore);
		button.setFont(font);
	}		
	
	// --------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Слушатель нажатия кнопки "Обзор..."
	 */
	SelectionAdapter selectionView = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent event) { 
			
			FileDialog fileDialog = new FileDialog (shell, SWT.OPEN); 
			fileDialog.setText("Выбор файла");
	    	Dialogue dialog = new Dialogue (fileDialog); 	
	    	String fileName = dialog.play();	//Открытие окна для выбора файла и присваивание имени выбранного файла переменной fileName
	    	if (!fileName.contentEquals(""))
	    		textFile.setText(fileName); //Пользователь видит название ранее выбранного файла, даже если была нажата кнопка "Отмена"
		}
	 };
	
	 // -------------------------------------------------------------------------------------------------------------------------------------
	 
	 /**
	 * Слушатель нажатия кнопки "Отправить"
	 */
	SelectionAdapter selectionSend = new SelectionAdapter() {
		 @Override
		 public void widgetSelected(SelectionEvent event) { 
						
			 errorChecker.setConfig(textFile.getText(), textIP.getText(), textPort.getText()); 
			 errorChecker.checkUserInput(); 
			 int messageCode = errorChecker.getMessageCode();
			 String message;
			 			
			 if (messageCode  == SWT.OK) { //Если поля заполнены правильно:
				 
				 message = "Файл отправлен";
				 String fileName = textFile.getText();
				 String iP = textIP.getText();
				 port = Integer.parseInt(textPort.getText());
				 
				 if (windowGraphicsListener != null)
					 windowGraphicsListener.readyToSend(); //Вызов метода слушателя класса WindowGraphics
		
			 	 client.setConfig(iP, port, fileName);
			 	 client.run();  //Запуск клиента
			 		
			 	 errorChecker.setConfig(client.getErrorMessage());
			 	 errorChecker.checkSendError();
			 	 messageCode = errorChecker.getMessageCode();
			 	 if (messageCode == SWT.ERROR) {
			 		 message = client.getErrorMessage();
			 	 }
			 	 //Создание дочернего окна с сообщением пользователю (вид сообщения зависит от переменных messageCode и message):
			 	 createMessageBox (messageCode , message);
			 	 
			 	 if (windowGraphicsListener != null)
			 		 windowGraphicsListener.finished(); //Вызов метода слушателя класса WindowGraphics
			 	 
			 } else { //Если хотя бы одно поле заполнено неправильно:
				 	
				 	message = errorChecker.getMessage();
			 		createMessageBox (messageCode, message);
			 }	
		 }
	};
		
	/**
	* Метод для создание дочернего окна с сообщением пользователю (вид сообщения зависит от переданных параметров).
	* @param messageCode - код ошибки (SWT.OK, SWT.ERROR или SWT.ICON_INFORMATION)
	* @param message - сообщение для пользователя
	*/
	public void createMessageBox (int messageCode, String message) {
			
		MessageBox messageBox = new MessageBox (shell, messageCode);
		messageBox.setText("Сообщение");
		messageBox.setMessage(message);
		messageBox.open();
	}
	
	/**
	 * Метод добавляет переданный слушатель класса WindowGraphics.
	 * @param windowGraphicsListener - слушатель класса WindowGraphicsListener
	 */
	public void addWindowGraphicsListener (WindowGraphicsListener windowGraphicsListener) {
		this.windowGraphicsListener = windowGraphicsListener;
	}
	
	/**
	 * Метод возвращает порт.
	 */
	public int getPort() {
		return port;
	}
	// ----------------------------------------------------------------------------------------------------------------------------------
}
	  
