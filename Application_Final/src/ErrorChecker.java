import org.eclipse.swt.SWT;

/**
 * Класс предназначен для определения наличия ошибок ввода данных пользователем
 * в текстовые поля для IP-адреса и порта, ошибок поиска файла и его отправки;
 * а также для определения вида данных ошибок.
 */

public class ErrorChecker {

	private String textFile; //Имя файла
	private String textiP; //IP-адрес
	private String textPort; //Порт
	private int messageCode; // Переменнная, в которую будет записано число, соотвествующее типу сообщения, которое необходимо отправить пользователю
	private String errorMessage; //Переменнная, в которую будет записано сообщение пользователю
	
	/**
	 * Конструктор класса Errors.
	 */
	public ErrorChecker () { 
	}
	
	/**
	 * Метод для определения ошибок ввода.
	 */
	public void checkUserInput() { 
		
		if ((textFile.equals("")) || (textiP.equals("")) || (textPort.equals(""))) { //Если поля не заполнены:
			messageCode = SWT.ICON_WARNING;
			errorMessage = "Все поля должны быть заполнены";
			return;
		}
		int intNumber = 0;
		//Блок, где temp присваивается значение поля "Порт", если пользователь ввёл целое число:
		try { 
				intNumber = Integer.parseInt(textPort);
		} catch (NumberFormatException e) { //Обработка ошибки ввода (введено нецелое число)
			messageCode = SWT.ICON_WARNING; //Соответствует сообщению-предупреждению
			errorMessage = "Значение поля \"Порт\" должно быть целым числом в пределах от 1 до 65000";
			return;
		}
			
		if (!((intNumber >= 1) && (intNumber <= 65000))){ 
			messageCode = SWT.ICON_WARNING;
			errorMessage = "Значение поля \"Порт\" должно быть целым числом в пределах от 1 до 65000";
			return;
		}

		IPChecker iPChecker = new IPChecker (textiP); 
		if (!iPChecker.isIP()) { //Если метод вернул false, введённое в поле IP значение некорректно
			messageCode = SWT.ICON_WARNING;
			errorMessage = "Введите корректный IP-адрес (\"***.***.***.***\", где \"***\" - число от 0 до 255)";
		} 		
	}
	
	/**
	 * Метод для определения ошибок поиска и отправки файла.
	 */
	public void checkSendError() {
		if (!errorMessage.equals("")) messageCode = SWT.ERROR;
	}
	
	/**
	 * Метод возвращает код ошибки.
	 */
	public int getMessageCode() { 
		return messageCode;
	}
	
	/**
	 * Метод возвращает сообщение об ошибке.
	 */
	public String getMessage() { 
		return errorMessage;
	}
	
	/**
	 * Метод устанавливает конфигурацию класса.
	 * @param textFile - имя файла для его проверки
	 * @param textiP - IP-адрес для его проверки
	 * @param textPort - порт для его роверки
	 */
	public void setConfig (String textFile, String textiP, String textPort) {
		
		messageCode = SWT.OK;  //Соответствует информационному сообщению
		errorMessage = "";
		this.textFile = textFile;
		this.textiP = textiP;
		this.textPort = textPort;
	}
	
	/**
	 * Метод устанавливает конфигурацию класса.
	 * @param errorMessage - сообщение об ошибке для его проверки
	 */
	public void setConfig (String errorMessage) {
		
		this.errorMessage = errorMessage;
	}
}
	
	
	

