import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import dyehard.Reflection.ClassReflector;

import javax.swing.JButton;

public class ReflectionFileChooser extends JFileChooser {

	public JComboBox comboBox;
	private ClassReflector cf;
	
    public ReflectionFileChooser() {
    	comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] { 
        		"Lab 0 - Dyehard Exception", 
        		"Lab 1 - Basic Inheritance and Fields",
        		"Lab 2 - New Hero Texture, Override Methods",
        		"Lab 3 - Verify Fields, Methods, and Constructors",
        		"Lab 4 - New Debris and Debris Generator with Verification",
        		"Lab 5 - Create a new PowerUp and Weapon",
        		"Lab 6 - All Together"
        		}));

        JPanel panel1 = (JPanel) this.getComponent(3);
        JPanel panel2 = (JPanel) panel1.getComponent(3);

        Component c1=panel2.getComponent(0);//optional used to add the buttons after combobox
        Component c2=panel2.getComponent(1);//optional used to add the buttons after combobox
        panel2.removeAll();

        panel2.add(comboBox);
        panel2.add(c1);//optional used to add the buttons after combobox
        panel2.add(c2);//optional used to add the buttons after combobox

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Java Files", "java");
    	setFileFilter(filter);
    	
    	File workingDirectory = new File(System.getProperty("user.dir"));
    	setCurrentDirectory(workingDirectory);
    	
    	setDialogTitle("Choose Your .Java File");
    	
    	addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FilePreviewer fPreview = new FilePreviewer();
	            cf = new ClassReflector(getSelectedFile().getName().replaceFirst("[.][^.]+$", ""));
	            fPreview.textDetails = cf.printDetails();
			}
    	});
    }
}

class FilePreviewer extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	protected JTextField textField;
    protected JTextArea textArea;
    protected String textDetails;
    
	@Override
	public void actionPerformed(ActionEvent e) {
		 //String text = textField.getText();
	     textArea.append(textDetails + '\n');
	     textField.selectAll();
	     textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	
	
}
