package spacetrader.ui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 * SerializableColor is a class used to store colors in a serializable way.
 *
 * @author Team TYN
 */
public final class SerializableColor implements Serializable {

    /**
     * The (non-serializable) JavaFX color object that is wrapped by the class.
     */
    private transient Color color;

    /**
     * Constructor for SerializableColor.
     *
     * @param aColor the color that we are storing
     */
    public SerializableColor(final Color aColor) {
        this.color = aColor;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Method to read in the color from the storage file.
     *
     * @param in the input stream being read from
     * @throws IOException thrown if problem with the file
     * @throws ClassNotFoundException thrown if problem with the class
     */
    private void readObject(final ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        color = Color.color(
                in.readDouble(),
                in.readDouble(),
                in.readDouble(),
                in.readDouble());
    }

    /**
     * Method to write the color to the storage file.
     *
     * @param out the Output stream being written to
     * @throws IOException thrown if problem with the file
     */
    private void writeObject(final ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeDouble(color.getRed());
        out.writeDouble(color.getGreen());
        out.writeDouble(color.getBlue());
        out.writeDouble(color.getOpacity());
    }
}
