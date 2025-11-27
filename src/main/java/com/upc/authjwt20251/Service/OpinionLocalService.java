package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.OpinionLocalDTO;
import com.upc.authjwt20251.Entities.OpinionLocal;
import com.upc.authjwt20251.Repository.OpinionLocalRepository;
import com.upc.authjwt20251.Repository.ProductoLocalRepository;
import com.upc.authjwt20251.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpinionLocalService {
    private final OpinionLocalRepository opinionLocalRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoLocalRepository productoLocalRepository;

    public OpinionLocalService(OpinionLocalRepository opinionLocalRepository,
                               UsuarioRepository usuarioRepository,
                               ProductoLocalRepository productoLocalRepository) {
        this.opinionLocalRepository = opinionLocalRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoLocalRepository = productoLocalRepository;
    }

    private OpinionLocal convertToEntity(OpinionLocalDTO dto) {
        OpinionLocal op = new OpinionLocal();
        op.setComentario(dto.getComentario());
        op.setCalificacion(dto.getCalificacion());
        op.setUsuario(usuarioRepository.findById(dto.getUsuarioId()).orElse(null));
        op.setProductoLocal(productoLocalRepository.findById(dto.getProductoLocalId()).orElse(null));
        return op;
    }

    private OpinionLocalDTO convertToDto(OpinionLocal op) {
        if (op == null) return null;
        OpinionLocalDTO dto = new OpinionLocalDTO();
        dto.setId(op.getId());
        dto.setComentario(op.getComentario());
        dto.setCalificacion(op.getCalificacion());
        dto.setUsuarioId(op.getUsuario() != null ? op.getUsuario().getId() : null);
        dto.setProductoLocalId(op.getProductoLocal() != null ? op.getProductoLocal().getId() : null);
        return dto;
    }

    public List<OpinionLocalDTO> findAll() {
        return opinionLocalRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public OpinionLocalDTO create(OpinionLocalDTO dto) {
        OpinionLocal opToSave = convertToEntity(dto);
        OpinionLocal savedOp = opinionLocalRepository.save(opToSave);

        return convertToDto(savedOp);
    }

    public OpinionLocalDTO update(Long id, OpinionLocalDTO dto) {
        OpinionLocal op = opinionLocalRepository.findById(id).orElse(null);

        if (op != null) {
            op.setComentario(dto.getComentario());
            op.setCalificacion(dto.getCalificacion());
            op.setUsuario(usuarioRepository.findById(dto.getUsuarioId()).orElse(null));
            op.setProductoLocal(productoLocalRepository.findById(dto.getProductoLocalId()).orElse(null));

            OpinionLocal updatedOp = opinionLocalRepository.save(op);

            return convertToDto(updatedOp);
        }
        return null;
    }

    public boolean delete(Long id) {
        OpinionLocal opin = opinionLocalRepository.findById(id).orElse(null);

        if (opin == null) {
            return false;
        }

        opinionLocalRepository.delete(opin);
        return true;
    }

    public OpinionLocalDTO findById(Long id) {
        OpinionLocal op = opinionLocalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrado"));
        return convertToDto(op);
    }
}