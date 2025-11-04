/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import es.unirioja.paw.service.data.SessionConstants;
import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.ClienteEntity;
import es.unirioja.paw.repository.ClienteRepository;
import es.unirioja.paw.service.AuthService;
import es.unirioja.paw.service.AuthServiceImpl;
import es.unirioja.paw.service.PedidoService;
import es.unirioja.paw.service.PedidoServiceImpl;
import es.unirioja.paw.service.RegistroClienteUseCase;
import es.unirioja.paw.service.data.RegistroClienteRequest;
import es.unirioja.paw.service.data.RegistroClienteResponse;
import es.unirioja.paw.web.data.RegistroClienteValidationResponse;
import es.unirioja.paw.web.data.RegistroPostPayload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jarein
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    PedidoService pedidoService;
    @Autowired
    private RegistroClienteUseCase registroClienteUseCase;
    private final String USER_REGISTERED_KEY = "userRegistered";
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(value = "error", required = false) String error,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Credenciales incorrectas");
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        ClienteEntity c = authService.authenticate(username, password);
        if (c != null) {
            session.setAttribute("cliente", c);
            CestaCompraEntity cestaEntity = pedidoService.findCestaCliente(c.getCodigo());
            session.setAttribute(SessionConstants.CESTA_KEY, cestaEntity);
            String URLdestino = (String) session.getAttribute("URLdestino");
            session.removeAttribute("URLdestino");
            if (URLdestino != null && !URLdestino.isEmpty()) {
                return "redirect:" + URLdestino;
            }
            return "redirect:/cliente/cuenta";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/logout")
    public String mostrarLogout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        redirectAttributes.addFlashAttribute("confirmacion", "Sesión cerrada correctamente");
        return "redirect:/auth/login";
    }

    @GetMapping("/register")
    public String registro(Model model) {
        model.addAttribute("contentName", "Registro de cliente");
        model.addAttribute("registroPostPayload", new RegistroPostPayload());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registroSubmit(
            @ModelAttribute RegistroPostPayload modelAttribute,
            HttpSession session, Model model) {

        // Validación de los datos del formulario
        RegistroClienteValidationResponse validationResponse = validateRegistroPayload(modelAttribute);

        if (!validationResponse.isSuccess()) {
            model.addAttribute("messages", validationResponse.getMessageCollection());
            model.addAttribute("backing", modelAttribute);
            return "auth/register";
        }

        // Proceso de registro
        RegistroClienteResponse registroClienteResponse
                = registroClienteUseCase.registrar(new RegistroClienteRequest(modelAttribute));

        logger.info("Registro correcto? {}", registroClienteResponse.isSuccess());

        if (registroClienteResponse.isSuccess()) {
            session.setAttribute(USER_REGISTERED_KEY, registroClienteResponse.cliente);
            return "redirect:/auth/welcome";
        }

        // Si falla el registro
        model.addAttribute("backing", modelAttribute);
        return "auth/register";
    }

    @GetMapping("/welcome")
    public String welcome(Model model, HttpSession session) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(
                USER_REGISTERED_KEY);
        if (cliente != null) {
            model.addAttribute("clienteRegistrado", cliente);
            session.removeAttribute(USER_REGISTERED_KEY);
        }
        return "auth/welcome";
    }

    private RegistroClienteValidationResponse validateRegistroPayload(RegistroPostPayload modelAttribute) {
        RegistroClienteValidationResponse response = new RegistroClienteValidationResponse();
        response.setSuccess(true);
        String password1 = modelAttribute.getPassword1();
        String password2 = modelAttribute.getPassword2();
        String email = modelAttribute.getEmail();
        String username = modelAttribute.getUsername();
        
        if (email == null || email.isBlank()) {
            response.getMessageCollection().add("Campo 'Email': debe proporcionar un valor");
            response.setSuccess(false);
        }else if (modelAttribute.getEmail().length() > 100) {
        response.getMessageCollection().add("Campo 'Email': longitud máxima excedida");
        response.setSuccess(false);
    }
        
        if (username == null || username.isBlank()) {
            response.getMessageCollection().add("Campo 'Nombre de usuario': debe proporcionar un valor");
            response.setSuccess(false);
        }else if (modelAttribute.getUsername().length() > 50) {
        response.getMessageCollection().add("Campo 'Nombre de usuario': longitud máxima excedida");
        response.setSuccess(false);
    }else if (authService.existeUsuario(username)) {
        response.getMessageCollection().add("El nombre de usuario ya está en uso");
        response.setSuccess(false);
    }
        
        if (password1 == null || password1.isBlank()) {
            response.getMessageCollection().add("Campo 'Contraseña': debe proporcionar un valor");
            response.setSuccess(false);
        }else if (password1.length() > 50) {
        response.getMessageCollection().add("Campo 'Contraseña': longitud máxima excedida");
        response.setSuccess(false);
    }

        if (password2 == null || password2.isBlank()) {
            response.getMessageCollection().add("Campo 'Repita contraseña': debe proporcionar un valor");
            response.setSuccess(false);
        }

        if (password1 != null && password2 != null && !password1.equals(password2)) {
            response.getMessageCollection().add("Los campos de las contraseñas no coinciden");
            response.setSuccess(false);
        }

        return response;
    }
}
