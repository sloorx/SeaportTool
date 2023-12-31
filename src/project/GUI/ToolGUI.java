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
import javax.swing.BorderFactory;
import javax.swing.Box;
import java.awt.Component;
import java.awt.Color;
import java.awt.Font;

/**
 * @author Christoph Mehlis
 * 
 *         The ToolGUI class represents the main graphical user interface for
 *         the Seaport Tool application. It provides functionality for loading
 *         and saving data, as well as managing different panels for fleet,
 *         quests, solutions, and individual solutions.
 */
public class ToolGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 9152709864005137965L;
	private JPanel pnContentPane;
	private LinkedBlockingDeque<GUIEvent> eventQueue;
	private FleetPanel fp;
	private QuestPanel questPanelGUI;
	private QuestForm questFormGUI;
	private SolutionsListGui solutionslistGUI;
	private SolutionGui solutionGUI;
	private ToolController controller;

	/**
	 * Create the frame.
	 */
	public ToolGUI() {
		setTitle("Seaport-Tool");
		eventQueue = new LinkedBlockingDeque<GUIEvent>();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setBounds(100, 100, 500, 450);

		JMenuBar mbMain = new JMenuBar();
		setJMenuBar(mbMain);

		JMenu mLoadSave = new JMenu("Menu");
		mLoadSave.setForeground(new Color(0, 0, 0));
		mLoadSave.setFont(new Font("Segoe UI", Font.BOLD, 15));
		mLoadSave.setBackground(new Color(203, 236, 254));
		mLoadSave.setBorder(BorderFactory.createLineBorder(new Color(227, 227, 227), 2));
		mLoadSave.setOpaque(true);
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
		btnShips.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnShips.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (questPanelGUI.isVisible() || questFormGUI.isVisible() || solutionslistGUI.isVisible()
						|| solutionGUI.isVisible()) {
					questPanelGUI.setVisible(false);
					questFormGUI.setVisible(false);
					solutionslistGUI.setVisible(false);
					solutionGUI.setVisible(false);
				}
				fp.setVisible(true);
			}
		});

		JButton btnQuest = new JButton("Quest");
		btnQuest.setFont(new Font("Tahoma", Font.BOLD, 14));

		JButton btnSolution = new JButton("Loesung");
		btnSolution.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSolution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (questPanelGUI.isVisible() || questFormGUI.isVisible() || fp.isVisible()
						|| solutionGUI.isVisible()) {
					questFormGUI.setVisible(false);
					fp.setVisible(false);
					solutionGUI.setVisible(false);
				}

				questPanelGUI.btnSolutionActionPerformed(null);
			}
		});

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
		questFormGUI.setBounds(0, 0, 421, 338);
		pnMain.add(questFormGUI);

		questPanelGUI = new QuestPanel(this);
		questPanelGUI.setBounds(0, 0, 421, 338);
		pnMain.add(questPanelGUI);

		fp = new FleetPanel(this);
		fp.setBounds(0, 0, 421, 470);
		pnMain.add(fp);

		solutionslistGUI = new SolutionsListGui(this);
		solutionslistGUI.setBounds(0, 0, 421, 338);
		pnMain.add(solutionslistGUI);
		solutionslistGUI.setTitleTable();

		solutionGUI = new SolutionGui(this);
		solutionGUI.setBounds(0, 0, 421, 338);
		pnMain.add(solutionGUI);

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

	/**
	 * Runs the ToolGUI thread and waits for fired events.
	 */
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
					List<Solution> solutions = new ArrayList<>();
					for (int i = 0; i < params.size(); i++) {
						solutions.add((Solution) params.get(i));
					}
					setSolutionsToGUI(solutions);
					break;
				case CLEAR:
					fp.clearView();
					questPanelGUI.clearView(questFormGUI.isVisible());
					questFormGUI.clearView();
					solutionGUI.clearView();
					solutionslistGUI.clearView();
					break;
				default:
					break;
				}
			}
		}

	}

	/**
	 * Sets the controller for the ToolGUI.
	 * 
	 * @param controller The ToolController instance.
	 */
	public void setController(ToolController controller) {
		this.controller = controller;
	}

	/**
	 * Updates the GUI with the specified GUIEvent.
	 * 
	 * @param event The GUIEvent to process.
	 */
	public synchronized void updateGUI(GUIEvent event) {
		eventQueue.add(event);
	}

	/**
	 * Updates the controller with the specified GUIEvent.
	 * 
	 * @param event The GUIEvent to process.
	 */
	public void updateController(GUIEvent event) {
		controller.update(event);
	}

	/**
	 * Shows the quest panel when the "Quest" button is clicked.
	 * 
	 * @param e The ActionEvent triggered by the button click.
	 */
	public void showQuestPanel(ActionEvent e) {
		if (fp.isVisible() || questFormGUI.isVisible() || solutionslistGUI.isVisible() || solutionGUI.isVisible()) {
			fp.setVisible(false);
			questFormGUI.setVisible(false);
			solutionslistGUI.setVisible(false);
			solutionGUI.setVisible(false);
		}
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

	private void setSolutionsToGUI(List<Solution> solutions) {
		questPanelGUI.setVisible(false);
		solutionslistGUI.updateTable(solutions);
		solutionslistGUI.setVisible(true);
	}

	public void openSolutionGUIAndShowSelectedSolution(int index) {
		solutionslistGUI.setVisible(false);
		solutionGUI.setTable(solutionslistGUI.getSolutions().get(index));
		solutionGUI.setVisible(true);
	}

	public void openSolutionsListGUI() {
		solutionGUI.setVisible(false);
		solutionslistGUI.setVisible(true);
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
