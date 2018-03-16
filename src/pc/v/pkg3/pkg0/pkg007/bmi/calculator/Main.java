package pc.v.pkg3.pkg0.pkg007.bmi.calculator;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;

/**
 * Pro/g/ramming Challenges v 3.0 
 * 007 - BMI calculator
 * @author Emil.Karpowicz
 */
public class Main extends JFrame
{
    private static final int SCR_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int SCR_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    private JPanel mainPanel = new JPanel();
    
    private MyButton calculate = new MyButton("Oblicz");
    private MyButton reset = new MyButton("Wyczyść");
    
    private JPanel labelPanel = new JPanel();
    private JPanel sexChooser = new JPanel();
    private JPanel heightChooser = new JPanel();
    private JPanel weightChooser = new JPanel();
    private JPanel controlPanel = new JPanel();
    private JPanel resultPanel = new JPanel();
    
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton male = new JRadioButton("M", true);
    private JRadioButton female = new JRadioButton("K", false);
    
    private static MyTextField height = new  MyTextField();
    private static MyTextField weight = new  MyTextField();
    
    private static JLabel bmiLabel_Line1 = new JLabel("Twoje BMI: 0.00");
    private static JLabel bmiLabel_Line2 = new JLabel("Twój wskaźnik");
    
    public Main()
    {
        EventQueue.invokeLater(() -> 
        {
        this.setTitle("BMI Calculator");
        this.setSize(300, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        
        this.getContentPane().add(mainPanel);
        mainPanel.setLayout(new GridLayout(6,1));
        mainPanel.setBorder(BorderFactory.createBevelBorder(0));
        
        mainPanel.add(labelPanel);
            labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));
            JLabel titleLabel = new JLabel("Body mass index (BMI)");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            Box labelLine = Box.createHorizontalBox();
            labelPanel.add(Box.createVerticalStrut(10));
            labelPanel.add(labelLine);
            labelLine.add(titleLabel);
        mainPanel.add(sexChooser);
            male.setFont(new Font("Arial", Font.BOLD, 20));
            female.setFont(new Font("Arial", Font.BOLD, 20));
            JLabel plec = new JLabel("Płeć: ");
            plec.setPreferredSize(new Dimension(50,30));
            sexChooser.add(plec);
            buttonGroup.add(male);
            buttonGroup.add(female);
            sexChooser.add(male);
            sexChooser.add(female);
        mainPanel.add(heightChooser);
            JLabel wzrost = new JLabel("Wzrost: ");
            wzrost.setPreferredSize(new Dimension(50, 30));
            heightChooser.add(wzrost);
            height.setPreferredSize(new Dimension(200,30));
            heightChooser.add(height);
        mainPanel.add(weightChooser);
            JLabel waga = new JLabel("Waga: ");
            waga.setPreferredSize(new Dimension(50,30));
            weightChooser.add(waga);
            weight.setPreferredSize(new Dimension(200,30));
            weightChooser.add(weight);
        mainPanel.add(resultPanel);
            resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.PAGE_AXIS));
                Box upperLine = Box.createHorizontalBox();
                Box lowerLine = Box.createHorizontalBox();
                resultPanel.add(upperLine);
                resultPanel.add(lowerLine);
                bmiLabel_Line1.setFont(new Font("Arial", Font.BOLD, 20));
                bmiLabel_Line2.setFont(new Font("Arial", Font.BOLD, 15));
                upperLine.add(bmiLabel_Line1);
                lowerLine.add(bmiLabel_Line2);
        mainPanel.add(controlPanel);
            calculate.setPreferredSize(new Dimension(100,30));
            reset.setPreferredSize(new Dimension(100,30));
            controlPanel.add(calculate);
            controlPanel.add(reset);
        } );   
    }

    public static void main(String[] args) 
    {
       new Main().setVisible(true);
    }
    
    public static double getWeightToCalculate()
    {
        if (weight.getText().isEmpty()) return 0; 
        else return Double.parseDouble(weight.getText());
    }
    
    public static double getHeightToCalculate()
    {
        if (height.getText().isEmpty()) return 0; 
        else return Double.parseDouble(height.getText());
    }
    
    public static void changeBMI(Double BMI)
    {
        DecimalFormat df = new DecimalFormat("0.00");
        Main.bmiLabel_Line1.setText("Twoje BMI: "+ df.format(BMI));
        Main.bmiLabel_Line1.revalidate();
        Main.bmiLabel_Line2.setText(TranslateBmi.translateBmi(BMI));
        Main.bmiLabel_Line2.revalidate();
    }
            
    
}

class MyTextField extends JTextField
{
    MyTextField()
    {
        this.addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyTyped(KeyEvent e) 
            {
               char vchar = e.getKeyChar();
               if ( !Character.isDigit(vchar) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE)) e.consume();
             };
        });
    }
        
}

class MyButton extends JButton
{
    MyButton(String bLabel)
    {
        super(bLabel);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (((JButton)e.getSource()).getText() == "Oblicz") MyButton.calculate();
                else if (((JButton)e.getSource()).getText() == "Wyczyść") MyButton.reset();
            }
        });
    }
    
    public static void calculate()
    {
        double BMI = 0.0; 
        if ( Main.getWeightToCalculate() == 0 || Main.getHeightToCalculate() == 0) JOptionPane.showMessageDialog(null, new String("Enter proper height and weight values"), new String("Error"), JOptionPane.ERROR_MESSAGE);
        else BMI =  Main.getWeightToCalculate() / ((Main.getHeightToCalculate()/100)*(Main.getHeightToCalculate()/100));
        Main.changeBMI(BMI);
        
    }
    
    public static void reset()
    {
        System.out.println("Rest");
    }
    
}


class TranslateBmi
{
    static String bmiDescription;
    
    public static String translateBmi(double BMI)
    {
        if (BMI < 16) bmiDescription = "Wygłodzenie"; 
        else if (BMI >= 16 && BMI < 17) bmiDescription = "Wychudzenie"; 
        else if (BMI >= 17 && BMI < 18.5) bmiDescription = "Niedowaga"; 
        else if (BMI >= 18.5 && BMI < 25) bmiDescription = "Waga prawidłowa"; 
        else if (BMI >= 25 && BMI < 30) bmiDescription = "Nadwaga"; 
        else if (BMI >= 30 && BMI < 35) bmiDescription = "Otyłość I stopnia"; 
        else if (BMI >= 35 && BMI < 40) bmiDescription = "Otyłość II stopnia"; 
        else if (BMI >= 40) bmiDescription = "Otyłość III stopnia"; 
        
        return bmiDescription;
    }
    
}