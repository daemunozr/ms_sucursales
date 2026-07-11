package com.sivebo.ms_sucursales.utils;

import org.springframework.stereotype.Component;

import com.sivebo.ms_sucursales.dto.response.ComunaResponseDTO;
import com.sivebo.ms_sucursales.dto.response.RegionResponseDTO;
import com.sivebo.ms_sucursales.dto.response.SucursalResponseDTO;
import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.model.Region;
import com.sivebo.ms_sucursales.model.Sucursal;

@Component
public class MapperUtil {

        public SucursalResponseDTO mapSucursalToDTO(Sucursal sucursal) {
                Comuna comuna = sucursal.getComuna();
                if (comuna == null) {
                        throw new IllegalStateException(
                                        "La sucursal '" + sucursal.getNombre() + "' no tiene una comuna asociada");
                }
                Region region = comuna.getRegion();
                if (region == null) {
                        throw new IllegalStateException(
                                        "La comuna '" + comuna.getNombre() + "' no tiene una región asociada");
                }
                return new SucursalResponseDTO(
                                sucursal.getNombre(),
                                comuna.getNombre(),
                                region.getNombre(),
                                sucursal.getDireccionFisica(),
                                sucursal.getTelefonoContacto(),
                                sucursal.getEstado());
        }

        public ComunaResponseDTO mapComunaToDTO(Comuna comuna) {
                Region region = comuna.getRegion();
                if (region == null) {
                        throw new IllegalStateException(
                                        "La comuna '" + comuna.getNombre() + "' no tiene una región asociada");
                }
                return new ComunaResponseDTO(
                                comuna.getNombre(),
                                region.getNombre());
        }

        public RegionResponseDTO mapRegionToDTO(Region region) {
                return new RegionResponseDTO(
                                region.getNombre());
        }
}
