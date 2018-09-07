/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iPAYROLL;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class ViewCompensationRecordsFormController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private TextField checkno1;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField npay1;

    @FXML
    private ComboBox EmployeeIds1;

    @FXML
    private TextField paymentend1;

    @FXML
    private TextField accountno1;

    @FXML
    private Button OKBtn1;

    @FXML
    private TextField paybegin1;

    
    
   
    
   
    
     final ObservableList<String> data = FXCollections.observableArrayList();
    EmployeeProfilesAdapter employees;
    CompensationRecordsAdapter CompRECC;

    public void setModel(EmployeeProfilesAdapter _employees, CompensationRecordsAdapter _1CompRecc) {
        employees = _employees;
       CompRECC =_1CompRecc;
        submitEmployeeslist();
    }

     public void getProfile() {
        EmployeeProfiles oneEmpolyee = null;
        try {
            oneEmpolyee = employees.findProfile(this.EmployeeIds1.getValue().toString());

        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
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
     
     public void submitEmployeeslist() {
        try {
            data.addAll(employees.getEmployeeIDs());
        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
        }
    }
     public void ShowCompensationRecords(){
         //Input the retrived items from compensation records table into their respective textfields to display
         
       npay1.setText("Net Pay 1");
       paymentend1.setText("End of Payment1");
       accountno1.setText("Account number xx98122");
       paybegin1.setText("Beginning Date");
       checkno1.setText("1000911");
       
  
        
        
         
   
//       Double earnings=(moneyemployee.getWorkHours())*(moneyemployee.getRatePerHour());
//        this.EarningsBf1.setText(earnings.toString());
//        Double afterdeduc= earnings-(earnings*0.025);//due to given fixed rate of 2.5 percent.
//        this.FinalWage1.setText(afterdeduc.toString());
//        // Now sending final figure to the compensations table.
     
     }
     public void OkBtn(){
//          CompensationRecords comprecord2=null;
//        try {
//            comprecord2 = CompRECC.findCompensationRecord(this.EmployeeIds1.getValue().toString());
//
//        } catch (SQLException ex) {
//            displayAlert("ERROR: " + ex.getMessage());
//        }
        
        //This function will retrive all the necessary attributes of most archived Compensation Record from the SQL database of CompensationRecordsAdapter
        
        
        ShowCompensationRecords();
        
        
        }
      
     
     
        public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
        }
    
    
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         EmployeeIds1.setItems(data);
    }    
    
      
    
}
