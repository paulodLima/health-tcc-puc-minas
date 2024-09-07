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
    document VARCHAR(255),
    status BOOLEAN
);

CREATE TABLE tb_reimbursement_requests (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    inclusion_date TIMESTAMPTZ DEFAULT now(),
    update_date TIMESTAMPTZ DEFAULT now(),
    inclusion_user VARCHAR(255),
    update_user VARCHAR(255),
    employee_id UUID NOT NULL,
    company_id UUID NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    status ENUM('pending', 'approved', 'rejected') DEFAULT 'pending',
    FOREIGN KEY (employee_id) REFERENCES TB_USER(id),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE tb_invoices (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    inclusion_date TIMESTAMPTZ DEFAULT now(),
    update_date TIMESTAMPTZ DEFAULT now(),
    inclusion_user VARCHAR(255),
    update_user VARCHAR(255),
    reimbursement_request_id UUID NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (reimbursement_request_id) REFERENCES reimbursement_requests(id)
);

CREATE TABLE tb_medical_requests (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    inclusion_date TIMESTAMPTZ DEFAULT now(),
    update_date TIMESTAMPTZ DEFAULT now(),
    inclusion_user VARCHAR(255),
    update_user VARCHAR(255),
    reimbursement_request_id UUID NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (reimbursement_request_id) REFERENCES reimbursement_requests(id)
);

