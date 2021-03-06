/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason;

import static com.metamug.mason.Router.APPLICATION_FORM_URLENCODED;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author user
 */
@RunWith(MockitoJUnitRunner.class)
public class RouterTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    private StringWriter stringWriter;
    private PrintWriter writer;
    @Mock
    private ServletInputStream inputStream;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        ServletContext context = mock(ServletContext.class);
        when(request.getServletContext()).thenReturn(context);
        when(request.getServletContext().getContextPath()).thenReturn("backend");

        //prepare String Writer
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        try {
            when(response.getWriter()).thenReturn(writer);
        } catch (IOException ex) {
            Logger.getLogger(RouterTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRestCall() {
        when(request.getContentType()).thenReturn("blah");
        when(request.getServletPath()).thenReturn("/backend/v1.9/resource");
        when(request.getMethod()).thenReturn("POST");
        String[] params = new String[]{"name"};
        when(request.getParameterNames()).thenReturn(Collections.enumeration(Arrays.asList(params)));
        when(request.getParameterValues("name")).thenReturn(new String[]{"anish", "deepak", "kaustubh"});
        when(request.getContentType()).thenReturn(APPLICATION_FORM_URLENCODED);
        Router router = new Router();

        try {
            router.doFilter(request, response, filterChain);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(RouterTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        verify(request, atLeast(1)).getContentType(); // verify if Content Type was called
        verify(request, atLeast(1)).getServletPath(); // verify if servlet path was called
        writer.flush(); // it may not have been flushed yet...
        System.out.println(stringWriter.toString());
        assertTrue(stringWriter.toString().contains("404"));
    }

    //@Test
    public void testResourceNotFound() {
        when(request.getContentType()).thenReturn("application/json");
        when(request.getServletPath()).thenReturn("/backend/v1.9/resource");
        when(request.getMethod()).thenReturn("POST");

        //InputStream stream = new ByteArrayInputStream("Definitely Not JSON".getBytes(StandardCharsets.UTF_8));
        try {
            when(request.getInputStream()).thenReturn(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(RouterTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        Router router = new Router();

        try {
            router.doFilter(request, response, filterChain);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(RouterTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        verify(request, atLeast(1)).getContentType(); // verify if Content Type was called
        verify(request, atLeast(1)).getServletPath(); // verify if servlet path was called
        writer.flush(); // it may not have been flushed yet...
        System.out.println(stringWriter.toString());
        assertTrue(stringWriter.toString().contains("404"));
    }
}
