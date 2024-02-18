package com.m2p.at.ftbtests.api.rest.bdd.utils;

import lombok.Data;

import java.util.List;

/**
 * A container to keep various data (e.g. API call responses) between scenario steps.
 * This is the way to exchange data between steps e.g. get response, store in here, get the response in the next step.
 */
@Data
public class ExchangeStorage<R> {
    // [WARNING] This is a throw-away code, please refactor it heavily in case of a real project. Concurrency will be an issue otherwise!
    private R lastApiCallSingleItemResponse;
    private List<R> lastApiCallListResponse;
}
