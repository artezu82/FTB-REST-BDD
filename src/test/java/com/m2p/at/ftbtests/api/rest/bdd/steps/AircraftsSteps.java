package com.m2p.at.ftbtests.api.rest.bdd.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.m2p.at.ftbtests.api.rest.bdd.config.AppConfig;
import com.m2p.at.ftbtests.api.rest.bdd.model.api.AircraftDto;
import com.m2p.at.ftbtests.api.rest.bdd.model.api.CreateAircraftDto;
import com.m2p.at.ftbtests.api.rest.bdd.utils.ApiCalls;
import com.m2p.at.ftbtests.api.rest.bdd.utils.ExchangeStorage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.mk_latn.No;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigCache;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.HttpHeaders;

import static io.restassured.RestAssured.basic;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * A step-definition class to contain FTB REST API AIRCRAFTS resource related steps.
 */
@Slf4j
public class AircraftsSteps {

    private final static String PATH = "aircrafts";
    private final static String PATH_DETAILS = PATH + "/{id}";
    private final static String PATH_MODELNAME = PATH + "/model/{modelName}";

    private final ApiCalls apiCalls;
    private final ExchangeStorage<AircraftDto> storage;

    public AircraftsSteps() {
    this.apiCalls = new ApiCalls();
    this.storage = new ExchangeStorage<>();
    }

    private final AppConfig appConfig = ConfigCache.getOrCreate(AppConfig.class);

    @Given("there is at least one aircraft with null seats created in the database and its id is found and remembered")
    public void checkHealthAndSetup() {
        doBasicSuitePreconditionSetup();
    }
    private void doBasicSuitePreconditionSetup() {
        RestAssured.config = RestAssured.config().logConfig(LogConfig
                .logConfig()
                .enablePrettyPrinting(true)
                .enableLoggingOfRequestAndResponseIfValidationFails()
                .blacklistHeader(HttpHeaders.AUTHORIZATION));

        RestAssured.authentication = basic(appConfig.admin(), appConfig.password());
        //RestAssured.authentication = basic("john", "john123");
    }

    @When("client gets details of Aircraft id={long}")
    public void getById(long id) {
        var response = apiCalls.doGet(SC_OK, AircraftDto.class, PATH_DETAILS, String.valueOf(id));
        storage.setLastApiCallSingleItemResponse(response);
    }

    @When("client gets details of just created Aircraft")
    public void getJustCreated() {
        var response = apiCalls
                .doGet(SC_OK, AircraftDto.class, PATH_DETAILS,
                        storage.getLastApiCallSingleItemResponse().getAircraftId());
        storage.setLastApiCallSingleItemResponse(response);
    }

    @When("client tries to create an Aircraft having manufacturer={string} and model={string} and number of seats={int}")
    public void create(String manufacturer, String model, Integer numberOfSeats) throws JsonProcessingException {
        var data = CreateAircraftDto.of()
                .manufacturer(manufacturer)
                .model(model)
                .numberOfSeats(numberOfSeats)
                .build();
        var response = apiCalls.doPost(SC_CREATED, AircraftDto.class, data, PATH);
        storage.setLastApiCallSingleItemResponse(response);
    }

    @When("client tries to create an Aircraft having manufacturer={string} and model={string} and null seats")
    public void createNullSeats(String manufacturer, String model) throws JsonProcessingException {
        var data = CreateAircraftDto.of()
                .manufacturer(manufacturer)
                .model(model)
                .build();
        var response = apiCalls.doPost(SC_CREATED, AircraftDto.class, data, PATH);
        storage.setLastApiCallSingleItemResponse(response);
    }


    @Then("aircraft data to be manufacturer={string} and model={string} and number of seats={int}")

    @Then("aircraft data to be manufacturer={string} and model={string} and null seats")

    @Then("returned aircraft data to be manufacturer={string} and model={string} and null seats")
    public void verifySingleAircraftDataNullSeats(String manufacturer, String model) {
        var lastResponse = storage.getLastApiCallSingleItemResponse();
        assertThat(lastResponse.getManufacturer())
                .as("Seems Aircraft response contained unexpected manufacturer value.")
                .isEqualTo(manufacturer);
        assertThat(lastResponse.getModel())
                .as("Seems Aircraft response contained unexpected model value.")
                .isEqualTo(model);
        assertThat(lastResponse.getNumberOfSeats())
                .as("Seems Aircraft response contained unexpected number-of-seats value.")
                .isEqualTo(null);
    }

    @Then("returned aircraft data to be manufacturer={string} and model={string} and number of seats={int}")
    public void verifySingleAircraftData(String manufacturer, String model, Integer numberOfSeats) {
       var lastResponse = storage.getLastApiCallSingleItemResponse();
       assertThat(lastResponse.getManufacturer())
               .as("Seems Aircraft response contained unexpected manufacturer value.")
               .isEqualTo(manufacturer);
       assertThat(lastResponse.getModel())
               .as("Seems Aircraft response contained unexpected model value.")
               .isEqualTo(model);
       assertThat(lastResponse.getNumberOfSeats())
               .as("Seems Aircraft response contained unexpected number-of-seats value.")
               .isEqualTo(numberOfSeats);
    }



}