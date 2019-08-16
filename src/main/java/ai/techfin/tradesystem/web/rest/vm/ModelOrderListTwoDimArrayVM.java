package ai.techfin.tradesystem.web.rest.vm;

import java.util.Arrays;

public class ModelOrderListTwoDimArrayVM {

    private String model;

    private String[][] data;

    public ModelOrderListTwoDimArrayVM() {}

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ModelOrderListTwoDimArrayVM{" +
            "model: " + model +
            "orders: " + Arrays.toString(data) +
            "}";
    }

}