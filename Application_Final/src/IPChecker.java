import java.util.regex.*;

/**
 * Класс предназначен для проверки введённого пользователем в поле "IP-адрес:" значения на соотвествие шаблону IP-адреса.
 *
 */
public class IPChecker {
	
	private String iP; //IP-адрес
	private Pattern pattern; //Объект класса Pattern
	private String iPPattern; //Паттерн IP-адреса
	
	/**
	 * Конструктор класса IPChecker.
	 * @param iP - IP-адрес для проверки
	 */
	public IPChecker (String iP) {
		this.iP = iP;
		iPPattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."  //Маска для определения соответствия введённого зачения шаблону
					+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
					+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
					+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	}
	
	/**
	 * Метод, проверяющий введённый IP-адрес.
	 * @return true - в слачае, когда введённое значение является IP-адресом, false - в обратном случае
	 */
	public boolean isIP() { 
		
		pattern = Pattern.compile(iPPattern); //Присваивание переменной pattern значения, возвращаемого Pattern.compile(), которое соответствует iPPattern
		return pattern.matcher(iP).matches();//Проверка на соответствие строки iP шаблону pattern
	}
}
