package project.GUI;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShipPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JTextField tfName;
	public JFormattedTextField tfCapacity;
	public JFormattedTextField tfAmount;
	private JLabel lblShip;
	public JButton btnSave;
	public JButton btnDelete;
	public JButton btnCancel;
	public String oldShipname;

	/**
	 * Create the panel.
	 */
	public ShipPanel() {
		setLayout(null);

		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(true);
	    formatter.setCommitsOnValidEdit(true);
		
		tfName = new JTextField();
		tfName.setBounds(175, 66, 164, 19);
		add(tfName);
		tfName.setColumns(10);
				
		tfCapacity = new JFormattedTextField(formatter);
		tfCapacity.setColumns(10);
		tfCapacity.setBounds(175, 120, 164, 19);
		add(tfCapacity);

		tfAmount = new JFormattedTextField(formatter);
		tfAmount.setColumns(10);
		tfAmount.setBounds(175, 169, 164, 19);
		add(tfAmount);
		
		DocumentListener dl = new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  checkSave();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  checkSave();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  checkSave();
			  }
		};
		
		tfName.getDocument().addDocumentListener(dl);
		tfCapacity.getDocument().addDocumentListener(dl);
		tfAmount.getDocument().addDocumentListener(dl);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(76, 67, 78, 13);
		add(lblName);

		JLabel lblCapacity = new JLabel("Kapazitaet:");
		lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCapacity.setBounds(76, 115, 89, 18);
		add(lblCapacity);

		JLabel lblAmount = new JLabel("Anzahl:");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAmount.setBounds(76, 167, 78, 13);
		add(lblAmount);

		lblShip = new JLabel("Schiff bearbeiten");
		lblShip.setHorizontalAlignment(SwingConstants.LEFT);
		lblShip.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblShip.setBounds(54, 21, 199, 34);
		add(lblShip);

		btnSave = new JButton("Speichern");
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSave.setBounds(214, 210, 125, 21);
		add(btnSave);

		btnDelete = new JButton("Loeschen");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDelete.setBounds(76, 210, 125, 21);
		add(btnDelete);

		btnCancel = new JButton("Abbrechen");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancel.setBounds(214, 241, 125, 21);
		add(btnCancel);
	}

	void checkSave() {
		btnSave.setEnabled(!tfName.getText().equals("") && !tfCapacity.getText().equals("") && !tfAmount.getText().equals(""));
	}

	public void clearView() {
		tfName.setText("");
		tfCapacity.setValue(null);
		tfAmount.setValue(null);
	}

	public void addShip() {
		lblShip.setText("Schiff hinzufuegen");
		clearView();
		btnDelete.setEnabled(false);
		btnSave.setEnabled(false);
	}
	
	public void editShip(String[] values) {
		lblShip.setText("Schiff bearbeiten");
		oldShipname = values[0];
		tfName.setText(values[0]);
		tfCapacity.setValue(Integer.parseInt(values[1]));
		tfAmount.setValue(Integer.parseInt(values[2]));
		btnDelete.setEnabled(true);
	}
}
