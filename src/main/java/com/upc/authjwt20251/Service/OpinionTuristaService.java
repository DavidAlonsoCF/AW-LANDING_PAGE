package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.OpinionTuristaDTO;
import com.upc.authjwt20251.Entities.OpinionTurista;
import com.upc.authjwt20251.Repository.DestinoRepository;
import com.upc.authjwt20251.Repository.OpinionTuristaRepository;
import com.upc.authjwt20251.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpinionTuristaService {

    private final OpinionTuristaRepository opinionTuristaRepository;
    private final UsuarioRepository usuarioRepository;
    private final DestinoRepository destinoRepository;

    public OpinionTuristaService(OpinionTuristaRepository opinionTuristaRepository,
                                 UsuarioRepository usuarioRepository,
                                 DestinoRepository destinoRepository) {
        this.opinionTuristaRepository = opinionTuristaRepository;
        this.usuarioRepository = usuarioRepository;
        this.destinoRepository = destinoRepository;
    }

    private OpinionTuristaDTO toDTO(OpinionTurista op) {
        if (op == null) return null;
        OpinionTuristaDTO dto = new OpinionTuristaDTO();
        dto.setId(op.getId());
        dto.setComentario(op.getComentario());
        dto.setCalificacion(op.getCalificacion());
        dto.setUsuarioId(op.getUsuario() != null ? op.getUsuario().getId() : null);
        dto.setDestinoId(op.getDestino() != null ? op.getDestino().getId() : null);
        return dto;
    }

    private OpinionTurista toEntity(OpinionTuristaDTO dto) {
        OpinionTurista opinion = new OpinionTurista();
        opinion.setComentario(dto.getComentario());
        opinion.setCalificacion(dto.getCalificacion());

        opinion.setUsuario(usuarioRepository.findById(dto.getUsuarioId()).orElse(null));
        opinion.setDestino(destinoRepository.findById(dto.getDestinoId()).orElse(null));

        return opinion;
    }

    public List<OpinionTuristaDTO> findAll() {
        return opinionTuristaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public OpinionTuristaDTO create(OpinionTuristaDTO dto) {
        OpinionTurista opinionToSave = toEntity(dto);
        OpinionTurista savedOpinion = opinionTuristaRepository.save(opinionToSave);

        return toDTO(savedOpinion);
    }

    public OpinionTuristaDTO update(Long id, OpinionTuristaDTO dto) {
        OpinionTurista opinion = opinionTuristaRepository.findById(id).orElse(null);

        if (opinion != null) {
            opinion.setComentario(dto.getComentario());
            opinion.setCalificacion(dto.getCalificacion());

            opinion.setUsuario(usuarioRepository.findById(dto.getUsuarioId()).orElse(null));
            opinion.setDestino(destinoRepository.findById(dto.getDestinoId()).orElse(null));

            OpinionTurista updatedOpinion = opinionTuristaRepository.save(opinion);
            return toDTO(updatedOpinion);
        }
        return null;
    }

    public boolean delete(Long id) {
        OpinionTurista op = opinionTuristaRepository.findById(id).orElse(null);

        if (op == null) {
            return false;
        }

        opinionTuristaRepository.delete(op);
        return true;
    }
}