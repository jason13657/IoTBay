import { AccessLog } from "../model/AccessLog";

export interface IAccessLogService {
  getLogs(): Promise<AccessLog[]>;
  getLogById(id: string): Promise<AccessLog>;
  createLog(log: AccessLog): Promise<AccessLog>;
  deleteLog(id: string): void;
}
