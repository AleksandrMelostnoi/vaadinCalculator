package com.melostnoy;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

@Theme("mytheme")
public class StartUI extends UI implements Button.ClickListener {

    private final String[] OPERATIONS = new String[]{"7", "8", "9", "/", "4", "5", "6",
            "*", "1", "2", "3", "-", "0", "=", "C", "+"};
    private double current = 0.0;
    private double stored = 0.0;
    private char lastOperationRequested = 'C';
    private final TextField display = new TextField();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setId("mainAppLayout");
        mainLayout.setSizeFull();
        GridLayout calcLayout = new GridLayout(4, 5);
        calcLayout.addStyleName("calcLayout");
        calcLayout.addComponent(display, 0, 0, 3, 0);
        calcLayout.setMargin(true);
        calcLayout.setSpacing(true);
        calcLayout.setSizeUndefined();
        calcLayout.setCaption("Калькулятор");
        display.setId("inputData");
        display.setValue("0.0");
        display.setEnabled(false);

        for (String caption : OPERATIONS) {
            Button button = new Button(caption);
            button.addClickListener(this);
            calcLayout.addComponent(button);
        }

        mainLayout.addComponent(calcLayout);
        mainLayout.setComponentAlignment(calcLayout, Alignment.MIDDLE_CENTER);
        setContent(mainLayout);
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        Button button = clickEvent.getButton();
        char requestedOperation = button.getCaption().charAt(0);
        display.setValue(String.valueOf(calculate(requestedOperation)));
    }

    private double calculate(char requestedOperation) {
        if ('0' <= requestedOperation && requestedOperation <= '9') {
            current = current * 10
                    + Double.parseDouble("" + requestedOperation);
            return current;
        }
        switch (lastOperationRequested) {
            case '+':
                stored += current;
                break;
            case '-':
                stored -= current;
                break;
            case '/':
                stored /= current;
                break;
            case '*':
                stored *= current;
                break;
            case 'C':
                stored = current;
                break;
        }
        lastOperationRequested = requestedOperation;
        current = 0.0;
        if (requestedOperation == 'C') {
            stored = 0.0;
        }
        return stored;
    }

    @WebServlet(urlPatterns = "/*", name = "StartUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = StartUI.class, productionMode = false)
    public static class StartUIServlet extends VaadinServlet {
    }

}
