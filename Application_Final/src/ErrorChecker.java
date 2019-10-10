import org.eclipse.swt.SWT;

/**
 * ����� ������������ ��� ����������� ������� ������ ����� ������ �������������
 * � ��������� ���� ��� IP-������ � �����, ������ ������ ����� � ��� ��������;
 * � ����� ��� ����������� ���� ������ ������.
 */

public class ErrorChecker {

	private String textFile; //��� �����
	private String textiP; //IP-�����
	private String textPort; //����
	private int messageCode; // �����������, � ������� ����� �������� �����, �������������� ���� ���������, ������� ���������� ��������� ������������
	private String errorMessage; //�����������, � ������� ����� �������� ��������� ������������
	
	/**
	 * ����������� ������ Errors.
	 */
	public ErrorChecker () { 
	}
	
	/**
	 * ����� ��� ����������� ������ �����.
	 */
	public void checkUserInput() { 
		
		if ((textFile.equals("")) || (textiP.equals("")) || (textPort.equals(""))) { //���� ���� �� ���������:
			messageCode = SWT.ICON_WARNING;
			errorMessage = "��� ���� ������ ���� ���������";
			return;
		}
		int intNumber = 0;
		//����, ��� temp ������������� �������� ���� "����", ���� ������������ ��� ����� �����:
		try { 
				intNumber = Integer.parseInt(textPort);
		} catch (NumberFormatException e) { //��������� ������ ����� (������� ������� �����)
			messageCode = SWT.ICON_WARNING; //������������� ���������-��������������
			errorMessage = "�������� ���� \"����\" ������ ���� ����� ������ � �������� �� 1 �� 65000";
			return;
		}
			
		if (!((intNumber >= 1) && (intNumber <= 65000))){ 
			messageCode = SWT.ICON_WARNING;
			errorMessage = "�������� ���� \"����\" ������ ���� ����� ������ � �������� �� 1 �� 65000";
			return;
		}

		IPChecker iPChecker = new IPChecker (textiP); 
		if (!iPChecker.isIP()) { //���� ����� ������ false, �������� � ���� IP �������� �����������
			messageCode = SWT.ICON_WARNING;
			errorMessage = "������� ���������� IP-����� (\"***.***.***.***\", ��� \"***\" - ����� �� 0 �� 255)";
		} 		
	}
	
	/**
	 * ����� ��� ����������� ������ ������ � �������� �����.
	 */
	public void checkSendError() {
		if (!errorMessage.equals("")) messageCode = SWT.ERROR;
	}
	
	/**
	 * ����� ���������� ��� ������.
	 */
	public int getMessageCode() { 
		return messageCode;
	}
	
	/**
	 * ����� ���������� ��������� �� ������.
	 */
	public String getMessage() { 
		return errorMessage;
	}
	
	/**
	 * ����� ������������� ������������ ������.
	 * @param textFile - ��� ����� ��� ��� ��������
	 * @param textiP - IP-����� ��� ��� ��������
	 * @param textPort - ���� ��� ��� �������
	 */
	public void setConfig (String textFile, String textiP, String textPort) {
		
		messageCode = SWT.OK;  //������������� ��������������� ���������
		errorMessage = "";
		this.textFile = textFile;
		this.textiP = textiP;
		this.textPort = textPort;
	}
	
	/**
	 * ����� ������������� ������������ ������.
	 * @param errorMessage - ��������� �� ������ ��� ��� ��������
	 */
	public void setConfig (String errorMessage) {
		
		this.errorMessage = errorMessage;
	}
}
	
	
	

