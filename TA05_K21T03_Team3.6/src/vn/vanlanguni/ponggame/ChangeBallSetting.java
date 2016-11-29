package vn.vanlanguni.ponggame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class ChangeBallSetting extends JDialog {
	private ButtonGroup btng;
	private JRadioButton btnball1,btnball2,btnball3;
	private ImageIcon imgball1,imgball2,imgball3;
	private String nameBall;
	ve panel = new ve();
	
	//Xem khai bao MyDialogResult o cuoi class nay
	public MyDialogResult dialogResult;
	
	public ChangeBallSetting() {
		setPreferredSize(new Dimension(300, 250));
		setTitle("Change Background Playing");
		getContentPane().setLayout(null);
		setModal(true);
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(150,10,100,100);
		
		
		btng = new ButtonGroup();
		btnball1 = new JRadioButton("Ball 1");
		btnball2 = new JRadioButton("Ball 2");
		
		btnball3 = new JRadioButton("Ball 3");
		btng.add(btnball1);btng.add(btnball2);btng.add(btnball3);
		dialogResult =MyDialogResult.DEFAULT;
		btnball1.setBounds(10,10,100,25);
		btnball2.setBounds(10,40,100,25);
		btnball3.setBounds(10,70,100,25);
		btnball1.setSelected(true);
		getContentPane().add(btnball1);
		getContentPane().add(btnball2);
		getContentPane().add(btnball3);
		getContentPane().add(panel);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialogResult = MyDialogResult.YES;
				setVisible(false);
			}
		});
		btnSave.setBounds(44, 114+50, 89, 23);
		getContentPane().add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogResult =MyDialogResult.CANCEL;
				setVisible(false);
			}
		});
		btnCancel.setBounds(154, 114+50, 89, 23);
		getContentPane().add(btnCancel);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				int result = JOptionPane.showConfirmDialog(ChangeBallSetting.this, "Exit?");
				if(result == JOptionPane.YES_OPTION){
					setVisible(false);
				}				
			}
		});
	}
	
	public Settings getSettings (){
		Settings st = new Settings();
		/* thay ten anh~ trong nameBall*/
		if ( btnball1.isSelected()){
			nameBall = "Images/neptune.png";
			st.setBall1(nameBall );
			
			
		}else if (btnball2.isSelected()){
			nameBall = "Images/football.png";
			st.setBall2(nameBall );
			
			
		}else if (btnball3.isSelected()){
			nameBall = "Images/tenis.png";
			st.setBall3(nameBall );
			
			
		}
		return st;
		
		
	}

	
	

	public class ve extends JPanel{

		/* (non-Javadoc)
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			
			
			
			if ( btnball1.isSelected()){
				
				nameBall = "Images/neptune.png";
				repaint();
			}else if (btnball2.isSelected()){
				nameBall = "Images/football.png";
				repaint();
			}else if (btnball3.isSelected()){
				nameBall = "Images/tenis.png";
				repaint();
			}
			imgball1 = new ImageIcon(nameBall);
			g.drawImage(imgball1.getImage(),0,0,100,100,null);
			
		}
		
	}

	
	
	
}


