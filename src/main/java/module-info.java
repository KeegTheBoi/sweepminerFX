module com.javafxgrid {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens com.javafxgrid.view to javafx.fxml;
    exports com.javafxgrid;
}
