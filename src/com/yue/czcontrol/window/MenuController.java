package com.yue.czcontrol.window;
import com.yue.czcontrol.AlertBox;
import com.yue.czcontrol.ErrorCode;
import com.yue.czcontrol.ExceptionBox;
import com.yue.czcontrol.exception.UnknownException;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    /**
     * The Border Pane.
     */
    @FXML
    private BorderPane bp;
    /**
     * Main Btn.
     */
    @FXML
    private Button mainBtn;
    /**
     * MemberList Btn.
     */
    @FXML
    private Button memberListBtn;
    /**
     * Add Member Btn.
     */
    @FXML
    private Button addMemberBtn;
    /**
     * Delete Member Btn.
     */
    @FXML
    private Button deleteMemberBtn;
    /**
     * Edit Member Btn.
     */
    @FXML
    private Button editMemberBtn;
    /**
     * Leave Btn.
     */
    @FXML
    private Button leaveBtn;
    /**
     * Logout Btn.
     */
    @FXML
    private Button logoutBtn;
    /**
     * Message Btn.
     */
    @FXML
    private Button messageBtn;
    /**
     * Add Leave Btn.
     */
    @FXML
    private Button addLeaveBtn;

    /**
     * If the ButtonBar is set effect, it will be in the list.
     */
    private final List<ButtonBar> barList = new ArrayList<>();

    /**
     * Set Fade in and out effect to ButtonBar.
     * @param mouseEvent MouseEvent
     */
    @FXML
    public void fadeBar(final MouseEvent mouseEvent) {
        ButtonBar bar = (ButtonBar) mouseEvent.getSource();
        if (!barList.contains(bar)) {
            barList.add(bar);
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.4);
            bar.setEffect(colorAdjust);

            bar.setOnMouseEntered(e1 -> {
                Timeline fadeInTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                                new KeyValue(colorAdjust.brightnessProperty(),
                                        colorAdjust.brightnessProperty()
                                                .getValue(),
                                        Interpolator.LINEAR)),
                        new KeyFrame(Duration.seconds(0.5),
                                new KeyValue(colorAdjust.brightnessProperty(),
                                        0.4,
                                        Interpolator.LINEAR)
                        ));
                fadeInTimeline.setCycleCount(1);
                fadeInTimeline.setAutoReverse(false);
                fadeInTimeline.play();

            });

            bar.setOnMouseExited(e1 -> {
                Timeline fadeOutTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                                new KeyValue(colorAdjust.brightnessProperty(),
                                        colorAdjust.brightnessProperty()
                                                .getValue(),
                                        Interpolator.LINEAR)),
                        new KeyFrame(Duration.seconds(0.5),
                                new KeyValue(colorAdjust.brightnessProperty(),
                                        0,
                                        Interpolator.LINEAR)
                        ));
                fadeOutTimeline.setCycleCount(1);
                fadeOutTimeline.setAutoReverse(false);
                fadeOutTimeline.play();
            });
        }
    }

    /**
     * Get Button to change Scene.
     * @param event ActionEvent
     */
    @FXML
    public void change(final ActionEvent event) {
        if (event.getSource() == mainBtn) {
            changeTo("Home");
        } else if (event.getSource() == memberListBtn) {
            changeTo("MemberList");
        } else if (event.getSource() == addMemberBtn) {
            changeTo("AddMember");
        } else if (event.getSource() == deleteMemberBtn) {
            changeTo("DeleteMember");
        } else if (event.getSource() == editMemberBtn) {
            changeTo("EditMember");
        } else if (event.getSource() == leaveBtn) {
            changeTo("Leave");
        } else if (event.getSource() == messageBtn) {
            changeTo("Message");
        } else if(event.getSource() == addLeaveBtn) {
            changeTo("AddLeave");
        } else if (event.getSource() == logoutBtn) {
            AlertBox.show("Logout", "Are you sure to logout?", AlertBox.Type.LOGOUT);
        }
    }

    /**
     * Change Scene to the Specific file.
     * @param fileName FileName
     */
    private void changeTo(final String fileName) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().
                            getResource("fxml/features/" + fileName + ".fxml"));
            bp.setCenter(root);
        } catch (IOException e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        }
    }

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
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("fxml/features/Home.fxml"));
            bp.setCenter(root);
        } catch (IOException e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        }
    }
}
