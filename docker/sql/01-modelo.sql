CREATE DATABASE keycloak;
-- Ensure the uuid-ossp extension is available
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create the table
CREATE TABLE tb_user (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    inclusion_date TIMESTAMPTZ,
    update_date TIMESTAMPTZ,
    inclusion_user VARCHAR(255),
    update_user VARCHAR(255),
    name VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    status BOOLEAN,
    is_password_temporary BOOLEAN
);

CREATE TABLE tb_companies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    inclusion_date TIMESTAMPTZ DEFAULT now(),
    update_date TIMESTAMPTZ DEFAULT now(),
    inclusion_user VARCHAR(255),
    update_user VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(14),
    status BOOLEAN
);

CREATE TABLE tb_reimbursement_requests (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    amount NUMERIC(15, 2) NOT NULL,
    company_id UUID NOT NULL,
    employee_id UUID NOT NULL,
    inclusion_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    inclusion_user VARCHAR(255) NOT NULL,
    status SMALLINT NOT NULL, -- Coluna para o status, agora numérica
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_user VARCHAR(255),
    
    -- Adiciona um comentário explicativo para a coluna status
    CONSTRAINT fk_company
        FOREIGN KEY (company_id) REFERENCES tb_companies(id),
    CONSTRAINT fk_employee
        FOREIGN KEY (employee_id) REFERENCES TB_USER(id)
);

-- Adicionar um comentário à coluna status
COMMENT ON COLUMN tb_reimbursement_requests.status IS
    '1 = PENDING, 2 = APPROVED, 3 = REJECTED';
	
CREATE TABLE tb_invoices (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    inclusion_date TIMESTAMPTZ DEFAULT now(),
    update_date TIMESTAMPTZ DEFAULT now(),
    inclusion_user VARCHAR(255),
    update_user VARCHAR(255),
    reimbursement_request_id UUID NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (reimbursement_request_id) REFERENCES tb_reimbursement_requests(id)
);

CREATE TABLE tb_medical_requests (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    inclusion_date TIMESTAMPTZ DEFAULT now(),
    update_date TIMESTAMPTZ DEFAULT now(),
    inclusion_user VARCHAR(255),
    update_user VARCHAR(255),
    reimbursement_request_id UUID NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (reimbursement_request_id) REFERENCES tb_reimbursement_requests(id)
);

CREATE TABLE tb_menu (
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    TITULO VARCHAR(100) NOT NULL,
    ICONE VARCHAR(100) NULL,
    URL VARCHAR(300) NULL,
    DESCRICAO VARCHAR(500) NULL,
    MENU_PAI_ID UUID NULL
);

CREATE TABLE tb_role (
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    NOME VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE tb_menu_role (
    MENU_ID UUID NOT NULL,
    ROLE_ID UUID NOT NULL,
    PRIMARY KEY (MENU_ID, ROLE_ID),
    FOREIGN KEY (MENU_ID) REFERENCES tb_menu(ID) ON DELETE CASCADE,
    FOREIGN KEY (ROLE_ID) REFERENCES tb_role(ID) ON DELETE CASCADE
);

INSERT INTO tb_role (ID,NOME) VALUES ('6c67fd3c-c9d1-4fde-8f59-c766abd87776','admin');
INSERT INTO tb_role (ID,NOME) VALUES ('0d6adaf9-20ab-412e-b336-17f78b0c13ff','user');
INSERT INTO tb_role (id, nome) VALUES ('52412b57-7f95-4174-9e8d-5469c63c6b18', 'manager');

INSERT INTO tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('898bc02b-95bb-4b48-8b66-3c6f7c1ad5f4', 'Usuários', 'pi-users', '', 'usuarios', null);
INSERT INTO tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('16b367ba-b8cd-4724-a633-f7084fbb9c8a', 'Configurações', 'pi-cog', '/login', 'teste', null);
INSERT INTO tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('349bdd98-5b4b-4da7-99f4-3b0a3265bd62', 'Administrar', 'pi-user-edit', '/cadastro-usuario', 'Administrar usuarios', '898bc02b-95bb-4b48-8b66-3c6f7c1ad5f4');
INSERT INTO tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('3a3d1b76-c8cc-4cce-b06d-963d4af77e97', 'Reembolso', 'pi-wallet', '/reembolso', 'solicitar reembolso', null);



INSERT INTO tb_menu_role (MENU_ID, ROLE_ID) VALUES ('16b367ba-b8cd-4724-a633-f7084fbb9c8a', '6c67fd3c-c9d1-4fde-8f59-c766abd87776');
INSERT INTO tb_menu_role (MENU_ID, ROLE_ID) VALUES ('898bc02b-95bb-4b48-8b66-3c6f7c1ad5f4', '6c67fd3c-c9d1-4fde-8f59-c766abd87776');
INSERT INTO tb_menu_role (MENU_ID, ROLE_ID) VALUES ('349bdd98-5b4b-4da7-99f4-3b0a3265bd62', '6c67fd3c-c9d1-4fde-8f59-c766abd87776');
INSERT INTO tb_menu_role (menu_id, role_id) VALUES ('3a3d1b76-c8cc-4cce-b06d-963d4af77e97', '0d6adaf9-20ab-412e-b336-17f78b0c13ff');
INSERT INTO tb_menu_role (MENU_ID, ROLE_ID) VALUES ('349bdd98-5b4b-4da7-99f4-3b0a3265bd62', '6c67fd3c-c9d1-4fde-8f59-c766abd87776');
