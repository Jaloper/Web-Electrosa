/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import es.unirioja.paw.exception.AccessNotAuthorizedException;
import es.unirioja.paw.exception.PedidoNotFoundException;
import es.unirioja.paw.jpa.ClienteAvatarInfo;
import es.unirioja.paw.jpa.ClienteEntity;
import es.unirioja.paw.jpa.LineaPedidoEntity;
import es.unirioja.paw.jpa.LineaanuladaEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.jpa.PedidoanuladoEntity;
import es.unirioja.paw.service.AvatarClienteChangeUseCase;
import es.unirioja.paw.service.PedidoService;
import es.unirioja.paw.service.data.AvatarClienteChangeRequest;
import es.unirioja.paw.service.data.AvatarClienteChangeResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/clientes", "/cliente"})
public class ClienteController {

    @Autowired
    PedidoService pedidoService;
    @Autowired
    private AvatarClienteChangeUseCase avatarClienteChangeUseCase;

    @GetMapping("/cuenta")
    public String cuentaCliente(Model model, HttpSession session) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("cliente", cliente);
        return "area_cliente";
    }

    @GetMapping("/pedidos")
    public String listaPedidos(Model model, HttpSession session) {

        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        List<PedidoEntity> pedidos = pedidoService.findByCliente(cliente.getCodigo());

        model.addAttribute("pedidos", pedidos);
        return "pedidos_cliente";
    }

    @GetMapping("/pedidos/{codigo}")
    public String mostrarPedido(
            @PathVariable("codigo") String codigo,
            Model model, HttpSession session) throws PedidoNotFoundException {

        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        PedidoEntity pedido = pedidoService.findOne(codigo);
        if (pedido == null) {
            throw new PedidoNotFoundException("Pedido " + codigo + " no encontrado");
        }
        if (!pedido.getCodigoCliente().equals(cliente.getCodigo())) {
            throw new AccessNotAuthorizedException("No está autorizado para consultar este pedido");
        }

        List<LineaPedidoEntity> lineaPedido = pedido.getLineas();

        model.addAttribute("pedido", pedido);
        model.addAttribute("lineaPedido", lineaPedido);
        return "ficha-pedido";
    }

    @GetMapping("/pedidos/anulados")
    public String listaPedidosAnulados(Model model, HttpSession session) {

        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        List<PedidoanuladoEntity> pedidosAnulados = pedidoService.findAnulados(cliente.getCodigo());

        model.addAttribute("pedidosAnulados", pedidosAnulados);
        return "pedidos_cliente";
    }

    @GetMapping("/pedidos/anulados/{codigo}")
    public String mostrarPedidoAnulado(
            @PathVariable("codigo") String codigo,
            Model model, HttpSession session) throws PedidoNotFoundException {

        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        PedidoanuladoEntity pedido = pedidoService.findById(codigo);
        List<LineaanuladaEntity> lineaAnulada = pedido.getLineas();

        model.addAttribute("pedido", pedido);
        model.addAttribute("lineaAnulada", lineaAnulada);
        return "ficha-pedido-anulado";
    }

    @GetMapping("/pedidos/anular/{id}")
    public String anularPedido(@PathVariable("id") String id) {
        pedidoService.anularPedido(id);
        return "redirect:/cliente/pedidos";
    }

@PostMapping("/avatar")
public String uploadAvatarImage(
        HttpSession session,
        HttpServletRequest request,
        @RequestParam("file") MultipartFile file) {
    
    ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
    String realTargetPath = request.getServletContext()
                                 .getRealPath("/static/assets/avatar");
    
    Optional<AvatarClienteChangeResponse> response = avatarClienteChangeUseCase
            .execute(new AvatarClienteChangeRequest(
                cliente.getCodigo(),
                file,
                realTargetPath));

    if (response.isPresent()) {
        session.setAttribute("clienteAvatar", new ClienteAvatarInfo(response.get().cliente));
    }
    return "redirect:/cliente/cuenta";
}
}
