package com.example.week6.view;

import com.example.week6.pojo.Wizard;
import com.example.week6.pojo.Wizards;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


//import java.awt.*;

@Route(value = "/mainPage.it")
public class MainWizardView extends VerticalLayout {
    private TextField fullName;
    private NumberField dollars;
    private RadioButtonGroup gender;
    private ComboBox position, school, house;
    private Button left, create, update, delete, right;
    private int count = -1;
    private Wizard wizard;

    public MainWizardView(){
        fullName = new TextField();
        fullName.setPlaceholder("Fullname");
        dollars = new NumberField();
        dollars.setLabel("Dollars");
        dollars.setPrefixComponent(new Span("$"));
        RadioButtonGroup<String> gender = new RadioButtonGroup<>();
        gender.setLabel("Gender :");
        gender.setItems("Male", "Female");
        ComboBox position = new ComboBox<>();
        position.setPlaceholder("Position");
        position.setItems("Student", "Teacher");
        ComboBox school = new ComboBox<>();
        school.setPlaceholder("School");
        school.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        ComboBox house = new ComboBox<>();
        house.setPlaceholder("House");
        house.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");
        left = new Button("<<");
        create = new Button("Create");
        update = new Button("Update");
        delete = new Button("Delete");
        right = new Button(">>");
        HorizontalLayout h1 = new HorizontalLayout();
        h1.add(left, create, update, delete, right);
        this.add(fullName, gender, position, dollars, school, house, h1);

        create.addClickListener(event -> {
            MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
            data.add("fullName", fullName.getValue());
            data.add("sex", gender.getValue());
            data.add("position", position.getValue()+"");
            data.add("dollars", dollars.getValue()+"");
            data.add("school", school.getValue()+"");
            data.add("house", house.getValue()+"");

            Wizards out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(data))
                    .retrieve()
                    .bodyToMono(Wizards.class)
                    .block();
            Notification.show("Wizard has been created", 5000, Notification.Position.BOTTOM_START);
        });
        right.addClickListener(event -> {
            Wizards out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(Wizards.class)
                    .block();
            if(this.count < out.getModel().size()-1){
                this.count += 1;
            }
            wizard = out.getModel().get(count);
            fullName.setValue(wizard.getName());
            if(wizard.getSex().equals("m")){
                gender.setValue("Male");
            }else if(wizard.getSex().equals("f")){
                gender.setValue("Female");
            }
            position.setValue(wizard.getPosition());
            dollars.setValue(wizard.getMoney());
            school.setValue(wizard.getSchool());
            house.setValue(wizard.getHouse());
        });

        left.addClickListener(event ->{
           Wizards out = WebClient.create()
                   .get()
                   .uri("http://localhost:8080/wizards")
                   .retrieve()
                   .bodyToMono(Wizards.class)
                   .block();
           if(this.count > 0){
               this.count -= 1;
           }
            wizard = out.getModel().get(count);
            fullName.setValue(wizard.getName());
            if(wizard.getSex().equals("m")){
                gender.setValue("Male");
            }else if(wizard.getSex().equals("f")){
                gender.setValue("Female");
            }
            position.setValue(wizard.getPosition());
            dollars.setValue(wizard.getMoney());
            school.setValue(wizard.getSchool());
            house.setValue(wizard.getHouse());
        });

        update.addClickListener(event -> {
            if(this.count >= 0){
                MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
                data.add("id", wizard.get_id());
                data.add("fullName", fullName.getValue());
                if (gender.getValue().equals("Male")) {
                    data.add("sex", "m");
                } else {
                    data.add("sex", "f");
                }
                data.add("position", position.getValue()+"");
                data.add("dollars", dollars.getValue()+"");
                data.add("school", school.getValue()+"");
                data.add("house", house.getValue()+"");

                String out = WebClient.create()
                        .post()
                        .uri("http://localhost:8080/updateWizard")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters.fromFormData(data))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
                Notification.show("Wizard has been updated", 5000, Notification.Position.BOTTOM_START);
        }});

        delete.addClickListener(event ->{
            MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
            data.add("id", wizard.get_id());
             WebClient.create()
                    .post()
                    .uri("http://localhost:8080/deleteWizard")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(data))
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .block();
            Wizards out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(Wizards.class)
                    .block();
            if (this.count > 0){
                this.count -= 1;
            }
            wizard = out.getModel().get(count);
            fullName.setValue(wizard.getName());
            if (wizard.getSex().equals("m")){
                gender.setValue("Male");
            }
            else if (wizard.getSex().equals("f")){
                gender.setValue("Female");
            }
            position.setValue(wizard.getPosition());
            dollars.setValue(wizard.getMoney());
            school.setValue(wizard.getSchool());
            house.setValue(wizard.getHouse());
            Notification.show("Wizard has been deleted", 5000, Notification.Position.BOTTOM_START);
        });
    }

}
