package project.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import project.EventTypes;
import project.Fleet;
import project.GUIEvent;
import project.Ship;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
		lblFleet.setBounds(10, 11, 223, 22);
		pnFleet.add(lblFleet);

		JButton btnEdit = new JButton("Bearbeiten");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setEnabled(false);
		btnEdit.setBounds(218, 215, 110, 25);
		pnFleet.add(btnEdit);

		JButton btnDelete = new JButton("Loeschen");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setEnabled(false);
		btnDelete.setBounds(218, 250, 110, 25);
		pnFleet.add(btnDelete);

		JButton btnAdd = new JButton("Hinzufuegen");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tpFleetPanel.setSelectedIndex(1);
				sp.addShip();
				addShip = true;
			}
		});
		btnAdd.setBounds(93, 217, 115, 25);
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
		c2.setHeaderValue("Kapazitaet");
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

	public void addShipToGUI() {
		Fleet f = Fleet.getInstance();
		Collection<Ship> ships = f.getShips();
		
		Iterator<Ship> itr = ships.iterator();
		Ship lastShip = itr.next();
		
		while(itr.hasNext()) {
			lastShip = itr.next();
	    }
		
		//TODO Zeile zu Tabelle hinzufuegen
//		DefaultTableModel dtm = (DefaultTableModel) tblShips.getModel();
//		dtm.setRowCount(5);	
//		dtm.addRow(new Object[]{lastShip.getName(), lastShip.getCapacity(), lastShip.getAmount()});
	}
	
	private void saveShip() {
		tpFleetPanel.setSelectedIndex(0);
		
		ArrayList<Object> shipInfos = new ArrayList<Object>();
		shipInfos.add(sp.tfName.getText());
		shipInfos.add(Integer.valueOf(sp.tfCapacity.getText()));
		shipInfos.add(Integer.valueOf(sp.tfAmount.getText()));

		GUIEvent ge;
		if (addShip) 			
			ge = new GUIEvent(EventTypes.SHIP_ADDED, shipInfos);
		else 
			ge = new GUIEvent(EventTypes.SHIP_EDITED, shipInfos);
		

		parent.updateController(ge);
	}
}
