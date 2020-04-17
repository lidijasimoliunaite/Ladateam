import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Main {

	private JFrame frame;
	private JTable table;
	private JComboBox comboBox;
	private JTextField textField_group;
	private JTextField textField_lastname;
	private JTextField textField_firstname;
	private JTextField textField_id;

	public Connection getConnection() {
		String dbURL = "jdbc:mysql://localhost:3306/userinfo";
		String username = "root";
		String password = "Online123";
		Connection conn;
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
			return conn;
		} catch (SQLException ex) {
		    ex.printStackTrace();
		    return null;
		}
	}
	String query = "SELECT * FROM users";
	public ArrayList<UserInfo> getUsersList(String query){
		ArrayList<UserInfo> usersList = new ArrayList<UserInfo>();
		Connection connection = getConnection();
		Statement st;
		ResultSet rs;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			UserInfo user;
			while(rs.next()) {
				user = new UserInfo(rs.getInt("user_id"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("groupid"),rs.getInt("result"));
				usersList.add(user);		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usersList;
	}
	
	public void DisplayInTable(String OrderQuery) {
		ArrayList<UserInfo> list = getUsersList(OrderQuery);
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] {"user-id","firstname","lastname","groupid","result"});
		Object[] row = new Object[5];
		for(int i=0; i < list.size(); i++) {
			row[0] = list.get(i).getId();
			row[1] = list.get(i).getFirstName();
			row[2] = list.get(i).getLastName();
			row[3] = list.get(i).getGroup();
			row[4] = list.get(i).getResult();
			model.addRow(row);
		}
		table.setModel(model);
	}
	
	public void executeSQLQuery(String sql, String message) {
		Connection conn = getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			if((st.executeUpdate(sql)) == 1) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.setRowCount(0);
				DisplayInTable(query);
				JOptionPane.showMessageDialog(null, "Data "+message+" Succefully");
			}else {
				JOptionPane.showMessageDialog(null, "Data not"+message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		DisplayInTable(query);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1050, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUserName = new JLabel("First name:");
		lblUserName.setFont(new Font("Soup of Justice (cyrillic by shurupkin)", Font.PLAIN, 18));
		lblUserName.setBounds(26, 201, 110, 38);
		frame.getContentPane().add(lblUserName);
		
		JLabel lblUserId_1_1 = new JLabel("Last name:");
		lblUserId_1_1.setFont(new Font("Soup of Justice (cyrillic by shurupkin)", Font.PLAIN, 18));
		lblUserId_1_1.setBounds(26, 238, 110, 38);
		frame.getContentPane().add(lblUserId_1_1);
		
		JLabel lblUserId_1_1_1 = new JLabel("Class group:");
		lblUserId_1_1_1.setFont(new Font("Soup of Justice (cyrillic by shurupkin)", Font.PLAIN, 18));
		lblUserId_1_1_1.setBounds(26, 275, 110, 38);
		frame.getContentPane().add(lblUserId_1_1_1);
		
		textField_lastname = new JTextField();
		textField_lastname.setColumns(10);
		textField_lastname.setBounds(140, 238, 243, 30);
		frame.getContentPane().add(textField_lastname);
		
		textField_firstname = new JTextField();
		textField_firstname.setColumns(10);
		textField_firstname.setBounds(140, 201, 243, 30);
		frame.getContentPane().add(textField_firstname);
		
		textField_id = new JTextField();
		textField_id.setEnabled(false);
		textField_id.setColumns(10);
		textField_id.setBounds(341, 539, -8, -8);
		frame.getContentPane().add(textField_id);
		
		textField_group = new JTextField();
		textField_group.setBounds(90, 523, -16, -8);
		frame.getContentPane().add(textField_group);
		textField_group.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(453, 67, 547, 486);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				TableModel model = table.getModel();
                textField_id.setText(model.getValueAt(i,0).toString());
                textField_firstname.setText(model.getValueAt(i,1).toString());
                textField_lastname.setText(model.getValueAt(i,2).toString());
                textField_group.setText(model.getValueAt(i,3).toString());
			}
		});
		table.setToolTipText("");
		table.setSurrendersFocusOnKeystroke(true);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"First Name", "Last Name", "Group", "Result"
			}
		));
		
		JButton btnAddUser = new JButton("ADD user");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "INSERT INTO users(firstname, lastname, groupid) VALUES ('"+textField_firstname.getText()+"','"+textField_lastname.getText()+"','"+textField_group.getText()+"')";
				executeSQLQuery(sql, "Inserted");
			}
		});
		btnAddUser.setFont(new Font("Soup of Justice (cyrillic by shurupkin)", Font.PLAIN, 16));
		btnAddUser.setBounds(17, 333, 132, 48);
		frame.getContentPane().add(btnAddUser);
		
		JButton btnEditUser = new JButton("Edit user");
		btnEditUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql = "UPDATE users SET firstname='"+textField_firstname.getText()+"', lastname='"+textField_lastname.getText()+"', groupid='"+textField_group.getText()+"' WHERE user_id='"+textField_id.getText()+"'";
				executeSQLQuery(sql, "Updated");
			}
		});
		btnEditUser.setFont(new Font("Soup of Justice (cyrillic by shurupkin)", Font.PLAIN, 16));
		btnEditUser.setBounds(159, 333, 132, 48);
		frame.getContentPane().add(btnEditUser);
		
		JButton btnRemoveUser = new JButton("remove user");
		btnRemoveUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String sql = "DELETE FROM users WHERE user_id='"+textField_id.getText()+"'";
				executeSQLQuery(sql, "Deleted");
			}
		});
		btnRemoveUser.setFont(new Font("Soup of Justice (cyrillic by shurupkin)", Font.PLAIN, 16));
		btnRemoveUser.setBounds(301, 333, 132, 48);
		frame.getContentPane().add(btnRemoveUser);

		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "CA00CH45-3001", "YH00BM47-3004", "KV00CP21-3001"}));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_group.setText(comboBox.getSelectedItem().toString());
			}
		});
		comboBox.setBounds(146, 280, 233, 30);
		
		frame.getContentPane().add(comboBox);
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Artiom\\Desktop\\Files for study and other\\LADA logotypes\\java.png"));
		lblNewLabel.setBounds(17, 29, 426, 123);
		frame.getContentPane().add(lblNewLabel);
	}
}

}
