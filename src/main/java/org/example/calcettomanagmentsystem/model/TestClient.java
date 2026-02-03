package org.example.calcettomanagmentsystem.model;

import org.jetbrains.annotations.NotNull;

public record TestClient(
        int cid,
        String cname
) {
    @NotNull
    @Override
    public String toString() {
        return "TestUser{"  +
                "id="       + cid    +
                ", name='"  + cname  + "'" +
                '}';
    }
}
