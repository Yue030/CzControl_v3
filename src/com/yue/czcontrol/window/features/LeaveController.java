package com.yue.czcontrol.window.features;

import com.yue.czcontrol.ExceptionBox;
import com.yue.czcontrol.connector.DBConnector;
import com.yue.czcontrol.error.DBCloseFailedError;
import com.yue.czcontrol.error.DBConnectFailedError;
import com.yue.czcontrol.exception.UnknownException;
import com.yue.czcontrol.object.Leave;
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

public class LeaveController implements Initializable {
    /**
     * Table.
     */
    @FXML
    private TableView<Leave> table;
    /**
     * Id.
     */
    @FXML
    private TableColumn<Leave, String> idColumn;
    /**
     * Name.
     */
    @FXML
    private TableColumn<Leave, String> nameColumn;
    /**
     * Reason.
     */
    @FXML
    private TableColumn<Leave, String> reasonColumn;
    /**
     * Time.
     */
    @FXML
    private TableColumn<Leave, String> timeColumn;
    /**
     * Handler.
     */
    @FXML
    private TableColumn<Leave, String> handlerColumn;

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
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        reasonColumn.setCellValueFactory(
                new PropertyValueFactory<>("reason")
        );

        timeColumn.setCellValueFactory(
                new PropertyValueFactory<>("time")
        );

        handlerColumn.setCellValueFactory(
                new PropertyValueFactory<>("handler")
        );

        table.setEditable(false);
        try {
            table.setItems(getLeaves());
        } catch (DBCloseFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
            box.show();
        }
    }

    /**
     * get leaves.
     * @return members ObservableList
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    private ObservableList<Leave> getLeaves() throws DBCloseFailedError {
        ObservableList<Leave> leaves = FXCollections.observableArrayList();
        setLeave(leaves);
        return leaves;
    }

    /**
     * Set leave profile to List.
     * @param leaves Members List
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    private void setLeave(final ObservableList<Leave> leaves) throws DBCloseFailedError {
        try {
            //Get data(SQL Syntax)
            String select =
                    "SELECT b.id, NAME, DATE, REASON, b.HANDLER FROM"
                            + "`PLAYER` AS A INNER JOIN `leave`"
                            + "as b ON a.id = b.lid";
            //String select to PreparedStatement
            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(select);

            ResultSet rs = psst.executeQuery();

            //Detect data
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String date = rs.getString("DATE");
                String reason = rs.getString("REASON");
                String handler = rs.getString("HANDLER");

                leaves.add(new Leave(id, name, date, reason, handler));
            }
        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
        } catch (DBConnectFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        } finally {
            DBConnector.close();
        }
    }
}
