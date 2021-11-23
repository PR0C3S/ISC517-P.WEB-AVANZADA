package com.pucmm.practica5.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pucmm.practica5.entities.Mock;
import com.pucmm.practica5.repositorios.MockRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@Controller
@RequestMapping(path="/jwt")
public class JWTController {

    @Autowired
    private MockRepository mockRepository;

    @Value("${token_jwt}")
    private String llaveSecreta;

    //Debe estar logueado previamente para acceder aqui
    @RequestMapping(path ="/auth/{id}", method = RequestMethod.GET)
    public void getAuth(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        String token = "";
        Mock mock = mockRepository.getById(id);
        token = generarToken(mock);
        System.out.println("TOKEN CREADO CORRECTAMENTE");
        System.out.println("TOKEN: "+token);
        response.setHeader("Authorization", token);
        RequestDispatcher view = request.getRequestDispatcher("/practica5/mock/view/"+mock.getProyecto().getUsuario().getUsername()+"/"+mock.getEndPoint());
        view.forward(request, response);
    }
    @RequestMapping(path ="/mock/view/{username}/{link}", method = RequestMethod.GET)
    public ResponseEntity<String> getMockJWT(@PathVariable("username") String username, @PathVariable("link") String link, HttpServletRequest request ) throws org.json.simple.parser.ParseException, InterruptedException {

        Mock act = mockRepository.getMockByEndPointAndUsername(link, username);

        if (!request.getMethod().equals(act.getAccessMethod()))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mock NOT FOUND");
        }

        HttpHeaders headers = new HttpHeaders();
        try {
            headers= StringToHeader(headers,act.getListaHeaders());
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        headers.add("Content-Type", act.getResponseContentType()+"; charset="+act.getCharset());


        Thread.sleep(act.getTimeDelay()*1000L);
        return  ResponseEntity.status(act.getHttpStatus())
                .headers(headers)
                .body(act.getHttpResponseBody());
    }

    //Debe estar logueado previamente para acceder aqui
    @RequestMapping(path ="/auth/{id}", method = RequestMethod.POST)
    public void postAuth(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        String token = "";
        Mock mock = mockRepository.getById(id);
        token = generarToken(mock);
        System.out.println("TOKEN CREADO CORRECTAMENTE");
        System.out.println("TOKEN: "+token);
        response.setHeader("Authorization", token);
        RequestDispatcher view = request.getRequestDispatcher("/practica5/mock/view/"+mock.getProyecto().getUsuario().getUsername()+"/"+mock.getEndPoint());
        view.forward(request, response);
    }
    @RequestMapping(path ="/mock/view/{username}/{link}", method = RequestMethod.POST)
    public ResponseEntity<String> PostMockJWT(@PathVariable("username") String username, @PathVariable("link") String link, HttpServletRequest request) throws org.json.simple.parser.ParseException, InterruptedException {

        Mock act = mockRepository.getMockByEndPointAndUsername(link, username);
        if (!request.getMethod().equals(act.getAccessMethod()))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mock NOT FOUND");
        }

        HttpHeaders headers = new HttpHeaders();
        try {
            headers= StringToHeader(headers,act.getListaHeaders());
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        headers.add("Content-Type", act.getResponseContentType()+"; charset="+act.getCharset());

        Thread.sleep(act.getTimeDelay()*1000L);
        return  ResponseEntity.status(act.getHttpStatus())
                .headers(headers)
                .body(act.getHttpResponseBody());
    }

    @RequestMapping(path ="/mock/view/{username}/{link}", method = RequestMethod.PUT)
    public ResponseEntity<String> putMockJWT(@PathVariable("username") String username, @PathVariable("link") String link, HttpServletRequest request ) throws org.json.simple.parser.ParseException, InterruptedException {

        Mock act = mockRepository.getMockByEndPointAndUsername(link,username);
        if (!request.getMethod().equals(act.getAccessMethod()))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mock NOT FOUND");
        }


        HttpHeaders headers = new HttpHeaders();
        try {
            headers= StringToHeader(headers,act.getListaHeaders());
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        headers.add("Content-Type", act.getResponseContentType()+"; charset="+act.getCharset());


        Thread.sleep(act.getTimeDelay()*1000L);
        return  ResponseEntity.status(act.getHttpStatus())
                .headers(headers)
                .body(act.getHttpResponseBody());
    }

    //Debe estar logueado previamente para acceder aqui
     @RequestMapping(path ="/mock/view/{username}/{link}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMockJWT(@PathVariable("username") String username, @PathVariable("link") String link, HttpServletRequest request ) throws org.json.simple.parser.ParseException, InterruptedException {

        Mock act = mockRepository.getMockByEndPointAndUsername(link,username);
        if (!request.getMethod().equals(act.getAccessMethod()))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mock NOT FOUND");
        }


        HttpHeaders headers = new HttpHeaders();
        try {
            headers= StringToHeader(headers,act.getListaHeaders());
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        headers.add("Content-Type", act.getResponseContentType()+"; charset="+act.getCharset());

        Thread.sleep(act.getTimeDelay()*1000L);
        return  ResponseEntity.status(act.getHttpStatus())
                .headers(headers)
                .body(act.getHttpResponseBody());
    }

    @RequestMapping(path ="/mock/view/{username}/{link}", method = RequestMethod.PATCH)
    public ResponseEntity<String> patchMockJWT(@PathVariable("username") String username, @PathVariable("link") String link, HttpServletRequest request ) throws org.json.simple.parser.ParseException, InterruptedException {

        Mock act = mockRepository.getMockByEndPointAndUsername(link,username);
        if (!request.getMethod().equals(act.getAccessMethod()))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mock NOT FOUND");
        }
        HttpHeaders headers = new HttpHeaders();
        try {
            headers= StringToHeader(headers,act.getListaHeaders());
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        headers.add("Content-Type", act.getResponseContentType()+"; charset="+act.getCharset());

        Thread.sleep(act.getTimeDelay()*1000L);
        return  ResponseEntity.status(act.getHttpStatus())
                .headers(headers)
                .body(act.getHttpResponseBody());
    }

   @RequestMapping(path ="/mock/view/{username}/{link}", method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsMockJWT(@PathVariable("username") String username, @PathVariable("link") String link, HttpServletRequest request ) throws org.json.simple.parser.ParseException, InterruptedException {

        Mock act = mockRepository.getMockByEndPointAndUsername(link,username);
        if (!request.getMethod().equals(act.getAccessMethod()))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mock NOT FOUND");
        }
        HttpHeaders headers = new HttpHeaders();
        try {
            headers= StringToHeader(headers,act.getListaHeaders());
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        headers.add("Content-Type", act.getResponseContentType()+"; charset="+act.getCharset());

        Thread.sleep(act.getTimeDelay()*1000L);
        return  ResponseEntity.status(act.getHttpStatus())
                .headers(headers)
                .body(act.getHttpResponseBody());
    }

    //autentication para postman
    @PostMapping("/authenticate/{id}")
    public ResponseEntity<?> authenticateForPostman(@PathVariable("id") long id) throws Exception{
        Mock mock = mockRepository.getById(id);
        String token = generarToken(mock);
        System.out.println("TOKEN CREADO CORRECTAMENTE");
        String body = "{\"SecretKey\": \""+token+"\", \"MethodAc\": \""+mock.getAccessMethod()+"\", \"Ruta\": \""+mock.getRUTA()+"\"}";
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(body);
        return  ResponseEntity.ok(json);
    }
    private HttpHeaders StringToHeader(HttpHeaders httpHeaders, String aux) throws org.json.simple.parser.ParseException {

        if(!aux.equals("")){
            JsonObject jsonObject = new JsonParser().parse(aux).getAsJsonObject();
            Iterator<String> keys = jsonObject.keySet().iterator();

            while (keys.hasNext()) {
                String key = keys.next();
                httpHeaders.add(key, String.valueOf(jsonObject.get(key)));
            }
        }

        return httpHeaders;
    }
    private String generarToken(Mock mock) throws ParseException {

        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(mock.getProyecto().getUsuario().getNombre())
                .claim("roles",mock.getProyecto().getUsuario().getRoles())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(DATE_FORMAT.parse(mock.fechaVencimiento()))
                .signWith(SignatureAlgorithm.HS512, llaveSecreta.getBytes()).compact();

        return "Bearer " + token;
    }
}
