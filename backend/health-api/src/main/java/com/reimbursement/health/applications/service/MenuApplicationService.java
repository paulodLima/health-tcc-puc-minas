package com.reimbursement.health.applications.service;

import com.reimbursement.health.adapters.repositories.jpa.MenuRepository;
import com.reimbursement.health.domain.commands.menus.AlterarMenuCommand;
import com.reimbursement.health.domain.commands.menus.CriarMenuCommand;
import com.reimbursement.health.domain.entities.Menu;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuApplicationService {

    @Autowired
    private MenuRepository repository;

    public void criar(CriarMenuCommand command) {
        var menuPai = command.getMenuSuperiorId() == null ? null : repository.findById(UUID.fromString(command.getMenuSuperiorId())).orElseThrow();
        var menu = Menu.builder()
                .titulo(command.getTitulo())
                .url(command.getUrl())
                .icone(command.getIcone())
                .descricao(command.getDescricao())
                .menuPai(menuPai)
                .build();
        repository.save(menu);

    }
    public List<Menu> listarPorRole(String roleName) {
        List<Menu> allMenus = repository.findByRoleName(roleName);

        Set<UUID> submenuIds = allMenus.stream()
                .flatMap(menu -> menu.getSubMenus().stream())
                .map(Menu::getId)
                .collect(Collectors.toSet());

        return allMenus.stream()
                .filter(menu -> !submenuIds.contains(menu.getId()))
                .collect(Collectors.toList());
    }

    public List<Menu> listar() {
        return repository.findByMenuPai(null);
    }

    public Menu buscar(String id) {
        return repository.findById(UUID.fromString(id)).orElseThrow();
    }

    public void alterar(AlterarMenuCommand command) {
        var menu = repository.findById(UUID.fromString(command.getId())).orElseThrow();
        menu.setTitulo(command.getTitulo());
        menu.setUrl(command.getUrl());
        menu.setIcone(command.getIcone());
        menu.setDescricao(command.getDescricao());
        repository.save(menu);
    }

    public void excluir(UUID id) {
        var menu = repository.findById(id).orElseThrow();
        excluirRecursivo(menu);
    }

    private void excluirRecursivo(Menu menu) {
        for (var subMenu : menu.getSubMenus()) {
            excluirRecursivo(subMenu);
        }
        repository.delete(menu);
    }
}
