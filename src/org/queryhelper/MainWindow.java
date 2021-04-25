package org.queryhelper;

import java.awt.BorderLayout;
import java.awt.Color;
// import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
// import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

/** @author Soe Min Htut */
public class MainWindow {

  private JButton     runButton;
  private JFrame      frmQueryHelper;
  private JLabel      leftLabel;
  private JLabel      rightLabel;
  private JLabel      topLabel;
  private JTextArea   leftTextArea;
  private JTextArea   rightTextArea;
  private JTextField  topTextField;
  private QueryHelper queryhelper;
  //  private MyOwnFocusTraversalPolicy newPolicy;

  /** Launch the application. */
  public static void main(String[] args) {
    EventQueue.invokeLater(
        new Runnable() {
          public void run() {
            try {
              MainWindow window = new MainWindow();
              window.frmQueryHelper.setVisible(true);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
  }

  /** Create the application. */
  public MainWindow() {
    initialize();
  }

  /** Initialize the contents of the frame. */
  private void initialize() {

    try {
      for (javax.swing.UIManager.LookAndFeelInfo info :
          javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(MainWindow.class.getName())
          .log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(MainWindow.class.getName())
          .log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(MainWindow.class.getName())
          .log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(MainWindow.class.getName())
          .log(java.util.logging.Level.SEVERE, null, ex);
    }

    frmQueryHelper = new JFrame();
    frmQueryHelper.setFont(new Font("Dialog", Font.PLAIN, 30));
    frmQueryHelper.setTitle("Query Helper");
    frmQueryHelper.setBounds(100, 100, 850, 600);
    frmQueryHelper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frmQueryHelper.getContentPane().setLayout(new BorderLayout(5, 5));

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    frmQueryHelper.setLocation(
        dim.width / 2 - frmQueryHelper.getSize().width / 2,
        dim.height / 2 - frmQueryHelper.getSize().height / 2);

    JPanel topPanel = new JPanel();
    topPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
    frmQueryHelper.getContentPane().add(topPanel, BorderLayout.NORTH);
    topPanel.setLayout(new BorderLayout(5, 5));

    topTextField = new JTextField();
    topTextField.setToolTipText(
        "Formula textbox in which formula texts have to be using with # symbol");
    topTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
    topPanel.add(topTextField, BorderLayout.CENTER);
    topTextField.setColumns(20);

    JPanel centerPanel = new JPanel();
    frmQueryHelper.getContentPane().add(centerPanel, BorderLayout.CENTER);
    centerPanel.setLayout(new GridLayout(1, 2, 5, 5));

    JPanel leftPanel = new JPanel();
    leftPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
    leftPanel.setLayout(new BorderLayout(5, 5));

    leftLabel = new JLabel(" Input Data Textbox");

    JScrollPane leftScrollPane = new JScrollPane();
    leftPanel.add(leftLabel, BorderLayout.NORTH);
    leftPanel.add(leftScrollPane, BorderLayout.CENTER);
    centerPanel.add(leftPanel);

    leftTextArea = new JTextArea();
    leftTextArea.setToolTipText(
        "Input data textbox in which data have to be separated by ; symbol\r\n");
    leftTextArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
    leftScrollPane.setViewportView(leftTextArea);

    leftLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
    leftLabel.setDisplayedMnemonic(KeyEvent.VK_I);
    leftLabel.setLabelFor(leftTextArea);

    JPanel rightPanel = new JPanel();
    rightPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
    rightPanel.setLayout(new BorderLayout(5, 5));

    rightLabel = new JLabel(" Output Data Textbox");
    JScrollPane rightScrollPane = new JScrollPane();
    rightPanel.add(rightLabel, BorderLayout.NORTH);
    rightPanel.add(rightScrollPane, BorderLayout.CENTER);
    centerPanel.add(rightPanel);

    rightTextArea = new JTextArea();
    rightTextArea.setToolTipText(
        "Output data textbox in which result will be placed by 'run' button's click action");
    rightTextArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
    rightScrollPane.setViewportView(rightTextArea);

    rightLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
    rightLabel.setDisplayedMnemonic(KeyEvent.VK_O);
    rightLabel.setLabelFor(rightTextArea);

    runButton = new JButton("Run");
    runButton.setMnemonic('R');
    runButton.setToolTipText(
        "Run button is to generate output data, by inserting input data into the formula");

    runButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {

            queryhelper =
                new QueryHelper(
                    topTextField.getText(), leftTextArea.getText(), rightTextArea.getText());
            queryhelper.execute();

            if (queryhelper.getMsg().equals("OK")) {
              rightTextArea.setText(queryhelper.getRightText());
            } else {
              rightTextArea.setText(null);
              JOptionPane.showMessageDialog(frmQueryHelper, queryhelper.getMsg());
            }
          }
        });

    runButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
    topPanel.add(runButton, BorderLayout.EAST);

    topLabel = new JLabel(" Formula Text = ");
    topLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
    topPanel.add(topLabel, BorderLayout.WEST);
    topLabel.setDisplayedMnemonic(KeyEvent.VK_F);
    topLabel.setLabelFor(topTextField);

    UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 18));

    topTextField.setRequestFocusEnabled(true);

    topTextField.setNextFocusableComponent(leftTextArea);
    leftTextArea.setNextFocusableComponent(runButton);
    runButton.setNextFocusableComponent(rightTextArea);
    rightTextArea.setNextFocusableComponent(topTextField);

    //    Vector<Component> order = new Vector<Component>(4);
    //    order.add(topTextField);
    //    order.add(leftTextArea);
    //    order.add(runButton);
    //    order.add(rightTextArea);
    //    newPolicy = new MyOwnFocusTraversalPolicy(order);
  }
}
