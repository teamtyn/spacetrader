package spacetrader;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 * 
 * @author Team TYN
 */
public class Xform extends Group {

    public enum RotateOrder {
        XYZ, XZY, YXZ, YZX, ZXY, ZYX
    }

    private final Translate translate = new Translate();
    private final Rotate rx = new Rotate();

    {
        rx.setAxis(Rotate.X_AXIS);
    }
    private final Rotate ry = new Rotate();

    {
        ry.setAxis(Rotate.Y_AXIS);
    }
    private final Rotate rz = new Rotate();

    {
        rz.setAxis(Rotate.Z_AXIS);
    }
    private final Scale s = new Scale();

    /**
     * 
     */
    public Xform() {
        super();
        getTransforms().addAll(translate, rz, ry, rx, s);
    }

    /**
     * 
     * @param rotateOrder 
     */
    public Xform(RotateOrder rotateOrder) {
        super();
        // choose the order of rotations based on the rotateOrder
        switch (rotateOrder) {
            case XYZ:
                getTransforms().addAll(translate, rz, ry, rx, s);
                break;
            case XZY:
                getTransforms().addAll(translate, ry, rz, rx, s);
                break;
            case YXZ:
                getTransforms().addAll(translate, rz, rx, ry, s);
                break;
            case YZX:
                getTransforms().addAll(translate, rx, rz, ry, s);
                break;
            case ZXY:
                getTransforms().addAll(translate, ry, rx, rz, s);
                break;
            case ZYX:
                getTransforms().addAll(translate, rx, ry, rz, s);
                break;
        }
    }

    /**
     * 
     * @param x
     * @param y
     * @param z 
     */
    public final void setTranslate(final double x,
            final double y, final double z) {
        translate.setX(x);
        translate.setY(y);
        translate.setZ(z);
    }

    /**
     * 
     * @param x
     * @param y 
     */
    public final void setTranslate(final double x, final double y) {
        translate.setX(x);
        translate.setY(y);
    }

    /**
     * 
     * @param x 
     */
    public final void setTx(double x) {
        translate.setX(x);
    }

    /**
     * 
     * @param y 
     */
    public final void setTy(double y) {
        translate.setY(y);
    }

    /**
     * 
     * @param z 
     */
    public final void setTz(double z) {
        translate.setZ(z);
    }

    /**
     * 
     * @return 
     */
    public final double getTx() {
        return translate.getTx();
    }

    /**
     * 
     * @return 
     */
    public final double getTy() {
        return translate.getTy();
    }

    /**
     * 
     * @return 
     */
    public final double getTz() {
        return translate.getTz();
    }

    /**
     * 
     * @return 
     */
    public final DoubleProperty xProperty() {
        return translate.xProperty();
    }

    /**
     * 
     * @return 
     */
    public final DoubleProperty yProperty() {
        return translate.yProperty();
    }

    /**
     * 
     * @return 
     */
    public final DoubleProperty zProperty() {
        return translate.zProperty();
    }

    /**
     * 
     * @param x
     * @param y
     * @param z 
     */
    public final void setRotate(final double x, final double y, final double z) {
        rx.setAngle(x);
        ry.setAngle(y);
        rz.setAngle(z);
    }

    /**
     * 
     * @param x 
     */
    public final void setRx(final double x) {
        rx.setAngle(x);
    }

    /**
     * 
     * @param y 
     */
    public final void setRy(final double y) {
        ry.setAngle(y);
    }

    /**
     * 
     * @param z 
     */
    public final void setRz(final double z) {
        rz.setAngle(z);
    }

    /**
     * 
     * @return 
     */
    public final double getRx() {
        return rx.getAngle();
    }

    /**
     * 
     * @return 
     */
    public final double getRy() {
        return ry.getAngle();
    }

    /**
     * 
     * @return 
     */
    public final double getRz() {
        return rz.getAngle();
    }

    /**
     * 
     * @return 
     */
    public final DoubleProperty rxProperty() {
        return rx.angleProperty();
    }

    /**
     * 
     * @return 
     */
    public final DoubleProperty ryProperty() {
        return ry.angleProperty();
    }

    /**
     * 
     * @return 
     */
    public final DoubleProperty rzProperty() {
        return rz.angleProperty();
    }

    /**
     * 
     * @param scaleFactor 
     */
    public final void setScale(final double scaleFactor) {
        s.setX(scaleFactor);
        s.setY(scaleFactor);
        s.setZ(scaleFactor);
    }

    /**
     * 
     * @param x
     * @param y
     * @param z 
     */
    public final void setScale(final double x, final double y, final double z) {
        s.setX(x);
        s.setY(y);
        s.setZ(z);
    }

    /**
     * 
     * @param x 
     */
    public final void setSx(final double x) {
        s.setX(x);
    }

    /**
     * 
     * @param y 
     */
    public final void setSy(final double y) {
        s.setY(y);
    }

    /**
     * 
     * @param z 
     */
    public final void setSz(final double z) {
        s.setZ(z);
    }

    /**
     * 
     * @return 
     */
    public final double getSx() {
        return s.getX();
    }

    /**
     * 
     * @return 
     */
    public final double getSy() {
        return s.getY();
    }

    /**
     * 
     * @return 
     */
    public final double getSz() {
        return s.getZ();
    }

    /**
     * 
     * @return 
     */
    public final DoubleProperty sxProperty() {
        return s.xProperty();
    }

    /**
     * 
     * @return 
     */
    public final DoubleProperty syProperty() {
        return s.yProperty();
    }

    /**
     * 
     * @return 
     */
    public final DoubleProperty szProperty() {
        return s.zProperty();
    }

    /**
     * 
     */
    public void reset() {
        translate.setX(0.0);
        translate.setY(0.0);
        translate.setZ(0.0);
        rx.setAngle(0.0);
        ry.setAngle(0.0);
        rz.setAngle(0.0);
        s.setX(1.0);
        s.setY(1.0);
        s.setZ(1.0);
    }
}
