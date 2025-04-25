import { AccessLog } from "../models/AccessLog";
import { HttpClient } from "../network/HttpClient";

export interface IAccessLogService {
  getLogs(): Promise<AccessLog[]>;
  getLogById(id: number): Promise<AccessLog>;
  getLogsByUserId(userId: number): Promise<AccessLog[]>;
  createLog(log: AccessLog): Promise<AccessLog>;
  deleteLog(id: number): void;
}

export class AccessLogService implements IAccessLogService {
  private httpClient: HttpClient;

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  async getLogs(): Promise<AccessLog[]> {
    const data = await this.httpClient.get<AccessLog[]>("/access-logs");
    return data.map(AccessLog.fromJson);
  }

  async getLogById(id: number): Promise<AccessLog> {
    const data = await this.httpClient.get<AccessLog>(`/access-logs?id=${id}`);
    return AccessLog.fromJson(data);
  }

  async getLogsByUserId(email: number): Promise<AccessLog[]> {
    const data = await this.httpClient.get<AccessLog[]>(`/access-logs?userId=${email}`);
    return data.map(AccessLog.fromJson);
  }

  async createLog(log: AccessLog): Promise<AccessLog> {
    const data = await this.httpClient.post<AccessLog>("/access-logs", log.toJson());
    return AccessLog.fromJson(data);
  }

  async deleteLog(id: number): Promise<void> {
    await this.httpClient.delete(`/access-logs?id=${id}`);
  }
}
