package com.legenkiy;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class BidVaultApplication {
    public static void main(String[] args) {
        Quarkus.run(args);
    }
}
