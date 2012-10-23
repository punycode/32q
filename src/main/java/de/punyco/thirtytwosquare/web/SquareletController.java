package de.punyco.thirtytwosquare.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import de.punyco.thirtytwosquare.domain.Squarelet;


@RequestMapping("/squarelets")
@Controller
@RooWebScaffold(path = "squarelets", formBackingObject = Squarelet.class)
@RooWebJson(jsonObject = Squarelet.class)
public class SquareletController {
}
