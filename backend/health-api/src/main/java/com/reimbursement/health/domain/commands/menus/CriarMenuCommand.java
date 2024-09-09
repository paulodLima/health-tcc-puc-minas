package com.reimbursement.health.domain.commands.menus;

import lombok.Data;

@Data
public class CriarMenuCommand {
    private String titulo;
    private String icone;
    private String url;
    private String descricao;
    private String menuSuperiorId;
}
