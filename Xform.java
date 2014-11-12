package spacetrader;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 * An Xform is a transformation group designed for three dimensional
 * transformations.
 *
 * @author Team TYN
 */
public class Xform extends Group {

    /**
     * Specifies order of rotation transforms.
     */
    public enum RotateOrder {
        /**
         * Indicates a rotation order of XYZ.
         */
        XYZ,
        /**
         * Indicates a rotation order of XZY.
         */
        XZY,
        /**
         * Indicates a rotation order of YXZ.
         */
        YXZ,
        /**
         * Indicates a rotation order of YZX.
         */
        YZX,
        /**
         * Indicates a rotation order of ZXY.
         */
        ZXY,
        /**
         * Indicates a rotation order of ZYX.
         */
        ZYX
    }

    /**
     * The translate for this Xform.
     */
    private final Translate t = new Translate();
    /**
     * The x axis rotation for this Xform.
     */
    private final Rotate rx = new Rotate();
    {
        rx.setAxis(Rotate.X_AXIS);
    }
    /**
     * The y axis rotation for this Xform.
     */
    private final Rotate ry = new Rotate();
    {
        ry.setAxis(Rotate.Y_AXIS);
    }
    /**
     * The z axis rotation for this Xform.
     */
    private final Rotate rz = new Rotate();
    {
        rz.setAxis(Rotate.Z_AXIS);
    }
    /**
     * The scale for this Xform.
     */
    private final Scale s = new Scale();

    /**
     * Constructs an Xform with rotation order XYZ.
     */
    public Xform() {
        super();
        getTransforms().addAll(t, rz, ry, rx, s);
    }

    /**
     * Constructs an Xform with the specified rotation order.
     *
     * @param rotateOrder The rotation order.
     */
    public Xform(final RotateOrder rotateOrder) {
        super();
        switch (rotateOrder) {
            case XYZ:
                getTransforms().addAll(t, rz, ry, rx, s);
                break;
            case XZY:
                getTransforms().addAll(t, ry, rz, rx, s);
                break;
            case YXZ:
                getTransforms().addAll(t, rz, rx, ry, s);
                break;
            case YZX:
                getTransforms().addAll(t, rx, rz, ry, s);
                break;
            case ZXY:
                getTransforms().addAll(t, ry, rx, rz, s);
                break;
            default:
                getTransforms().addAll(t, rx, ry, rz, s);
                break;
        }
    }

    /**
     * Sets all three components of the translation.
     *
     * @param x The x component of the translation.
     * @param y the y component of the translation.
     * @param z The z component of the translation.
     */
    public final void setTranslate(
            final double x, final double y, final double z) {
        t.setX(x);
        t.setY(y);
        t.setZ(z);
    }

    /**
     * Sets the x and y components of the translation.
     *
     * @param x The x component of the translation.
     * @param y The y component of the translation.
     */
    public final void setTranslate(final double x, final double y) {
        t.setX(x);
        t.setY(y);
    }

    /**
     * Sets the x component of the translation.
     *
     * @param x The x component of the translation.
     */
    public final void setTx(final double x) {
        t.setX(x);
    }

    /**
     * Sets the y component of the translation.
     *
     * @param y The y component of the translation.
     */
    public final void setTy(final double y) {
        t.setY(y);
    }

    /**
     * Sets the z component of the translation.
     *
     * @param z The z component of the translation.
     */
    public final void setTz(final double z) {
        t.setZ(z);
    }

    /**
     * Gets the x component of the translation.
     *
     * @return The x component of the translation.
     */
    public final double getTx() {
        return t.getTx();
    }

    /**
     * Gets the y component of the translation.
     *
     * @return The y component of the translation.
     */
    public final double getTy() {
        return t.getTy();
    }

    /**
     * Gets the z component of the translation.
     *
     * @return The z component of the translation.
     */
    public final double getTz() {
        return t.getTz();
    }

    /**
     * Gets the x translate property.
     *
     * @return The x translate property.
     */
    public final DoubleProperty xProperty() {
        return t.xProperty();
    }

    /**
     * Gets the y translate property.
     *
     * @return The y translate property.
     */
    public final DoubleProperty yProperty() {
        return t.yProperty();
    }

    /**
     * Gets the z translate property.
     *
     * @return The z translate property.
     */
    public final DoubleProperty zProperty() {
        return t.zProperty();
    }

    /**
     * Sets the x, y, and z rotations.
     *
     * @param x The x rotation.
     * @param y The y rotation.
     * @param z The z rotation.
     */
    public final void setRotate(
            final double x, final double y, final double z) {
        rx.setAngle(x);
        ry.setAngle(y);
        rz.setAngle(z);
    }

    /**
     * Sets the x rotation.
     *
     * @param x The x rotation.
     */
    public final void setRx(final double x) {
        rx.setAngle(x);
    }

    /**
     * Sets the y rotation.
     *
     * @param y The y rotation.
     */
    public final void setRy(final double y) {
        ry.setAngle(y);
    }

    /**
     * Sets the z rotation.
     *
     * @param z The z rotation.
     */
    public final void setRz(final double z) {
        rz.setAngle(z);
    }

    /**
     * Gets the x rotation.
     *
     * @return The x rotation.
     */
    public final double getRx() {
        return rx.getAngle();
    }

    /**
     * Gets the y rotation.
     *
     * @return The y rotation.
     */
    public final double getRy() {
        return ry.getAngle();
    }

    /**
     * Gets the z rotation.
     *
     * @return The z rotation.
     */
    public final double getRz() {
        return rz.getAngle();
    }

    /**
     * Gets the x rotation property.
     *
     * @return The x rotation property.
     */
    public final DoubleProperty rxProperty() {
        return rx.angleProperty();
    }

    /**
     * Gets the y rotation property.
     *
     * @return The y rotation property.
     */
    public final DoubleProperty ryProperty() {
        return ry.angleProperty();
    }

    /**
     * Gets the z rotation property.
     *
     * @return The z rotation property.
     */
    public final DoubleProperty rzProperty() {
        return rz.angleProperty();
    }

    /**
     * Sets the scale of this Xform.
     *
     * @param scaleFactor The scale factor.
     */
    public final void setScale(final double scaleFactor) {
        s.setX(scaleFactor);
        s.setY(scaleFactor);
        s.setZ(scaleFactor);
    }

    /**
     * Sets the x, y, and z components of the scale.
     *
     * @param x The x component of the scale.
     * @param y The y component of the scale.
     * @param z The z component of the scale.
     */
    public final void setScale(final double x, final double y, final double z) {
        s.setX(x);
        s.setY(y);
        s.setZ(z);
    }

    /**
     * Sets the x component of the scale.
     *
     * @param x The x component of the scale.
     */
    public final void setSx(final double x) {
        s.setX(x);
    }

    /**
     * Sets the y component of the scale.
     *
     * @param y The y component of the scale.
     */
    public final void setSy(final double y) {
        s.setY(y);
    }

    /**
     * Sets the z component of the scale.
     *
     * @param z The z component of the scale.
     */
    public final void setSz(final double z) {
        s.setZ(z);
    }

    /**
     * Gets the x component of the scale.
     * @return The x component of the scale.
     */
    public final double getSx() {
        return s.getX();
    }

    /**
     * Gets the y component of the scale.
     *
     * @return The y component of the scale.
     */
    public final double getSy() {
        return s.getY();
    }

    /**
     * Gets the z component of the scale.
     *
     * @return the z component of the scale.
     */
    public final double getSz() {
        return s.getZ();
    }

    /**
     * Gets the x scale property.
     *
     * @return The x scale property.
     */
    public final DoubleProperty sxProperty() {
        return s.xProperty();
    }

    /**
     * Gets the y scale property.
     *
     * @return The y scale property.
     */
    public final DoubleProperty syProperty() {
        return s.yProperty();
    }

    /**
     * Gets the z scale property.
     *
     * @return The z scale ptoperty.
     */
    public final DoubleProperty szProperty() {
        return s.zProperty();
    }

    /**
     * Resets the Xform to default values.
     */
    public final void reset() {
        t.setX(0.0);
        t.setY(0.0);
        t.setZ(0.0);
        rx.setAngle(0.0);
        ry.setAngle(0.0);
        rz.setAngle(0.0);
        s.setX(1.0);
        s.setY(1.0);
        s.setZ(1.0);
    }
}
