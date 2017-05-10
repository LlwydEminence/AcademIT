import controller.Controller;
import gui.FrameView;
import model.TemperatureConverter;

public class Main {
    public static void main(String[] args) {
        FrameView view = new FrameView();
        TemperatureConverter converter = new TemperatureConverter();
        Controller controller = new Controller(view, converter);
        view.addController(controller);
        view.startTemperatureConverter();
    }
}
