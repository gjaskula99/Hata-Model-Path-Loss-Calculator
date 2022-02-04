package hata_calculator;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*Warning! Military Software Detected!
 * TOP SECRET CLEARANCE REQUIRED
 */

public class calculator extends JFrame implements ActionListener
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JFrame window = new JFrame();
	JLabel info = new JLabel();
	JButton field1 = new JButton();
	
	JLabel title = new JLabel("HATA MODEL LOSS CALCULATOR");
	
	JTextArea distance = new JTextArea();
	JLabel distance_text = new JLabel("Distance between TX and RX (in km)");
	JTextArea freq = new JTextArea();
	JLabel freq_text = new JLabel("Frequency (in MHz)");
	JTextArea bts = new JTextArea();
	JLabel bts_text = new JLabel("Height of BTS (in metres)");
	JTextArea mobile = new JTextArea();
	JLabel mobile_text = new JLabel("Height of mobile terminal (in metres)");
	JRadioButton suburban = new JRadioButton("Suburban");
	JRadioButton urban = new JRadioButton("Urban");
	JRadioButton open = new JRadioButton("Open");
	ButtonGroup area = new ButtonGroup();
	JButton calc = new JButton();
	JTextArea result_text = new JTextArea("Results will appear here.");
	JScrollPane result = new JScrollPane(result_text);
	private static DecimalFormat dB = new DecimalFormat("0.000");
	
	static int mode = 1;
	
	public calculator()
	{
		//JFrame
		System.out.println("Setting things up...");
		window.setSize(500, 450);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Hata Model Loss Calculator");
		window.setLayout(null);
		window.setResizable(false);
		
		//GUI init
		title.setBounds(100, 10, 300, 25);
		title.setFont(new Font("Serif", Font.PLAIN, 18));
		window.add(title);
		
		distance.setBounds(50, 50, 100, 20);
		distance.setEditable(true);
		distance_text.setBounds(160, 50, 300, 20);
		freq.setBounds(50, 80, 100, 20);
		freq.setEditable(true);
		freq_text.setBounds(160, 80, 300, 20);
		bts.setBounds(50, 110, 100, 20);
		bts.setEditable(true);
		bts_text.setBounds(160, 110, 300, 20);
		mobile.setBounds(50, 140, 100, 20);
		mobile.setEditable(true);
		mobile_text.setBounds(160, 140, 300, 20);
		
		urban.setBounds(200, 180, 100, 20);
		urban.setSelected(true);
		urban.addActionListener(this);
		suburban.setBounds(200, 210, 100, 20);
		suburban.addActionListener(this);
		open.setBounds(200, 240, 100, 20);
		open.addActionListener(this);
		area.add(urban);
		area.add(suburban);
		area.add(open);
		
		calc.setBounds(150, 300, 200, 25);
		calc.setText("CALCULATE");
		calc.addActionListener(this);
		
		result_text.setLineWrap(true);
		result_text.setWrapStyleWord(true);
		result_text.setEditable(false);
		result.setBounds(100, 340, 300, 70);
		
		window.add(distance);
		window.add(distance_text);
		window.add(freq);
		window.add(freq_text);
		window.add(bts);
		window.add(bts_text);
		window.add(mobile);
		window.add(mobile_text);
		
		window.add(urban);
		window.add(suburban);
		window.add(open);
		
		window.add(calc);
		window.add(result);
		
		//Visibility
		System.out.println("Ready");
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		System.out.println("Initializing...");
		new calculator();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==urban) mode = 1;
		if(e.getSource()==suburban) mode = 2;
		if(e.getSource()==open) mode = 3;
		if(e.getSource()==calc)
		{
			result_text.setText("");
			System.out.println("Calculating...");
			double f = 0, d = 0, base = 0, mob = 0;
			if(is_numeric(freq.getText()) && !freq.getText().isEmpty())f = Double.parseDouble(freq.getText());
			else
			{
				System.out.println("Frequency not set");
				result_text.setText("Frequency not set");
				return;
			}
			if(is_numeric(distance.getText()) && !distance.getText().isEmpty())d = Double.parseDouble(distance.getText());
			else
			{
				System.out.println("Distance not set");
				result_text.setText("Distance not set");
				return;
			}
			if(is_numeric(bts.getText()) && !bts.getText().isEmpty())base = Double.parseDouble(bts.getText());
			else
			{
				System.out.println("BTS height not set");
				result_text.setText("BTS height not set");
				return;
			}
			if(is_numeric(mobile.getText()) && !mobile.getText().isEmpty())mob = Double.parseDouble(mobile.getText());
			else
			{
				System.out.println("Mobile station height not set");
				result_text.setText("Mobile station height not set");
				return;
			}
			
			if(f <= 150 || f >= 1500)
			{
				System.out.println("Frequency out of bounds");
				result_text.setText("Frequency out of bounds");
				return;
			}
			if(d <= 0)
			{
				System.out.println("Distance must be more than zero");
				result_text.setText("Distance must be more than zero");
				return;
			}
			if(base <= 0)
			{
				System.out.println("BTS height must be more than zero");
				result_text.setText("BTS height must be more than zero");
				return;
			}
			if(mob <= 0)
			{
				System.out.println("Mobile terminal height must be more than zero");
				result_text.setText("Mobile terminal height must be more than zero");
				return;
			}
			
			//Data correct
			if(mode == 1)
			{
				result_text.setText("Results for urban:\n");
				double urban = get_urban(f, d, base, mob);
				System.out.println("Result is " + urban);
				result_text.setText(result_text.getText() + dB.format(urban) + " dB");
			}
			else if(mode == 2)
			{
				result_text.setText("Results for suburban:\n");
				double suburban = get_suburban(f, d, base, mob);
				System.out.println("Result is " + suburban);
				result_text.setText(result_text.getText() + dB.format(suburban) + " dB");
			}
			else if(mode == 3)
			{
				result_text.setText("Results for open:\n");
				double open = get_open(f, d, base, mob);
				System.out.println("Result is " + open);
				result_text.setText(result_text.getText() + dB.format(open) + " dB");
			}
			
		}
	}
	
	public static double get_a(double f, double mob)
	{
		if(mode == 1) return (1.1 * Math.log10(f))*mob - (1.56*Math.log10(f) - 0.8);
		if(mode == 2)
		{
			if(f < 200) return 8.29*(Math.pow(Math.log10(1.54*mob), 2)) - 1.1;
			else return 3.2*(Math.pow(Math.log10(11.75*mob), 2)) - 4.97;
		}
		return -1;
	}
	
	public static double get_urban(double f, double d, double base, double mob)
	{
		return 69.55 + 26.16 * Math.log10(f) - get_a(f, mob) - 13.83 * Math.log10(base) + (44.9 - 6.55 * Math.log10(base))*Math.log10(d);
	}
	
	public static double get_suburban(double f, double d, double base, double mob)
	{
		return get_urban(f, d, base, mob) - 2 * Math.pow( Math.log10( f/28 ) , 2) - 5.4;
	}
	
	public static double get_open(double f, double d, double base, double mob)
	{
		return get_urban(f, d, base, mob) - 4.78 * Math.pow( Math.log10(f) , 2) + 18.33 * Math.log10(f) - 40.94;
	}
	
	public static boolean is_numeric(String str)
	{ 
		try
		{  
		    Double.parseDouble(str);  
		    return true;
		}
		catch(NumberFormatException e)
		{  
		    return false;  
		}  
	}
}
