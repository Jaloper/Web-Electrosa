/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package es.unirioja.paw.db;

import es.unirioja.paw.dao.ArticuloDAO;
import es.unirioja.paw.dao.FabricanteDAO;
import es.unirioja.paw.model.Fabricante;
import es.unirioja.paw.model.TipoArticulo;
import es.unirioja.paw.mysql.CatalogoDaoMySQL;
import java.sql.Connection;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import es.unirioja.paw.dao.CatalogoDAO;

/**
 *
 * @author Jarein
 */
public class GestorBDTest {

    public GestorBDTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getArticulosLike method, of class GestorBD.
     */
    /**
     * Test of getConnection method, of class GestorBD.
     */
    @Test
    public void testGetArticulosLike() throws Exception {
        System.out.println("getArticulosLike");
        String nombre = "Sci";
        GestorBD instance = new GestorBD();
        List<String> result = instance.getArticulosLike(nombre);
        assertNotNull(result);
        assertTrue(result.size() == 4);
    }
    @Test
        public void testArticuloDAOfindTiposArticulo() throws Exception{
        System.out.println("ArticuloDAO.findTiposArticulo");
        List<TipoArticulo> result = ArticuloDAO.findTiposArticulos();
        assertNotNull(result);
        assertTrue(result.size() == 9);
    }
    @Test
    public void testFabricanteDAOfindAll() throws Exception{
        System.out.println("FabricanteDAO.findAll");
        List<Fabricante> result = FabricanteDAO.findAll();
        assertNotNull(result);
        assertTrue(result.size() == 3);
    }
}
