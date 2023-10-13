package project.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class FleetPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tblShips;
	private ShipPanel sp;
	private boolean addShip = false;
	private ToolGUI parent;
	private JTabbedPane tpFleetPanel;

	/**
	 * Create the panel.
	 */
	public FleetPanel(ToolGUI parent) {
		setLayout(null);
		this.parent = parent;

		tpFleetPanel = new JTabbedPane(JTabbedPane.TOP);
		tpFleetPanel.setBounds(0, 0, 363, 312);
		add(tpFleetPanel);
				
		JPanel pnFleet = new JPanel();
		pnFleet.setBounds(0, 0, 363, 290);
		tpFleetPanel.add(pnFleet);
		pnFleet.setLayout(null);
		
		JLabel lblFleet = new JLabel("Flotte bearbeiten");
		lblFleet.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFleet.setBounds(10, 11, 154, 22);
		pnFleet.add(lblFleet);

		JButton btnEdit = new JButton("Bearbeiten");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setEnabled(false);
		btnEdit.setBounds(230, 217, 100, 25);
		pnFleet.add(btnEdit);

		JButton btnDelete = new JButton("L\u00F6schen");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setEnabled(false);
		btnDelete.setBounds(230, 250, 100, 25);
		pnFleet.add(btnDelete);

		JButton btnAdd = new JButton("Hinzuf\u00FCgen");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tpFleetPanel.setSelectedIndex(1);
				sp.addShip();
				addShip = true;
			}
		});
		btnAdd.setBounds(106, 217, 105, 25);
		pnFleet.add(btnAdd);

		tblShips = new JTable();
		tblShips.setModel(new DefaultTableModel());
		tblShips.setBounds(1, 1, 450, 0);
		pnFleet.add(tblShips);

		JScrollPane spTable = new JScrollPane(tblShips);
		spTable.setBounds(25, 50, 306, 156);
		pnFleet.add(spTable);

		TableColumnModel cm = tblShips.getColumnModel();
		TableColumn c1 = new TableColumn();
		c1.setHeaderValue("Schiff");
		cm.addColumn(c1);
		TableColumn c2 = new TableColumn();
		c2.setHeaderValue("Kapazit�t");
		cm.addColumn(c2);
		TableColumn c3 = new TableColumn();
		c3.setHeaderValue("Anzahl");
		cm.addColumn(c3);

		sp = new ShipPanel();
		sp.setBounds(0, 0, 477, 400);
		sp.btnSave.addActionListener(e -> saveShip());
		tpFleetPanel.add(sp);

		setBounds(0, 0, 363, 312);
				
		setVisible(false);		
	}

	private void saveShip() {
		tpFleetPanel.setSelectedIndex(0);
		
		ArrayList<Object> shipInfos = new ArrayList<Object>();
		shipInfos.add(sp.tfName.getText());
		shipInfos.add(sp.tfCapacity.getText());
		shipInfos.add(sp.tfAmount.getText());

		//seaport.GUIEvent ge;
		if (addShip) {
			//DefaultTableModel dtm = (DefaultTableModel) tblShips.getModel();
			//dtm.setRowCount(5);
			//dtm.addRow(new Object[]{shipInfos.get(0).toString(), shipInfos.get(1).toString(), shipInfos.get(2).toString()});	
			//dtm.addRow(new Object[]{sp.tfName.getText(), sp.tfCapacity.getText(), sp.tfAmount.getText()});		
			
			//ge = new seaport.GUIEvent(seaport.EventTypes.SHIP_ADDED, shipInfos);
		} else {
			//ge = new seaport.GUIEvent(seaport.EventTypes.SHIP_EDITED, shipInfos);
		}

		//parent.updateController(ge);
	}
}
