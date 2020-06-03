package com.github.tanxinzheng.framework.web.rest;

import com.github.tanxinzheng.framework.web.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BaseErrorController extends AbstractErrorController {

    private final ErrorProperties errorProperties;

    /**
     * Create a new {@link BasicErrorController} instance.
     * @param errorAttributes the error attributes
     * @param errorProperties configuration properties
     */
    @Autowired
    public BaseErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        this(errorAttributes, errorProperties, Collections.emptyList());
    }

    /**
     * Create a new {@link BasicErrorController} instance.
     * @param errorAttributes the error attributes
     * @param errorProperties configuration properties
     * @param errorViewResolvers error view resolvers
     */
    public BaseErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties,
                               List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorViewResolvers);
        Assert.notNull(errorProperties, "ErrorProperties must not be null");
        this.errorProperties = errorProperties;
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String, Object> model = Collections
                .unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
    }

    @RequestMapping
    public ResponseEntity<RestResponse> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<RestResponse>(status);
        }
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        RestResponse restResponse = RestResponse.failed(status, (String) body.get("message"));
        restResponse.setError((String) body.get("error"));
        return new ResponseEntity<>(restResponse, status);
    }

    /**
     * Determine if the stacktrace attribute should be included.
     * @param request the source request
     * @param produces the media type produced (or {@code MediaType.ALL})
     * @return if the stacktrace attribute should be included
     */
    protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
        ErrorProperties.IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }

    /**
     * Provide access to the error properties.
     * @return the error properties
     */
    protected ErrorProperties getErrorProperties() {
        return this.errorProperties;
    }

}
