package com.sivebo.ms_sucursales.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.model.EstadoSucursal;
import com.sivebo.ms_sucursales.model.Region;
import com.sivebo.ms_sucursales.model.Sucursal;
import com.sivebo.ms_sucursales.repository.ComunaRepository;
import com.sivebo.ms_sucursales.repository.RegionRepository;
import com.sivebo.ms_sucursales.repository.SucursalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

        private final SucursalRepository sucursalRepository;
        private final ComunaRepository comunaRepository;
        private final RegionRepository regionRepository;

        private record RegionSeed(String nombre) {
        }

        private record ComunaSeed(String nombre, String regionNombre) {
        }

        private record SucursalSeed(String nombre, String comunaNombre, String direccion, String telefono) {
        }

        private static final List<RegionSeed> REGIONES = List.of(
                        new RegionSeed("Arica y Parinacota"),
                        new RegionSeed("Antofagasta"),
                        new RegionSeed("Coquimbo"),
                        new RegionSeed("Valparaiso"),
                        new RegionSeed("Metropolitana"),
                        new RegionSeed("O'Higgins"),
                        new RegionSeed("Maule"),
                        new RegionSeed("Biobio"),
                        new RegionSeed("Los Lagos"),
                        new RegionSeed("Magallanes"));

        private static final List<ComunaSeed> COMUNAS = List.of(
                        new ComunaSeed("Arica", "Arica y Parinacota"),
                        new ComunaSeed("Antofagasta", "Antofagasta"),
                        new ComunaSeed("La Serena", "Coquimbo"),
                        new ComunaSeed("Valparaiso", "Valparaiso"),
                        new ComunaSeed("Santiago", "Metropolitana"),
                        new ComunaSeed("Rancagua", "O'Higgins"),
                        new ComunaSeed("Talca", "Maule"),
                        new ComunaSeed("Concepcion", "Biobio"),
                        new ComunaSeed("Puerto Montt", "Los Lagos"),
                        new ComunaSeed("Punta Arenas", "Magallanes"));

        private static final List<SucursalSeed> SUCURSALES = List.of(
                        new SucursalSeed("Correos Arica", "Arica", "Arturo Prat 305", "+56582231305"),
                        new SucursalSeed("Correos Antofagasta", "Antofagasta", "Washington 2613", "+56552225050"),
                        new SucursalSeed("Correos La Serena", "La Serena", "Matta 461", "+56512224610"),
                        new SucursalSeed("Correos Valparaiso", "Valparaiso", "Prat 856", "+56322254850"),
                        new SucursalSeed("Correos Plaza de Armas", "Santiago", "Plaza de Armas 989", "+56226980300"),
                        new SucursalSeed("Correos Rancagua", "Rancagua", "Campos 322", "+56722231220"),
                        new SucursalSeed("Correos Talca", "Talca", "1 Oriente 1150", "+56712226500"),
                        new SucursalSeed("Correos Concepcion", "Concepcion", "Galeria Foro Universitario", "+56412230900"),
                        new SucursalSeed("Correos Puerto Montt", "Puerto Montt", "Rancagua 126", "+56652253400"),
                        new SucursalSeed("Correos Punta Arenas", "Punta Arenas", "Bories 911", "+56612241300"));

        @Override
        public void run(String... args) {
                if (regionRepository.count() > 0) {
                        log.info(">>> regiones ya cargadas. Se omite inicialización.");
                } else {
                        log.info(">>> Cargando regiones iniciales...");

                        REGIONES.forEach(seed -> regionRepository.save(new Region(null, seed.nombre())));

                        log.info(">>> Regiones iniciales cargadas exitosamente.");
                }

                if (comunaRepository.count() > 0) {
                        log.info(">>> comunas ya cargadas. Se omite inicialización.");
                } else {
                        log.info(">>> Cargando comunas iniciales...");

                        COMUNAS.forEach(seed -> {
                                Region region = regionRepository.findByNombre(seed.regionNombre())
                                                .orElseThrow(() -> new RuntimeException(
                                                                "Region no encontrada"));
                                comunaRepository.save(new Comuna(null, seed.nombre(), region));
                        });

                        log.info(">>> Comunas iniciales cargadas exitosamente.");
                }

                if (sucursalRepository.count() > 0) {
                        log.info(">>> Sucursales ya cargadas. Se omite inicialización.");
                } else {
                        log.info(">>> Cargando sucursales iniciales...");

                        SUCURSALES.forEach(seed -> {
                                Comuna comuna = comunaRepository.findByNombre(seed.comunaNombre())
                                                .orElseThrow(() -> new RuntimeException(
                                                                "Comuna no encontrada"));
                                sucursalRepository.save(new Sucursal(
                                                null,
                                                seed.nombre(),
                                                comuna,
                                                seed.direccion(),
                                                seed.telefono(),
                                                EstadoSucursal.ACTIVA));
                        });

                        log.info(">>> Sucursales iniciales cargadas exitosamente.");
                }

                log.info(">>> Inicialización de datos completada.");
        }

}
