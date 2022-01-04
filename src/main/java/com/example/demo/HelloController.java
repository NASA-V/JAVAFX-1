
package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.sqlite.JDBC;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class HelloController {

    public final String Connection_String = "jdbc:sqlite::memory:";
    private int counter;


    public Button btnDatalist;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAddSeries"
    private Button btnAddSeries; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="btnEnter"
    private Button btnEnter; // Value injected by FXMLLoader

    @FXML // fx:id="btnGenerate"
    private Button btnGenerate; // Value injected by FXMLLoader

    @FXML // fx:id="cmbDataset_List"
    private ComboBox<String> cmbDataset_List; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSeries_List"
    private ComboBox<String> cmbSeries_List; // Value injected by FXMLLoader

    ObservableList<String> Series_Names = FXCollections.observableArrayList();
    ObservableList<String> Datalist_Names = FXCollections.observableArrayList();


    @FXML // fx:id="tblDataset"
    private TableView<All_Series> tblDataset; // Value injected by FXMLLoader

    //    @FXML
//    public TableColumn<All_Series, Integer> _id;
    @FXML
    public TableColumn<All_Series, Integer> _X;
    @FXML
    public TableColumn<All_Series, Integer> _Y;

    @FXML // fx:id="tfX_Axis"
    private TextField tfX_Axis; // Value injected by FXMLLoader

    @FXML // fx:id="tfY_Axis"
    private TextField tfY_Axis; // Value injected by FXMLLoader

    private All_Series allSeries;


    //btnnewdataset click
    @FXML
    void CreateDataset(ActionEvent event) {
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Dataset");
        td.showAndWait();
        String s = td.getEditor().getText();
        Datalist_Names.add(s);
        btnAddSeries.setDisable(false);
    }

    //btnenter click
    @FXML
    void AddEntry(ActionEvent event) {
        int x = Integer.parseInt(tfX_Axis.getText());
        int y = Integer.parseInt(tfY_Axis.getText());


        if (!tfX_Axis.getText().isEmpty() && !tfY_Axis.getText().isEmpty()) {
//            insertAll_Series(Double.parseDouble(tfX_Axis.getText()), Double.parseDouble(tfY_Axis.getText()));
            tfX_Axis.setText("0");
            tfY_Axis.setText("0");
            All_Series series = new All_Series(x, y);
            setseries(series);
            tblDataset.getItems().add(series);
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "Must Enter a number", ButtonType.OK);
            alert.showAndWait();
        }
        btnAddSeries.setDisable(false);

    }

    private void setseries(All_Series allSeries) {
        this.allSeries = allSeries;
    }

    private Connection connect() {
        Connection conn = null;
        try {
            DriverManager.registerDriver(new JDBC());
            conn = getConnection(Connection_String);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //btnnewseries click
    @FXML
    void AddSeries(ActionEvent event) {
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Enter Series Name");
        td.showAndWait();
        //where s is the name of the series
        String s = td.getEditor().getText();
        Series_Names.add(s);
        tblDataset.setItems(null);

        counter++;
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet res = null;
            res = stmt.executeQuery("SELECT name from sqlite_master where type='table' and name = 'All_Series'");
            if (!res.next()) {
                stmt.execute("create table All_Series(_id INT default 1,_X  DOUBLE precision,_Y  DOUBLE precision)");
            }

            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO All_Series VALUES (?,?,?)");
            preparedStatement.setDouble(1, this.counter);
            double x;
            double y;


//                x = Double.parseDouble(tblDataset.getItems(i, 0).toString());
//                y = Double.parseDouble(tblDataset.getValueAt(i, 1).toString());

//
//                String query = "insert into All_Series values(?,?,?)";
//                PreparedStatement statement = conn.prepareStatement(query);
//                statement.setDouble(1, this.counter);
//                statement.setDouble(2, x);
//                statement.setDouble(3, y);
//                statement.executeUpdate();
            res.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }




       /* EventHandler<ActionEvent> SelectedSeries =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        if(cmbSeries_List.getValue().equalsIgnoreCase())
                        selected.setText(combo_box.getValue() + " selected");
                    }
                };*/

        btnEnter.setDisable(false);
        btnCancel.setDisable(false);
        tfY_Axis.setDisable(false);
        tfX_Axis.setDisable(false);
    }


    @FXML
    void GenerateGraph(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE, "you clicked add series", ButtonType.OK);
        alert.showAndWait();
    }

    //btncancel click
    @FXML
    void OnCancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "This will delete your series information, are you sure?", ButtonType.NO, ButtonType.OK);

        alert.showAndWait();

    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAddSeries != null : "fx:id=\"btnAddSeries\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert btnEnter != null : "fx:id=\"btnEnter\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert btnGenerate != null : "fx:id=\"btnGenerate\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert cmbDataset_List != null : "fx:id=\"cmbDataset_List\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert cmbSeries_List != null : "fx:id=\"cmbSeries_List\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert tblDataset != null : "fx:id=\"tblDataset\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert tfX_Axis != null : "fx:id=\"tfX_Axis\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert tfY_Axis != null : "fx:id=\"tfY_Axis\" was not injected: check your FXML file 'hello-view.fxml'.";
        _X.setCellValueFactory(new PropertyValueFactory<>("_X"));
        _Y.setCellValueFactory(new PropertyValueFactory<>("_Y"));
        tfX_Axis.setText("0");
        tfY_Axis.setText("0");
        cmbDataset_List.setItems(Datalist_Names);
        cmbSeries_List.setItems(Series_Names);
//        tblDataset.setItems(series);
//        _id.setCellValueFactory(new PropertyValueFactory<>("_id"));
        btnGenerate.setDisable(true);
        btnAddSeries.setDisable(true);
        btnDatalist.setDisable(true);
        btnCancel.setDisable(true);



    }


}
