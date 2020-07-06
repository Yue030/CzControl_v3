package com.yue.czcontrol.window.features;

import com.yue.czcontrol.ExceptionBox;
import com.yue.czcontrol.connector.DBConnector;
import com.yue.czcontrol.object.Member;
import com.yue.czcontrol.utils.PDFGenerater;
import com.yue.czcontrol.utils.StackTrace;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

    /**
     * Table.
     */
    @FXML
    private TableView<Member> table;

    /**
     * id Column.
     */
    @FXML
    private TableColumn<Member, String> idColumn;
    /**
     * name Column.
     */
    @FXML
    private TableColumn<Member, String> nameColumn;
    /**
     * rank Column.
     */
    @FXML
    private TableColumn<Member, String> rankColumn;
    /**
     * active Column.
     */
    @FXML
    private TableColumn<Member, String> activeColumn;
    /**
     * handler Column.
     */
    @FXML
    private TableColumn<Member, String> handlerColumn;
    /**
     *
     */
    @FXML
    private TableColumn<Member, String> activeAllColumn;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        //Set Cell Value
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        rankColumn.setCellValueFactory(
                new PropertyValueFactory<>("rank"));
        activeColumn.setCellValueFactory(
                new PropertyValueFactory<>("active"));
        activeAllColumn.setCellValueFactory(
                new PropertyValueFactory<>("active_all"));
        handlerColumn.setCellValueFactory(
                new PropertyValueFactory<>("handler"));

        table.setEditable(false);
        table.setItems(getMembers());
    }

    /**
     * get Members.
     * @return members ObservableList
     */
    public ObservableList<Member> getMembers() {
        ObservableList<Member> members = FXCollections.observableArrayList();
        setMembers(members);
        return members;
    }

    /**
     * Set members profile to List.
     * @param members Members List
     */
    public void setMembers(final ObservableList<Member> members) {
        try {
            //Get data(SQL Syntax)
            String select = "SELECT * FROM player";
            //String select to PreparedStatement
            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(select);

            ResultSet rs = psst.executeQuery();

            //Detect data
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String rank = rs.getString("RANK");
                String active = rs.getString("ACTIVE");
                String active_all = rs.getString("ACTIVE_ALL");
                String handler = rs.getString("HANDLER");

                members.add(new Member(id, name, rank, active, active_all, handler));
            }
        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
        } finally {
            DBConnector.close();
        }
    }

    /**
     * Generate PDF File.
     * {@link PDFGenerater#generate()}
     */
    @FXML
    public void generatePDF() {
        PDFGenerater.generate();
    }
}
