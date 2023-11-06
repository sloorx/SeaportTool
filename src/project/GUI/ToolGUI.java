package project.GUI;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

import project.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.LayoutStyle.ComponentPlacement;

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
		setTitle("Seaport-Tool");
		eventQueue = new LinkedBlockingDeque<GUIEvent>();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setBounds(100, 100, 500, 400);

		JMenuBar mbMain = new JMenuBar();
		setJMenuBar(mbMain);

		JMenu mLoadSave = new JMenu("Menu");
		mbMain.add(mLoadSave);

		JMenuItem miLoad = new JMenuItem("Laden");
		miLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("Datei zum Laden ausw�hlen");
				chooser.setDialogType(JFileChooser.OPEN_DIALOG);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Seaport-Datei: sp", "sp");
				chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
				chooser.setFileFilter(filter);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File f = chooser.getSelectedFile();
					ArrayList<Object> loadInfo = new ArrayList<Object>();
					loadInfo.add(f.getPath());
					controller.update(new GUIEvent(EventTypes.LOAD, loadInfo));
				}
			}
		});
		mLoadSave.add(miLoad);

		JMenuItem miSave = new JMenuItem("Speichern");
		miSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("Speicherort ausw�hlen");
				chooser.setDialogType(JFileChooser.SAVE_DIALOG);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Seaport-Datei: sp", "sp");
				chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
				chooser.setFileFilter(filter);

				if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					File f = chooser.getSelectedFile();
					String path = f.getPath();
					ArrayList<Object> saveInfo = new ArrayList<Object>();

					if (!filter.accept(f)) {
						if (path.contains("."))
							path.substring(0, path.lastIndexOf('.'));

						path = path + ".sp";
					}

					saveInfo.add(path);
					controller.update(new GUIEvent(EventTypes.SAVE, saveInfo));
				}
			}
		});
		mLoadSave.add(miSave);
		pnContentPane = new JPanel();
		pnContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(pnContentPane);
		pnContentPane.setLayout(null);

		JPanel pnMain = new JPanel();
		pnMain.setBounds(32, 36, 421, 303);
		pnContentPane.add(pnMain);

		JPanel pnButtons = new JPanel();
		pnButtons.setBounds(0, 0, 484, 38);
		pnContentPane.add(pnButtons);

		JButton btnShips = new JButton("Flotte");
		btnShips.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (questPanelGUI.isVisible() || questFormGUI.isVisible()) {
					questPanelGUI.setVisible(false);
					questFormGUI.setVisible(false);
				}
				fp.setVisible(true);
			}
		});

		JButton btnQuest = new JButton("Quest");

		JButton btnSolution = new JButton("Loesung");
		GroupLayout gl_pnButtons = new GroupLayout(pnButtons);
		gl_pnButtons.setHorizontalGroup(gl_pnButtons.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnButtons.createSequentialGroup().addGap(30)
						.addComponent(btnShips, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE).addGap(20)
						.addComponent(btnQuest, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE).addGap(18)
						.addComponent(btnSolution, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
						.addGap(30)));
		gl_pnButtons.setVerticalGroup(gl_pnButtons.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnButtons.createSequentialGroup().addGap(7)
						.addGroup(gl_pnButtons.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnShips, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSolution, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnQuest, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		pnButtons.setLayout(gl_pnButtons);
		pnMain.setLayout(null);

		questFormGUI = new QuestForm(this);
		questFormGUI.setBounds(0, 0, 421, 303);
		pnMain.add(questFormGUI);

		questPanelGUI = new QuestPanel(this);
		questPanelGUI.setBounds(0, 0, 421, 303);
		pnMain.add(questPanelGUI);

		fp = new FleetPanel(this);
		fp.setBounds(0, 0, 421, 303);
		pnMain.add(fp);

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(0, 37, 30, 303);
		pnContentPane.add(verticalStrut);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setBounds(454, 41, 30, 298);
		pnContentPane.add(verticalStrut_1);

		btnQuest.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				showQuestPanel(evt);
			}
		});
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
					closeQuestForm();
					break;
				case RESOURCE_EDITED:
					closeQuestForm();
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

	public void showQuestPanel(ActionEvent e) {
		if (fp.isVisible())
			fp.setVisible(false);
		if (questFormGUI.isVisible())
			questFormGUI.setVisible(false);
		questPanelGUI.updateTable();
		questPanelGUI.setVisible(true);
	}

	private void openQuestForm() {
		questPanelGUI.setVisible(false);
		questFormGUI.setVisible(true);
	}

	public void closeQuestForm() {
		questFormGUI.setVisible(false);
		questPanelGUI.updateTable();
		questPanelGUI.setVisible(true);
	}

	public void showException(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Fehler", JOptionPane.ERROR_MESSAGE);
	}

	public Map<String, Integer> getQuest() {
		return controller.getQuest();
	}

	public void addNewQuest() {
		questFormGUI.setTitleNewQuest();
		openQuestForm();
	}

	public void editQuest(String editRes, String editMenge) {
		questFormGUI.editQuest(editRes, editMenge);
		openQuestForm();
	}
}
