package com.reimbursement.health.adapters.controllers;

import com.reimbursement.health.applications.service.MenuApplicationService;
import com.reimbursement.health.domain.commands.menus.AlterarMenuCommand;
import com.reimbursement.health.domain.commands.menus.CriarMenuCommand;
import com.reimbursement.health.domain.dtos.MenuDto;
import com.reimbursement.health.domain.entities.Menu;
import com.reimbursement.health.domain.entities.Role;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/menu")
@AllArgsConstructor
public class MenuController {

    private final MenuApplicationService menuApplicationService;

    @GetMapping("/{id}")
    public MenuDto buscar(@PathVariable String id) {
        var menu = menuApplicationService.buscar(id);
        return toDto(menu);
    }

    @GetMapping()
    public List<MenuDto> listar() {
        var menus = menuApplicationService.listar();
        return menus.stream().map(MenuController::toDto).toList();
    }

    @GetMapping("/byRoleName/{roleName}")
    public List<MenuDto> listarPorRoleName(@PathVariable String roleName) {
        var menus = menuApplicationService.listarPorRole(roleName);
        return menus.stream().map(MenuController::toDto).toList();
    }

    @PostMapping()
    public void criar(@RequestBody CriarMenuCommand command) {
        menuApplicationService.criar(command);
    }

    @PutMapping("/{id}")
    public void alterar(@PathVariable UUID id, @RequestBody AlterarMenuCommand command) {
        menuApplicationService.alterar(command);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {
        menuApplicationService.excluir(id);
    }

    private static MenuDto toDto(Menu menu) {
        return MenuDto.builder()
                .id(menu.getId().toString())
                .titulo(menu.getTitulo())
                .icone(menu.getIcone())
                .url(menu.getUrl())
                .descricao(menu.getDescricao())
                .roles(menu.getRoles().stream().map(Role::getNome).toList())
                .subMenus(menu.getSubMenus().stream().map(MenuController::toDto).toList())
                .build();
    }
}
