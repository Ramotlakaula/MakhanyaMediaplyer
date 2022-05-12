package com.example.makhanyavideo;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class HelloController implements Initializable {
    @FXML
    private MediaView mediaView;
    private MediaPlayer player;
    private MediaPlayer mp;

    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;

    @FXML
    private Slider volumeSlider;

    @FXML
    private HBox man;



    @FXML
    private Label durationlab = new Label();
    @FXML
    private Slider prograssbar = new Slider();
    @FXML
    private ImageView imageview;

    @FXML
    private ImageView playone;

    @FXML
    private ImageView onecontinue;

    @FXML
    private ImageView pausebtn;

    @FXML
    private ImageView oneplay;

    @FXML
    private ImageView shuffel;

    @FXML
    private Label exit;

    @FXML
    private ProgressBar progressbar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String video = getClass().getResource("/School.mp4").toExternalForm();

        Media media = new Media(video);
        player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);
        Makhanya();
        volumeSliderVolume();

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(volumeSlider.isValueChanging()){
                    player.setVolume(volumeSlider.getValue()/100);
                }
            }
        });
        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                prograssbar.setValue(newValue.toSeconds());
            }
        });
        prograssbar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.seek(Duration.seconds(prograssbar.getValue()));
            }
        });

        prograssbar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.seek(Duration.seconds(prograssbar.getValue()));
            }
        });

        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                Duration total=media.getDuration();
                prograssbar.setMax(total.toSeconds());
            }
        });
    }


    public void Makhanya(){
        durationlab.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(player.getCurrentTime()) + " / 0:30";
            }
        },player.currentTimeProperty()));
    }

    public String getTime(Duration time){
        int hours = (int) time.toHours();
        int minutes = (int) time.toMinutes();
        int seconds = (int) time.toSeconds();

        if (seconds > 59) seconds = seconds % 60;
        if (minutes > 59) minutes = minutes % 60;
        if (hours > 59) hours = hours % 60;

        if(hours > 0 ) return String.format("%d:%02d%02d",
                hours, minutes, seconds);

        else return String.format("%02d:%02d",
                minutes,seconds);
    }

    @FXML
   public void volumeSliderVolume() {
        volumeSlider.setValue(player.getVolume()*100);
        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if(volumeSlider.isValueChanging()){
                            player.setVolume(volumeSlider.getValue()/100);
                        }
                    }
                });
            }
        });
    }
    @FXML
    void pausebtnVideo(MouseEvent event) {
        player.pause();
    }
    @FXML
    void playoneVideo(MouseEvent event) {
        player.play();
    }
    @FXML
    void onecontinueVideo(MouseEvent event) {
        player.pause();
    }

    @FXML
    void oneplayered(MouseEvent event) {
        player.stop();
    }

    @FXML
    void exitMusic(MouseEvent event) {
        Platform.exit();
    }


}
