import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class FleetPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tblShips;
	private ShipPanel sp;
	private boolean addShip = false;
	private ToolGUI parent;
	
	/**
	 * Create the panel.
	 */
	public FleetPanel(ToolGUI parent) {
		setLayout(null);
		
		JLabel lblFleet = new JLabel("Flotte bearbeiten");
		lblFleet.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFleet.setBounds(32, 37, 231, 21);
		add(lblFleet);
		
		JButton btnEdit = new JButton("Bearbeiten");
		btnEdit.setEnabled(false);
		btnEdit.setBounds(162, 251, 85, 21);
		add(btnEdit);
		
		JButton btnDelete = new JButton("L\u00F6schen");
		btnDelete.setEnabled(false);
		btnDelete.setBounds(257, 251, 85, 21);
		add(btnDelete);
		
		JButton btnAdd = new JButton("Hinzuf\u00FCgen");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sp.add(btnAdd);
				addShip = true;
			}
		});
		btnAdd.setBounds(66, 251, 85, 21);
		add(btnAdd);
		
		tblShips = new JTable();
		tblShips.setBounds(32, 68, 311, 160);
		add(tblShips);
		
	    TableColumn c1 = new TableColumn();
	    c1.setHeaderValue("Schiff");
	    tblShips.getColumnModel().addColumn(c1);
	    TableColumn c2 = new TableColumn();
	    c2.setHeaderValue("Kapazität");
	    tblShips.getColumnModel().addColumn(c2);
	    TableColumn c3 = new TableColumn();
	    c3.setHeaderValue("Anzahl");
	    tblShips.getColumnModel().addColumn(c3);

	    sp = new ShipPanel();
	    sp.btnSave.addActionListener(e -> saveShip());
	}
	
	private void saveShip() {
		
		ArrayList<Object> shipInfos = new ArrayList<Object>();
		shipInfos.add(sp.tfName.getText());
		shipInfos.add(sp.tfCapacity.getText());
		shipInfos.add(sp.tfAmount.getText());
		
		GUIEvent ge;
		if (addShip)
			ge = new GUIEvent(EventTypes.SHIP_ADDED, shipInfos);
		else
			ge = new GUIEvent(EventTypes.SHIP_EDITED, shipInfos);
		
		parent.updateController(ge);				
	}

}


