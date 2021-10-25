package com.pucmm.practica2.controllers;


import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pucmm.practica2.entities.Mock;
import com.pucmm.practica2.entities.Proyecto;
import com.pucmm.practica2.entities.seguridad.Rol;
import com.pucmm.practica2.entities.seguridad.Usuario;
import com.pucmm.practica2.repositorios.MockRepository;
import com.pucmm.practica2.repositorios.ProyectoRepository;
import com.pucmm.practica2.repositorios.seguridad.RolRepository;
import com.pucmm.practica2.repositorios.seguridad.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.micrometer.core.instrument.config.validate.ValidationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;




import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(path="/practica2")
public class ThymeleafController {
    @Value("${token_jwt}")
    private String llaveSecreta;

    //Inyección de dependencia para la internacionalización
    @Autowired
    private MessageSource messageSource;
    private Rol rolAdmin = new Rol("ROLE_ADMIN");
    private Rol rolUser = new Rol("ROLE_USER");

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private MockRepository mockRepository;

    //Atributo para encriptar la informacion
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


//    FUNCIONES EXTRA DE ELIMINAR USUARIO NO PEDIDO EN LA ASIGNACION
//    @Secured({"ROLE_ADMIN"})
//    @PostMapping("/eliminar/{username}")
//    public String postEliminarUsuario(@PathVariable("username") String username) {
//        Usuario act = usuarioRepository.findByUsername(username);
//        Set<Rol> actRoles = act.getRoles();
//        usuarioRepository.delete(act);
//        return "redirect:/practica2/listarUsuarios";
//    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping(path ="/listarUsuarios")
    public String getListaUsuarios(Model model, Locale locale, Principal principal){

        Usuario act = usuarioRepository.findByUsername(principal.getName());

        model.addAttribute("admin",true); //se manda usuario
        model.addAttribute("login",act); //se manda usuario
        model.addAttribute("listaUsuarios",usuarioRepository.findAll()); //se manda usuario
        //i18n pasando parametros
        model.addAttribute("t1", messageSource.getMessage("t1", null, locale));
        model.addAttribute("t2", messageSource.getMessage("t2", null, locale));
        model.addAttribute("t3", messageSource.getMessage("t3", null, locale));
        model.addAttribute("t4", messageSource.getMessage("t4", null, locale));
        model.addAttribute("t5", messageSource.getMessage("t5", null, locale));
        model.addAttribute("bt2", messageSource.getMessage("bt2", null, locale));
        model.addAttribute("titulo", messageSource.getMessage("nav0", null, locale));
        model.addAttribute("nav0", messageSource.getMessage("nav0", null, locale));
        model.addAttribute("nav1", messageSource.getMessage("nav1", null, locale));
        model.addAttribute("nav2", messageSource.getMessage("nav2", null, locale));
        model.addAttribute("nav3", messageSource.getMessage("nav3", null, locale));
        return "ListarUsuarios";
    }

    @GetMapping(path = "/login")
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("Autentificacion", "error", error);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping(path ="/registrar")
    public String getRegistrar(Model model, Locale locale, Principal principal,
                               @RequestParam(value="check", required=false, defaultValue="false") boolean check
    ){
        Usuario act= usuarioRepository.findByUsername(principal.getName());
        if(act!= null && act.isAdmin())
        {
            model.addAttribute("admin",true); //se manda usuario
        }
        //i18n pasando parametros
        model.addAttribute("check",check);
        model.addAttribute("login",act); //se manda nombre simplemente para ocultar navs


        model.addAttribute("nav4", messageSource.getMessage("nav4", null, locale));
        model.addAttribute("nav1", messageSource.getMessage("nav1", null, locale));
        model.addAttribute("nav2", messageSource.getMessage("nav2", null, locale));
        model.addAttribute("nav3", messageSource.getMessage("nav3", null, locale));
        model.addAttribute("nav0", messageSource.getMessage("nav0", null, locale));
        return "Registro";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping(path ="/registrar/user")
    public String postRegistrar(Model model, Locale locale,
                                @RequestParam("usuario") String usuario,
                                @RequestParam("nombre") String nombre,
                                @RequestParam("password") String password,
                                RedirectAttributes attr
    ){


        if(usuarioRepository.findByUsername(usuario.toLowerCase()) !=null)
        {
            attr.addAttribute ("check", true); //se manda para que active que el usuario esta en uso
            return "redirect:/practica2/registrar";
        }

        Usuario tmp = new Usuario();
        tmp.setUsername(usuario.toLowerCase());
        tmp.setNombre(nombre.toLowerCase());
        tmp.setPassword(bCryptPasswordEncoder.encode(password.toLowerCase()));
        tmp.setRoles(new HashSet<>(Arrays.asList(rolUser)));




        Proyecto act = new Proyecto();
        act.setUsuario(tmp);
        tmp.setProyecto(act);
        usuarioRepository.save(tmp);
        return "redirect:/practica2/listarUsuarios";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/ascender/{username}")
    public String postAscenderUsuario(@PathVariable("username") String username) {
        System.out.println(username);
        Usuario act = usuarioRepository.findByUsername(username);
        act.setRoles(new HashSet<>(List.of(rolAdmin,rolUser)));
        usuarioRepository.save(act);

        return "redirect:/practica2/listarUsuarios";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/descender/{username}")
    public String postDescenderUsuario(@PathVariable("username") String username) {
        Usuario act = usuarioRepository.findByUsername(username);
        act.setRoles(new HashSet<>(List.of(rolUser)));
        usuarioRepository.save(act);

        return "redirect:/practica2/listarUsuarios";
    }

    @GetMapping(path ="/listarMock")
    public String getListaMock(Model model, Locale locale, Principal principal){

        Usuario act = usuarioRepository.findByUsername(principal.getName());

        if(act!= null && act.isAdmin())
        {
            model.addAttribute("admin",true); //se manda usuario
            model.addAttribute("listaMock",mockRepository.findAll()); //se manda todos los mocks
        }else{

            model.addAttribute("listaMock", act.getProyecto().getMocks()); //se manda mock solo del usuario
        }
        model.addAttribute("login",act); //se manda usuario

        //i18n pasando parametros
        model.addAttribute("t0", messageSource.getMessage("t0", null, locale));
        model.addAttribute("t1", messageSource.getMessage("t1", null, locale));
        model.addAttribute("t2", messageSource.getMessage("t2", null, locale));
        model.addAttribute("t3", messageSource.getMessage("t3", null, locale));
        model.addAttribute("t4", messageSource.getMessage("t4", null, locale));
        model.addAttribute("t5", messageSource.getMessage("t5", null, locale));
        model.addAttribute("t6", messageSource.getMessage("t6", null, locale));
        model.addAttribute("bt1", messageSource.getMessage("bt1", null, locale));
        model.addAttribute("titulo", messageSource.getMessage("nav1", null, locale));
        model.addAttribute("nav0", messageSource.getMessage("nav0", null, locale));
        model.addAttribute("nav1", messageSource.getMessage("nav1", null, locale));
        model.addAttribute("nav2", messageSource.getMessage("nav2", null, locale));
        model.addAttribute("nav3", messageSource.getMessage("nav3", null, locale));
        return "ListarMock";
    }

    @GetMapping(path ="/mock/new")
    public String getNuevoMock(Model model, Locale locale, Principal principal){

        //validar que el usuario esta logueado si no esta logueado se envia al login
        //si esta logueado crear un string llamado login con el user de el
        Usuario act = usuarioRepository.findByUsername(principal.getName());
        if(act!= null && act.isAdmin())
        {
            model.addAttribute("admin",true); //se manda usuario
        }
        model.addAttribute("login",act); //se manda usuario
        //i18n pasando parametros
        model.addAttribute("nombre0", messageSource.getMessage("nombre0", null, locale));
        model.addAttribute("nombre1", messageSource.getMessage("nombre1", null, locale));
        model.addAttribute("nombre2", messageSource.getMessage("nombre2", null, locale));
        model.addAttribute("nombre3", messageSource.getMessage("nombre3", null, locale));
        model.addAttribute("nombre4", messageSource.getMessage("nombre4", null, locale));
        model.addAttribute("nombre5", messageSource.getMessage("nombre5", null, locale));
        model.addAttribute("nombre6", messageSource.getMessage("nombre6", null, locale));
        model.addAttribute("nombre7", messageSource.getMessage("nombre7", null, locale));
        model.addAttribute("nombre8", messageSource.getMessage("nombre8", null, locale));
        model.addAttribute("nombre9", messageSource.getMessage("nombre9", null, locale));
        model.addAttribute("nombre10", messageSource.getMessage("nombre10", null, locale));
        model.addAttribute("nombre11", messageSource.getMessage("nombre11", null, locale));
        model.addAttribute("nombre12", messageSource.getMessage("nombre12", null, locale));
        model.addAttribute("titulo", messageSource.getMessage("bt1", null, locale));
        model.addAttribute("nav0", messageSource.getMessage("nav0", null, locale));
        model.addAttribute("nav1", messageSource.getMessage("nav1", null, locale));
        model.addAttribute("nav2", messageSource.getMessage("nav2", null, locale));
        model.addAttribute("nav3", messageSource.getMessage("nav3", null, locale));

        return "Mock";
    }

    @PostMapping(path ="/mock/new/save")
    public String postMock(Model model, Locale locale,
                           Principal principal,
                           @RequestParam("MockIdentifier") String nombre,
                           @RequestParam("enlace2") String enlace2,
                           @RequestParam("method") String method,
                           @RequestParam("Status") String Status,
                           @RequestParam("Charset") String Charset,
                           @RequestParam("ContentType") String ContentType,
                           @RequestParam("HttpResponse") String ResponseBody,
                           @RequestParam("TimeDelay") String TimeDelay,
                           @RequestParam("ExpiryTime") String ExpiryTime,
                           @RequestParam("HttpHeaders") String Headers,
                           RedirectAttributes attr
    ) throws URISyntaxException {
        Usuario usuario = usuarioRepository.findByUsername(principal.getName());

        Mock viejo = mockRepository.getMockByEndPointAndUsername(enlace2,usuario.getUsername());
        if(viejo != null)
        {
            throw new IllegalArgumentException("End Point en uso");
        }

        Mock crear = new Mock();
        if(nombre.equals(""))
        {
            nombre=enlace2;
        }

        crear.setName(nombre);
        crear.setEndPoint(enlace2);
        crear.SETURL(usuario.getUsername());
        crear.setHttpStatus(HttpStatus.valueOf(Status));
        crear.setCharset(Charset);
        crear.setHttpResponseBody(ResponseBody);
        crear.setExpiryTime(ExpiryTime);
        crear.setTimeDelay(Integer.valueOf(TimeDelay));
        crear.setListaHeaders(Headers);
        crear.setResponseContentType(ContentType);
        crear.setProyecto(usuario.getProyecto());

        //method
        if(method.equals("GET"))
        {
            crear.setAccessMethod(HttpMethod.GET);
        }else if(method.equals("POST"))
        {
            crear.setAccessMethod(HttpMethod.POST);
        }else if(method.equals("PUT"))
        {
            crear.setAccessMethod(HttpMethod.PUT);
        }else if(method.equals("PATCH"))
        {
            crear.setAccessMethod(HttpMethod.PATCH);
        }else if (method.equals("DELETE"))
        {
            crear.setAccessMethod(HttpMethod.DELETE);
        }else{
            crear.setAccessMethod( HttpMethod.OPTIONS);
        }
        mockRepository.save(crear);
        return "redirect:/practica2/listarMock";
    }

    @PostMapping("/eliminar/mock/{id}")
    public String postEliminarMock(@PathVariable("id") long id) {
        mockRepository.deleteById(id);
        return "redirect:/practica2/listarMock";
    }




    @RequestMapping(path ="/mock/{username}/view/{link}")
    public ResponseEntity<String> getMockJWT(Model model, @PathVariable("username") String username, @PathVariable("link") String link, HttpServletResponse response ) throws org.json.simple.parser.ParseException, InterruptedException {

        Mock act = mockRepository.getMockByEndPointAndUsername(link,username);
        HttpHeaders headers = new HttpHeaders();
        try {
            headers= StringToHeader(headers,act.getListaHeaders());
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        headers.add("Content-Type", act.getResponseContentType()+"; charset="+act.getCharset());
        headers.setLocation(act.getURL());

        //this.wait(act.getTimeDelay()*1000L);
        return  ResponseEntity.status(act.getHttpStatus())
                .headers(headers)
                .body(act.getHttpResponseBody());
    }

    @PostMapping("/auth/{id}")
    public void auth(Principal principal, @PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {

        String token = "";
        Mock mock = mockRepository.getById(id);

        token = generarToken(mock);
        System.out.println("TOKEN CREADO CORRECTAMENTE");

        response.setHeader("Authorization", token);
        request.setAttribute("id",mock.getId());
        RequestDispatcher view = request.getRequestDispatcher("/practica2/mock/"+mock.getProyecto().getUsuario().getUsername()+"/view/"+mock.getEndPoint());
        view.forward(request, response);
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
                .signWith(SignatureAlgorithm.HS512,
                        llaveSecreta.getBytes()).compact();

        return "Bearer " + token;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String manejoPeticionMalElaborada(IllegalArgumentException e){
        return e.getMessage();
    }

}
