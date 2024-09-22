package com.example.conductor_app;

import android.app.Activity;
import android.content.Context;
import com.example.conductor_app.backend.Repository.PatenteDao;
import com.example.conductor_app.backend.Service.PatenteRepetidaException;
import com.example.conductor_app.backend.Service.PatenteService;
import com.example.conductor_app.backend.modelo.Patente;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class PatenteServiceTest {

    private PatenteService patenteService;
    private PatenteDao patenteDao;

    @Before
    public void setUp() {
        patenteDao = mock(PatenteDao.class);
        patenteService = new PatenteService(patenteDao); // Usa el mock aquí
    }

    @Test
    public void testGetAllPatentes() {
        List<Patente> patentes = new ArrayList<>();
        Patente patente = new Patente();
        patente.setCaracteres("ABC123");
        patentes.add(patente);

        when(patenteDao.findAll()).thenReturn(patentes);

        List<Patente> result = patenteService.getAllPatentes();

        // Verificar que se retornaron las patentes
        assertEquals(1, result.size());
        assertEquals("ABC123", result.get(0).getCaracteres());
    }

    @Test
    public void testSavePatente_whenPatenteNoExiste() throws Exception {
        Patente patente = new Patente();
        patente.setCaracteres("XYZ789");

        when(patenteDao.findByCaracteres("XYZ789")).thenReturn(null);

        patenteService.savePatente(patente);

        // Verificar que se llamó al método de insertar
        verify(patenteDao).insert(patente);
    }

    @Test(expected = PatenteRepetidaException.class)
    public void testSavePatente_whenPatenteYaExiste() throws Exception {
        Patente patente = new Patente();
        patente.setCaracteres("XYZ789");

        Patente existente = new Patente();
        existente.setCaracteres("XYZ789");

        when(patenteDao.findByCaracteres("XYZ789")).thenReturn(existente);

        patenteService.savePatente(patente);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSavePatente_whenFormatoInvalido() throws Exception {
        Patente patente = new Patente();
        patente.setCaracteres("INVALID"); // Formato inválido

        patenteService.savePatente(patente); // Debería lanzar IllegalArgumentException
    }
}
