
import org.eclipse.swt.widgets.FileDialog;

/**
 * Класс предназначен для создания фильтра для выбора файла и возврата имени выбранного файла.
 *
 */
public class Dialogue {
	
	private FileDialog dialogue;
	
	/**
	 * Конструктор класса Dialogue.
	 * @param dialogue - объект класса FileDialog
	 */
	public Dialogue(FileDialog dialogue) {
		this.dialogue = dialogue;
	}
	
	/*Массив строк, каждая из которых содержит описание для расширения, 
	 *которое будут иметь отображающиеся в окне файлы в случае выбора данного расширения пользователем:*/
	private String[][] FILTERS = {{"Файлы Word (*.docx)" , "*.docx"},  
            {"Файлы Excel (*.xlsx)", "*.xlsx"},
            {"Файлы Adobe (*.pdf)" , "*.pdf" },
            {"Все файлы (*.*)"     , "*.*"  }};
	
	/**
	 * Метод, устанавливающий фильтр диалога.
	 * @param dialog - объект, для которого определяется фильтр диалога
	 */
	public void setFilters(FileDialog dialog) 
	{
	    String[] names = new String[FILTERS.length];
	    String[] exts  = new String[FILTERS.length];
	    for (int i = 0; i < FILTERS.length; i++) {
	        names[i] = FILTERS[i][0]; //Описания расширений
	        exts [i] = FILTERS[i][1]; //Расширения
	    }
	    
	    dialog.setFilterNames(names);
	    dialog.setFilterExtensions(exts);
	}
	
	/**
	 * Метод для открытия окна выбора файла и возврата его имени.
	 * @return String fname - имя файла, "" - если файл не выбран
	 */
	public String play() {
		
		setFilters(dialogue);
		String fname = dialogue.open();
		if (fname != null) {
			return fname;
		} else return "";
		
	}
	
}
