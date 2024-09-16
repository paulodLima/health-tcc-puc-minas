export interface CreateReimbursementCommand {
  user: string;
  company: string;
  medicalUrl: File;
  invoiceUrl: File;
  amount: number;
}
export interface UpdateStatus {
  observation: string;
  status: number;
}

export interface ReimbursementResponseDto {
  id:string;
  inclusionDate:Date;
  inclusionUser:string;
  updateDate:Date;
  updateUser:string;
  amount:number;
  status:string;
  medicalUrl:string;
  invoiceUrl:string;
  company:string;
  user:string,
  observation:string
}

export interface StatusInfo {
  label: string;
  severity: Severity;
}
type Severity = 'success' | 'secondary' | 'info' | 'warning' | 'danger' | 'contrast';
