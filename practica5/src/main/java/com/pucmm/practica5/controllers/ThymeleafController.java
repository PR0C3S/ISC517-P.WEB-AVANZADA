package com.pucmm.practica5.controllers;


import com.pucmm.practica5.entities.Mock;
import com.pucmm.practica5.entities.Proyecto;
import com.pucmm.practica5.entities.seguridad.Rol;
import com.pucmm.practica5.entities.seguridad.Usuario;
import com.pucmm.practica5.repositorios.MockRepository;
import com.pucmm.practica5.repositorios.seguridad.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URISyntaxException;
import java.util.*;

@Controller
@RequestMapping(path="/practica5")
public class ThymeleafController {


    //Inyección de dependencia para la internacionalización
    @Autowired
    private MessageSource messageSource;
    private Rol rolAdmin = new Rol("ROLE_ADMIN");
    private Rol rolUser = new Rol("ROLE_USER");

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MockRepository mockRepository;

    //Atributo para encriptar la informacion
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    //FUNCIONES EXTRA DE ELIMINAR USUARIO NO PEDIDO EN LA ASIGNACION
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/eliminar/{username}")
    public String postEliminarUsuario(@PathVariable("username") String username) {
        usuarioRepository.deleteById(username);
        return "redirect:/practica5/listarUsuarios";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping(path ="/listarUsuarios")
    public String getListaUsuarios(Model model, Locale locale, HttpSession sesion, HttpServletRequest request){
        Usuario act= usuarioRepository.findByUsername(request.getUserPrincipal().getName());
        model.addAttribute("contador", ""+sesion.getAttribute("contador"));
        model.addAttribute("puerto", ""+request.getLocalPort());
        model.addAttribute("x1", messageSource.getMessage("x1", null, locale));
        model.addAttribute("x2", messageSource.getMessage("x2", null, locale));
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
        model.addAttribute("bt3", messageSource.getMessage("bt3", null, locale));
        model.addAttribute("bt4", messageSource.getMessage("bt4", null, locale));
        model.addAttribute("titulo", messageSource.getMessage("nav0", null, locale));
        model.addAttribute("nav0", messageSource.getMessage("nav0", null, locale));
        model.addAttribute("nav1", messageSource.getMessage("nav1", null, locale));
        model.addAttribute("nav2", messageSource.getMessage("nav2", null, locale));
        model.addAttribute("nav3", messageSource.getMessage("nav3", null, locale));
        model.addAttribute("t7", messageSource.getMessage("t7", null, locale));
        model.addAttribute("t8", messageSource.getMessage("t8", null, locale));
        model.addAttribute("t9", messageSource.getMessage("t9", null, locale));

        return "ListarUsuarios";
    }

    @GetMapping(path = "/login")
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("Autentificacion", "error", error);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping(path ="/registrar")
    public String getRegistrar(Model model, Locale locale, HttpSession sesion, HttpServletRequest request,
                               @RequestParam(value="check", required=false, defaultValue="false") boolean check
    ){

        Usuario act= usuarioRepository.findByUsername(request.getUserPrincipal().getName());
        if(act!= null && act.isAdmin())
        {
            model.addAttribute("admin",true); //se manda usuario
        }

        model.addAttribute("contador", ""+sesion.getAttribute("contador"));
        model.addAttribute("puerto", ""+request.getLocalPort());
        model.addAttribute("x1", messageSource.getMessage("x1", null, locale));
        model.addAttribute("x2", messageSource.getMessage("x2", null, locale));
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
    public String postRegistrar( @RequestParam("usuario") String usuario,
                                @RequestParam("nombre") String nombre,
                                @RequestParam("password") String password,
                                RedirectAttributes attr
    ){


        if(usuarioRepository.findByUsername(usuario.toLowerCase()) !=null)
        {
            attr.addAttribute ("check", true); //se manda para que active que el usuario esta en uso
            return "redirect:/practica5/registrar";
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
        return "redirect:/practica5/listarUsuarios";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/ascender/{username}")
    public String postAscenderUsuario(@PathVariable("username") String username) {
        System.out.println(username);
        Usuario act = usuarioRepository.findByUsername(username);
        act.setRoles(new HashSet<>(List.of(rolAdmin,rolUser)));
        usuarioRepository.save(act);

        return "redirect:/practica5/listarUsuarios";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/descender/{username}")
    public String postDescenderUsuario(@PathVariable("username") String username) {
        Usuario act = usuarioRepository.findByUsername(username);
        act.setRoles(new HashSet<>(List.of(rolUser)));
        usuarioRepository.save(act);

        return "redirect:/practica5/listarUsuarios";
    }

    @GetMapping(path ="/listarMock")
    public String getListaMock(Model model, Locale locale, HttpSession sesion, HttpServletRequest request){

        Usuario act= usuarioRepository.findByUsername(request.getUserPrincipal().getName());
        Integer contador = (Integer) sesion.getAttribute("contador");
        if(contador == null){
            contador = 0;
        }
        contador++;
        sesion.setAttribute("contador", contador);
        if(act!= null && act.isAdmin())
        {
            model.addAttribute("admin",true); //se manda usuario
            model.addAttribute("listaMock",mockRepository.findAll()); //se manda todos los mocks
        }else{
            model.addAttribute("listaMock", act.getProyecto().getMocks()); //se manda mock solo del usuario
        }
        model.addAttribute("login",act); //se manda usuario

        //i18n pasando parametros
        model.addAttribute("contador", ""+sesion.getAttribute("contador"));
        model.addAttribute("puerto", ""+request.getLocalPort());
        model.addAttribute("x1", messageSource.getMessage("x1", null, locale));
        model.addAttribute("x2", messageSource.getMessage("x2", null, locale));
        model.addAttribute("t0", messageSource.getMessage("t0", null, locale));
        model.addAttribute("t1", messageSource.getMessage("t1", null, locale));
        model.addAttribute("t2", messageSource.getMessage("t2", null, locale));
        model.addAttribute("t3", messageSource.getMessage("t3", null, locale));
        model.addAttribute("t4", messageSource.getMessage("t4", null, locale));
        model.addAttribute("t5", messageSource.getMessage("t5", null, locale));
        model.addAttribute("t6", messageSource.getMessage("t6", null, locale));
        model.addAttribute("bt1", messageSource.getMessage("bt1", null, locale));
        model.addAttribute("bt5", messageSource.getMessage("bt5", null, locale));
        model.addAttribute("bt6", messageSource.getMessage("bt6", null, locale));
        model.addAttribute("titulo", messageSource.getMessage("nav1", null, locale));
        model.addAttribute("nav0", messageSource.getMessage("nav0", null, locale));
        model.addAttribute("nav1", messageSource.getMessage("nav1", null, locale));
        model.addAttribute("nav2", messageSource.getMessage("nav2", null, locale));
        model.addAttribute("nav3", messageSource.getMessage("nav3", null, locale));
        return "ListarMock";
    }

    @GetMapping(path ="/mock/new")
    public String getNuevoMock(Model model, Locale locale, HttpSession sesion, HttpServletRequest request){

        //validar que el usuario esta logueado si no esta logueado se envia al login
        //si esta logueado crear un string llamado login con el user de el
        Usuario act= usuarioRepository.findByUsername(request.getUserPrincipal().getName());

        if(act!= null && act.isAdmin())
        {
            model.addAttribute("admin",true); //se manda usuario
        }
        model.addAttribute("contador", ""+sesion.getAttribute("contador"));
        model.addAttribute("puerto", ""+request.getLocalPort());
        model.addAttribute("x1", messageSource.getMessage("x1", null, locale));
        model.addAttribute("x2", messageSource.getMessage("x2", null, locale));
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
        model.addAttribute("op", messageSource.getMessage("op", null, locale));

        return "Mock";
    }


    @PostMapping(path ="/mock/new/save")
    public String postMock(HttpSession sesion, HttpServletRequest request,
                           @RequestParam("MockIdentifier") String nombre,
                           @RequestParam("enlace2") String enlace2,
                           @RequestParam("method") String method,
                           @RequestParam("Status") String Status,
                           @RequestParam("Charset") String Charset,
                           @RequestParam("ContentType") String ContentType,
                           @RequestParam("HttpResponse") String ResponseBody,
                           @RequestParam("TimeDelay") String TimeDelay,
                           @RequestParam("ExpiryTime") String ExpiryTime,
                           @RequestParam("HttpHeaders") String Headers
    ) throws URISyntaxException {
        Usuario act= usuarioRepository.findByUsername(request.getUserPrincipal().getName());

        Mock viejo = mockRepository.getMockByEndPointAndUsername(enlace2,act.getUsername());
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
        crear.setRUTA("/jwt/mock/view/"+act.getUsername()+"/"+enlace2);
        crear.setHttpStatus(HttpStatus.valueOf(Status));
        crear.setCharset(Charset);
        crear.setHttpResponseBody(ResponseBody);
        crear.setExpiryTime(ExpiryTime);
        crear.setTimeDelay(Integer.valueOf(TimeDelay));
        crear.setListaHeaders(Headers);
        crear.setResponseContentType(ContentType);
        crear.setProyecto(act.getProyecto());
        crear.setAccessMethod(method);

        mockRepository.save(crear);
        return "redirect:/practica5/listarMock";
    }

    @PostMapping("/eliminar/mock/{id}")
    public String postEliminarMock(@PathVariable("id") long id) {
        mockRepository.deleteById(id);
        return "redirect:/practica5/listarMock";
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String manejoPeticionMalElaborada(IllegalArgumentException e){
        return e.getMessage();
    }

}
