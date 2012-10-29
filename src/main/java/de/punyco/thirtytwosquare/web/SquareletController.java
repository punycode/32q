package de.punyco.thirtytwosquare.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import de.punyco.thirtytwosquare.domain.Squarelet;
import de.punyco.thirtytwosquare.service.SquareletService;


@RequestMapping("/squarelets")
@RooWebScaffold(path = "squarelets", formBackingObject = Squarelet.class)
@RooWebJson(jsonObject = Squarelet.class)
@Controller
public class SquareletController {

    @Autowired
    SquareletService squareletService;


    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Squarelet squarelet, BindingResult bindingResult, Model uiModel,
            HttpServletRequest httpServletRequest) {

        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, squarelet);
            return "squarelets/create";
        }
        uiModel.asMap().clear();
        squareletService.saveSquarelet(squarelet);
        return "redirect:/squarelets/" + encodeUrlPathSegment(squarelet.getId().toString(), httpServletRequest);
    }


    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {

        populateEditForm(uiModel, new Squarelet());
        return "squarelets/create";
    }


    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {

        uiModel.addAttribute("squarelet", squareletService.findSquarelet(id));
        uiModel.addAttribute("itemId", id);
        return "squarelets/show";
    }


    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size, Model uiModel) {

        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("squarelets", squareletService.findSquareletEntries(firstResult, sizeNo));
            float nrOfPages = (float) squareletService.countAllSquarelets() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
                    : nrOfPages));
        } else {
            uiModel.addAttribute("squarelets", squareletService.findAllSquarelets());
        }
        return "squarelets/list";
    }


    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Squarelet squarelet, BindingResult bindingResult, Model uiModel,
            HttpServletRequest httpServletRequest) {

        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, squarelet);
            return "squarelets/update";
        }
        uiModel.asMap().clear();
        squareletService.updateSquarelet(squarelet);
        return "redirect:/squarelets/" + encodeUrlPathSegment(squarelet.getId().toString(), httpServletRequest);
    }


    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {

        populateEditForm(uiModel, squareletService.findSquarelet(id));
        return "squarelets/update";
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size, Model uiModel) {

        Squarelet squarelet = squareletService.findSquarelet(id);
        squareletService.deleteSquarelet(squarelet);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/squarelets";
    }


    void populateEditForm(Model uiModel, Squarelet squarelet) {

        uiModel.addAttribute("squarelet", squarelet);
    }


    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {

        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }
}
