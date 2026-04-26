package com.currency_exchange_service.currency_exchange_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;    //for environment
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    CurrencyExchangeRepository repository;

    //to get value of port
    private Environment environment;

    public CurrencyExchangeController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from,
                                                  @PathVariable String to){

        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);

        if(currencyExchange==null){
            throw new RuntimeException("Unable to fetch data" + from +" and" + to);
        }

        //CurrencyExchange currencyExchange = new CurrencyExchange(100L, from, to, BigDecimal.valueOf(50));
        //now, we'll get currencyExchange from repository.

        String port=environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);   //setting value of Port
        return currencyExchange;
    }
}
