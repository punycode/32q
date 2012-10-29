package de.punyco.thirtytwosquare.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import de.punyco.thirtytwosquare.domain.Squarelet;
import de.punyco.thirtytwosquare.service.SquareletService;


/**
 * A central place to register application converters and formatters.
 */
@RooConversionService
@Configurable
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

    @Autowired
    SquareletService squareletService;


    @Override
    protected void installFormatters(FormatterRegistry registry) {

        super.installFormatters(registry);
        // Register application converters and formatters
    }


    public Converter<Squarelet, String> getSquareletToStringConverter() {

        return new Converter<Squarelet, String>() {

            public String convert(Squarelet squarelet) {

                return new StringBuilder().append(squarelet.getMetadata()).toString();
            }
        };
    }


    public Converter<Long, Squarelet> getIdToSquareletConverter() {

        return new Converter<Long, Squarelet>() {

            public Squarelet convert(java.lang.Long id) {

                return squareletService.findSquarelet(id);
            }
        };
    }


    public Converter<String, Squarelet> getStringToSquareletConverter() {

        return new Converter<String, Squarelet>() {

            public Squarelet convert(String id) {

                return getObject().convert(getObject().convert(id, Long.class), Squarelet.class);
            }
        };
    }


    public void installLabelConverters(FormatterRegistry registry) {

        registry.addConverter(getSquareletToStringConverter());
        registry.addConverter(getIdToSquareletConverter());
        registry.addConverter(getStringToSquareletConverter());
    }


    public void afterPropertiesSet() {

        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
