package AirPlane.view;

import AirPlane.model.AirPlane;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;



public class AirPlaneGUI extends JFrame implements altitudeObserver {

    private JProgressBar progressBar;


    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel actionButtonsPanel;

    private JLabel airplaneActionState, jLabelAltitude, jLabelTargetAltitude;
    private AirplaneView airPlane;

    public AirPlaneGUI(AirPlane airPlane) {
        this.airPlane = airPlane;
        airPlane.setObserver(this);

        setTitle("Airplane Exercise");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createLeftPanel();
        createRightPanel();
        writeAirplaneLog("", false);

        pack();
        setSize(500, 400);
        setVisible(true);
    }

    private void createRightPanel() {

        rightPanel = new JPanel();

        leftPanel.add(rightPanel);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));


        airplaneActionState = new JLabel("Welcome to the airplane exercise!");
        airplaneActionState.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(airplaneActionState);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));


        JPanel airplaneStatePanel1 = new JPanel();
        airplaneStatePanel1.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel altitute = getStateJLabel("Altitude");
        JLabel targetAltitude = getStateJLabel("Target Altitude");
        titlePanel.add(altitute);
        titlePanel.add(targetAltitude);
        airplaneStatePanel1.add(titlePanel, BorderLayout.NORTH);



        jLabelAltitude = getStateJLabel("0");
        jLabelTargetAltitude = getStateJLabel("0");


        JPanel airplaneStatePanel2 = new JPanel();
        airplaneStatePanel2.setLayout(new FlowLayout(FlowLayout.CENTER));

        airplaneStatePanel2.add(jLabelAltitude);
        airplaneStatePanel2.add(jLabelTargetAltitude);
        airplaneStatePanel2.setSize(280, 50);

        airplaneStatePanel1.add(airplaneStatePanel2, BorderLayout.CENTER);
        rightPanel.add(airplaneStatePanel1);

        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setForeground(Color.green);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(progressBar);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 350)));
        rightPanel.setBounds(180, 23, 280, 500);
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private void createLeftPanel() {
        leftPanel = new JPanel();
        getContentPane().add(leftPanel, BorderLayout.CENTER);
        leftPanel.setLayout(null);

        actionButtonsPanel = new JPanel();
        FlowLayout ButtonsPanelLayout = new FlowLayout();
        leftPanel.add(actionButtonsPanel);
        actionButtonsPanel.setLayout(ButtonsPanelLayout);
        actionButtonsPanel.setBounds(12, 23, 150, 315);
        actionButtonsPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));

        JButton startMotor = getActionButton("Start Motor");
        actionButtonsPanel.add(startMotor);

        JButton takeOff = getActionButton("Take Off");
        actionButtonsPanel.add(takeOff);

        JButton stopMotor = getActionButton("Stop Motor");
        actionButtonsPanel.add(stopMotor);

        JButton increaseAltitude = getActionButton("Increase Altitude");
        actionButtonsPanel.add(increaseAltitude);

        JButton decreaseAltitude = getActionButton("Decrease Altitude");
        actionButtonsPanel.add(decreaseAltitude);

        JButton newAirplane = getActionButton("New Airplane");
//        actionButtonsPanel.add(newAirplane);

        JButton loadAirplane = getActionButton("Log Airplane");
//        actionButtonsPanel.add(loadAirplane);

        JButton readAirplane = getActionButton("Read Airplane");
        //actionButtonsPanel.add(readAirplane);


        startMotor.addActionListener(this::actionStartMotor);
        stopMotor.addActionListener(this::actionStopMotor);
        takeOff.addActionListener(this::actionTakeOff);
        increaseAltitude.addActionListener(this::actionIncreaseAltitude);
        decreaseAltitude.addActionListener(this::actionDecreaseAltitude);
        newAirplane.addActionListener(this::actionNewAirplane);
        readAirplane.addActionListener(this::actionReadAirplaneLog);
        loadAirplane.addActionListener(this::actionLogAirplane);
    }

    private JLabel getStateJLabel(String message) {

        JLabel jLabel = new JLabel(message, SwingConstants.CENTER);
        jLabel.setMinimumSize(new Dimension(80, 15));
        jLabel.setPreferredSize(new Dimension(80, 15));
        jLabel.setMaximumSize(new Dimension(80, 15));
        jLabel.setFont(new Font(jLabel.getName(), Font.PLAIN, 10));
        return jLabel;
    }


    private JButton getActionButton(String btnTitle) {
        JButton jButton = new JButton();
        jButton.setText(btnTitle);
        jButton.setPreferredSize(new Dimension(120, 23));
        return jButton;
    }

    private void actionStartMotor(ActionEvent event) {
        boolean isSuccess = false;

        try {
            isSuccess = airPlane.turnMotorOn();
        } catch (AirplaneException e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            writeAirplaneLog("StartMotor:" + e.getMessage(), true);
        } finally {
            if (isSuccess) {
                airplaneActionState.setText("Airplane Started.");
                writeAirplaneLog("StartMotor:SUCCESS:" + airplaneActionState.getText(), true);
            }
        }
    }

    private void actionTakeOff(ActionEvent event) {
        boolean isException = false;

        try {
            airPlane.takeOffAirPlane();
        } catch (AirplaneException e) {
            isException = true;
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
            writeAirplaneLog("TakeOff:" + e.getMessage(), true);
        } finally {
            if (!isException) {
                airplaneActionState.setText("We have take off ! Altitude increased to " + airPlane.getAltitude());
                writeAirplaneLog("TakeOff:SUCCESS:" + airplaneActionState.getText(), true);
            }
        }
    }

    private void actionStopMotor(ActionEvent event) {
        boolean isException = false;

        try {
            airPlane.turnMotorOff();
        } catch (AirplaneException e) {
            isException = true;
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            writeAirplaneLog("StopMotor:" + e.getMessage(), true);
        } finally {
            if (!isException) {
                airplaneActionState.setText("Stopping Motor");
                writeAirplaneLog("StopMotor:SUCCESS:" + airplaneActionState.getText(), true);
            }
        }
    }

    private void actionIncreaseAltitude(ActionEvent event) {
        boolean isException = false;
        try {
            airPlane.increaseAltitude();
        } catch (AirplaneWarningException e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            writeAirplaneLog("IncreaseAltitude:" + e.getMessage(), true);
        } catch (AirplaneException e) {
            isException = true;
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            writeAirplaneLog("IncreaseAltitude:" + e.getMessage(), true);
        } finally {
            if (!isException) {
                airplaneActionState.setText("Altitude increased to " + airPlane.getAltitude());
                writeAirplaneLog("IncreaseAltitude:SUCCESS:" + airplaneActionState.getText(), true);
            }
        }
    }

    private void actionDecreaseAltitude(ActionEvent event) {
        boolean isException = false;
        try {
            airPlane.decreaseAltitude();
        } catch (AirplaneWarningException e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
            writeAirplaneLog("DecreaseAltitude:" + e.getMessage(), true);
        } catch (AirplaneException e) {
            isException = true;
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            writeAirplaneLog("DecreaseAltitude:" + e.getMessage(), true);
        } finally {
            if (!isException) {
                airplaneActionState.setText("Altitude decreased to " + airPlane.getAltitude());
                if (airPlane.isLanded())
                    airplaneActionState.setText(airplaneActionState.getText() + "\n landed successfully");
                writeAirplaneLog("DecreaseAltitude:SUCCESS:" + airplaneActionState.getText(), true);
            }
        }
    }

    private void actionNewAirplane(ActionEvent event) {
        airPlane = null;
        airPlane = new AirPlane();
        jLabelTargetAltitude.setText("0");
        jLabelAltitude.setText("0");
        progressBar.setValue(0);
        airplaneActionState.setText("<html>Welcome to <br/> the airplane exercise!<html>");
        writeAirplaneLog("", false);
    }

    private void actionReadAirplaneLog(ActionEvent event) {
        String airplaneLog = "";
//
        String fileName = "airplane.txt";
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                airplaneLog += "\n" + scanner.nextLine();
            }
            new AirplaneLoadLogGUI(airplaneLog);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeAirplaneLog(String message, boolean isAppend) {
        String fileName = "airplane.txt";
        File file = new File(fileName);
        try (PrintWriter out = new PrintWriter(new FileOutputStream(file, isAppend))) {
            if (message != "")
                out.println(message);
            else
                out.print(message);
        } catch (FileNotFoundException exception) {
            System.out.println("if the file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason: " + file.getAbsolutePath());
        }
    }

    private void actionLogAirplane(ActionEvent actionEvent) {
        String airplaneLog = "";
//
        String fileName = "airplane.txt";
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words = line.split(":");
                System.out.println(words[1]);
                if (words[1].equals("SUCCESS")) {
                    System.out.printf("yes");
                    airplaneLog += words[0] + " : " + words[2] + "\n";
                }
            }
            System.out.println(airplaneLog);

            new AirplaneLogGUI(airplaneLog);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void altitudeChanged(int currentAltitude, int targetAltitude) {
        jLabelTargetAltitude.setText(String.valueOf(targetAltitude));
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (currentAltitude < targetAltitude)
                    actionPerformIncrease(currentAltitude,targetAltitude);
                else
                    actionPerformDecrease(currentAltitude,targetAltitude);
            }
        }).start();
    }

    private void actionPerformIncrease(int currentAltitude, int targetAltitude){
        DecimalFormat df2 = new DecimalFormat("#.##");
        double changeableAltitude = currentAltitude;
        while (Math.round(changeableAltitude)  < targetAltitude) {

            changeableAltitude +=  Double.parseDouble(df2.format (Math.round((targetAltitude - changeableAltitude) * 35.0) / 100.0));
            progressBar.setValue(((int) Math.round(((changeableAltitude * 100.0) / 12000.0))));
            jLabelAltitude.setText(String.valueOf(Math.round(changeableAltitude)));
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void actionPerformDecrease(int currentAltitude, int targetAltitude){
        DecimalFormat df2 = new DecimalFormat("#.##");
        double changeableAltitude = currentAltitude;
        while (Math.round(changeableAltitude)  > targetAltitude) {
            changeableAltitude -=  Double.parseDouble(df2.format (Math.round((changeableAltitude - targetAltitude) * 35.0) / 100.0));
            progressBar.setValue(((int) Math.round(((changeableAltitude * 100.0) / 12000.0))));
            jLabelAltitude.setText(String.valueOf(Math.round(changeableAltitude)));
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}