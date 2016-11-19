package com.smarthome.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Created by vkotari on 10/23/2016.
 */
public class SmartHomeUIController extends AbstractController{

    private GpioPinDigitalOutput output;

    @Override
    @RequestMapping( method= RequestMethod.GET, value="/switchcontrol" )
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {

        ModelAndView model = new ModelAndView("SmartUIControllerResponse");
        model.addObject("msg", request.getParameter("switch")+","+request.getParameter("control"));

        Pin raspiPin = null;
        switch(Integer.parseInt(request.getParameter("switch"))){
            case 1: raspiPin = RaspiPin.GPIO_04; break;
            case 2: raspiPin = RaspiPin.GPIO_05; break;
            case 3: raspiPin = RaspiPin.GPIO_06; break;
            default:
        }

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // by default we will use gpio pin #01; however, if an argument
        // has been provided, then lookup the pin by address
        Pin pin = CommandArgumentParser.getPin(
                RaspiPin.class,    // pin provider class to obtain pin instance from
                raspiPin);             // argument array to search in

        if(null == this.output) {
            output = gpio.provisionDigitalOutputPin(pin, "My Output", PinState.HIGH);
        }
        if("on".equalsIgnoreCase(request.getParameter("control"))) {
            output.high();
        } else {
            output.low();
        }

        return model;
    }
}
