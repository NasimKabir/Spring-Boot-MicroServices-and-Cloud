package com.nasim.filter;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasim.response.HttpResponse;


@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
    	HttpResponse httpResponse = new HttpResponse(HttpStatus.UNAUTHORIZED.value(),"Internal Server Exception");
         response.setContentType(APPLICATION_JSON_VALUE);
         response.setStatus(UNAUTHORIZED.value());
         OutputStream outputStream = response.getOutputStream();
         ObjectMapper mapper = new ObjectMapper();
         mapper.writeValue(outputStream, httpResponse);
         outputStream.flush();
    }
}
