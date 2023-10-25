package project.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
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
import java.util.List;
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
	private JButton btnEdit;

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

		btnEdit = new JButton("Bearbeiten");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tpFleetPanel.setSelectedIndex(1);
				int row = tblShips.getSelectedRow();
				sp.editShip(new String[] { tblShips.getValueAt(row, 0).toString(), tblShips.getValueAt(row, 1).toString(), tblShips.getValueAt(row, 2).toString() });
				addShip = false;
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setEnabled(false);
		btnEdit.setBounds(218, 217, 110, 25);
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
		tblShips.setModel(new DefaultTableModel(0,0));		
		tblShips.setBounds(1, 1, 450, 0);
		pnFleet.add(tblShips);

		JScrollPane spTable = new JScrollPane(tblShips);
		spTable.setBounds(25, 50, 306, 156);
		pnFleet.add(spTable);
		
		DefaultTableModel dtm = new DefaultTableModel(0, 0);
		dtm.setColumnIdentifiers(new String[] { "Schiff", "Kapazitaet", "Anzahl"});
		tblShips.setModel(dtm);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tblShips.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		tblShips.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		
		tblShips.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		    	btnEdit.setEnabled(tblShips.getSelectedRow() > - 1);;
		    }
		});

		sp = new ShipPanel();
		sp.setBounds(0, 0, 477, 400);
		sp.btnSave.addActionListener(e -> saveShip());
		sp.btnCancel.addActionListener(e -> cancelShip());
		tpFleetPanel.add(sp);

		setBounds(0, 0, 363, 312);
				
		setVisible(false);		
	}

	public void addShipToGUI(GUIEvent event) {
		List<Object> params = event.getParameters();
				
		DefaultTableModel dtm = (DefaultTableModel) tblShips.getModel();
		dtm.addRow(new Object[]{(String) params.get(0), ((Integer) params.get(1)).intValue(), ((Integer) params.get(2)).intValue()});
	}
	
	private void clearTableSelection() {
		tblShips.clearSelection();
		
		TableColumnModel tcm = tblShips.getColumnModel();
		tcm.getSelectionModel().clearSelection();
	}
	
	public void updateShipFromGUI(GUIEvent event) {
		List<Object> params = event.getParameters();
		String shipname = (String) params.get(0);
		
		for (int i = 0; i < tblShips.getRowCount(); i++) {
			if (tblShips.getValueAt(i, 0).equals(shipname)) {
				
				tblShips.setValueAt((String) params.get(1), i, 0);
				tblShips.setValueAt(((Integer) params.get(2)).intValue(), i, 1);
				tblShips.setValueAt(((Integer) params.get(3)).intValue(), i, 2);	
				 
				clearTableSelection();				
				btnEdit.setEnabled(false);				
				break;
			}
		}
		
	}
	
	private void cancelShip() {
		tpFleetPanel.setSelectedIndex(0);
		sp.clearView();
		clearTableSelection();	
	}
	
	private void saveShip() {
		tpFleetPanel.setSelectedIndex(0);
		
		ArrayList<Object> shipInfos = new ArrayList<Object>();
		
		if (!addShip) 
			shipInfos.add(sp.oldShipname);		
		
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
