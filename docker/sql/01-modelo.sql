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
    observation VARCHAR(255) NOT NULL,
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
	s3_key VARCHAR(255),
    inclusion_user VARCHAR(255),
    update_user VARCHAR(255),
    reimbursement_request_id UUID NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (reimbursement_request_id) REFERENCES tb_reimbursement_requests(id)
);

CREATE TABLE tb_medical_requests (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    inclusion_date TIMESTAMPTZ DEFAULT now(),
	s3_key VARCHAR(255),
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

-----------------------------------------------------------------       MENU         -----------------------------------------------------------------------------------
INSERT INTO public.tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('898bc02b-95bb-4b48-8b66-3c6f7c1ad5f4', 'Usuários', 'pi-users', '', 'usuarios', null);
INSERT INTO public.tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('16b367ba-b8cd-4724-a633-f7084fbb9c8a', 'Configurações', 'pi-cog', '/login', 'teste', null);
INSERT INTO public.tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('3a3d1b76-c8cc-4cce-b06d-963d4af77e97', 'Reembolso', 'pi-wallet', '', 'reembolso', null);
INSERT INTO public.tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('85471f7d-e6e0-4f12-a9bf-93d9c8888fb0', 'Nova', 'pi-money-bill', '/reembolso/new', 'Solicitar reembolso', '3a3d1b76-c8cc-4cce-b06d-963d4af77e97');
INSERT INTO public.tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('e427ec51-960c-4e4c-b4a3-085086d118a2', 'Solicitações', 'pi-cog', '/reembolso', 'Administrar reembolso', '3a3d1b76-c8cc-4cce-b06d-963d4af77e97');
INSERT INTO public.tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('17bf3124-79af-410c-b54b-631794d0a18f', 'Empresa', 'pi-shop', '', 'Empresa', null);
INSERT INTO public.tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('bd183600-892a-4fbe-95ff-e3f3941b259d', 'Nova', 'pi-shop', '/empresa/new', 'Cadastrar Empresa', '17bf3124-79af-410c-b54b-631794d0a18f');
INSERT INTO public.tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('3f9e1e0e-a4bd-4c76-bceb-fb5769be9241', 'Lista', 'pi-shop', '/empresa', 'Lista Empresa', '17bf3124-79af-410c-b54b-631794d0a18f');
INSERT INTO public.tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('349bdd98-5b4b-4da7-99f4-3b0a3265bd62', 'Administrar', 'pi-user-edit', '/usuario/new', 'Administrar usuarios', '898bc02b-95bb-4b48-8b66-3c6f7c1ad5f4');
INSERT INTO public.tb_menu (id, titulo, icone, url, descricao, menu_pai_id) VALUES ('cd91f870-b2d6-42ea-85ee-717b586e6bb6', 'Lista', 'pi-user', '/usuario', 'Administrar usuarios', '898bc02b-95bb-4b48-8b66-3c6f7c1ad5f4');

-----------------------------------------------------------------       ROLES         -----------------------------------------------------------------------------------

INSERT INTO public.tb_role (id, nome) VALUES ('6c67fd3c-c9d1-4fde-8f59-c766abd87776', 'admin');
INSERT INTO public.tb_role (id, nome) VALUES ('0d6adaf9-20ab-412e-b336-17f78b0c13ff', 'user');
INSERT INTO public.tb_role (id, nome) VALUES ('52412b57-7f95-4174-9e8d-5469c63c6b18', 'manager');


------------------------------------------------------------------- MENU ROLES -------------------------------------------------------------------------------------------

INSERT INTO public.tb_menu_role (menu_id, role_id) VALUES ('16b367ba-b8cd-4724-a633-f7084fbb9c8a', '6c67fd3c-c9d1-4fde-8f59-c766abd87776');
INSERT INTO public.tb_menu_role (menu_id, role_id) VALUES ('898bc02b-95bb-4b48-8b66-3c6f7c1ad5f4', '6c67fd3c-c9d1-4fde-8f59-c766abd87776');
INSERT INTO public.tb_menu_role (menu_id, role_id) VALUES ('349bdd98-5b4b-4da7-99f4-3b0a3265bd62', '6c67fd3c-c9d1-4fde-8f59-c766abd87776');
INSERT INTO public.tb_menu_role (menu_id, role_id) VALUES ('3a3d1b76-c8cc-4cce-b06d-963d4af77e97', '0d6adaf9-20ab-412e-b336-17f78b0c13ff');
INSERT INTO public.tb_menu_role (menu_id, role_id) VALUES ('85471f7d-e6e0-4f12-a9bf-93d9c8888fb0', '52412b57-7f95-4174-9e8d-5469c63c6b18');
INSERT INTO public.tb_menu_role (menu_id, role_id) VALUES ('e427ec51-960c-4e4c-b4a3-085086d118a2', '52412b57-7f95-4174-9e8d-5469c63c6b18');
INSERT INTO public.tb_menu_role (menu_id, role_id) VALUES ('85471f7d-e6e0-4f12-a9bf-93d9c8888fb0', '6c67fd3c-c9d1-4fde-8f59-c766abd87776');
INSERT INTO public.tb_menu_role (menu_id, role_id) VALUES ('e427ec51-960c-4e4c-b4a3-085086d118a2', '6c67fd3c-c9d1-4fde-8f59-c766abd87776');
INSERT INTO public.tb_menu_role (menu_id, role_id) VALUES ('85471f7d-e6e0-4f12-a9bf-93d9c8888fb0', '0d6adaf9-20ab-412e-b336-17f78b0c13ff');
INSERT INTO public.tb_menu_role (menu_id, role_id) VALUES ('3a3d1b76-c8cc-4cce-b06d-963d4af77e97', '6c67fd3c-c9d1-4fde-8f59-c766abd87776');
INSERT INTO public.tb_menu_role (menu_id, role_id) VALUES ('17bf3124-79af-410c-b54b-631794d0a18f', '6c67fd3c-c9d1-4fde-8f59-c766abd87776');


------------------------------------------------------------------- USUARIO -------------------------------------------------------------------------------------------

INSERT INTO public.tb_user (id, inclusion_date, update_date, inclusion_user, update_user, name, login, email, status, is_password_temporary) VALUES ('c2279f18-92ca-47d9-ab98-ab627f2a3f9e', '2024-09-13 00:57:38.070195 +00:00', '2024-09-20 15:56:27.493060 +00:00', '', 'manager', 'paulo', 'teste', 'paulodlima2014@gmail.com', true, null);

------------------------------------------------------------------- EMPRESA -------------------------------------------------------------------------------------------

INSERT INTO public.tb_companies (id, inclusion_date, update_date, inclusion_user, update_user, name, cnpj, status) VALUES ('53730362-21ef-4a8e-b33f-7a22a1f5dabd', '2024-09-13 00:54:51.670593 +00:00', '2024-09-20 13:51:46.786466 +00:00', 'teste', 'manager', 'Supermercado Rio verde', '59863414000197', false);
INSERT INTO public.tb_companies (id, inclusion_date, update_date, inclusion_user, update_user, name, cnpj, status) VALUES ('5b12f8c1-7800-4ba2-beb3-1ec39c210727', '2024-09-20 01:09:14.440423 +00:00', '2024-09-20 15:36:11.845668 +00:00', 'manager', 'manager', 'Magalu Luiza', '42416750000199', true);

------------------------------------------------------------------- REEmBOLSO -------------------------------------------------------------------------------------------

INSERT INTO public.tb_reimbursement_requests (id, amount, company_id, employee_id, inclusion_date, inclusion_user, status, update_date, update_user, observation) VALUES ('c434e26e-3557-4a26-a70e-4bfb513cf59e', 123.00, 'f627f806-0c73-4a0a-a118-dc8cb4ce1c99', 'c2279f18-92ca-47d9-ab98-ab627f2a3f9e', '2024-09-15 15:24:07.900777', 'user', 1, '2024-09-20 12:56:55.785398', 'user', null);
INSERT INTO public.tb_reimbursement_requests (id, amount, company_id, employee_id, inclusion_date, inclusion_user, status, update_date, update_user, observation) VALUES ('003c507e-4758-43b9-bd84-ff02109b2908', 2900.00, 'f627f806-0c73-4a0a-a118-dc8cb4ce1c99', 'c2279f18-92ca-47d9-ab98-ab627f2a3f9e', '2024-09-14 22:35:30.120000', 'manager', 1, '2024-09-20 13:04:19.976211', 'user', null);
INSERT INTO public.tb_reimbursement_requests (id, amount, company_id, employee_id, inclusion_date, inclusion_user, status, update_date, update_user, observation) VALUES ('04f9f311-e510-41fb-af68-35df79181187', 236.00, 'f627f806-0c73-4a0a-a118-dc8cb4ce1c99', 'c2279f18-92ca-47d9-ab98-ab627f2a3f9e', '2024-09-13 09:30:36.000000', 'manager', 1, '2024-09-20 13:04:56.286767', 'user', null);
INSERT INTO public.tb_reimbursement_requests (id, amount, company_id, employee_id, inclusion_date, inclusion_user, status, update_date, update_user, observation) VALUES ('2fac62b9-5e6c-452d-b806-c1b0e27df92c', 150.00, '3148e728-c4b1-4ec0-9636-92740e0f60f4', 'c2279f18-92ca-47d9-ab98-ab627f2a3f9e', '2024-09-20 13:05:49.283601', 'user', 1, null, null, null);

------------------------------------------------------------------- NOTA FISCAL -------------------------------------------------------------------------------------------

INSERT INTO public.tb_invoices (id, inclusion_date, update_date, inclusion_user, update_user, reimbursement_request_id, file_url, s3_key) VALUES ('caf6ce4f-e289-4e22-adb6-79daa9138157', '2024-09-15 18:24:09.371982 +00:00', '2024-09-20 15:56:58.197881 +00:00', 'user', 'user', 'c434e26e-3557-4a26-a70e-4bfb513cf59e', 'https://mytccbuket.s3.amazonaws.com/Captura%20de%20tela%202024-09-08%20110130.png', 'Captura de tela 2024-09-08 110130.png');
INSERT INTO public.tb_invoices (id, inclusion_date, update_date, inclusion_user, update_user, reimbursement_request_id, file_url, s3_key) VALUES ('1589c568-00d9-45e6-8e18-264d335204ce', '2024-09-13 01:36:26.086990 +00:00', '2024-09-20 16:04:21.011010 +00:00', 'manager', 'user', '003c507e-4758-43b9-bd84-ff02109b2908', 'https://mytccbuket.s3.amazonaws.com/Captura%20de%20tela%202024-09-08%20110130.png', 'Captura de tela 2024-09-08 110130.png');
INSERT INTO public.tb_invoices (id, inclusion_date, update_date, inclusion_user, update_user, reimbursement_request_id, file_url, s3_key) VALUES ('2ed74efb-98f5-4bfd-a2d2-d3d52d288a37', '2024-09-13 01:36:26.086990 +00:00', '2024-09-20 16:04:57.439152 +00:00', 'manager', 'user', '04f9f311-e510-41fb-af68-35df79181187', 'https://mytccbuket.s3.amazonaws.com/Captura%20de%20tela%202024-09-08%20110130.png', 'Captura de tela 2024-09-08 110130.png');
INSERT INTO public.tb_invoices (id, inclusion_date, update_date, inclusion_user, update_user, reimbursement_request_id, file_url, s3_key) VALUES ('f21e99e6-bc18-4a98-ad7f-48221f66bbf6', '2024-09-20 16:05:50.014818 +00:00', null, 'user', null, '2fac62b9-5e6c-452d-b806-c1b0e27df92c', 'https://mytccbuket.s3.amazonaws.com/Captura%20de%20tela%202024-09-08%20114713.png', 'Captura de tela 2024-09-08 114713.png');

------------------------------------------------------------------- PEDIDO MEDICO -------------------------------------------------------------------------------------------
INSERT INTO public.tb_medical_requests (id, inclusion_date, update_date, inclusion_user, update_user, reimbursement_request_id, file_url, s3_key) VALUES ('575d7554-6989-4fe5-a0b1-bfdfcc6e6395', '2024-09-15 18:24:10.558111 +00:00', '2024-09-20 15:56:58.867249 +00:00', 'user', 'user', 'c434e26e-3557-4a26-a70e-4bfb513cf59e', 'https://mytccbuket.s3.amazonaws.com/Captura%20de%20tela%202024-09-08%20110130.png', 'Captura de tela 2024-09-08 110130.png');
INSERT INTO public.tb_medical_requests (id, inclusion_date, update_date, inclusion_user, update_user, reimbursement_request_id, file_url, s3_key) VALUES ('9d7ee42d-0e18-418c-a982-b8f0eb3dcead', '2024-09-13 01:36:57.571892 +00:00', '2024-09-20 16:04:21.534687 +00:00', 'manager', 'user', '003c507e-4758-43b9-bd84-ff02109b2908', 'https://mytccbuket.s3.amazonaws.com/Captura%20de%20tela%202024-09-08%20110130.png', 'Captura de tela 2024-09-08 110130.png');
INSERT INTO public.tb_medical_requests (id, inclusion_date, update_date, inclusion_user, update_user, reimbursement_request_id, file_url, s3_key) VALUES ('8638fef4-ed23-4e47-a10f-7cca04c2a8f2', '2024-09-13 01:36:57.571892 +00:00', '2024-09-20 16:04:57.796304 +00:00', 'manager', 'user', '04f9f311-e510-41fb-af68-35df79181187', 'https://mytccbuket.s3.amazonaws.com/Captura%20de%20tela%202024-09-08%20110130.png', 'Captura de tela 2024-09-08 110130.png');
INSERT INTO public.tb_medical_requests (id, inclusion_date, update_date, inclusion_user, update_user, reimbursement_request_id, file_url, s3_key) VALUES ('ee23e818-b8e9-4e57-be3c-395cedad6894', '2024-09-20 16:05:50.779936 +00:00', null, 'user', null, '2fac62b9-5e6c-452d-b806-c1b0e27df92c', 'https://mytccbuket.s3.amazonaws.com/Captura%20de%20tela%202024-09-16%20094438.png', 'Captura de tela 2024-09-16 094438.png');
