package de.punyco.thirtytwosquare.web;

import de.punyco.thirtytwosquare.domain.Squarelet;
import de.punyco.thirtytwosquare.service.PostingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.core.convert.converter.Converter;

import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;


/**
 * A central place to register application converters and formatters.
 */
@RooConversionService
@Configurable
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

    @Autowired
    PostingService postingService;

    @Override
    protected void installFormatters(FormatterRegistry registry) {

        super.installFormatters(registry);
        // Register application converters and formatters
    }


    public Converter<Squarelet, String> getSquareletToStringConverter() {

        return new Converter<Squarelet, String>() {

            public String convert(Squarelet squarelet) {

                return "(no displayable fields)";
            }
        };
    }


    public Converter<String, Squarelet> getIdToSquareletConverter() {

        return new Converter<String, Squarelet>() {

            public Squarelet convert(String id) {

                return postingService.findSquarelet(id);
            }
        };
    }


    public void installLabelConverters(FormatterRegistry registry) {

        registry.addConverter(getSquareletToStringConverter());
        registry.addConverter(getIdToSquareletConverter());
    }


    @Override
    public void afterPropertiesSet() {

        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
