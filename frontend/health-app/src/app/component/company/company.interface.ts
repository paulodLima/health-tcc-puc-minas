export interface CreateReimbursementCommand {
  cnpj: string;
  name: string;
  status: boolean;
}
export interface StatusCompanyCommand {
  id: string;
  updateUser: string;
}

export interface CompanyDto {
  id:string;
  inclusionDate:Date;
  cnpj:string;
  updateDate:Date;
  name:string;
  status:boolean;
  inclusionUser:string;
  updateUser:string;
}
