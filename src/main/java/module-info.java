module de.hmb.pp.test {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens de.hmb.pp.test to javafx.fxml;
    exports de.hmb.pp.test;
    exports de.hmb.pp.test.blackjack;
    opens de.hmb.pp.test.blackjack to javafx.fxml;
    exports de.hmb.pp.test.util;
    opens de.hmb.pp.test.util to javafx.fxml;
    exports de.hmb.pp.test.ssp;
    opens de.hmb.pp.test.ssp to javafx.fxml;
    exports de.hmb.pp.test.ttt;
    opens de.hmb.pp.test.ttt to javafx.fxml;
}