package spacetrader;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 *
 * @author Administrator
 */
public class Xform extends Group {

    public enum RotateOrder {

        XYZ, XZY, YXZ, YZX, ZXY, ZYX
    }

    private final Translate t = new Translate();
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

    public Xform() {
        super();
        getTransforms().addAll(t, rz, ry, rx, s);
    }

    public Xform(RotateOrder rotateOrder) {
        super();
        // choose the order of rotations based on the rotateOrder
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
            case ZYX:
                getTransforms().addAll(t, rx, ry, rz, s);
                break;
        }
    }

    public void setTranslate(double x, double y, double z) {
        t.setX(x);
        t.setY(y);
        t.setZ(z);
    }

    public void setTranslate(double x, double y) {
        t.setX(x);
        t.setY(y);
    }

    public void setTx(double x) {
        t.setX(x);
    }

    public void setTy(double y) {
        t.setY(y);
    }

    public void setTz(double z) {
        t.setZ(z);
    }

    public double getTx() {
        return t.getTx();
    }

    public double getTy() {
        return t.getTy();
    }

    public double getTz() {
        return t.getTz();
    }

    public DoubleProperty xProperty() {
        return t.xProperty();
    }

    public DoubleProperty yProperty() {
        return t.yProperty();
    }

    public DoubleProperty zProperty() {
        return t.zProperty();
    }

    public void setRotate(double x, double y, double z) {
        rx.setAngle(x);
        ry.setAngle(y);
        rz.setAngle(z);
    }

    public void setRx(double x) {
        rx.setAngle(x);
    }

    public void setRy(double y) {
        ry.setAngle(y);
    }

    public void setRz(double z) {
        rz.setAngle(z);
    }

    public double getRx() {
        return rx.getAngle();
    }

    public double getRy() {
        return ry.getAngle();
    }

    public double getRz() {
        return rz.getAngle();
    }

    public DoubleProperty rxProperty() {
        return rx.angleProperty();
    }

    public DoubleProperty ryProperty() {
        return ry.angleProperty();
    }

    public DoubleProperty rzProperty() {
        return rz.angleProperty();
    }

    public void setScale(double scaleFactor) {
        s.setX(scaleFactor);
        s.setY(scaleFactor);
        s.setZ(scaleFactor);
    }

    public void setScale(double x, double y, double z) {
        s.setX(x);
        s.setY(y);
        s.setZ(z);
    }

    public void setSx(double x) {
        s.setX(x);
    }

    public void setSy(double y) {
        s.setY(y);
    }

    public void setSz(double z) {
        s.setZ(z);
    }

    public double getSx() {
        return s.getX();
    }

    public double getSy() {
        return s.getY();
    }

    public double getSz() {
        return s.getZ();
    }

    public DoubleProperty sxProperty() {
        return s.xProperty();
    }

    public DoubleProperty syProperty() {
        return s.yProperty();
    }

    public DoubleProperty szProperty() {
        return s.zProperty();
    }

    public void reset() {
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
