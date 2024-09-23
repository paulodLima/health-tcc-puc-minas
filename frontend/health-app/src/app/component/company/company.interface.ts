export interface CreateCompanyCommand {
  cnpj: string;
  name: string;
  status: boolean;
}
export interface UpdateCompanyCommand {
  id: string
  cnpj: string;
  name: string;
  status: boolean;
}
export interface StatusCompanyCommand {
  id: string;
  updateUser: string;
}
interface Cnpj {
  numero: string;
  numeroFormatado: string;
}
export interface CompanyDto {
  id:string;
  inclusionDate:Date;
  cnpj:Cnpj;
  updateDate:Date;
  name:string;
  status:boolean;
  inclusionUser:string;
  updateUser:string;
}
