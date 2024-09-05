
-- Ensure the uuid-ossp extension is available
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create the table
CREATE TABLE tb_user (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    inclusion_date TIMESTAMPTZ,
    update_date TIMESTAMPTZ,
    inclusion_user VARCHAR(255),
    update_user VARCHAR(255),
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
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

