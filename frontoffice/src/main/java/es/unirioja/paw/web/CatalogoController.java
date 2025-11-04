package es.unirioja.paw.web;

import es.unirioja.paw.exception.ArticuloNotFoundException;
import es.unirioja.paw.jpa.ArticuloEntity;
import es.unirioja.paw.repository.ArticuloRepository;
import es.unirioja.paw.service.ArticuloFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private ArticuloRepository articuloRepository;
    @Autowired
    private ArticuloFinder articuloFinder;
    private static final int ArtsPag = 15;

@GetMapping
public String mostrarCatalogo(
        @RequestParam(name = "page", defaultValue = "1") int pag,
        @RequestParam(name = "fabricante", required = false) String fabricante,
        @RequestParam(name = "tipo", required = false) String tipo,
        Model model) {

    Pageable paginado = PageRequest.of(pag - 1, ArtsPag);
    Page<ArticuloEntity> paginaArticulos;

    ArticuloEntity entidadEjemplo = new ArticuloEntity();
    if (fabricante != null && !fabricante.isEmpty()) {
        entidadEjemplo.setFabricante(fabricante);
    }
    if (tipo != null && !tipo.isEmpty()) {
        entidadEjemplo.setTipo(tipo);
    }

    if ((fabricante != null && !fabricante.isEmpty()) || (tipo != null && !tipo.isEmpty())) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
            .withIgnoreNullValues()
            .withIgnorePaths("codigo", "nombre", "pvp", "foto","descripcion");

        Example<ArticuloEntity> example = Example.of(entidadEjemplo, matcher);
        paginaArticulos = articuloRepository.findBy(example, query -> query.page(paginado));

    } else {
        paginaArticulos = articuloRepository.findAll(paginado);
    }

    model.addAttribute("fabricantes", articuloFinder.findFabricantes());
    model.addAttribute("tiposArticulo", articuloFinder.findTiposArticulo());

    model.addAttribute("articulos", paginaArticulos.getContent());
    model.addAttribute("currentPage", pag);
    model.addAttribute("totalPages", paginaArticulos.getTotalPages());
    model.addAttribute("totalArts", paginaArticulos.getTotalElements());

    model.addAttribute("fabricanteSeleccionado", fabricante);
    model.addAttribute("tipoSeleccionado", tipo);

    return "catalogo";
}

@GetMapping("/{artId}")
public String mostrarFichaArticulo(
        @PathVariable("artId") String artId,
        Model model) throws ArticuloNotFoundException {
    
    ArticuloEntity articulo = articuloRepository.findById(artId)
            .orElseThrow(() -> new ArticuloNotFoundException(
            "Artículo " + artId + " no encontrado"));
    
    model.addAttribute("articulo", articulo);
    
    return "ficha-articulo";
}
}