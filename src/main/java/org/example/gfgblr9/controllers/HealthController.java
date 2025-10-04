package org.example.gfgblr9.controllers;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.gfgblr9.annotations.InitSalary;
import org.example.gfgblr9.annotations.JsonSerializableField;
import org.example.gfgblr9.models.Employee;
import org.example.gfgblr9.models.Record;
import org.example.gfgblr9.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@RestController("/v1")
public class HealthController {

    public HealthController() {
       log.info("HealthController getting initiated");
    }



    /*@Autowired*/
    private UserService userService; // field injection



    @Autowired
    public HealthController(UserService userService) {
        this.userService = userService;  // constructor injection // required dependecies
    }

   /* @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService; // setter injection // optional dependencies
    }*/

    @GetMapping("/")
    public String home() {
        log.info("HealthController getting initiated");
        return "Hello World";
    }

    @RequestMapping(name="get",path = "/healthCheck")
    public String checkHealth() {
        return "OK";
    }

    @GetMapping("/welcome/{version}")
    public String welcome(@RequestParam(value = "q") String name, @PathVariable("version") String v) {
        return "Welcome " + name + " to " + v;
    }

    @PostMapping("/welcome/kyc")
    public ResponseEntity<Record> welcomeKyc(@RequestBody org.example.gfgblr9.models.Record record) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("tid", "sdsdcdvcd");
        return new ResponseEntity<>(record, headers, HttpStatus.ACCEPTED);

    }

    @GetMapping("/serializeEmployee")
    public String serializeEmployee() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       /* Employee employee = new Employee(1, "xyz", "yxg", 25, 300000);
        Class<?> cls = employee.getClass();
        String title = cls.getDeclaredFields()[1].getAnnotation(JsonSerializableField.class).value();
        //cls.getAnnotatedInterfaces()[0].g
        //Object obj  = cls.getDeclaredMethod("getEmployeeDetails").invoke(employee, title);
       // return "Hello "+ state ;*/
        Class<?> cls = Employee.class;
        Employee employee = new Employee(1,"sdfvds","wdcewsd", 25, 0);
        int salary = 0;
        for (Method m : cls.getDeclaredMethods()) {
            if (m.getAnnotation(InitSalary.class) != null) {
                salary = m.getAnnotation(InitSalary.class).value();
                m.invoke(employee, salary);
            }
        }


        return "ok";
    }
}
