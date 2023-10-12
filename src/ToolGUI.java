import java.util.concurrent.LinkedBlockingDeque;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Component;

public class ToolGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private JPanel pnContentPane;
	private LinkedBlockingDeque<GUIEvent> eventQueue;
	private FleetPanel fp;

	/**
	 * Create the frame.
	 */
	public ToolGUI() {        
		setBounds(100, 100, 493, 401);
		pnContentPane = new JPanel();
		pnContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(pnContentPane);
		pnContentPane.setLayout(null);
		
		JPanel pnMain = new JPanel();
		pnMain.setBounds(0, 45, 477, 400);
		pnContentPane.add(pnMain);
		pnMain.setLayout(null);
		
		fp = new FleetPanel(this);
		fp.setBounds(0, 0, 477, 400);
		pnMain.add(fp, BorderLayout.SOUTH);
		pnMain.setLayout(new BorderLayout());
		
		
		JButton btnShips = new JButton("Flotte");
		btnShips.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fp.setVisible(true);				
			}
		});
		btnShips.setBounds(20, 11, 89, 23);
		pnContentPane.add(btnShips);
		
		JButton btnQuest = new JButton("Quest");
		btnQuest.setBounds(137, 11, 89, 23);
		pnContentPane.add(btnQuest);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
	}

	@Override
	public void run() {
				
	}
	
	public void updateController(GUIEvent event) {
		//Toolcontroller für Update aufrufen
	}
	
	public void update(GUIEvent event) {
		
	}
	
	public static void main(String[] args) {
		new ToolGUI();
		
	}
}
