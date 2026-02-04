package org.example.calcettomanagmentsystem.model;

import org.jetbrains.annotations.NotNull;

public record Player(
        String pid,
        String pname,
        String pemail
) {
    @Override
    public String toString() {
        return "Player{" +
                "pid='" + pid +
                ", pname='" + pname +
                ", pemail='" + pemail +
                '}';
    }
}
