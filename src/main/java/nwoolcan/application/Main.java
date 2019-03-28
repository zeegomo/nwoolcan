package nwoolcan.application;

import nwoolcan.view.View;
import nwoolcan.view.ViewImpl;

final class Main {
    static final View view = new ViewImpl();

    private Main() { }

    public static void main(final String[] args) {
        System.out.println("Mago Iulius");

        view.start();
    }
}
