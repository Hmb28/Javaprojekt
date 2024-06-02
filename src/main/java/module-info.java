module de.hmb.pp.test {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens de.hmb.pp.test to javafx.fxml;
    exports de.hmb.pp.test;
}