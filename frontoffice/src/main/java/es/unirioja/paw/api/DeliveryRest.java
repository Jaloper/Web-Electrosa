/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.api;

import es.unirioja.paw.exception.PedidoNotFoundException;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.repository.PedidoRepository;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jarein
 */
@RestController
@RequestMapping("/api/delivery")
public class DeliveryRest {
     @Autowired
    private PedidoRepository pedidoRepository;
     
    @PostMapping("/{pedidoId}/schedule")
    @Transactional
    public PedidoEntity programarEntrega(
            @PathVariable("pedidoId") String pedidoId,
            @RequestBody DeliveryScheduleRequest request) {

        Optional<PedidoEntity> pedidoOpt = pedidoRepository.findById(pedidoId);

        if (pedidoOpt.isPresent()) {
            PedidoEntity pedido = pedidoOpt.get();

            if (pedido.getCursado()!=1) {
                pedido.setCursado(1);
                pedido.setFechacierre(new Date(System.currentTimeMillis()+request.ndays));
                pedidoRepository.save(pedido);
            }

            return pedido;
        }

        throw new PedidoNotFoundException(pedidoId);
    }

    @PostMapping("/{pedidoId}/schedule/cancel")
    @Transactional
    public PedidoEntity cancelarEntrega(@PathVariable("pedidoId") String pedidoId) {
        Optional<PedidoEntity> pedidoOpt = pedidoRepository.findById(pedidoId);

        if (pedidoOpt.isPresent()) {
            PedidoEntity pedido = pedidoOpt.get();

            if (pedido.getCursado()==1) {
                pedido.setCursado(0);
                 pedido.setFechacierre(null);
                pedidoRepository.save(pedido);
            }

            return pedido;
        }

        throw new PedidoNotFoundException(pedidoId);
    }

    public static class DeliveryScheduleRequest {
        private int ndays;

        public int getNdays() {
            return ndays;
        }

        public void setNdays(int ndays) {
            this.ndays = ndays;
        }
    }
}