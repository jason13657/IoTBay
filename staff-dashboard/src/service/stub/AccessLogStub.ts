import { AccessLog } from "../../model/AccessLog";
import { IAccessLogService } from "../AccessLog";

export class AccessLogServiceStub implements IAccessLogService {
  private logs: AccessLog[] = [
    new AccessLog(1, 100, "Login", new Date("2025-04-23T12:00:00Z")),
    new AccessLog(2, 101, "Logout", new Date("2025-04-23T14:00:00Z")),
  ];

  async getLogs(): Promise<AccessLog[]> {
    return Promise.resolve(this.logs);
  }

  async getLogById(id: string): Promise<AccessLog> {
    const log = this.logs.find((l) => l.id === parseInt(id));
    if (!log) {
      throw new Error(`Log with ID ${id} not found.`);
    }
    return Promise.resolve(log);
  }

  async createLog(log: AccessLog): Promise<AccessLog> {
    const newId = this.logs.length + 1;
    const newLog = new AccessLog(newId, log.userId, log.action, log.timestamp);
    this.logs.push(newLog);
    return Promise.resolve(newLog);
  }

  async deleteLog(id: string): Promise<void> {
    const index = this.logs.findIndex((l) => l.id === parseInt(id));
    if (index === -1) {
      throw new Error(`Log with ID ${id} not found.`);
    }
    this.logs.splice(index, 1);
    return Promise.resolve();
  }
}
