
import org.eclipse.swt.widgets.FileDialog;

/**
 * ����� ������������ ��� �������� ������� ��� ������ ����� � �������� ����� ���������� �����.
 *
 */
public class Dialogue {
	
	private FileDialog dialogue;
	
	/**
	 * ����������� ������ Dialogue.
	 * @param dialogue - ������ ������ FileDialog
	 */
	public Dialogue(FileDialog dialogue) {
		this.dialogue = dialogue;
	}
	
	/*������ �����, ������ �� ������� �������� �������� ��� ����������, 
	 *������� ����� ����� �������������� � ���� ����� � ������ ������ ������� ���������� �������������:*/
	private String[][] FILTERS = {{"����� Word (*.docx)" , "*.docx"},  
            {"����� Excel (*.xlsx)", "*.xlsx"},
            {"����� Adobe (*.pdf)" , "*.pdf" },
            {"��� ����� (*.*)"     , "*.*"  }};
	
	/**
	 * �����, ��������������� ������ �������.
	 * @param dialog - ������, ��� �������� ������������ ������ �������
	 */
	public void setFilters(FileDialog dialog) 
	{
	    String[] names = new String[FILTERS.length];
	    String[] exts  = new String[FILTERS.length];
	    for (int i = 0; i < FILTERS.length; i++) {
	        names[i] = FILTERS[i][0]; //�������� ����������
	        exts [i] = FILTERS[i][1]; //����������
	    }
	    
	    dialog.setFilterNames(names);
	    dialog.setFilterExtensions(exts);
	}
	
	/**
	 * ����� ��� �������� ���� ������ ����� � �������� ��� �����.
	 * @return String fname - ��� �����, "" - ���� ���� �� ������
	 */
	public String play() {
		
		setFilters(dialogue);
		String fname = dialogue.open();
		if (fname != null) {
			return fname;
		} else return "";
		
	}
	
}
