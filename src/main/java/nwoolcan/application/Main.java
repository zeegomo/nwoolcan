package nwoolcan.application;

import nwoolcan.view.View;
import nwoolcan.view.ViewImpl;

final class Main {
    private Main() { }

    public static void main(final String[] args) {
        final View view = new ViewImpl();

        System.out.println("Mago Iulius");

        view.start();
    }
}
