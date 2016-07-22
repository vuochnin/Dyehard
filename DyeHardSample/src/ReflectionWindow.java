import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ReflectionWindow extends JFrame implements ActionListener{
	
	private JTextArea foo = new JTextArea();
	private JButton submit = new JButton("Verify");
	
	public ReflectionWindow(){
		super("Sammy's Awesome Window");
		
		setSize(500, 300);
		
		submit.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		foo.append("Good job student");
		add(foo, BorderLayout.CENTER);
		add(submit, BorderLayout.SOUTH);
		
		setVisible(true);
		
		
	}
	
	public static void main(String[] args){
		ReflectionWindow myWindow = new ReflectionWindow();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("fooooo");
	}
}
