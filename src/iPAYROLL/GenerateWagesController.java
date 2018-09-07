/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iPAYROLL;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class GenerateWagesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
        @FXML
    private Button ChooseAll1;

    @FXML
    private TextField Hours1;

    @FXML
    private TextField EarningsBf1;

    @FXML
    private TextField FinalWage1;

    @FXML
    private ComboBox EmployeeIds1;

    @FXML
    private TextField PayForm1;

    @FXML
    private Button GenWage1;

    @FXML
    private TextField Pay1;

    @FXML
    private TextField Name1;
    
    @FXML
    private CheckBox Exempt;
    
    @FXML
    private Button cancelBtn;
    
   
    private Double afterdeduc;
    
    private double number;
    

    final ObservableList<String> data = FXCollections.observableArrayList();
    EmployeeProfilesAdapter employees;
    CompensationRecordsAdapter CompRecord1;
    
    
    public void setModel(EmployeeProfilesAdapter _employees,CompensationRecordsAdapter _1CompRecc) {
        employees = _employees;
         CompRecord1 =_1CompRecc;
        buildData();
    }

     public void getProfile() {
        EmployeeProfiles oneEmpolyee = null;
        try {
            oneEmpolyee = employees.findProfile(this.EmployeeIds1.getValue().toString());

        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
        }
        this.Name1.setText(oneEmpolyee.getName());
       
        this.Hours1.setText(oneEmpolyee.getWorkHours().toString());
        this.Pay1.setText(oneEmpolyee.getRatePerHour().toString());
        this.PayForm1.setText(oneEmpolyee.getPayForm());
        
        if (oneEmpolyee.getExempt() == 1) {
            this.Exempt.setSelected(true);
        } else {
            this.Exempt.setSelected(false);
        }

    }

    
     
     private void displayAlert(String msg) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent ERROR = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/iPAYROLL/WesternLogo.png"));
            controller.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }
     
     public void buildData() {
        try {
            data.addAll(employees.getEmployeeIDs());
        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
        }
    }
     
     
     public void GeneratingWages() throws SQLException{
        EmployeeProfiles moneyemployee=null;
        
        try {
            moneyemployee = employees.findProfile(this.EmployeeIds1.getValue().toString());

        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
        }
        
         
   
       Double earnings=(moneyemployee.getWorkHours())*(moneyemployee.getRatePerHour());
        this.EarningsBf1.setText(earnings.toString());
        
        afterdeduc= earnings-(earnings*0.025);//due to given fixed rate of 2.5 percent.
         number= afterdeduc;
        this.FinalWage1.setText(afterdeduc.toString());
        
        //recordPay();// Column of double cannot hodl values of type CHAR error messsage despite number a double type variable being based into netPay, so diabled for now.
         displayAlert("Wages have been generated!");
        cancel();
        
     }
     
     public void recordPay() throws SQLException{
        // Now sending final figure to the compensations table.
//         CompensationRecords compensationrec=null;
//         
//         try {
//            compensationrec = CompRecord1.insertCompensationRecord();
//
//        }  catch (SQLException ex) {
//            displayAlert("ERROR: " + ex.getMessage());
//        }
         
      Random rand = new Random();
        int n= rand.nextInt(1000);
        String num= Integer.toString(n);
        
        Random rand1 = new Random();
        int n1= rand1.nextInt(888);
        String num2= Integer.toString(n1);
        
        CompensationRecords compensationrec=new CompensationRecords();
        
         compensationrec.setID(this.EmployeeIds1.getValue().toString());
         compensationrec.setNetPay(number);
         compensationrec.setPayCheckNo(num);
         compensationrec.setPayAccountNo(num2);
         
         java.util.Date uDate = new java.util.Date();
         compensationrec.setPayDate(new java.sql.Date(uDate.getTime()));
         compensationrec.setPayBegin(new java.sql.Date(uDate.getTime()));
         compensationrec.setPayEnd(new java.sql.Date(uDate.getTime()));
            CompRecord1.insertCompensationRecord(compensationrec);
            
       
     }
     
      public void getAllEmployeeDetails() throws SQLException{
        EmployeeProfiles moneyemployee=null;
        for (int i=0; i<data.size();i++){
        try {
            moneyemployee = employees.findProfile(data.get(i));

        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
        }
        
       Double earnings=(moneyemployee.getWorkHours())*(moneyemployee.getRatePerHour());
       
       afterdeduc= earnings-(earnings*0.025);//due to given fixed rate of 2.5 percent.
    //  recordPay();// NULL Pointer exception error despite intializing up top and from the main form controller so disabled.
        // Now sending final figure to the compensations table.
        }

     }
     
     
        public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        EmployeeIds1.setItems(data);
       Name1.setEditable(false);
       Hours1.setEditable(false);
        Pay1.setEditable(false);
         PayForm1.setEditable(false);
          EarningsBf1.setEditable(false);
           FinalWage1.setEditable(false);
    }    
    
}
