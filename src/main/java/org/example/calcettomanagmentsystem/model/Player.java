package org.example.calcettomanagmentsystem.model;

import org.jetbrains.annotations.NotNull;

public record Player(
        int pid,
        String pname,
        String pemail
) {
    @NotNull
    @Override
    public String toString() {
        return "Player{" +
                "pid=" + pid +
                ", pname='" + pname +
                "', pemail='" + pemail +
                "'}";
    }
}
