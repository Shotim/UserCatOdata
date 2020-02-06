package com.leverx.odata2train;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import static org.springframework.boot.SpringApplication.run;

@ServletComponentScan
@SpringBootApplication
public class Odata2trainApplication {

    public static void main(String[] args) {
        run(Odata2trainApplication.class, args);
    }

}
