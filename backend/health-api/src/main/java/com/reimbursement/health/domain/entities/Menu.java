package com.reimbursement.health.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@jakarta.persistence.Entity
@Table(name = "TB_MENU")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends Entity {
    private String titulo;
    private String icone;
    private String url;
    private String descricao;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "tb_menu_role",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "menu_pai_id")
    private Menu menuPai;

    @Builder
    public Menu(String titulo, String icone, String url, String descricao, Menu menuPai) {
        setId(UUID.randomUUID());
        this.titulo = titulo;
        this.icone = icone;
        this.url = url;
        this.descricao = descricao;
        this.menuPai = menuPai;
        this.subMenus = new ArrayList<>();
    }

    @OneToMany(mappedBy = "menuPai", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> subMenus;

    public void adicionarSubMenu(Menu menu) {
        this.subMenus.add(menu);
        menu.setMenuPai(this);
    }

    public void removerSubMenu(Menu menu) {
        this.subMenus.remove(menu);
        menu.setMenuPai(null);
    }
}
