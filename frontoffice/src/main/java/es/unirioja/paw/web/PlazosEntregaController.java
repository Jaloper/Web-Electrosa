/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import es.unirioja.paw.jpa.PedidoStat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Jarein
 */
@Controller
@RequestMapping("/about/delivery")
public class PlazosEntregaController {
    private static final String[] REGIONES = {"PB", "IB", "IN"};
    private static final int MIN_DIAS_ENVIO = 1;
    private static final int[] MAN_DIAS_ENTREGA = {5, 8, 15}; // PB, IB, IN
    
 
 @GetMapping
    public String mostrarGrafico(
            Model model) {
        
         List<PedidoStat> stats= generarPedidosRandom(100);
        model.addAttribute("stats", stats);

        return "about/delivery";
    }
    
     private List<PedidoStat> generarPedidosRandom(int n) {
        List<PedidoStat> stats = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < n; i++) {
            int indice = random.nextInt(REGIONES.length);
            String region = REGIONES[indice];
            LocalDate fechaEnvio = generarFechasRandom();
            
            int maxDeliveryDays = MAN_DIAS_ENTREGA[indice];
            int deliveryDays = MIN_DIAS_ENVIO + random.nextInt(maxDeliveryDays - MIN_DIAS_ENVIO + 1);
            LocalDate fechaEntrega = fechaEnvio.plusDays(deliveryDays);
            
            String orderCode = "P"+String.format("%06d", i) +"-" + (fechaEntrega.getYear());
            PedidoStat stat = new PedidoStat(orderCode, fechaEnvio, fechaEntrega, region);
            
            stats.add(stat);
        }
        
        return stats;
    }

    private LocalDate generarFechasRandom() {
        long minDay = LocalDate.now().minusYears(1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}
