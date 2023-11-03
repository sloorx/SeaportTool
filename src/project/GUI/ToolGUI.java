package project.GUI;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import project.EventTypes;
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

	private static final long serialVersionUID = 9152709864005137965L;
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
		eventQueue = new LinkedBlockingDeque<GUIEvent>();
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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
				if (questPanelGUI.isVisible())
					questPanelGUI.setVisible(false);
				fp.setVisible(true);
			}
		});
		btnShips.setBounds(20, 11, 89, 23);
		pnContentPane.add(btnShips);

		JButton btnQuest = new JButton("Quest");
		btnQuest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fp.isVisible())
					fp.setVisible(false);
				if (questFormGUI.isVisible())
					questFormGUI.setVisible(false);
				questPanelGUI.setVisible(true);
			}
		});
		btnQuest.setBounds(137, 11, 89, 23);
		pnContentPane.add(btnQuest);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				updateController(new GUIEvent(EventTypes.EXIT, null));
			}
		});
	}

	@Override
	public void run() {
		List<Object> params;		
		GUIEvent event;
		
		while (true) {
			if (!eventQueue.isEmpty()) {
				event = eventQueue.poll();
				params = event.getParameters();
				
				switch (event.getType()) {
				case ERROR:
					showException((String) params.get(0));
					break;
				case SHIP_ADDED:
					fp.addShipToGUI(event);
					break;
				case SHIP_EDITED:
					fp.updateShipFromGUI(event);
					break;
				case SHIP_REMOVED:
					fp.removeShipFromGUI(event);
					break;
				case RESOURCE_ADDED:

					break;
				case RESOURCE_EDITED:

					break;
				case RESOURCE_REMOVED:

					break;
				case SOLUTION_ADDED:

					break;
				default:
					break;
				}
			}
		}
			
	}

	public void setController(ToolController controller) {
		this.controller = controller;
	}

	public synchronized void updateGUI(GUIEvent event) {		
		eventQueue.add(event);
	}

	public void updateController(GUIEvent event) {
		controller.update(event);
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

}
