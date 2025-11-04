/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.ClienteEntity;
import es.unirioja.paw.jpa.LineaCestaCompraEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.service.CestaCompraUseCase;
import es.unirioja.paw.service.PedidoService;
import es.unirioja.paw.service.data.AddToCartRequest;
import es.unirioja.paw.service.data.FinalizarCompraRequest;
import es.unirioja.paw.service.data.FinalizarCompraResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Jarein
 */
@Controller
@RequestMapping({"/clientes", "/cliente"})
public class CestaController {

    @Autowired
    PedidoService pedidoService;
    @Autowired
    CestaCompraUseCase cestaCompraUseCase;

    @GetMapping("/cesta")
    public String mostrarCesta(Model model, HttpSession session) {
        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute("cesta");
        List<LineaCestaCompraEntity> lineas = (cesta != null) ? cesta.getLineas() : new ArrayList<>();
        double total = 0;
        for (LineaCestaCompraEntity l : lineas) {
            total += l.getCantidad() * l.getPrecio();
        }

        model.addAttribute("lineasCesta", lineas);
        model.addAttribute("preciototal", total);
        return "cesta";
    }

    @GetMapping("/cesta/add/{codigoArticulo}") //Usamos un GET en lugar de un POST porque así un usuario que no está loggueado puede añadir a su carrito y no perder el curso tras logguearse.
    public String añadirProducto(@PathVariable("codigoArticulo") String codigoArticulo, HttpSession session) {
        CestaCompraEntity cesta = cestaFromSession(session);
        if (cesta == null) {
            return "redirect:/auth/login";
        }

        cestaCompraUseCase.add(new AddToCartRequest(codigoArticulo, cesta));
        session.setAttribute("cesta", cesta);
        return "redirect:/cliente/cesta";
    }

    private CestaCompraEntity cestaFromSession(HttpSession session) {
        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute("cesta");
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");

        if (cliente == null) {
            return null;
        }
        if (cesta == null) {
            cesta = new CestaCompraEntity();
            cesta.setCodigoCliente(cliente.getCodigo());
            cesta.setFechaInicio(LocalDateTime.now());
            cesta.setLineas(new ArrayList<>());
        }
        return cesta;
    }

    @GetMapping("/cesta/realizar-pedido")
    public String realizarPedido(HttpSession session, Model model) {
        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute("cesta");
        if (cesta == null) {
            return "redirect:/catalogo";
        }
        model.addAttribute("narticulos", cesta.getLineas().size());
        return "direccion_envio";
    }

    @GetMapping("/cesta/guardar-pedido")
    public String procesarPedido(
            @RequestParam("calle") String calle,
            @RequestParam("codigoPostal") String codigoPostal,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("provincia") String provincia,
            @RequestParam("fechaEntregaDeseada") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntregaDeseada,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute("cesta");
        if (cesta == null || cesta.getLineas() == null || cesta.getLineas().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "La cesta está vacía");
            return "redirect:/cliente/cesta";
        }

        FinalizarCompraRequest request = new FinalizarCompraRequest(cesta, calle, codigoPostal, ciudad, provincia, fechaEntregaDeseada);
        FinalizarCompraResponse response = cestaCompraUseCase.finalizarCompra(request);
        if (response.pedido == null) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el pedido");
            return "redirect:/cliente/cesta";
        }
        pedidoService.deleteCestaCliente(cliente.getCodigo());
        session.removeAttribute("cesta");
        redirectAttributes.addFlashAttribute("success", "Pedido creado con éxito. Nº: " + response.pedido.getCodigo());
        return "redirect:/cliente/pedidos/" + response.pedido.getCodigo();
    }
    
    @PostMapping("/cesta/update")
@ResponseBody
public ResponseEntity<Map<String, Object>> actualizarLineaCesta(
        @RequestParam("lineId") String codigoArticulo,
        @RequestParam("quantity") int quantity,
        HttpSession session
) {
    Map<String, Object> response = new HashMap<>();
    CestaCompraEntity cesta = cestaFromSession(session);

    if (cesta == null) {
        response.put("success", false);
        response.put("message", "Debe iniciar sesión");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    cestaCompraUseCase.actualizarCantidad(cesta, codigoArticulo, quantity);
    session.setAttribute("cesta", cesta);

    double lineTotal = 0, cartTotal = 0;
    for (LineaCestaCompraEntity l : cesta.getLineas()) {
        int qty = l.getArticulo().getCodigo().equals(codigoArticulo)
                  ? quantity
                  : l.getCantidad();
        double sub = l.getPrecio() * qty;
        if (l.getArticulo().getCodigo().equals(codigoArticulo)) {
            lineTotal = sub;
        }
        cartTotal += sub;
    }

    response.put("success", true);
    response.put("lineTotal", lineTotal);
    response.put("cartTotal", cartTotal);
    response.put("newQuantity", quantity);
    return ResponseEntity.ok(response);
}




}