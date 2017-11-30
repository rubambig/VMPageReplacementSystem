import javax.swing.JFileChooser;
import java.io.File;
/**********************************************
* Chooses an input file for main to read from.
* @author Gloire Rubambiza
* @since 11/28/2017
************************************************/
public class FileChoice {

  /*******************
  * Empty constructor.
  ********************/
  public FileChoice () {

  }

  /***************************************
  * Chooses the file to be used as input.
  * @return the absolute path of the file.
  ***************************************/
  public String chooseFile () {

    String fileName = "";
    // Set the current directory as default.
    File file = new File(System.getProperty("user.dir"));
    JFileChooser select = new JFileChooser(file);

    select.setFileFilter(new Filter());
    select.setDialogTitle("Select an input file for ViMPaReS ");

    int status = 0;
    select.setDialogType(JFileChooser.OPEN_DIALOG);
    status = select.showOpenDialog(null);

    if ( status == JFileChooser.APPROVE_OPTION ) {
      fileName = select.getSelectedFile().getAbsolutePath();
    }
    return fileName;
  }

  /***************************************
  * Narrows the selection to *.data files.
  ****************************************/
  class Filter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File f) {
      return f.isDirectory( ) || f.getAbsolutePath( ).endsWith( ".data" );
    }

    @Override
    public String getDescription() {
      return "Data files ( *.data )";
    }
  }

}
