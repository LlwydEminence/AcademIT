import common.TemperatureConverter;
import gui.FrameView;
import model.CelsiusConverter;
import model.FahrenheitConverter;
import model.KelvinConverter;

public class Main {
    public static void main(String[] args) {
        TemperatureConverter[] temperatureConverters = {new CelsiusConverter(), new KelvinConverter(),
                new FahrenheitConverter()};
        FrameView view = new FrameView(temperatureConverters);
        view.startTemperatureConverter();
    }
}
