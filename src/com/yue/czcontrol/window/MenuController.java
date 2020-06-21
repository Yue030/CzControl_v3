package com.yue.czcontrol.window;

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
     * The Border Pane
     */
    public BorderPane bp;

    //FXML Button
    public Button mainBtn;
    public Button memberListBtn;
    public Button addMemberBtn;
    public Button deleteMemberBtn;
    public Button editMemberBtn;
    public Button leaveBtn;
    public Button logoutBtn;
    public Button messageBtn;

    /**
     * If the ButtomBar is set effect, it will be in the list
     */
    private final List<ButtonBar> barList = new ArrayList<>();

    /**
     * Set Fade in and out effect to ButtonBar
     * @param mouseEvent MouseEvent
     */
    @FXML
    public void fadeBar(MouseEvent mouseEvent) {
        ButtonBar bar = (ButtonBar) mouseEvent.getSource();
        if(!barList.contains(bar)){
            barList.add(bar);
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.4);
            bar.setEffect(colorAdjust);

            bar.setOnMouseEntered(e1 -> {
                Timeline fadeInTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                                new KeyValue(colorAdjust.brightnessProperty(), colorAdjust.brightnessProperty().getValue(), Interpolator.LINEAR)),
                        new KeyFrame(Duration.seconds(0.5), new KeyValue(colorAdjust.brightnessProperty(), 0.4, Interpolator.LINEAR)
                        ));
                fadeInTimeline.setCycleCount(1);
                fadeInTimeline.setAutoReverse(false);
                fadeInTimeline.play();

            });

            bar.setOnMouseExited(e1 -> {
                Timeline fadeOutTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                                new KeyValue(colorAdjust.brightnessProperty(), colorAdjust.brightnessProperty().getValue(), Interpolator.LINEAR)),
                        new KeyFrame(Duration.seconds(0.5), new KeyValue(colorAdjust.brightnessProperty(), 0, Interpolator.LINEAR)
                        ));
                fadeOutTimeline.setCycleCount(1);
                fadeOutTimeline.setAutoReverse(false);
                fadeOutTimeline.play();
            });
        }
    }

    /**
     * Get Button to change Scene
     * @param event ActionEvent
     * @throws IOException IOException
     */
    @FXML
    public void change(ActionEvent event) throws IOException {
        if(event.getSource() == mainBtn){
            changeTo("Home");
        } else if(event.getSource() == memberListBtn){
            changeTo("MemberList");
        } else if(event.getSource() == addMemberBtn){
            changeTo("AddMember");
        } else if(event.getSource() == deleteMemberBtn){
            changeTo("DeleteMember");
        } else if(event.getSource() == editMemberBtn){
            changeTo("EditMember");
        } else if(event.getSource() == leaveBtn){
            changeTo("Leave");
        } else if(event.getSource() == logoutBtn){
            changeTo("Logout");
        } else if(event.getSource() == messageBtn){
            changeTo("Message");
        }
    }

    /**
     * Change Scene to the Specific file
     * @param fileName FileName
     */
    private void changeTo(String fileName){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("fxml/features/" + fileName + ".fxml"));
            bp.setCenter(root);
        } catch(IOException e){
            e.printStackTrace();
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
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/features/Home.fxml"));
            bp.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
