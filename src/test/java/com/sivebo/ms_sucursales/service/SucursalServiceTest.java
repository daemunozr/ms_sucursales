package com.sivebo.ms_sucursales.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sivebo.ms_sucursales.dto.request.SucursalCreateDTO;
import com.sivebo.ms_sucursales.dto.response.SucursalResponseDTO;
import com.sivebo.ms_sucursales.exception.EntityNotFoundException;
import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.model.EstadoSucursal;
import com.sivebo.ms_sucursales.model.Sucursal;
import com.sivebo.ms_sucursales.repository.ComunaRepository;
import com.sivebo.ms_sucursales.repository.SucursalRepository;
import com.sivebo.ms_sucursales.utils.MapperUtil;

@ExtendWith(MockitoExtension.class)
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private SucursalService sucursalService;

    private Sucursal sucursal;
    private SucursalResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Test Sucursal");

        responseDTO = new SucursalResponseDTO("Test Sucursal", "Test Comuna", "Test Region", "Dir", "123", EstadoSucursal.ACTIVA);
    }

    @Test
    void getByNameShouldReturnSucursal() {
        when(sucursalRepository.findByNombre("Test Sucursal")).thenReturn(Optional.of(sucursal));
        when(mapperUtil.mapSucursalToDTO(sucursal)).thenReturn(responseDTO);

        Optional<SucursalResponseDTO> result = sucursalService.getByNombre("Test Sucursal");

        assertTrue(result.isPresent());
        assertEquals("Test Comuna", result.get().getNombreComuna());
        verify(sucursalRepository, times(1)).findByNombre("Test Sucursal");
    }

    @Test
    void getByNombreNoEncontradoRetornaVacio() {
        when(sucursalRepository.findByNombre("Alksjddksljjdfhs")).thenReturn(Optional.empty());

        Optional<SucursalResponseDTO> result = sucursalService.getByNombre("Alksjddksljjdfhs");

        assertFalse(result.isPresent());
    }

    @Test
    void getAllRetornaListaMapeada() {
        when(sucursalRepository.findAll()).thenReturn(List.of(sucursal));
        when(mapperUtil.mapSucursalToDTO(sucursal)).thenReturn(responseDTO);

        List<SucursalResponseDTO> result = sucursalService.getAll();

        assertEquals(1, result.size());
        assertEquals("Test Sucursal", result.get(0).getNombre());
    }

    @Test
    void createComunaExistenteGuardaYRetornaDTO() {
        SucursalCreateDTO dto = new SucursalCreateDTO("Test Sucursal", "Santiago", "Calle 1", "123");
        Comuna comuna = new Comuna();

        when(comunaRepository.findByNombre("Santiago")).thenReturn(Optional.of(comuna));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursal);
        when(mapperUtil.mapSucursalToDTO(sucursal)).thenReturn(responseDTO);

        SucursalResponseDTO result = sucursalService.create(dto);

        assertNotNull(result);
        assertEquals("Test Sucursal", result.getNombre());
        verify(sucursalRepository).save(any(Sucursal.class));
    }

    @Test
    void createComunaNoExistenteLanzaEntityNotFoundException() {
        SucursalCreateDTO dto = new SucursalCreateDTO("Test", "Inexistente", "Calle", null);

        when(comunaRepository.findByNombre("Inexistente")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sucursalService.create(dto));
        verify(sucursalRepository, never()).save(any());
    }

    @Test
    void deactivateEncontradoCambiaEstadoAInactiva() {
        Sucursal inactiva = new Sucursal(1L, "Test Sucursal", null, "Dir", "123", EstadoSucursal.INACTIVA);
        SucursalResponseDTO inactivaDTO = new SucursalResponseDTO("Test Sucursal", "Test Comuna", "Test Region", "Dir", "123", EstadoSucursal.INACTIVA);

        when(sucursalRepository.findByNombre("Test Sucursal")).thenReturn(Optional.of(sucursal));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(inactiva);
        when(mapperUtil.mapSucursalToDTO(inactiva)).thenReturn(inactivaDTO);

        Optional<SucursalResponseDTO> result = sucursalService.deactivate("Test Sucursal");

        assertTrue(result.isPresent());
        assertEquals(EstadoSucursal.INACTIVA, result.get().getEstado());
    }

    @Test
    void deleteExistenteEliminaYRetornaTrue() {
        when(sucursalRepository.existsByNombre("Test Sucursal")).thenReturn(true);

        Boolean result = sucursalService.delete("Test Sucursal");

        assertTrue(result);
        verify(sucursalRepository, times(1)).deleteByNombre("Test Sucursal");
    }

    @Test
    void deleteNoExistenteNoEliminaYRetornaFalse() {
        when(sucursalRepository.existsByNombre("Inexistente")).thenReturn(false);

        Boolean result = sucursalService.delete("Inexistente");

        assertFalse(result);
        verify(sucursalRepository, never()).deleteByNombre(anyString());
    }
}
