package project.GUI;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShipPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JTextField tfName;
	public JTextField tfCapacity;
	public JTextField tfAmount;
	private JLabel lblShip;
	public JButton btnSave;
	public JButton btnDelete;
	private JButton btnCancel;

	/**
	 * Create the panel.
	 */
	public ShipPanel() {
		setLayout(null);
		setVisible(false);

		tfName = new JTextField();
		tfName.setBounds(144, 56, 145, 19);
		add(tfName);
		tfName.setColumns(10);
				
		tfCapacity = new JTextField();
		tfCapacity.setColumns(10);
		tfCapacity.setBounds(144, 109, 145, 19);
		add(tfCapacity);

		tfAmount = new JTextField();
		tfAmount.setColumns(10);
		tfAmount.setBounds(144, 158, 145, 19);
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
		lblName.setBounds(45, 56, 78, 13);
		add(lblName);

		JLabel lblCapacity = new JLabel("Kapazitaet:");
		lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCapacity.setBounds(45, 104, 89, 18);
		add(lblCapacity);

		JLabel lblAmount = new JLabel("Anzahl:");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAmount.setBounds(45, 156, 78, 13);
		add(lblAmount);

		lblShip = new JLabel("Schiff bearbeiten");
		lblShip.setHorizontalAlignment(SwingConstants.LEFT);
		lblShip.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblShip.setBounds(29, 11, 199, 19);
		add(lblShip);

		btnSave = new JButton("Speichern");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.setBounds(183, 199, 106, 21);
		add(btnSave);

		btnDelete = new JButton("Loeschen");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(67, 199, 106, 21);
		add(btnDelete);

		btnCancel = new JButton("Abbrechen");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(183, 230, 106, 21);
		add(btnCancel);
	}

	private void checkSave() {
		btnSave.setEnabled(!tfName.getText().equals("") && !tfCapacity.getText().equals("") && !tfAmount.getText().equals(""));
	}

	private void clearView() {
		tfName.setText("");
		tfCapacity.setText("");
		tfAmount.setText("");
	}

	public void addShip() {
		setVisible(true);
		lblShip.setText("Schiff hinzufuegen");
		clearView();
		btnDelete.setEnabled(false);
		btnSave.setEnabled(false);
	}
}
