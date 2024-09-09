package com.reimbursement.health.domain.commands.menus;

import lombok.Data;

@Data
public class AlterarMenuCommand {
    private String id;
    private String titulo;
    private String icone;
    private String url;
    private String descricao;
}
