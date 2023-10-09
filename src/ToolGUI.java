import java.util.concurrent.LinkedBlockingDeque;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ToolGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LinkedBlockingDeque<GUIEvent> eventQueue;

	/**
	 * Create the frame.
	 */
	public ToolGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
	}

	@Override
	public void run() {
				
	}
	
	public void updateController(GUIEvent event) {
		//Toolcontroller für Update aufrufen
	}
	
	public void update(GUIEvent event) {
		
	}

}
