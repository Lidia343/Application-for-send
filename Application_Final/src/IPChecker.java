import java.util.regex.*;

/**
 * ����� ������������ ��� �������� ��������� ������������� � ���� "IP-�����:" �������� �� ����������� ������� IP-������.
 *
 */
public class IPChecker {
	
	private String iP; //IP-�����
	private Pattern pattern; //������ ������ Pattern
	private String iPPattern; //������� IP-������
	
	/**
	 * ����������� ������ IPChecker.
	 * @param iP - IP-����� ��� ��������
	 */
	public IPChecker (String iP) {
		this.iP = iP;
		iPPattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."  //����� ��� ����������� ������������ ��������� ������� �������
					+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
					+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
					+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	}
	
	/**
	 * �����, ����������� �������� IP-�����.
	 * @return true - � ������, ����� �������� �������� �������� IP-�������, false - � �������� ������
	 */
	public boolean isIP() { 
		
		pattern = Pattern.compile(iPPattern); //������������ ���������� pattern ��������, ������������� Pattern.compile(), ������� ������������� iPPattern
		return pattern.matcher(iP).matches();//�������� �� ������������ ������ iP ������� pattern
	}
}
