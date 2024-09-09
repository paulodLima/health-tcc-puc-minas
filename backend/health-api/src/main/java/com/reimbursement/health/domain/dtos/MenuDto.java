package com.reimbursement.health.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class MenuDto {
    private String id;
    private String titulo;
    private String icone;
    private String url;
    private String descricao;
    private List<MenuDto> subMenus;
}
