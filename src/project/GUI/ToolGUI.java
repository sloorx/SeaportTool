package project.GUI;

import java.util.concurrent.LinkedBlockingDeque;
import project.GUIEvent;
import project.ToolController;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;

public class ToolGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private JPanel pnContentPane;
	private LinkedBlockingDeque<GUIEvent> eventQueue;
	private FleetPanel fp;
	private QuestPanel questPanelGUI;
	private QuestForm questFormGUI;
	private ToolController controller;

	/**
	 * Create the frame.
	 */
	public ToolGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				//TODO Exit übergeben
				//update(null);
			}
		});        
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

		questPanelGUI = new QuestPanel(this);
		questPanelGUI.setBounds(0, 0, 477, 400);
		pnMain.add(questPanelGUI, BorderLayout.SOUTH);
		pnMain.setLayout(new BorderLayout());

		questFormGUI = new QuestForm(this);
		questFormGUI.setBounds(0, 0, 477, 400);
		pnMain.add(questFormGUI, BorderLayout.SOUTH);
		pnMain.setLayout(new BorderLayout());
		
		JButton btnShips = new JButton("Flotte");
		btnShips.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(questPanelGUI.isVisible())
					questPanelGUI.setVisible(false);
				fp.setVisible(true);
			}
		});
		btnShips.setBounds(20, 11, 89, 23);
		pnContentPane.add(btnShips);
		
		JButton btnQuest = new JButton("Quest");
		btnQuest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fp.isVisible())
					fp.setVisible(false);
				if(questFormGUI.isVisible())
					questFormGUI.setVisible(false);
				questPanelGUI.setVisible(true);
			}
		});
		btnQuest.setBounds(137, 11, 89, 23);
		pnContentPane.add(btnQuest);

		//TODO eigenes Exit-Event, in dem EXIT dem Controller übergeben wird
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
	}

	@Override
	public void run() {
				
	}
	
	public void setController(ToolController controller) {
		this.controller = controller;
	}
	
	public void updateController(GUIEvent event) {
		//TODO
		//Toolcontroller fuer Update aufrufen
	}
	
	public void update(GUIEvent event) {
		//TODO Event an Controller übergeben
		//controller.update(event);
	}

	public void openQuestForm() {
		questPanelGUI.setVisible(false);
		questFormGUI.setVisible(true);
	}

	public void closeQuestForm() {
		questFormGUI.setVisible(false);
		questPanelGUI.setVisible(true);
	}
	
	public void showException(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Fehler", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main(String[] args) {
		new ToolGUI();
		
	}
}
