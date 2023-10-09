import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
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
		tfName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSave.setEnabled(checkSave());
			}
		});
		tfName.setBounds(143, 73, 145, 19);
		add(tfName);
		tfName.setColumns(10);

		tfCapacity = new JTextField();
		tfCapacity.setColumns(10);
		tfCapacity.setBounds(143, 126, 145, 19);
		add(tfCapacity);

		tfAmount = new JTextField();
		tfAmount.setColumns(10);
		tfAmount.setBounds(143, 175, 145, 19);
		add(tfAmount);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(55, 76, 78, 13);
		add(lblName);

		JLabel lblCapacity = new JLabel("Kapazit\u00E4t:");
		lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCapacity.setBounds(55, 124, 78, 18);
		add(lblCapacity);

		JLabel lblAmount = new JLabel("Anzahl:");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAmount.setBounds(55, 176, 78, 13);
		add(lblAmount);

		lblShip = new JLabel("Schiff bearbeiten");
		lblShip.setHorizontalAlignment(SwingConstants.LEFT);
		lblShip.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblShip.setBounds(39, 31, 199, 19);
		add(lblShip);

		btnSave = new JButton("Speichern");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.setBounds(182, 219, 106, 21);
		add(btnSave);

		btnDelete = new JButton("L\u00F6schen");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(66, 219, 106, 21);
		add(btnDelete);

		btnCancel = new JButton("Abbrechen");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(182, 250, 106, 21);
		add(btnCancel);
	}

	private boolean checkSave() {
		return (!tfName.getText().equals("") && !tfCapacity.getText().equals("") && !tfAmount.getText().equals(""));
	}

	private void clearView() {
		tfName.setText("");
		tfCapacity.setText("");
		tfAmount.setText("");
	}

	public void addShip() {
		setVisible(true);
		lblShip.setText("Schiff hinzufügen");
		clearView();
		btnDelete.setEnabled(false);
		btnSave.setEnabled(false);
	}
}
