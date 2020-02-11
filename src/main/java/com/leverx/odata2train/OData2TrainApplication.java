package com.leverx.odata2train;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import static org.springframework.boot.SpringApplication.run;

@ServletComponentScan
@SpringBootApplication
public class OData2TrainApplication {

    public static void main(String[] args) {
        run(OData2TrainApplication.class, args);
    }

}
