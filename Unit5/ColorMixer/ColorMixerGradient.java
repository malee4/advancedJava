package Unit5.ColorMixer;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.control.Button;

import javafx.scene.paint.*;
import javafx.scene.paint.Stop;
import java.lang.Integer;

public class ColorMixerGradient extends Application {
    private static boolean isRGB;
    
    private static final int WIDTH = 400;
    private static final int HEIGHT = 700;

    private static Label label1;
    private static Label label2;
    private static Label label3;

    private static Slider slider1;
    private static Slider slider2;
    private static Slider slider3;

    private static Color color;
    private static Color color1 = Color.WHITE; 
    private static Color color2 = Color.WHITE;

    private static Label rgb;
    private static Label hsb;
    private static Label hex;

    private static Background background;
    private static Rectangle rectangle;
    private static LinearGradient lg;
    private static VBox sceneContainer;
    private static Stop[] stops;

    public static String getHex() {
        String out = "#";
        out += Integer.toHexString((int) (color.getRed() * 255));
        out += Integer.toHexString((int) (color.getGreen() * 255));
        out += Integer.toHexString((int) (color.getBlue() * 255));
        
        return out.toUpperCase();
    }

    public static boolean isWhite() {
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);

        double y = 0.2126*red+0.7152*green+0.0722*blue;
        if (y > 127) return false;
        else return true;
    }

    public static void updateCodes() {
        color = Color.color(slider1.getValue(), slider2.getValue(), slider3.getValue());
        rectangle.setFill(color);
        // background = new Background(new BackgroundFill(color, null, null));
        // sceneContainer.setBackground(background);

        // update each of the labels
        String rgbText = "RGB Values: (" + (int) (color.getRed() * 255.0) + ", " + (int) (color.getGreen() * 255.0) + ", " + (int) (color.getBlue() * 255.0) + ")";
        rgb.setText(rgbText);

        String hsbText = "HSB Values: (" + (int) (color.getHue()) + " degrees, " + (int) (color.getSaturation() * 100.0) + "%, " + (int) (color.getBrightness() * 100.0) + "%)";
        hsb.setText(hsbText);

        String hexText = "Hex Code: " + getHex();
        hex.setText(hexText);

        if (isWhite()) {
            rgb.setTextFill(Color.WHITE);
            hsb.setTextFill(Color.WHITE);
            hex.setTextFill(Color.WHITE);

            label1.setTextFill(Color.WHITE);
            label2.setTextFill(Color.WHITE);
            label3.setTextFill(Color.WHITE);
        } else {
            rgb.setTextFill(Color.BLACK);
            hsb.setTextFill(Color.BLACK);
            hex.setTextFill(Color.BLACK);

            label1.setTextFill(Color.BLACK);
            label2.setTextFill(Color.BLACK);
            label3.setTextFill(Color.BLACK);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        stops = new Stop[] {new Stop(0, color1), new Stop(1, color2)};
        lg = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        rectangle = new Rectangle(100, 100);
        rectangle.setFill(color);

        sceneContainer = new VBox(20);
        sceneContainer.setAlignment(Pos.CENTER);

        color = Color.color(0, 0, 0);

        Scene scn = new Scene(sceneContainer, WIDTH, HEIGHT);

        label1 = new Label("Red: ");
        slider1 = new Slider(0, 1,0);
        slider1.setOnMouseDragged(e -> {
            updateCodes();
        });

        HBox box1 = new HBox();
        box1.getChildren().addAll(label1, slider1);
        box1.setAlignment(Pos.CENTER);

        label2 = new Label("Green: ");
        slider2 = new Slider(0, 1, 0);
        slider2.setOnMouseDragged(e -> {
            updateCodes();
        });
        
        HBox box2 = new HBox();
        box2.getChildren().addAll(label2, slider2);
        box2.setAlignment(Pos.CENTER);
        
        label3 = new Label("Blue: ");
        slider3 = new Slider(0, 1, 0);
        slider3.setOnMouseDragged(e -> {
            updateCodes();
        });

        HBox box3 = new HBox();
        box3.getChildren().addAll(label3, slider3);
        box3.setAlignment(Pos.CENTER);

        rgb = new Label();
        hsb = new Label();
        hex = new Label();

        updateCodes();

        VBox labels = new VBox();
        labels.getChildren().addAll(rgb, hsb, hex);
        labels.setAlignment(Pos.CENTER);

        Button color1Button = new Button("Update Color 1");
        color1Button.setOnAction(e -> {
            color1 = color;
            stops = new Stop[] {new Stop(0, color1), new Stop(1, color2)};
            lg = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
            
            background = new Background(new BackgroundFill(lg, null, null));
            sceneContainer.setBackground(background);
            
            primaryStage.setScene(scn);
            primaryStage.show();
        });
        Button color2Button = new Button("Update Color 2");
        color2Button.setOnAction(e -> {
            color2 = color;
            stops = new Stop[] {new Stop(0, color1), new Stop(1, color2)};
            lg = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

            background = new Background(new BackgroundFill(lg, null, null));
            sceneContainer.setBackground(background);

            primaryStage.setScene(scn);
            primaryStage.show();
        });

        HBox setColorBox = new HBox(20);
        setColorBox.getChildren().addAll(color1Button, color2Button);
        setColorBox.setAlignment(Pos.CENTER);

        // change the mode
        ComboBox<String> mode = new ComboBox<String>();
            mode.getItems().addAll("RGB", "HSB");
            mode.setEditable(true);
            mode.setOnAction(e -> {
                isRGB = mode.getValue().equals("RGB");

                if (isRGB) {
                    // alter the labels
                    label1.setText("Red: ");
                    label2.setText("Green: ");
                    label3.setText("Blue: ");

                    int red = (int) (slider1.getValue() * 255.0);
                    int green = (int) (slider2.getValue() * 255.0);
                    int blue = (int) (slider3.getValue() * 255.0);

                    color = Color.rgb(red, green, blue);

                } else {
                    label1.setText("Hue: ");
                    label2.setText("Saturation: ");
                    label3.setText("Brightness: ");

                    double hue = (slider1.getValue() * 360.0);
                    double saturation = slider2.getValue();
                    double brightness = slider3.getValue();

                    color = Color.hsb(hue, saturation, brightness);
                }

                // update each of the labels
                updateCodes();

                primaryStage.setScene(scn);
                primaryStage.show();
            });

            mode.setValue("RGB");

            // change the background color
            background = new Background(new BackgroundFill(lg, null, null));
            sceneContainer.setBackground(background);

            // set the layout
            sceneContainer.getChildren().addAll(mode, box1, box2, box3, rectangle, labels, setColorBox);
            sceneContainer.setAlignment(Pos.CENTER);

            primaryStage.setScene(scn);
            primaryStage.setTitle("Color Picker");
            primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
