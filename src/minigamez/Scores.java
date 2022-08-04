package minigamez;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class Scores extends Panel implements ActionListener{

	Window window;
	JLabel header;
	JButton back;
	JScrollPane scrollBoard;
	JTable jt;
	
	public Scores(String gameName) {
		super();
		this.window = Window.getWindow();
		
		header = new JLabel();
		if(Window.mode == 0)
			setLabel(header, "Scores", 175, 50, 250, 50, "Serif", 25, 3);
		else
			setLabel(header, "Scores", (window.getWidth()-400)/2, 100, 400, 75, "Serif", 50, 3);
			
		back = new JButton();
		if(Window.mode == 0)
			setButton(back, "Back", 5, 5, 50, 20, 15);
		else
			setButton(back, "Back", 10, 10, 100, 35, 25);
		back.addActionListener(this);
		
		ArrayList<String> game = new ArrayList<String>();
		ArrayList<String> player = new ArrayList<String>();
		ArrayList<String> score = new ArrayList<String>();
		String game_name = "";
		String player_name = "";
		String score_name = "";
		
		try {
			FileReader fr = new FileReader("src/resources/Save_file.txt");
			int i;
			while((i=fr.read())!=-1) { 
				do
				{
					game_name += (char)i;
					continue;
				}while((i=fr.read())!=';');
				
				while((i=fr.read())!=';')
				{
					player_name += (char)i;
					continue;
				}
				while((i=fr.read())!='\n')
				{
					score_name += (char)i;
					continue;
				}
				
				if(game_name.equals(gameName)) {
					game.add(game_name);
					player.add(player_name);
					
					score.add(score_name);
					
				}

				game_name = "";
				player_name = "";
				score_name = "";
				
			}
			
			fr.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		String[][] data = new String[game.size()][3];
		
		Iterator<String> game_itr = game.iterator();
		Iterator<String> player_itr = player.iterator();
		Iterator<String> score_itr = score.iterator();
		
		
		for(int i=0; i<game.size(); i++) {
				data[i][0] = game_itr.next();
				data[i][1] = player_itr.next(); 
				data[i][2] = score_itr.next();

				for(int j=i; j>0; j--) {
					
					if(data[j][0].equals("Mine Sweeper")) {
						if(Integer.parseInt(data[j][2]) <= Integer.parseInt(data[j-1][2])) {
							String temp_game = data[j-1][0];
							String temp_player = data[j-1][1];
							String temp_score = data[j-1][2];
						
							data[j-1][0] = data[j][0];
							data[j-1][1] = data[j][1];
							data[j-1][2] = data[j][2];
						
							data[j][0] = temp_game;
							data[j][1] = temp_player;
							data[j][2] = temp_score;
						}
					}
					else
					if(Integer.parseInt(data[j][2]) >= Integer.parseInt(data[j-1][2])) {
						String temp_player = data[j-1][1];
						String temp_score = data[j-1][2];
						
						data[j-1][0] = ""+j;
						data[j-1][1] = data[j][1];
						data[j-1][2] = data[j][2];
						
						data[j][0] = ""+(j+1);
						data[j][1] = temp_player;
						data[j][2] = temp_score;
						
					}
					
				}
				
		}
		

		for(int i=0; i<game.size(); i++) {
			if(data[i][0].equals("Mine Sweeper"))
			{
				int len = data[i][2].length(); 
				String sec = ""+data[i][2].charAt(len-2)+data[i][2].charAt(len-1);
				String min = "";
				for(int j = 0; j<len-2; j++) {
					min += data[i][2].charAt(j);
				}
			
				if(min=="")
					min = "00";
			
				data[i][0] = ""+(i+1);
				data[i][2] = min+":"+sec;
			}
		}
		String column[] = {"Pos", "Player Name", "Score"};
		jt = new JTable(data, column);
		jt.setBackground(Color.BLACK);
		jt.setForeground(Color.GREEN);
		jt.setFont(new Font(null, Font.PLAIN, 15));
		jt.setBorder(BorderFactory.createEmptyBorder());
		jt.setGridColor(Color.GREEN);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		jt.setDefaultRenderer(String.class, centerRenderer);
		
		JTableHeader th = jt.getTableHeader();
		th.setBackground(Color.BLACK);
		th.setForeground(Color.GREEN);
		th.setFont(new Font(null, Font.PLAIN, 15));
		th.setBorder(BorderFactory.createEmptyBorder());

		jt.getColumnModel().getColumn(0).setPreferredWidth(40);
		jt.getColumnModel().getColumn(0).setResizable(false);
		
		jt.getColumnModel().getColumn(1).setPreferredWidth(100);
		jt.getColumnModel().getColumn(1).setResizable(false);
		
		jt.getColumnModel().getColumn(2).setMaxWidth(80);
		jt.getColumnModel().getColumn(2).setResizable(false);
		
		jt.setRowHeight(50);
		
		scrollBoard = new JScrollPane(jt);
		scrollBoard.getViewport().setBackground(Color.BLACK);
		scrollBoard.setBorder(BorderFactory.createEmptyBorder());
		scrollBoard.getVerticalScrollBar().setBackground(Color.BLACK);
		scrollBoard.setWheelScrollingEnabled(true);
		scrollBoard.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			
			protected void configureScrollBarColors() {
				this.thumbColor = Color.GREEN;
			}
		});
		
		
		
		if(Window.mode == 0)
			scrollBoard.setBounds(100, 150, 400, 400);
		else
			scrollBoard.setBounds((window.getWidth()-800)/2, 200, 800, 400);
		
		
		window.add(header);
		window.add(back);
		window.add(scrollBoard);
		
		window.add(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == back) {
			window.remove(header);
			window.remove(back);
			window.remove(scrollBoard);
			
			back = null; 
			header = null;
			
			this.setVisible(false);
			window.setLayout(null);
			new GameScore(); 
		}
		
	}

}
