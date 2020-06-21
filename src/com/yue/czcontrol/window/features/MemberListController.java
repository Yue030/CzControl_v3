package com.yue.czcontrol.window.features;

import com.yue.czcontrol.object.Member;
import com.yue.czcontrol.window.SplashController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MemberListController implements Initializable {

    //Fxml Table
    public TableView<Member> table;

    //Fxml Columns
    public TableColumn<Member, String> idColumn;
    public TableColumn<Member, String> nameColumn;
    public TableColumn<Member, String> rankColumn;
    public TableColumn<Member, String> activeColumn;
    public TableColumn<Member, String> handlerColumn;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Set Cell Value
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
        handlerColumn.setCellValueFactory(new PropertyValueFactory<>("handler"));

        table.setEditable(false);
        table.setItems(getMembers());
    }

    /**
     * get Members
     * @return members ObservableList
     */
    public ObservableList<Member> getMembers(){
        ObservableList<Member> members = FXCollections.observableArrayList();
        setMembers(members);
        return members;
    }

    /**
     * Set members profile to List
     * @param members Members List
     */
    public void setMembers(ObservableList<Member> members){
        try{
            //Get data(SQL Syntax)
            String select = "SELECT * FROM player";
            //String select to PreparedStatement
            PreparedStatement psst = SplashController.getConn().prepareStatement(select);

            ResultSet rs = psst.executeQuery();

            //Detect data
            while(rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String rank = rs.getString("RANK");
                String active = rs.getString("ACTIVE");
                String handler = rs.getString("HANDLER");

                members.add(new Member(id, name, rank, active, handler));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
